package agh.po.bomberman.model.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.Arena;
import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.board.Direction;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    private static class FakeArena implements Arena {
        private final Set<Cell> walls = new HashSet<>();

        void wallAt(int col, int row) {
            walls.add(new Cell(col, row));
        }

        @Override
        public boolean isPassable(Cell cell, Creature mover) {
            return !walls.contains(cell);
        }

        @Override
        public Cell playerCell() {
            return null;
        }
    }

    private Player player;
    private FakeArena arena;

    @BeforeEach
    void createPlayer() {
        player = new Player();
        player.respawnAt(new Cell(3, 3));
        arena = new FakeArena();
    }

    private void walk(Direction direction, int ticks) {
        for (int tick = 0; tick < ticks; tick++) {
            player.move(direction, arena);
        }
    }

    @Test
    void walksThreePixelsEveryFourTicks() {
        walk(Direction.RIGHT, 4);

        assertEquals(51, player.getX());
        assertEquals(48, player.getY());
    }

    @Test
    void walksInEveryDirection() {
        walk(Direction.LEFT, 4);
        assertEquals(45, player.getX());

        walk(Direction.RIGHT, 4);
        assertEquals(48, player.getX());

        walk(Direction.UP, 4);
        assertEquals(45, player.getY());

        walk(Direction.DOWN, 4);
        assertEquals(48, player.getY());
    }

    @Test
    void standsStillForNone() {
        walk(Direction.NONE, 10);

        assertEquals(48, player.getX());
        assertEquals(48, player.getY());
    }

    @Test
    void deadPlayerDoesNotMove() {
        player.die();
        walk(Direction.RIGHT, 10);

        assertEquals(48, player.getX());
    }

    @Test
    void doesNotWalkIntoAWall() {
        arena.wallAt(4, 3);
        walk(Direction.RIGHT, 20);

        assertEquals(48, player.getX());
        assertEquals(48, player.getY());
    }

    @Test
    void walksUpToAWallAndStopsAtTheEdgeOfTheCell() {
        arena.wallAt(5, 3);
        walk(Direction.RIGHT, 100);

        assertTrue(player.isFullyInside(new Cell(4, 3)));
    }

    @Test
    void canWalkAwayFromAWall() {
        arena.wallAt(4, 3);
        walk(Direction.RIGHT, 8);
        walk(Direction.LEFT, 4);

        assertEquals(45, player.getX());
    }

    @Test
    void keepsWalkingOnceItIsPartlyInTheNextCell() {
        walk(Direction.RIGHT, 4);
        arena.wallAt(4, 3);
        arena.wallAt(5, 3);
        walk(Direction.RIGHT, 40);

        assertTrue(player.isFullyInside(new Cell(4, 3)));
    }

    @Test
    void cornerAssistSlidesBackOntoTheLaneFromBelow() {
        player.grantSpeed();
        player.moveBy(0, 3);

        walk(Direction.RIGHT, 3);
        assertEquals(48, player.getX());
        assertEquals(48, player.getY());

        walk(Direction.RIGHT, 1);
        assertEquals(49, player.getX());
    }

    @Test
    void cornerAssistSlidesBackOntoTheLaneFromAbove() {
        player.grantSpeed();
        player.moveBy(0, -3);

        walk(Direction.RIGHT, 3);

        assertEquals(48, player.getX());
        assertEquals(48, player.getY());
    }

    @Test
    void cornerAssistWorksForVerticalWalkingToo() {
        player.grantSpeed();
        player.moveBy(2, 0);

        walk(Direction.DOWN, 2);
        assertEquals(48, player.getX());
        assertEquals(48, player.getY());

        walk(Direction.DOWN, 1);
        assertEquals(49, player.getY());
    }

    @Test
    void cornerAssistUsesTheNearerLane() {
        player.grantSpeed();
        player.moveBy(0, 10);
        arena.wallAt(4, 3);

        walk(Direction.RIGHT, 6);

        assertEquals(48, player.getX());
        assertEquals(64, player.getY());
    }

    @Test
    void noCornerAssistWhenTheWayAheadIsBlocked() {
        player.grantSpeed();
        player.moveBy(0, 3);
        arena.wallAt(4, 3);

        walk(Direction.RIGHT, 10);

        assertEquals(48, player.getX());
        assertEquals(51, player.getY());
    }

    @Test
    void speedUpGivesOnePixelEveryTick() {
        player.grantSpeed();
        walk(Direction.RIGHT, 4);

        assertEquals(52, player.getX());
    }

    @Test
    void respawnAtPlacesThePlayerAndRevivesHim() {
        player.die();
        player.respawnAt(new Cell(1, 1));

        assertTrue(player.isAlive());
        assertEquals(16, player.getX());
        assertEquals(16, player.getY());
    }

    @Test
    void passesNeitherSoftBlocksNorBombsYet() {
        assertFalse(player.passesSoftBlocks());
        assertFalse(player.passesBombs());
    }
}

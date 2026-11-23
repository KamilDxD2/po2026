package agh.po.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.creature.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StageTest {

    private Board board;
    private Player player;
    private Stage stage;
    private Input input;

    @BeforeEach
    void createStage() {
        board = new Board(Board.WIDTH, Board.HEIGHT);
        player = new Player();
        stage = new Stage(board, player);
        input = new Input();
    }

    private void hold(int mask, int ticks) {
        for (int tick = 0; tick < ticks; tick++) {
            input.update(mask);
            stage.update(input);
        }
    }

    @Test
    void playerStartsOnTheStartCell() {
        assertTrue(player.isFullyInside(Board.START));
        assertEquals(Board.START, stage.playerCell());
    }

    @Test
    void newStageRevivesADeadPlayer() {
        player.die();
        new Stage(board, player);

        assertTrue(player.isAlive());
        assertTrue(player.isFullyInside(Board.START));
    }

    @Test
    void freeCellsArePassable() {
        assertTrue(stage.isPassable(new Cell(1, 1), player));
        assertTrue(stage.isPassable(new Cell(2, 1), player));
        assertTrue(stage.isPassable(new Cell(1, 2), player));
    }

    @Test
    void borderPillarsAndCellsOutsideAreNotPassable() {
        assertFalse(stage.isPassable(new Cell(0, 1), player));
        assertFalse(stage.isPassable(new Cell(1, 0), player));
        assertFalse(stage.isPassable(new Cell(2, 2), player));
        assertFalse(stage.isPassable(new Cell(-1, 1), player));
    }

    @Test
    void noDirectionMeansNoMovement() {
        hold(0, 30);

        assertTrue(player.isFullyInside(Board.START));
    }

    @Test
    void borderStopsThePlayerAtTheStart() {
        hold(Input.UP, 30);
        hold(Input.LEFT, 30);

        assertTrue(player.isFullyInside(Board.START));
    }

    @Test
    void walksAlongTheTopCorridorToTheBorder() {
        hold(Input.RIGHT, 1000);

        assertTrue(player.isFullyInside(new Cell(Board.WIDTH - 2, 1)));
    }

    @Test
    void walksDownTheLeftCorridorToTheBorder() {
        hold(Input.DOWN, 1000);

        assertTrue(player.isFullyInside(new Cell(1, Board.HEIGHT - 2)));
    }

    @Test
    void pillarBlocksTurningRightBetweenTwoPillars() {
        hold(Input.DOWN, 22);
        assertTrue(player.isFullyInside(new Cell(1, 2)));

        hold(Input.RIGHT, 30);
        assertTrue(player.isFullyInside(new Cell(1, 2)));
    }

    @Test
    void cornerAssistSlidesIntoTheCorridorBelow() {
        hold(Input.RIGHT, 3);
        assertEquals(18, player.getX());

        hold(Input.DOWN, 6);
        assertEquals(16, player.getX());
        assertTrue(player.getY() > 16);
    }

    @Test
    void playerCellFollowsThePlayer() {
        hold(Input.RIGHT, 12);

        assertEquals(new Cell(2, 1), stage.playerCell());
    }

    @Test
    void getBoardReturnsTheStageBoard() {
        assertSame(board, stage.getBoard());
    }
}

package agh.po.bomberman.model.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.board.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatureTest {

    private static class TestCreature extends Creature {
        private final double speed;

        TestCreature(double speed) {
            this.speed = speed;
        }

        @Override
        public boolean passesSoftBlocks() {
            return false;
        }

        @Override
        public boolean passesBombs() {
            return false;
        }

        @Override
        protected double speed() {
            return speed;
        }
    }

    private static final Cell HOME = new Cell(3, 5);

    private Creature creature;

    @BeforeEach
    void createCreature() {
        creature = new TestCreature(0.75);
        creature.placeAt(HOME);
    }

    private static int pixelsAfter(Creature mover, int ticks) {
        int pixels = 0;
        for (int tick = 0; tick < ticks; tick++) {
            pixels += mover.stepThisTick();
        }
        return pixels;
    }

    @Test
    void startsAlive() {
        assertTrue(creature.isAlive());
    }

    @Test
    void dieAndReviveChangeAlive() {
        creature.die();
        assertFalse(creature.isAlive());

        creature.revive();
        assertTrue(creature.isAlive());
    }

    @Test
    void placeAtPutsTheCreatureOnTheCellInPixels() {
        assertEquals(48, creature.getX());
        assertEquals(80, creature.getY());
    }

    @Test
    void moveByShiftsThePosition() {
        creature.moveBy(2, -3);

        assertEquals(50, creature.getX());
        assertEquals(77, creature.getY());
    }

    @Test
    void centerCellIsTheCellUnderTheMiddleOfTheCreature() {
        assertEquals(HOME, creature.centerCell());

        creature.moveBy(7, 0);
        assertEquals(HOME, creature.centerCell());

        creature.moveBy(1, 0);
        assertEquals(new Cell(4, 5), creature.centerCell());
    }

    @Test
    void centerCellFollowsVerticalMovementToo() {
        creature.moveBy(0, -9);

        assertEquals(new Cell(3, 4), creature.centerCell());
    }

    @Test
    void isAtCellCenterOnlyWhenExactlyOnACell() {
        assertTrue(creature.isAtCellCenter());

        creature.moveBy(1, 0);
        assertFalse(creature.isAtCellCenter());

        creature.moveBy(15, 0);
        assertTrue(creature.isAtCellCenter());
    }

    @Test
    void isFullyInsideOnlyItsOwnCell() {
        assertTrue(creature.isFullyInside(HOME));
        assertFalse(creature.isFullyInside(new Cell(4, 5)));

        creature.moveBy(1, 0);
        assertFalse(creature.isFullyInside(HOME));
    }

    @Test
    void overlapsOnlyItsOwnCellWhenExactlyOnIt() {
        assertTrue(creature.overlaps(HOME));
        assertFalse(creature.overlaps(new Cell(4, 5)));
        assertFalse(creature.overlaps(new Cell(3, 6)));
    }

    @Test
    void overlapsBothCellsWhileBetweenThem() {
        creature.moveBy(1, 0);

        assertTrue(creature.overlaps(HOME));
        assertTrue(creature.overlaps(new Cell(4, 5)));
        assertFalse(creature.overlaps(new Cell(5, 5)));
    }

    @Test
    void hitboxCoversTheWholeCreature() {
        creature.moveBy(4, 2);

        assertEquals(new Hitbox(52, 82, 16), creature.hitbox());
    }

    @Test
    void speedOfThreeQuartersGivesThreePixelsEveryFourTicks() {
        assertEquals(0, creature.stepThisTick());
        assertEquals(1, creature.stepThisTick());
        assertEquals(1, creature.stepThisTick());
        assertEquals(1, creature.stepThisTick());

        assertEquals(3, pixelsAfter(creature, 4));
        assertEquals(60, pixelsAfter(creature, 80));
    }

    @Test
    void speedOfOneGivesOnePixelEveryTick() {
        Creature fast = new TestCreature(1.0);

        assertEquals(1, fast.stepThisTick());
        assertEquals(1, fast.stepThisTick());
        assertEquals(60, pixelsAfter(fast, 60));
    }

    @Test
    void speedOfOneHalfGivesOnePixelEverySecondTick() {
        Creature slow = new TestCreature(0.5);

        assertEquals(0, slow.stepThisTick());
        assertEquals(1, slow.stepThisTick());
        assertEquals(30, pixelsAfter(slow, 60));
    }

    @Test
    void neverMovesMoreThanOnePixelInATick() {
        for (int tick = 0; tick < 200; tick++) {
            assertTrue(creature.stepThisTick() <= 1);
        }
    }

    @Test
    void placeAtClearsTheSubPixelRemainder() {
        creature.stepThisTick();
        creature.placeAt(HOME);

        assertEquals(0, creature.stepThisTick());
        assertEquals(1, creature.stepThisTick());
    }
}

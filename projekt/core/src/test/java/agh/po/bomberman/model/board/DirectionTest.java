package agh.po.bomberman.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void upAndDownAreOpposites() {
        assertEquals(Direction.DOWN, Direction.UP.opposite());
        assertEquals(Direction.UP, Direction.DOWN.opposite());
    }

    @Test
    void leftAndRightAreOpposites() {
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }

    @Test
    void noneIsItsOwnOpposite() {
        assertEquals(Direction.NONE, Direction.NONE.opposite());
    }

    @Test
    void oppositeOfOppositeIsTheSameDirection() {
        for (Direction direction : Direction.values()) {
            assertEquals(direction, direction.opposite().opposite());
        }
    }

    @Test
    void upMovesToASmallerRow() {
        assertEquals(0, Direction.UP.dx());
        assertEquals(-1, Direction.UP.dy());
    }

    @Test
    void rightMovesToABiggerColumn() {
        assertEquals(1, Direction.RIGHT.dx());
        assertEquals(0, Direction.RIGHT.dy());
    }

    @Test
    void noneDoesNotMove() {
        assertEquals(0, Direction.NONE.dx());
        assertEquals(0, Direction.NONE.dy());
    }

    @Test
    void oppositeStepsCancelOut() {
        for (Direction direction : Direction.values()) {
            assertEquals(0, direction.dx() + direction.opposite().dx());
            assertEquals(0, direction.dy() + direction.opposite().dy());
        }
    }
}

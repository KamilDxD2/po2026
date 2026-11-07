package agh.po.bomberman.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CellTest {

    @Test
    void cellsWithTheSameColumnAndRowAreEqual() {
        Cell first = new Cell(3, 5);
        Cell second = new Cell(3, 5);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void cellsWithDifferentPositionsAreNotEqual() {
        assertNotEquals(new Cell(3, 5), new Cell(5, 3));
        assertNotEquals(new Cell(3, 5), new Cell(3, 6));
    }

    @Test
    void setKeepsEqualCellsOnlyOnce() {
        Set<Cell> cells = new HashSet<>();
        cells.add(new Cell(1, 1));
        cells.add(new Cell(1, 1));
        cells.add(new Cell(2, 1));

        assertEquals(2, cells.size());
        assertTrue(cells.contains(new Cell(1, 1)));
    }

    @Test
    void mapFindsAValueByAnEqualCell() {
        Map<Cell, Integer> ticksLeft = new HashMap<>();
        ticksLeft.put(new Cell(4, 7), 30);

        assertEquals(30, ticksLeft.get(new Cell(4, 7)));
    }

    @Test
    void accessorsReturnColumnAndRow() {
        Cell cell = new Cell(3, 5);

        assertEquals(3, cell.col());
        assertEquals(5, cell.row());
    }

    @Test
    void nextMovesOneCellInEachDirection() {
        Cell start = new Cell(5, 5);

        assertEquals(new Cell(5, 4), start.next(Direction.UP));
        assertEquals(new Cell(5, 6), start.next(Direction.DOWN));
        assertEquals(new Cell(4, 5), start.next(Direction.LEFT));
        assertEquals(new Cell(6, 5), start.next(Direction.RIGHT));
    }

    @Test
    void nextWithNoneStaysOnTheSameCell() {
        Cell start = new Cell(5, 5);

        assertEquals(start, start.next(Direction.NONE));
    }

    @Test
    void stepAndStepBackReturnToTheStart() {
        Cell start = new Cell(5, 5);

        for (Direction direction : Direction.values()) {
            assertEquals(start, start.next(direction).next(direction.opposite()));
        }
    }

    @Test
    void nextDoesNotChangeTheOriginalCell() {
        Cell start = new Cell(5, 5);
        start.next(Direction.RIGHT);

        assertEquals(new Cell(5, 5), start);
    }
}

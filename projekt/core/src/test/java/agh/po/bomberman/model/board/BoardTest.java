package agh.po.bomberman.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach
    void createBoard() {
        board = new Board(Board.WIDTH, Board.HEIGHT);
    }

    @Test
    void hasTheRequestedSize() {
        assertEquals(31, board.getWidth());
        assertEquals(13, board.getHeight());
    }

    @Test
    void borderIsHard() {
        for (int col = 0; col < Board.WIDTH; col++) {
            assertEquals(Tile.HARD, board.tileAt(new Cell(col, 0)));
            assertEquals(Tile.HARD, board.tileAt(new Cell(col, Board.HEIGHT - 1)));
        }
        for (int row = 0; row < Board.HEIGHT; row++) {
            assertEquals(Tile.HARD, board.tileAt(new Cell(0, row)));
            assertEquals(Tile.HARD, board.tileAt(new Cell(Board.WIDTH - 1, row)));
        }
    }

    @Test
    void cellsWithEvenColumnAndEvenRowAreHard() {
        assertEquals(Tile.HARD, board.tileAt(new Cell(2, 2)));
        assertEquals(Tile.HARD, board.tileAt(new Cell(4, 6)));
        assertEquals(Tile.HARD, board.tileAt(new Cell(28, 10)));
    }

    @Test
    void otherInnerCellsAreFree() {
        assertEquals(Tile.FREE, board.tileAt(new Cell(1, 2)));
        assertEquals(Tile.FREE, board.tileAt(new Cell(2, 1)));
        assertEquals(Tile.FREE, board.tileAt(new Cell(3, 3)));
        assertEquals(Tile.FREE, board.tileAt(new Cell(29, 11)));
    }

    @Test
    void startAreaIsFree() {
        assertEquals(Tile.FREE, board.tileAt(Board.START));
        assertEquals(Tile.FREE, board.tileAt(Board.START.next(Direction.RIGHT)));
        assertEquals(Tile.FREE, board.tileAt(Board.START.next(Direction.DOWN)));
    }

    @Test
    void hasExactly154HardAnd249FreeCells() {
        int hard = 0;
        int free = 0;
        for (int row = 0; row < Board.HEIGHT; row++) {
            for (int col = 0; col < Board.WIDTH; col++) {
                Tile tile = board.tileAt(new Cell(col, row));
                if (tile == Tile.HARD) {
                    hard++;
                } else if (tile == Tile.FREE) {
                    free++;
                }
            }
        }

        assertEquals(154, hard);
        assertEquals(249, free);
    }

    @Test
    void cellsOutsideTheBoardAreHard() {
        assertEquals(Tile.HARD, board.tileAt(new Cell(-1, 5)));
        assertEquals(Tile.HARD, board.tileAt(new Cell(5, -1)));
        assertEquals(Tile.HARD, board.tileAt(new Cell(Board.WIDTH, 5)));
        assertEquals(Tile.HARD, board.tileAt(new Cell(5, Board.HEIGHT)));
    }

    @Test
    void smallBoardHasOnlyBorderAndOneFreeCell() {
        Board small = new Board(3, 3);

        assertEquals(Tile.FREE, small.tileAt(new Cell(1, 1)));
        assertEquals(Tile.HARD, small.tileAt(new Cell(0, 0)));
        assertEquals(Tile.HARD, small.tileAt(new Cell(2, 1)));
    }

    @Test
    void rejectsBoardsSmallerThan3x3() {
        assertThrows(IllegalArgumentException.class, () -> new Board(2, 5));
        assertThrows(IllegalArgumentException.class, () -> new Board(5, 2));
    }
}

package agh.po.bomberman.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.board.Board;
import org.junit.jupiter.api.Test;

class StateJsonTest {

    private static String tilesOf(String json) {
        int start = json.indexOf("\"tiles\":\"") + "\"tiles\":\"".length();
        int end = json.indexOf('"', start);
        return json.substring(start, end);
    }

    @Test
    void smallBoardGivesExactJson() {
        String json = StateJson.of(new Board(3, 3));

        assertEquals("{\"width\":3,\"height\":3,\"cellSize\":16,\"tiles\":\"HHHHFHHHH\"}", json);
    }

    @Test
    void headerHasSizeAndCellSize() {
        String json = StateJson.of(new Board(Board.WIDTH, Board.HEIGHT));

        assertTrue(json.startsWith("{\"width\":31,\"height\":13,\"cellSize\":16,\"tiles\":\""));
        assertTrue(json.endsWith("\"}"));
    }

    @Test
    void tilesHaveOneLetterPerCell() {
        String tiles = tilesOf(StateJson.of(new Board(Board.WIDTH, Board.HEIGHT)));

        assertEquals(Board.WIDTH * Board.HEIGHT, tiles.length());
    }

    @Test
    void tilesAreWrittenRowByRow() {
        String tiles = tilesOf(StateJson.of(new Board(Board.WIDTH, Board.HEIGHT)));

        assertEquals("H".repeat(31), tiles.substring(0, 31));
        assertEquals("H" + "F".repeat(29) + "H", tiles.substring(31, 62));
        assertEquals("HF" + "HF".repeat(14) + "H", tiles.substring(62, 93));
    }

    @Test
    void bridgeReturnsTheBoardAsJson() {
        assertEquals(StateJson.of(new Board(Board.WIDTH, Board.HEIGHT)), GameBridge.getState());
    }
}

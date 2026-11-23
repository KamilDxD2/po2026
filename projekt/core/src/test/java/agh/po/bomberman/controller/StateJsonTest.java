package agh.po.bomberman.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.Game;
import agh.po.bomberman.model.Input;
import agh.po.bomberman.model.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateJsonTest {

    private Game game;

    @BeforeEach
    void createGame() {
        game = new Game();
    }

    private static String tilesOf(String json) {
        int start = json.indexOf("\"tiles\":\"") + "\"tiles\":\"".length();
        int end = json.indexOf('"', start);
        return json.substring(start, end);
    }

    @Test
    void startsWithStateAndBoardSize() {
        String json = StateJson.of(game);

        assertTrue(json.startsWith(
                "{\"state\":\"PLAYING\",\"width\":31,\"height\":13,\"cellSize\":16,\"tiles\":\""));
    }

    @Test
    void endsWithThePlayerOnTheStartCell() {
        String json = StateJson.of(game);

        assertTrue(json.endsWith("\",\"player\":{\"x\":16,\"y\":16}}"));
    }

    @Test
    void playerPositionFollowsTheGame() {
        Input input = new Input();
        for (int tick = 0; tick < 4; tick++) {
            input.update(Input.RIGHT);
            game.update(input);
        }

        assertTrue(StateJson.of(game).endsWith("\"player\":{\"x\":19,\"y\":16}}"));
    }

    @Test
    void tilesHaveOneLetterPerCell() {
        String tiles = tilesOf(StateJson.of(game));

        assertEquals(Board.WIDTH * Board.HEIGHT, tiles.length());
    }

    @Test
    void tilesAreWrittenRowByRow() {
        String tiles = tilesOf(StateJson.of(game));

        assertEquals("H".repeat(31), tiles.substring(0, 31));
        assertEquals("H" + "F".repeat(29) + "H", tiles.substring(31, 62));
        assertEquals("HF" + "HF".repeat(14) + "H", tiles.substring(62, 93));
    }
}

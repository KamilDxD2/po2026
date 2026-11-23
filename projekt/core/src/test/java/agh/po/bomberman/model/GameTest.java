package agh.po.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.creature.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;
    private Input input;

    @BeforeEach
    void createGame() {
        game = new Game();
        input = new Input();
    }

    private void hold(int mask, int ticks) {
        for (int tick = 0; tick < ticks; tick++) {
            input.update(mask);
            game.update(input);
        }
    }

    @Test
    void newGameIsPlaying() {
        assertEquals(GameState.PLAYING, game.getState());
    }

    @Test
    void playerStartsAliveOnTheStartCell() {
        Player player = game.getPlayer();

        assertTrue(player.isAlive());
        assertTrue(player.isFullyInside(Board.START));
    }

    @Test
    void stageHasANormalSizeBoard() {
        Board board = game.getStage().getBoard();

        assertEquals(Board.WIDTH, board.getWidth());
        assertEquals(Board.HEIGHT, board.getHeight());
    }

    @Test
    void updateMovesThePlayer() {
        hold(Input.RIGHT, 4);

        assertEquals(19, game.getPlayer().getX());
        assertEquals(16, game.getPlayer().getY());
    }

    @Test
    void stageMovesTheSamePlayerTheGameHolds() {
        hold(Input.DOWN, 40);

        assertEquals(game.getPlayer().centerCell(), game.getStage().playerCell());
        assertTrue(game.getPlayer().getY() > 16);
    }

    @Test
    void sameStageAndPlayerForTheWholeGame() {
        Stage stage = game.getStage();
        Player player = game.getPlayer();

        hold(Input.RIGHT, 10);

        assertSame(stage, game.getStage());
        assertSame(player, game.getPlayer());
    }
}

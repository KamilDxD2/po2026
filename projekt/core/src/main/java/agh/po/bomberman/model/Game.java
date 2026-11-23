package agh.po.bomberman.model;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.creature.Player;

public class Game {
    private final Player player;
    private final Stage stage;
    private GameState state;

    public Game() {
        player = new Player();
        stage = new Stage(new Board(Board.WIDTH, Board.HEIGHT), player);
        state = GameState.PLAYING;
    }

    public void update(Input input) {
        if (state == GameState.PLAYING) {
            stage.update(input);
        }
    }

    public GameState getState() {
        return state;
    }

    public Player getPlayer() {
        return player;
    }

    public Stage getStage() {
        return stage;
    }
}

package agh.po.bomberman.controller;

import agh.po.bomberman.model.Game;
import agh.po.bomberman.model.Input;
import org.teavm.jso.JSExport;

public class GameBridge {

    private static Game game;
    private static final Input input = new Input();

    @JSExport
    public static void startNewGame() {
        game = new Game();
    }

    @JSExport
    public static void tick(int inputMask) {
        if (game == null) {
            return;
        }
        input.update(inputMask);
        game.update(input);
    }

    @JSExport
    public static String getState() {
        if (game == null) {
            return "null";
        }
        return StateJson.of(game);
    }

    public static void main(String[] args) {

    }
}

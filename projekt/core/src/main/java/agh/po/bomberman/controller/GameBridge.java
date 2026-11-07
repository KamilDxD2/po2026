package agh.po.bomberman.controller;

import agh.po.bomberman.model.board.Board;
import org.teavm.jso.JSExport;

public class GameBridge {

    private static final Board board = new Board(Board.WIDTH, Board.HEIGHT);

    @JSExport
    public static int pingJavaEngine() {
        return 2026;
    }

    @JSExport
    public static String getState() {
        return StateJson.of(board);
    }

    public static void main(String[] args) {

    }
}

package agh.po.bomberman.controller;

import org.teavm.jso.JSExport;

public class GameBridge {

    // Connection check for the view; replaced by the real bridge methods
    // (loadStages, startNewGame, tick, getState, ...) as the model grows.
    @JSExport
    public static int pingJavaEngine() {
        return 2026;
    }

    // TeaVM needs an entry point; the view calls the @JSExport methods instead.
    public static void main(String[] args) {

    }
}

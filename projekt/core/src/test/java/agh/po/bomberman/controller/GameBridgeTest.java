package agh.po.bomberman.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBridgeTest {

    @BeforeEach
    void startGame() {
        GameBridge.startNewGame();
    }

    private static void tick(int mask, int ticks) {
        for (int tick = 0; tick < ticks; tick++) {
            GameBridge.tick(mask);
        }
    }

    @Test
    void newGameIsPlayingWithThePlayerOnTheStartCell() {
        String state = GameBridge.getState();

        assertTrue(state.startsWith("{\"state\":\"PLAYING\""));
        assertTrue(state.endsWith("\"player\":{\"x\":16,\"y\":16}}"));
    }

    @Test
    void tickMovesThePlayer() {
        tick(Input.RIGHT, 4);

        assertTrue(GameBridge.getState().endsWith("\"player\":{\"x\":19,\"y\":16}}"));
    }

    @Test
    void tickWithNoKeysKeepsThePlayerStill() {
        tick(0, 20);

        assertTrue(GameBridge.getState().endsWith("\"player\":{\"x\":16,\"y\":16}}"));
    }

    @Test
    void startNewGameResetsThePlayer() {
        tick(Input.DOWN, 40);
        GameBridge.startNewGame();

        assertTrue(GameBridge.getState().endsWith("\"player\":{\"x\":16,\"y\":16}}"));
    }
}

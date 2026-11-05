package agh.po.bomberman.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Toolchain check: proves JUnit runs. Replace with real model tests.
class GameBridgeTest {

    @Test
    void pingReturnsTheYear() {
        assertEquals(2026, GameBridge.pingJavaEngine());
    }
}

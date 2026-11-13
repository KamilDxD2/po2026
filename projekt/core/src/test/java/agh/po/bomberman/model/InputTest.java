package agh.po.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agh.po.bomberman.model.board.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputTest {

    private Input input;

    @BeforeEach
    void createInput() {
        input = new Input();
    }

    @Test
    void startsWithNothingPressed() {
        assertEquals(Direction.NONE, input.getDirection());
        assertFalse(input.bombPressed());
        assertFalse(input.detonatePressed());
    }

    @Test
    void eachDirectionKeyGivesItsDirection() {
        input.update(Input.UP);
        assertEquals(Direction.UP, input.getDirection());

        input.update(Input.DOWN);
        assertEquals(Direction.DOWN, input.getDirection());

        input.update(Input.LEFT);
        assertEquals(Direction.LEFT, input.getDirection());

        input.update(Input.RIGHT);
        assertEquals(Direction.RIGHT, input.getDirection());
    }

    @Test
    void releasingEverythingGivesNoDirection() {
        input.update(Input.LEFT);
        input.update(0);

        assertEquals(Direction.NONE, input.getDirection());
    }

    @Test
    void newerDirectionWins() {
        input.update(Input.RIGHT);
        input.update(Input.RIGHT | Input.UP);

        assertEquals(Direction.UP, input.getDirection());
    }

    @Test
    void newerDirectionKeepsWinningWhileBothAreHeld() {
        input.update(Input.RIGHT);
        input.update(Input.RIGHT | Input.UP);
        input.update(Input.RIGHT | Input.UP);
        input.update(Input.RIGHT | Input.UP);

        assertEquals(Direction.UP, input.getDirection());
    }

    @Test
    void releasingTheNewerDirectionReturnsToTheOlderOne() {
        input.update(Input.RIGHT);
        input.update(Input.RIGHT | Input.UP);
        input.update(Input.RIGHT);

        assertEquals(Direction.RIGHT, input.getDirection());
    }

    @Test
    void releasingTheOlderDirectionKeepsTheNewerOne() {
        input.update(Input.RIGHT);
        input.update(Input.RIGHT | Input.UP);
        input.update(Input.UP);

        assertEquals(Direction.UP, input.getDirection());
    }

    @Test
    void bombKeysDoNotChangeTheDirection() {
        input.update(Input.LEFT);
        input.update(Input.LEFT | Input.BOMB | Input.DETONATE);

        assertEquals(Direction.LEFT, input.getDirection());
    }

    @Test
    void bombIsPressedOnlyInTheTickTheKeyGoesDown() {
        input.update(Input.BOMB);
        assertTrue(input.bombPressed());

        input.update(Input.BOMB);
        assertFalse(input.bombPressed());
    }

    @Test
    void bombCanBePressedAgainAfterRelease() {
        input.update(Input.BOMB);
        input.update(0);
        input.update(Input.BOMB);

        assertTrue(input.bombPressed());
    }

    @Test
    void detonateIsPressedOnlyInTheTickTheKeyGoesDown() {
        input.update(Input.DETONATE);
        assertTrue(input.detonatePressed());

        input.update(Input.DETONATE);
        assertFalse(input.detonatePressed());
    }

    @Test
    void bombAndDetonateAreSeparateKeys() {
        input.update(Input.BOMB);

        assertTrue(input.bombPressed());
        assertFalse(input.detonatePressed());
    }
}

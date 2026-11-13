package agh.po.bomberman.model.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HitboxTest {

    @Test
    void boxesSharingAnAreaOverlap() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox second = new Hitbox(10, 10, 16);

        assertTrue(first.overlaps(second));
    }

    @Test
    void boxOverlapsItself() {
        Hitbox box = new Hitbox(32, 48, 16);

        assertTrue(box.overlaps(box));
    }

    @Test
    void boxesOnePixelIntoEachOtherOverlap() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox second = new Hitbox(15, 0, 16);

        assertTrue(first.overlaps(second));
    }

    @Test
    void boxesTouchingSideBySideDoNotOverlap() {
        Hitbox left = new Hitbox(0, 0, 16);
        Hitbox right = new Hitbox(16, 0, 16);

        assertFalse(left.overlaps(right));
    }

    @Test
    void boxesTouchingTopToBottomDoNotOverlap() {
        Hitbox top = new Hitbox(0, 0, 16);
        Hitbox bottom = new Hitbox(0, 16, 16);

        assertFalse(top.overlaps(bottom));
    }

    @Test
    void boxesTouchingOnlyAtACornerDoNotOverlap() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox second = new Hitbox(16, 16, 16);

        assertFalse(first.overlaps(second));
    }

    @Test
    void boxesFarApartDoNotOverlap() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox second = new Hitbox(100, 0, 16);

        assertFalse(first.overlaps(second));
    }

    @Test
    void boxesInTheSameColumnButDifferentRowsDoNotOverlap() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox second = new Hitbox(0, 40, 16);

        assertFalse(first.overlaps(second));
    }

    @Test
    void smallBoxInsideALargeOneOverlaps() {
        Hitbox large = new Hitbox(0, 0, 16);
        Hitbox small = new Hitbox(4, 4, 8);

        assertTrue(large.overlaps(small));
        assertTrue(small.overlaps(large));
    }

    @Test
    void overlapIsTheSameInBothDirections() {
        Hitbox first = new Hitbox(0, 0, 16);
        Hitbox near = new Hitbox(10, 5, 16);
        Hitbox far = new Hitbox(16, 5, 16);

        assertEquals(first.overlaps(near), near.overlaps(first));
        assertEquals(first.overlaps(far), far.overlaps(first));
    }

    @Test
    void boxesWithTheSameValuesAreEqual() {
        assertEquals(new Hitbox(3, 5, 16), new Hitbox(3, 5, 16));
    }

    @Test
    void rejectsSizeThatIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> new Hitbox(0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Hitbox(0, 0, -4));
    }
}

package agh.po.bomberman.model.creature;

public record Hitbox(int x, int y, int size) {

    public Hitbox {
        if (size <= 0) {
            throw new IllegalArgumentException("Hitbox size must be positive, got " + size);
        }
    }

    public boolean overlaps(Hitbox other) {
        return x < other.x + other.size
                && other.x < x + size
                && y < other.y + other.size
                && other.y < y + size;
    }
}

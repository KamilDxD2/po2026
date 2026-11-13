package agh.po.bomberman.model;

import agh.po.bomberman.model.board.Direction;

public class Input {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int BOMB = 16;
    public static final int DETONATE = 32;

    private static final Direction[] DIRECTIONS = {
            Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    };

    private int mask;
    private int previousMask;
    private Direction direction = Direction.NONE;

    public void update(int mask) {
        previousMask = this.mask;
        this.mask = mask;
        direction = chooseDirection();
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean bombPressed() {
        return wentDown(BOMB);
    }

    public boolean detonatePressed() {
        return wentDown(DETONATE);
    }

    private Direction chooseDirection() {
        for (Direction candidate : DIRECTIONS) {
            if (wentDown(bitOf(candidate))) {
                return candidate;
            }
        }
        if (isHeld(bitOf(direction))) {
            return direction;
        }
        for (Direction candidate : DIRECTIONS) {
            if (isHeld(bitOf(candidate))) {
                return candidate;
            }
        }
        return Direction.NONE;
    }

    private boolean isHeld(int bit) {
        return (mask & bit) != 0;
    }

    private boolean wentDown(int bit) {
        return (mask & bit) != 0 && (previousMask & bit) == 0;
    }

    private static int bitOf(Direction direction) {
        return switch (direction) {
            case UP -> Input.UP;
            case DOWN -> Input.DOWN;
            case LEFT -> Input.LEFT;
            case RIGHT -> Input.RIGHT;
            case NONE -> 0;
        };
    }
}

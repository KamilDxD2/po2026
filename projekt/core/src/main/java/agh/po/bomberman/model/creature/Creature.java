package agh.po.bomberman.model.creature;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.board.Cell;

public abstract class Creature {
    private int x;
    private int y;
    private double subPixel;
    private boolean alive = true;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell centerCell() {
        int half = Board.CELL_SIZE / 2;
        return new Cell((x + half) / Board.CELL_SIZE, (y + half) / Board.CELL_SIZE);
    }

    public boolean isAtCellCenter() {
        return x % Board.CELL_SIZE == 0 && y % Board.CELL_SIZE == 0;
    }

    public boolean isFullyInside(Cell cell) {
        return x == cell.col() * Board.CELL_SIZE && y == cell.row() * Board.CELL_SIZE;
    }

    public boolean overlaps(Cell cell) {
        Hitbox cellBox = new Hitbox(cell.col() * Board.CELL_SIZE, cell.row() * Board.CELL_SIZE, Board.CELL_SIZE);
        return hitbox().overlaps(cellBox);
    }

    public Hitbox hitbox() {
        return new Hitbox(x, y, Board.CELL_SIZE);
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public abstract boolean passesSoftBlocks();

    public abstract boolean passesBombs();

    protected abstract double speed();

    protected int stepThisTick() {
        subPixel += speed();
        if (subPixel >= 1) {
            subPixel -= 1;
            return 1;
        }
        return 0;
    }

    protected void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    protected void placeAt(Cell cell) {
        x = cell.col() * Board.CELL_SIZE;
        y = cell.row() * Board.CELL_SIZE;
        subPixel = 0;
    }

    protected void revive() {
        alive = true;
    }
}

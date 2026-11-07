package agh.po.bomberman.model.board;

public record Cell(int col, int row) {

    public Cell next(Direction direction) {
        return new Cell(col + direction.dx(), row + direction.dy());
    }
}

package agh.po.bomberman.model.board;

public class Board {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int CELL_SIZE = 16;
    public static final Cell START = new Cell(1, 1);

    private final Tile[][] tiles;

    public Board(int width, int height) {
        if (width < 3 || height < 3) {
            throw new IllegalArgumentException("Board must be at least 3 x 3, got " + width + " x " + height);
        }
        tiles = new Tile[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = isHardBlock(col, row, width, height) ? Tile.HARD : Tile.FREE;
            }
        }
    }

    public Tile tileAt(Cell cell) {
        if (!isInside(cell)) {
            return Tile.HARD;
        }
        return tiles[cell.row()][cell.col()];
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    private boolean isInside(Cell cell) {
        return cell.col() >= 0 && cell.col() < getWidth()
                && cell.row() >= 0 && cell.row() < getHeight();
    }

    private static boolean isHardBlock(int col, int row, int width, int height) {
        boolean border = col == 0 || row == 0 || col == width - 1 || row == height - 1;
        boolean pillar = col % 2 == 0 && row % 2 == 0;
        return border || pillar;
    }
}

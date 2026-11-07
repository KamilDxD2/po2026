package agh.po.bomberman.controller;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.board.Tile;

final class StateJson {

    private StateJson() {
    }

    static String of(Board board) {
        StringBuilder json = new StringBuilder();
        json.append("{\"width\":").append(board.getWidth())
                .append(",\"height\":").append(board.getHeight())
                .append(",\"cellSize\":").append(Board.CELL_SIZE)
                .append(",\"tiles\":\"");
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                json.append(letter(board.tileAt(new Cell(col, row))));
            }
        }
        json.append("\"}");
        return json.toString();
    }

    private static char letter(Tile tile) {
        return switch (tile) {
            case FREE -> 'F';
            case HARD -> 'H';
            case SOFT -> 'S';
            case CRUMBLING -> 'C';
        };
    }
}

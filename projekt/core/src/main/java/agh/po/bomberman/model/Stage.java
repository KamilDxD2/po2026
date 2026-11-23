package agh.po.bomberman.model;

import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.creature.Creature;
import agh.po.bomberman.model.creature.Player;

public class Stage implements Arena {
    private final Board board;
    private final Player player;

    public Stage(Board board, Player player) {
        this.board = board;
        this.player = player;
        player.respawnAt(Board.START);
    }

    public void update(Input input) {
        updatePlayer(input);
    }

    @Override
    public boolean isPassable(Cell cell, Creature mover) {
        return switch (board.tileAt(cell)) {
            case FREE -> true;
            case HARD -> false;
            case SOFT, CRUMBLING -> mover.passesSoftBlocks();
        };
    }

    @Override
    public Cell playerCell() {
        return player.centerCell();
    }

    public Board getBoard() {
        return board;
    }

    private void updatePlayer(Input input) {
        player.move(input.getDirection(), this);
    }
}

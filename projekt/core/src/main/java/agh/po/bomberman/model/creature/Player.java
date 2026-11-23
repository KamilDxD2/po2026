package agh.po.bomberman.model.creature;

import agh.po.bomberman.model.Arena;
import agh.po.bomberman.model.board.Board;
import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.board.Direction;

public class Player extends Creature {
    private static final double NORMAL_SPEED = 0.75;
    private static final double FAST_SPEED = 1.0;

    private boolean speedUp;

    public void move(Direction direction, Arena arena) {
        if (!isAlive() || direction == Direction.NONE) {
            return;
        }
        if (stepThisTick() == 0) {
            return;
        }

        boolean horizontal = direction.dx() != 0;
        int along = horizontal ? getX() : getY();
        int across = horizontal ? getY() : getX();
        int lane = (across + Board.CELL_SIZE / 2) / Board.CELL_SIZE;
        int offLane = across - lane * Board.CELL_SIZE;

        if (along % Board.CELL_SIZE == 0) {
            int alongCell = along / Board.CELL_SIZE;
            Cell here = horizontal ? new Cell(alongCell, lane) : new Cell(lane, alongCell);
            if (!arena.isPassable(here.next(direction), this)) {
                return;
            }
        }

        if (offLane == 0) {
            moveBy(direction.dx(), direction.dy());
        } else {
            int towardLane = -Integer.signum(offLane);
            if (horizontal) {
                moveBy(0, towardLane);
            } else {
                moveBy(towardLane, 0);
            }
        }
    }

    public void respawnAt(Cell cell) {
        placeAt(cell);
        revive();
    }

    public void grantSpeed() {
        speedUp = true;
    }

    @Override
    public boolean passesSoftBlocks() {
        return false;
    }

    @Override
    public boolean passesBombs() {
        return false;
    }

    @Override
    protected double speed() {
        return speedUp ? FAST_SPEED : NORMAL_SPEED;
    }
}

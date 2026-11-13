package agh.po.bomberman.model;

import agh.po.bomberman.model.board.Cell;
import agh.po.bomberman.model.creature.Creature;

public interface Arena {

    boolean isPassable(Cell cell, Creature mover);

    Cell playerCell();
}

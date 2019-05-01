package com.uniceub.battleship.models.boards;

import com.uniceub.battleship.enums.GameStates;

import java.util.List;
import java.util.stream.Collectors;

public class FiringBoard extends GameBoard {

    public List<Coordinates> getOpenRandomPanels() {
        return this.panels.stream().filter(x -> x.getType() == GameStates.Empty && x.isRandomAvailable()).map(x -> x.getCoordinates()).collect(Collectors.toList());
    }

}

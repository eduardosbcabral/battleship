package com.uniceub.battleship.models.boards;

import com.uniceub.battleship.enums.GameStates;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiringBoard extends GameBoard {

    public List<Coordinates> getOpenRandomPanels() {
        return this.panels.stream().filter(x -> x.getType() == GameStates.Empty && x.isRandomAvailable()).map(x -> x.getCoordinates()).collect(Collectors.toList());
    }

    public List<Coordinates> getHitNeighbors() {
        ArrayList<Panel> panels = new ArrayList<Panel>();

        List<Panel> hits = panels.stream().filter(x -> x.getType() == GameStates.Hit).collect(Collectors.toList());

        for (Panel item : hits) {
            for (Panel item2 : this.getNeighbors(item.coordinates)) {
                hits.add(item2);
            }
        }

        return panels.stream().distinct().filter(x -> x.getType() == GameStates.Empty).map(x -> x.getCoordinates()).collect(Collectors.toList());
    }

    public List<Panel> getNeighbors(Coordinates coordinates) {
        int row = coordinates.row;
        int column = coordinates.column;

        ArrayList<Panel> panels = new ArrayList<Panel>();

        if(column > 1) {
            for (Panel panel : panels) {
                if(panel.getCoordinates().getRow() == row && panel.getCoordinates().getColumn() == column - 1) {
                    panels.add(panel);
                }
            }
        }

        if(row > 1) {
            for (Panel panel : panels) {
                if(panel.getCoordinates().getRow() == row + 1 && panel.getCoordinates().getColumn() == column) {
                    panels.add(panel);
                }
            }
        }

        if(row < 10) {
            for (Panel panel : panels) {
                if(panel.getCoordinates().getRow() == row - 1 && panel.getCoordinates().getColumn() == column) {
                    panels.add(panel);
                }
            }
        }

        if(column < 10) {
            for (Panel panel : panels) {
                if(panel.getCoordinates().getRow() == row && panel.getCoordinates().getColumn() == column + 1) {
                    panels.add(panel);
                }
            }
        }

        return panels;
    }

}

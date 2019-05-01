package com.uniceub.battleship.models.boards;

import com.uniceub.battleship.enums.GameStates;

public class Panel {

    public GameStates type;
    public Coordinates coordinates;

    public Panel(int row, int column) {
        this.coordinates = new Coordinates(row, column);
        this.type = GameStates.Empty;
    }

    public char status() {
        return this.type.getValue();
    }

    public boolean isOccupied() {
        return this.type == GameStates.Submarine
            || this.type == GameStates.Cruizer
            || this.type == GameStates.Carrier;
    }

    public boolean isRandomAvailable() {
        return (this.coordinates.getRow() % 2 == 0 && this.coordinates.getColumn() % 2 == 0)
            || (this.coordinates.getRow() % 2 == 1 && this.coordinates.getColumn() % 2 == 1);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public GameStates getType() {
        return type;
    }

    public void setType(GameStates type) {
        this.type = type;
    }
}

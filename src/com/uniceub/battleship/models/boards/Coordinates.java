package com.uniceub.battleship.models.boards;

public class Coordinates {

    public int row;
    public int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

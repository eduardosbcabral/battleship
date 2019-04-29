package com.uniceub.battleship.models.ships;

import com.uniceub.battleship.enums.GameStates;

public abstract class Ship {

    public String name;
    public int length;
    public int hits;
    public GameStates type;

    public boolean isSunk() {
        return hits >= length;
    }

}

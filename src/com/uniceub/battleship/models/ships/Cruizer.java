package com.uniceub.battleship.models.ships;

import com.uniceub.battleship.enums.GameStates;

public class Cruizer extends Ship {

    public Cruizer() {
        this.name = "Cruizer";
        this.length = 2;
        this.type = GameStates.Cruizer;
    }

}

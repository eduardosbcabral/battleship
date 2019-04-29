package com.uniceub.battleship.models.ships;

import com.uniceub.battleship.enums.GameStates;

public class Submarine extends Ship {

    public Submarine() {
        this.name = "Submarine";
        this.length = 1;
        this.type = GameStates.Submarine;
    }

}

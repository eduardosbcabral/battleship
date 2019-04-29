package com.uniceub.battleship.models.ships;

import com.uniceub.battleship.enums.GameStates;

public class Carrier extends Ship {

    public Carrier() {
        this.name = "Carrier";
        this.length = 3;
        this.type = GameStates.Carrier;
    }

}

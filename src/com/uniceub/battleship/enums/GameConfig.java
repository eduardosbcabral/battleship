package com.uniceub.battleship.enums;

public enum GameConfig {

    Rows(7),
    Columns(7);

    private final int value;

    GameConfig(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

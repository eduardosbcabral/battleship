package com.uniceub.battleship.enums;

public enum GameStates {

    Empty('o'),
    Submarine('S'),
    Cruizer('C'),
    Carrier('A'),
    Hit('X'),
    Miss('M');

    private final char value;

    GameStates(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}

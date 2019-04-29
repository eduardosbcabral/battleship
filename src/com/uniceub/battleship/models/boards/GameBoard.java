package com.uniceub.battleship.models.boards;

import com.uniceub.battleship.enums.GameConfig;

import java.util.ArrayList;

public class GameBoard {

    public ArrayList<Panel> panels;

    public GameBoard() {
        this.panels = new ArrayList<Panel>();

        for(int i = 1; i <= 10; i++) {
            for(int j = 1; j <= 10; j++) {
                this.panels.add(new Panel(i, j));
            }
        }

    }

}

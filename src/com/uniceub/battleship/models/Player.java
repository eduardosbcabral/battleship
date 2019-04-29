package com.uniceub.battleship.models;

import com.uniceub.battleship.enums.GameStates;
import com.uniceub.battleship.enums.ShotResult;
import com.uniceub.battleship.models.boards.Coordinates;
import com.uniceub.battleship.models.boards.FiringBoard;
import com.uniceub.battleship.models.boards.GameBoard;
import com.uniceub.battleship.models.boards.Panel;
import com.uniceub.battleship.models.ships.Carrier;
import com.uniceub.battleship.models.ships.Cruizer;
import com.uniceub.battleship.models.ships.Ship;
import com.uniceub.battleship.models.ships.Submarine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class Player {

    public String name;
    public GameBoard gameBoard;
    public FiringBoard firingBoard;
    public ArrayList<Ship> ships;

    public boolean hasLost() {
        return ships.stream().allMatch(x -> x.isSunk());
    }

    public Player(String name) {
        this.name = name;

        this.ships = new ArrayList<Ship>() {{
            new Submarine();
            new Cruizer();
            new Carrier();
        }};

        this.gameBoard = new GameBoard();
        this.firingBoard = new FiringBoard();
    }

    public void outputBoards() {
        System.out.println(this.name);
        System.out.println("Own Board:                          Firing Board:");

        for(int row = 1; row <= 10; row++)
        {
            for(int ownColumn = 1; ownColumn <= 10; ownColumn++)
            {
                int finalRow = row;
                int finalColumn = ownColumn;
                System.out.print(this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == finalRow && x.getCoordinates().getColumn() == finalColumn).findFirst().get().status() + " ");
            }
            System.out.print("                ");
            for (int firingColumn = 1; firingColumn <= 10; firingColumn++)
            {
                int finalRow = row;
                int finalColumn = firingColumn;
                System.out.print(this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == finalRow && x.getCoordinates().getColumn() == finalColumn).findFirst().get().status() + " ");
            }
            System.out.print("");
        }
        System.out.print("");
    }

    public void placeShips() {
        //Select a random row/column combination, then select a random orientation.
        //If none of the proposed panels are occupied, place the ship
        //Do this for all ships

        Random rand = new Random(UUID.randomUUID().hashCode());

        for (Ship ship : this.ships) {

            boolean isOpen = true;

            while(isOpen) {
                int startColumn = rand.nextInt((11 - 1) + 1) + 1;
                int startRow = rand.nextInt((11 - 1) + 1) + 1;
                int endRow = startRow, endColumn = startColumn;
                int orientation = rand.nextInt(((101 - 1) + 1) + 1) % 2;

                ArrayList<Integer> panelNumbers = new ArrayList<Integer>();
                if(orientation == 0) {
                    for(int i = 1; i < ship.length; i++) {
                        endColumn++;
                    }
                }
                else {
                    for(int i = 0; i < ship.length; i++) {
                        endColumn++;
                    }
                }

                //We cannot place ships beyond the boundaries of the board
                if(endRow > 10 || endColumn > 10) {
                    isOpen = true;
                    continue;
                }

                //Check if specified panels are occupied
                int finalEndColumn = endColumn;
                List<Panel> affectedPanels = this.gameBoard.panels.stream().filter(x -> x.getCoordinates().row >= startRow && x.getCoordinates().column >= startColumn && x.getCoordinates().row <= endRow && x.getCoordinates().column <= finalEndColumn).collect(Collectors.toList());
                if(affectedPanels.stream().anyMatch(x -> x.isOccupied())) {
                    isOpen = true;
                    continue;
                }

                for (Panel panel : affectedPanels) {
                    panel.type = ship.type;
                }

                isOpen = false;
            }
        }
    }

    public Coordinates fireShot() {
        List<Coordinates> hitNeighbors = this.firingBoard.getHitNeighbors();
        Coordinates coords;

        if(hitNeighbors.size()>0) {
            coords = this.searchingShot();
        } else {
            coords = this.randomShot();
        }

        System.out.println(this.name + " says: 'Firing shot at " + coords.row + ", " + coords.column + "'");
        return coords;
    }

    private Coordinates randomShot() {
        List<Coordinates> availablePanels = this.firingBoard.getOpenRandomPanels();
        Random rand = new Random(UUID.randomUUID().hashCode());
        int panelID = rand.nextInt(availablePanels.size());
        return availablePanels.get(panelID);
    }

    private Coordinates searchingShot()
    {
        Random rand = new Random(UUID.randomUUID().hashCode());
        List<Coordinates> hitNeighbors = this.firingBoard.getHitNeighbors();
        int neighborID = rand.nextInt(hitNeighbors.size());
        return hitNeighbors.get(neighborID);
    }

    public ShotResult processShot(Coordinates coords)
    {
        Panel panel = this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == coords.getRow() && x.getCoordinates().getColumn() == coords.getColumn()).findFirst().get();
        if(!panel.isOccupied())
        {
            System.out.println(this.name + " says: \"Miss!\"");
            return ShotResult.Miss;
        }
        Ship ship = this.ships.stream().filter(x -> x.type == panel.type).findFirst().get();
        ship.hits++;
        System.out.println(this.name + " says: \"Hit!\"");
        if (ship.isSunk())
        {
            System.out.println(this.name + " says: \"You sunk my " + ship.name + "!\"");
        }
        return ShotResult.Hit;
    }

    public void processShotResult(Coordinates coords, ShotResult result)
    {
        Panel panel = this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == coords.getRow() && x.getCoordinates().getColumn() == coords.getColumn()).findFirst().get();

        if(result == ShotResult.Hit) {
            panel.type = GameStates.Hit;
        } else {
            panel.type = GameStates.Miss;
        }
    }
}

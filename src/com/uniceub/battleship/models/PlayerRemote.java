package com.uniceub.battleship.models;

import com.uniceub.battleship.enums.GameStates;
import com.uniceub.battleship.enums.ShotResult;
import com.uniceub.battleship.interfaces.IPlayer;
import com.uniceub.battleship.models.boards.Coordinates;
import com.uniceub.battleship.models.boards.FiringBoard;
import com.uniceub.battleship.models.boards.GameBoard;
import com.uniceub.battleship.models.boards.Panel;
import com.uniceub.battleship.models.ships.Carrier;
import com.uniceub.battleship.models.ships.Cruizer;
import com.uniceub.battleship.models.ships.Ship;
import com.uniceub.battleship.models.ships.Submarine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PlayerRemote extends UnicastRemoteObject implements IPlayer {

    public String name;
    public GameBoard gameBoard;
    public FiringBoard firingBoard;
    public ArrayList<Ship> ships;
    Scanner scanner = new Scanner(System.in);
    private Coordinates shotCoordinates;

    public boolean hasLost() {
        return ships.stream().allMatch(x -> x.isSunk());
    }

    public PlayerRemote(String name) throws RemoteException  {

        this.name = name;

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Submarine());
        this.ships.add(new Carrier());
        this.ships.add(new Cruizer());

        this.gameBoard = new GameBoard();
        this.firingBoard = new FiringBoard();
    }

    public void outputBoards() {
        System.out.println(this.name);
        System.out.println("Own Board:                          Firing Board:");

        for (int row = 1; row <= 10; row++) {
            for (int ownColumn = 1; ownColumn <= 10; ownColumn++) {
                int finalRow = row;
                int finalColumn = ownColumn;
                System.out.print(this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == finalRow && x.getCoordinates().getColumn() == finalColumn).findFirst().get().status() + " ");
            }
            System.out.print("                ");
            for (int firingColumn = 1; firingColumn <= 10; firingColumn++) {
                int finalRow = row;
                int finalColumn = firingColumn;
                System.out.print(this.firingBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == finalRow && x.getCoordinates().getColumn() == finalColumn).findFirst().get().status() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void placeShips() {
        //Select a random row/column combination, then select a random orientation.
        //If none of the proposed panels are occupied, place the ship
        //Do this for all ships
        for (Ship ship : this.ships) {

            boolean isOpen = true;

            while (isOpen) {

                int startColumn = ThreadLocalRandom.current().nextInt(1, 11);
                int startRow = ThreadLocalRandom.current().nextInt(1, 11);
                int endRow = startRow;
                int endColumn = startColumn;
                int orientation = ThreadLocalRandom.current().nextInt(1, 101) % 2;

                ArrayList<Integer> panelNumbers = new ArrayList<Integer>();
                if (orientation == 0) {
                    for (int i = 1; i < ship.length; i++) {
                        endRow++;
                    }
                } else {
                    for (int i = 1; i < ship.length; i++) {
                        endColumn++;
                    }
                }

                //We cannot place ships beyond the boundaries of the board
                if (endRow > 10 || endColumn > 10) {
                    isOpen = true;
                    continue;
                }

                //Check if specified panels are occupied
                int finalEndRow = endRow;
                int finalEndColumn = endColumn;
                List<Panel> affectedPanels = this.gameBoard.panels.stream().filter(x -> x.getCoordinates().row >= startRow && x.getCoordinates().column >= startColumn && x.getCoordinates().row <= finalEndRow && x.getCoordinates().column <= finalEndColumn).collect(Collectors.toList());
                if (affectedPanels.stream().anyMatch(x -> x.isOccupied())) {
                    isOpen = true;
                    continue;
                }

                for (Panel panel : affectedPanels) {
                    panel.setType(ship.type);
                }

                isOpen = false;
            }
        }
    }

    public Coordinates fireShot() {
        System.out.println("==============================");
        System.out.println("It's " + this.name + " turn!!");
        System.out.print("Type the Y coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Type the X coordinate: ");
        int y = scanner.nextInt();

        Coordinates coords = new Coordinates(x, y);

        return coords;
    }

    public ShotResult processShot(Coordinates coords) {
        Panel panel = this.gameBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == coords.getRow() && x.getCoordinates().getColumn() == coords.getColumn()).findFirst().get();
        if (!panel.isOccupied()) {
            System.out.println(this.name + " says: \"Miss!\"");
            System.out.println();
            return ShotResult.Miss;
        }
        Ship ship = this.ships.stream().filter(x -> x.type == panel.type).findFirst().get();
        ship.hits++;
        System.out.println(this.name + " says: \"Hit!\"");
        if (ship.isSunk()) {
            System.out.println(this.name + " says: \"You sunk my " + ship.name + "!\"");
        }
        System.out.println();
        return ShotResult.Hit;
    }

    public void processShotResult(Coordinates coords, ShotResult result) {
        Panel panel = this.firingBoard.panels.stream().filter(x -> x.getCoordinates().getRow() == coords.getRow() && x.getCoordinates().getColumn() == coords.getColumn()).findFirst().get();

        if (result == ShotResult.Hit) {
            panel.type = GameStates.Hit;
        } else {
            panel.type = GameStates.Miss;
        }
    }

    public void playRound() {

        try {

            Coordinates coordinates = this.fireShot();

            PlayerRemote2 player2 = (PlayerRemote2) Naming.lookup("rmi://127.0.0.1:6667/Player2");

            ShotResult result = player2.processShotRemote(coordinates);
            this.processShotResult(coordinates, result);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Coordinates fireShotRemote() throws RemoteException {
        return this.fireShot();
    }

    @Override
    public void outputBoardsRemote() throws RemoteException {
        this.outputBoards();
    }

    @Override
    public ShotResult processShotRemote(Coordinates coordinates) throws RemoteException {
        return this.processShot(coordinates);
    }

    @Override
    public boolean hasLostRemote() throws RemoteException {
        return this.hasLost();
    }
}

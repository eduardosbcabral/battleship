package com.uniceub.battleship.models.game;

import com.uniceub.battleship.enums.ShotResult;
import com.uniceub.battleship.models.Player;
import com.uniceub.battleship.models.boards.Coordinates;

public class Game {

    public Player player1;
    public Player player2;

    public Game() {

        player1 = new Player("Eduardo");
        player2 = new Player("Felipe");

        player1.placeShips();
        player2.placeShips();

        player1.outputBoards();
        player2.outputBoards();

    }

    public void playRound() {
        //Each exchange of shots is called a Round.
        //One round = Player 1 fires a shot, then Player 2 fires a shot.
        Coordinates coordinates = this.player1.fireShot();
        ShotResult result = this.player2.processShot(coordinates);
        this.player1.processShotResult(coordinates, result);

        if (!player2.hasLost()) //If player 2 already lost, we can't let them take another turn.
        {
            coordinates = player2.fireShot();
            result = player1.processShot(coordinates);
            player2.processShotResult(coordinates, result);
        }
    }

    public void playToEnd() {
        while (!this.player1.hasLost() && !this.player2.hasLost()) {
            playRound();
        }

        this.player1.outputBoards();
        this.player2.outputBoards();

        if (this.player1.hasLost()) {
            System.out.println(this.player2.name + " has won the game!");
        } else if (this.player2.hasLost()) {
            System.out.println(this.player1.name + " has won the game!");
        }
    }
}

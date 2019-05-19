package com.uniceub.battleship.models.game;

import com.uniceub.battleship.interfaces.IGame;
import com.uniceub.battleship.interfaces.IPlayer;
import com.uniceub.battleship.models.PlayerRemote;
import com.uniceub.battleship.models.PlayerRemote2;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServer extends UnicastRemoteObject implements IGame {

    public PlayerRemote player1;

    public GameServer() throws RemoteException {

        try {
            PlayerRemote player1 = new PlayerRemote("Eduardo");

            Naming.rebind("//127.0.0.1:6667/Player1", player1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playToEnd() throws RemoteException {

        System.out.println("AAAAAAAAAA");
        try {
            IPlayer player2 = (IPlayer) Naming.lookup("rmi://127.0.0.1:6667/Player2");
            while (!this.player1.hasLost() && !player2.hasLostRemote()) {
                this.player1.playRound();
            }

            this.player1.outputBoards();

            if (this.player1.hasLost()) {
                System.out.println("Player 2 has won the game!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

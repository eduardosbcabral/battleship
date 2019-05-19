package com.uniceub.battleship.models.game;

import com.uniceub.battleship.interfaces.IGame;
import com.uniceub.battleship.interfaces.IPlayer;
import com.uniceub.battleship.models.PlayerRemote;
import com.uniceub.battleship.models.PlayerRemote2;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameClient extends UnicastRemoteObject implements IGame {

    public PlayerRemote2 player2;

    public GameClient() throws RemoteException {

        try {
            PlayerRemote2 player2 = new PlayerRemote2("Felipe");

            Naming.rebind("//127.0.0.1:6667/Player2", player2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playToEnd() throws RemoteException {
        try {

            IPlayer player1 = (IPlayer) Naming.lookup("rmi://127.0.0.1:6667/Player1");
            while (!player1.hasLostRemote() && !this.player2.hasLost()) {
                this.player2.playRound();
            }

            this.player2.outputBoards();

            if (this.player2.hasLost()) {
                System.out.println("Player 1 has won the game!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

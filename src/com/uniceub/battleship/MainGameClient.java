package com.uniceub.battleship;

import com.uniceub.battleship.interfaces.IGame;
import com.uniceub.battleship.models.game.GameClient;

import java.rmi.Naming;

public class MainGameClient {

    public static void main(String[] args) {

        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            IGame gameClient = (IGame) Naming.lookup("rmi://127.0.0.1:6667/GameClient");
            gameClient.playToEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

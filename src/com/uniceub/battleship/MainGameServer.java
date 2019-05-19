package com.uniceub.battleship;

import com.uniceub.battleship.interfaces.IGame;
import com.uniceub.battleship.models.game.GameClient;
import com.uniceub.battleship.models.game.GameServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainGameServer {

    public static void main(String[] args) {

        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            IGame gameServer = (IGame) Naming.lookup("rmi://127.0.0.1:6667/GameServer");
            gameServer.playToEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

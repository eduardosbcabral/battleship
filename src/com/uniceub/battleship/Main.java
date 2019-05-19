package com.uniceub.battleship;

import com.uniceub.battleship.models.game.GameClient;
import com.uniceub.battleship.models.game.GameServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {

    public static void main(String[] args) {


        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            LocateRegistry.createRegistry(6667);
            GameClient gameClient = new GameClient();
            GameServer gameServer = new GameServer();

            Naming.rebind("//127.0.0.1:6667/GameClient", gameClient);
            Naming.rebind("//127.0.0.1:6667/GameServer", gameServer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

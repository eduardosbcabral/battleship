package com.uniceub.battleship.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {

    void playToEnd() throws RemoteException;

}

package com.uniceub.battleship.interfaces;

import com.uniceub.battleship.enums.ShotResult;
import com.uniceub.battleship.models.boards.Coordinates;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote {

    Coordinates fireShotRemote() throws RemoteException;
    ShotResult processShotRemote(Coordinates coordinates) throws RemoteException;
    void outputBoardsRemote() throws RemoteException;
    boolean hasLostRemote() throws RemoteException;

}

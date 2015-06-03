package icyqueue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote
{
    public void echo(String sender, String message) throws RemoteException;
}

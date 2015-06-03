package icyqueue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote
{
    public String login(Client client, String desiredUserName)
            throws RemoteException;

    public void broadcast(Client sender, String message) throws RemoteException;
}

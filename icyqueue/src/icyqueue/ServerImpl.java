package icyqueue;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl implements Server
{
    // TODO Pick data structure for associating clients with names

    @Override
    public String login(Client client, String desiredUserName)
            throws RemoteException
    {
        String chosenUserName = uniqueName(desiredUserName);

        // TODO register client

        return chosenUserName;
    }

    @Override
    public void broadcast(Client sender, String message) throws RemoteException
    {
        // TODO send the message to all clients
    }

    public static void main(String[] args)
    {
        try
        {
            Server server = new ServerImpl();
            Server proxy = (Server) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("icyqueue", proxy);

            System.out.println("Server started. Press the RED SQUARE to shut it down!");
        }
        catch (RemoteException ex)
        {
            ex.printStackTrace();
        }
    }

    private String uniqueName(String prefix)
    {
        while (_loggedInClients.containsValue(prefix))
        {
            prefix += randomCharacter();
        }
        return prefix;
    }

    private static char randomCharacter()
    {
        String s = "0123456789QWERTZUIOPASDFGHJKLYXCVBNMqwertzuiopasdfghjklyxcvbnm";
        return s.charAt((int) (s.length() * Math.random()));
    }
}

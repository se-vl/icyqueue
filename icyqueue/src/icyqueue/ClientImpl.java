package icyqueue;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImpl implements Client
{
    @Override
    public void echo(String sender, String message) throws RemoteException
    {
        // TODO print the message in your favorite format
    }

    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Server serverProxy = (Server) registry.lookup("icyqueue");

            ClientImpl client = new ClientImpl();
            Client clientProxy = (Client) UnicastRemoteObject.exportObject(
                    client, 0);

            String userName = serverProxy.login(clientProxy, "Fred");
            System.out.println("You were logged in as " + userName);
            chatLoop(clientProxy, serverProxy);
        }
        catch (RemoteException | NotBoundException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void chatLoop(Client clientProxy, Server serverProxy)
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            String line = scanner.nextLine();
            while (!line.isEmpty())
            {
                serverProxy.broadcast(clientProxy, line);
                line = scanner.nextLine();
            }
        }
        catch (RemoteException ex)
        {
            System.out.println(ex);
        }
    }
}

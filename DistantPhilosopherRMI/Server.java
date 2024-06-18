import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static int nbrForks = 5;
    public static int rmiPort = 53001;

    public static void main(String[] args) {
        final String SERVICE = "Fork";
        System.setProperty("java.rmi.server.hostname","192.168.1.47");
        try {
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            for (int i = 0; i < nbrForks; i++) {
                ForkInterface stub = (ForkInterface) UnicastRemoteObject.exportObject(new Fork(), 0);
                registry.rebind(SERVICE + i, stub);
            }
            System.out.println("Stubs created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

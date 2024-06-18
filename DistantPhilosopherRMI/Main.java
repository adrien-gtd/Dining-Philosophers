import java.io.BufferedWriter;
import java.io.FileWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main (String [] args) {
        String host = "localhost";
        int port = 53001;
        Philosopher[] philosophers = new Philosopher[5];
        ForkInterface[] forks = new ForkInterface[philosophers.length];
        BufferedWriter writer = null;
        boolean print = false;
        final String SERVICE = "Fork";

        if (args.length == 2) {
            try {
                writer = new BufferedWriter(new FileWriter(args[0]));
                print = args[1].equals("y");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong arguments number. Include file name and y or n for console print");
            return;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            for (int i = 0; i < forks.length; i ++) {
                forks[i] = (ForkInterface)registry.lookup(SERVICE + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < philosophers.length; i++) {
            if(i != philosophers.length - 1) {
            philosophers[i] = new Philosopher(forks[i], forks[(i+1) % philosophers.length], writer, print);
            } else {
                philosophers[i] = new Philosopher(forks[(i+1) % philosophers.length], forks[i], writer, print);
            }

            Thread t = new Thread(philosophers[i], "Philosopher " + (i+1) );
            t.start();
        }
    }
}

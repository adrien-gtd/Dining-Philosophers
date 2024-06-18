import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    static int port = 80;
    static int nmbrPhilosopher = 5;
    
    public static void main (String [] args) {
        Philosopher[] philosophers = new Philosopher[nmbrPhilosopher];
        BufferedWriter writer = null;

        if(args.length == 1) {
            System.out.println(args[0]);
            try {
                writer = new BufferedWriter(new FileWriter("./log/" + args[0]));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            InetAddress addr = InetAddress.getLocalHost();
            for(int i = 0; i < philosophers.length; i++) {
                philosophers[i] = new Philosopher(new Socket(addr, port + i), writer);
                Thread t = new Thread(philosophers[i] ,"Philosopher " + i);
                t.start();
            }
            System.out.println("End of init");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    } 
}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int forkNumber = 5;

    public static void main (String[] args) {
        ServerSocket serverSocket = null; 
        int port = 80;
        Fork[] forks = new Fork[forkNumber];
        Thread[] threads = new Thread[forkNumber];
        ServerRunnable runnable = null;
        BufferedWriter writer = null;

        if(args.length == 1) {
            System.out.println(args[0]);
            try {
                writer = new BufferedWriter(new FileWriter("./log/" + args[0]));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < forkNumber; i++) {
            forks[i] = new Fork();
        }

        try {
            for(int i = 0; i < forkNumber; i++) {
                serverSocket = new ServerSocket(port + i) ;
                System.out.println("Listenning on port : " + (port + i));
                Socket socket = serverSocket.accept();
                if (i == forkNumber - 1) {
                    runnable = new ServerRunnable(socket, forks[(i+1) % forkNumber], forks[i], writer);
                } else {
                    runnable = new ServerRunnable(socket, forks[i], forks[(i+1) % forkNumber], writer);
                }
                threads[i] = new Thread(runnable, "Server thread n" + i);
                threads[i].start();
            }
            System.out.println("All server thread created, waiting for end of the run!");
            for (int i = 0; i < forkNumber; i++) {
                threads[i].join();
            }
        } 
        catch (Exception e) { 
            e.printStackTrace();
        }
        finally {
            for (int i = 0; i < forkNumber; i++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
            System.out.println("All thread finished, end of exectution!");
        }
    }
}

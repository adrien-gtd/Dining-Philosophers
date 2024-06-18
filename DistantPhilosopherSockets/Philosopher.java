import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Philosopher implements Runnable{
    // private String leftFork;
    // private String rightFork;
    Communication comm;
    int mealNmbr = 1;
    String threadName;
    BufferedWriter writer;

    public Philosopher  (Socket socket, BufferedWriter writer) {
        this.comm = new Communication (socket);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                printlog("End of execution, meal eaten : " + mealNmbr);
            }
        });
        this.writer = writer;
    }

    // private void getForks () throws IOException {
    //     comm.sendMessage(Thread.currentThread().getName() + ":GetForks\n");
    //     leftFork = comm.readMessage();
    //     rightFork = comm.readMessage();
    //     System.out.println(Thread.currentThread().getName() + " : Forks acquired "+ leftFork + " " + rightFork);
    // }
    
    private void writelog (String log) {
        try {
            writer.write(log+ "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printlog (String message) {
        if (writer != null) {
            writelog(threadName + " : " + message);
        }
        System.out.println(threadName + " : " + message);
    }

    private void busy(String message) throws InterruptedException {
        if (message != null) {
            printlog(message);
        }
        Thread.sleep(((int) (Math.random() * 256)));
    }

    private void pickLeftFork() throws IOException {
        comm.sendMessage("GetLeftFork\n");
        comm.readMessage();
        printlog("Picked up left fork");
    }

    private void pickRightFork() throws IOException {
        comm.sendMessage("GetRightFork\n");
        comm.readMessage();
        printlog("Picked up right fork");
    }

    private void putDownForks() throws IOException {
        comm.sendMessage("FreeForks\n");
        comm.readMessage();
        printlog("Put down forks, back to thinking!");

    }

    @Override
    public void run () {
        threadName = Thread.currentThread().getName();
        try {
            // getForks();
            while (true) {
                busy("Thinking");
                pickLeftFork();
                busy(null);
                pickRightFork();
                busy("Eating meal number " + mealNmbr);
                mealNmbr ++;
                putDownForks();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            comm.terminate();
        }
    }
}

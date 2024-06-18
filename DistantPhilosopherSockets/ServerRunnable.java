import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ServerRunnable implements Runnable {
    Communication comm;
    Fork leftFork;
    Fork rightFork;
    BufferedWriter writer;

    public ServerRunnable (Socket socket, Fork leftFork, Fork rightFork, BufferedWriter writer) {
        this.comm = new Communication (socket);
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.writer = writer;
    }

    private void writelog (String log) {
        try {
            writer.write(log + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printlog (String message) {
        if (writer != null) {
            writelog(Thread.currentThread().getName() + " : " + message);
        }
        System.out.println(Thread.currentThread().getName() + " : " + message);
    }

    private void processMessage (String message) throws IOException {
        if (message == null) {
            printlog("End of communication, stopping execution.");
            rightFork.freeFork();
            leftFork.freeFork();
            Thread.currentThread().interrupt();
            return;
        }

        printlog("Message received - " + message);
        
        if (message.equals("GetLeftFork")) {
            while(!leftFork.lockFork());
            comm.sendMessage("Left fork\n");
        } else if (message.equals("GetRightFork")) {
            while(!rightFork.lockFork());
            comm.sendMessage("Right fork\n");
        } else if (message.equals("FreeForks")) {
            rightFork.freeFork();
            leftFork.freeFork();
            comm.sendMessage("Free fork\n");
        } else { 
            printlog("Error!! Wrong message received " + message);
        }
    }


    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                processMessage(comm.readMessage());
            }
        }
        catch (SocketException e) {
            printlog("End of communication, stopping execution. Chaught at Socket reset exception");
            rightFork.freeFork();
            leftFork.freeFork();
        }
        catch (Exception e) {
            e.printStackTrace();
        }  
        finally {
            comm.terminate();
        }
    }
    
}

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.net.Socket;

public class Fork {
    // private Socket socket;
    // private BufferedReader rd;
    // private BufferedWriter wr;
    private boolean isFree = true;
    // public Fork (Socket socket) {
    //     this.socket = socket;
    //     try {
    //         wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    //         rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public synchronized boolean lockFork() {
        if (isFree) {
            this.isFree = false;
            return true;
        } 
        return false;
    }

    public synchronized void freeFork() {
        this.isFree = true;
    }

    public synchronized boolean isAvailable() {
        return isFree;
    }


    // @Override
    // public void run () {
    //     String message;
    //     while (true) {
    //         try {
    //             message = rd.readLine();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }

    //     }
    // }
}

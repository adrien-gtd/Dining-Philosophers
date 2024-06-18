import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Communication {
    private BufferedWriter wr;
    private BufferedReader rd;
    Socket socket = null;

    public Communication (Socket socket) {
        try {
            this.socket = socket;
            rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void terminate () {
        try {
            rd.close();
            wr.close();
            socket.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message) throws IOException {
        wr.write(message);
        wr.flush();
        return;
    }

    public String readMessage () throws IOException {
        return rd.readLine();
    }
    

}

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerListener implements Runnable{

    private DataInputStream dataIn;
    private boolean exit;

    public ServerListener (Socket socket)throws IOException {
        dataIn = new DataInputStream(socket.getInputStream());
        exit = false;
    }

    @Override
    public void run() {
        try{
            while (!exit) {

                int x =11;
                try {
                    x = (int) dataIn.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                    exit = true;
                    Server.board.setTitle("Client Disconnected");
                }
                Server.OpponentMoved(x);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("server finally");
        }


    }

    public void stop() {
        System.out.println("server listener stop() called");
        exit = true;
    }
}



import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
    public static DataOutputStream dataOut;
    public static ServerBoard board;
    public static boolean isMyTurn;
    public static String XorO;
    public static String opponentXorO;
    private static boolean serverGoesFirst;
    public static ArrayList<ServerSquare> squares;
    public static boolean gameOver = false;
    private static ServerGameOver gameOverJFrame;
    public static boolean opponentIsReady = true;
    private static ServerListener listener;
    private static DataInputStream dataIn;
    private static Socket socket;
    private static ServerSocket serverSocket;
    //private static JavaIOFileDescriptorAccess squares;

    public static void main(String[] args) throws IOException {



        boolean clientGoesFirst = rollDiceBool();
        isMyTurn = !clientGoesFirst;
        System.out.println(isMyTurn);
        boolean clientIsX = rollDiceBool();
        if (clientIsX){
            opponentXorO = "X";
            XorO = "O";
        } else{
            XorO = "X";
            opponentXorO = "O";
        }
        serverSocket = new ServerSocket(9090);
        socket = serverSocket.accept();

        dataOut = new DataOutputStream(socket.getOutputStream());

        dataOut.writeBoolean(clientGoesFirst);
        dataOut.writeBoolean(clientIsX);

        dataIn = new DataInputStream(socket.getInputStream());

        listener = new ServerListener(socket);
        new Thread(listener).start();



        board = new ServerBoard(false, !clientIsX, isMyTurn);
        board.setLayout(new GridLayout(3,3));
        squares = new ArrayList<>(10);
        //Square[] squares = new Square[10];
        for (int i = 0; i < 9; i++) {//0-8, feels bad on a 9 grid
            ServerSquare square = new ServerSquare(i);
            squares.add(square);
            board.add(square);
        }
        board.repaint();











        System.out.println("game Exited");


    }//end of main
    private static boolean rollDiceBool(){
        Random rand = new Random();
        int x = ( rand.nextInt(2));//0 or 1
        return x == 0;
    }

    public static void OpponentMoved(int num){

        if (num == -1){
            gameOver();
            opponentIsReady = false;
        } else if (num == 10) {
            opponentIsReady = true;
        }else if (num == 11) {
            System.out.println("default button message/ data not read");

        } else {
            System.out.println("Opponent moved: " + num);
            isMyTurn = true;
            board.setTitle("Server, Your Turn, " + XorO);
            squares.get(num).setText(opponentXorO);
            squares.get(num).text = opponentXorO;
        }

    }

    public static void gameOver(){
        gameOver = true;
        gameOverJFrame = new ServerGameOver();
    }

    public static void setBoardTitle(String title) { board.setTitle(title); }

    public static void reset(){
        gameOverJFrame.dispose();
        gameOver = false;
        try {
            dataOut.writeInt(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game Resetting");
        for (ServerSquare asquare: squares) {
            asquare.text = "-1";
            asquare.setText("");
            serverGoesFirst = !serverGoesFirst;
            if (serverGoesFirst) isMyTurn = true;
            if (!serverGoesFirst) isMyTurn = false;
        }
        board.repaint();
    }

    public static void CloseConnection() throws IOException {
        listener.stop();

        dataIn.close();
        dataOut.close();
        socket.close();
        serverSocket.close();
    }
}

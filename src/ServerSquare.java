import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class ServerSquare extends JButton implements MouseListener, WindowListener {

    private int position;//0-8
    String text;//X or O  until i add icons

    ServerSquare(int position){
        this.position = position;
        this.addMouseListener(this);
        this.text = "-1";
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(position+" Clicked");

        if(Server.isMyTurn && this.text.equals("-1") && !Server.gameOver && Server.opponentIsReady) {

            this.text = Server.XorO;
            this.setText(this.text);

            boolean gameOver = checkForWin();

            if (gameOver) System.out.println("game 0000000000over");
            if (!gameOver) System.out.println("game NOT over");

            System.out.println("clicked empty square on my turn");
            Server.isMyTurn = false;
            Server.setBoardTitle("Server, Opponent's Turn, " + Server.XorO);

            try {
                Server.dataOut.writeInt(position);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (gameOver) {
                try {
                    Server.gameOver();
                    Server.dataOut.writeInt(-1);
                    Server.opponentIsReady = false;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    private boolean checkForWin() {

        if (Server.squares.get(0).text == "X" && Server.squares.get(1).text == "X" && Server.squares.get(2).text == "X") return true;
        if (Server.squares.get(3).text == "X" && Server.squares.get(4).text == "X" && Server.squares.get(5).text == "X") return true;
        if (Server.squares.get(6).text == "X" && Server.squares.get(7).text == "X" && Server.squares.get(8).text == "X") return true;

        if (Server.squares.get(0).text == "O" && Server.squares.get(1).text == "O" && Server.squares.get(2).text == "O") return true;
        if (Server.squares.get(3).text == "O" && Server.squares.get(4).text == "O" && Server.squares.get(5).text == "O") return true;
        if (Server.squares.get(6).text == "O" && Server.squares.get(7).text == "O" && Server.squares.get(8).text == "O") return true;

        if (Server.squares.get(0).text == "X" && Server.squares.get(3).text == "X" && Server.squares.get(6).text == "X") return true;
        if (Server.squares.get(1).text == "X" && Server.squares.get(4).text == "X" && Server.squares.get(7).text == "X") return true;
        if (Server.squares.get(2).text == "X" && Server.squares.get(5).text == "X" && Server.squares.get(8).text == "X") return true;

        if (Server.squares.get(0).text == "O" && Server.squares.get(3).text == "O" && Server.squares.get(6).text == "O") return true;
        if (Server.squares.get(1).text == "O" && Server.squares.get(4).text == "O" && Server.squares.get(7).text == "O") return true;
        if (Server.squares.get(2).text == "O" && Server.squares.get(5).text == "O" && Server.squares.get(8).text == "O") return true;


        if (Server.squares.get(0).text == "O" && Server.squares.get(4).text == "O" && Server.squares.get(8).text == "O") return true;
        if (Server.squares.get(2).text == "O" && Server.squares.get(4).text == "O" && Server.squares.get(6).text == "O") return true;

        if (Server.squares.get(0).text == "X" && Server.squares.get(4).text == "X" && Server.squares.get(8).text == "X") return true;
        if (Server.squares.get(2).text == "X" && Server.squares.get(4).text == "X" && Server.squares.get(6).text == "X") return true;

        for (ServerSquare asquare:Server.squares) {
            if (asquare.text == "-1") return false;
        }
        return true;
    }

/*
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(position+" Clicked");
        try {
            Server.dataOut.writeInt(position);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

 */





    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) {}


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }



    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

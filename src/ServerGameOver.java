import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ServerGameOver extends JFrame {
    ServerGameOver(){
        setSize(200, 100);
        setTitle("Server, Game Over");
        setLocationRelativeTo(Server.board);

        JButton reset = new JButton();
        reset.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {

                Server.reset();
            }
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        reset.setText("reset");
        add(reset);
        setVisible(true);

    }
}

package game;

import javax.swing.*;
import java.util.Objects;

public class GameFrame extends JFrame {

    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon.png")));
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}

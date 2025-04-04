import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        setTitle("Super Hexagon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }
}

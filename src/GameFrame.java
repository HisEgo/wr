import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        setTitle("Super Hexagon");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private MainFrame mainFrame;
    private GamePanel gamePanel;

    public GameFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Super Hexagon");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel(mainFrame);
        add(gamePanel);

        validate();
        repaint();

        // استفاده از SwingUtilities.invokeLater برای نمایش فریم و درخواست فوکوس
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            gamePanel.requestFocusInWindow(); // درخواست فوکوس
        });
    }
}

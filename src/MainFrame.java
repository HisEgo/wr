import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Super Hexagon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(200, 50));

        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(200, 50));

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 50));

        mainMenuPanel.add(startButton);
        mainMenuPanel.add(settingsButton);
        mainMenuPanel.add(exitButton);

        add(mainMenuPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}

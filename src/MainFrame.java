import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class MainFrame extends JFrame {


    private JLabel bestTimeLabel;
    private SoundManager soundManager;

    public MainFrame() {
        setTitle("Super Hexagon");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        soundManager = new SoundManager("D:\\Homeworks\\AP2\\courtesy.wav"); // Replace with your music file path
        soundManager.playBackgroundMusic();

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GamePreparationFrame(MainFrame.this);            }

        });

        bestTimeLabel = new JLabel("Best Time: " + getBestTime());
        bestTimeLabel.setPreferredSize(new Dimension(200, 30));
        bestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton historyButton = new JButton("Game History");
        historyButton.setPreferredSize(new Dimension(200, 50));
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                JOptionPane.showMessageDialog(MainFrame.this, "Opening game history ...");
            }
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(200, 50));

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame(soundManager);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 50));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainMenuPanel.add(startButton);
        mainMenuPanel.add(bestTimeLabel);
        mainMenuPanel.add(historyButton);
        mainMenuPanel.add(settingsButton);
        mainMenuPanel.add(exitButton);

        add(mainMenuPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private String getBestTime() {
        String bestTime = "N/A";
        try (BufferedReader br = new BufferedReader(new FileReader("bestTime.txt"))) {
            String line = br.readLine();
            if (line != null && !line.isEmpty()) {
                try {
                    double time = Double.parseDouble(line);
                    bestTime = formatTime(time);
                } catch (NumberFormatException e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bestTime;
    }

    private String formatTime(double time) {
        int minutes = (int) (time / 60);
        int seconds = (int) (time % 60);
        int milliseconds = (int) ((time * 100) % 100);
        return String.format("%02d:%02d.%02d", minutes, seconds, milliseconds);
    }

    public void updateBestTime(String newTime) {
        bestTimeLabel.setText("Best Time: " + newTime);
    }
}
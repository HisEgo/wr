import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePreparationFrame extends JFrame {
    private JTextField playerNameField;
    private JButton startButton;

    public GamePreparationFrame() {
        setTitle("Game Preparation");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JLabel nameLabel = new JLabel("Player Name:");
        playerNameField = new JTextField(15);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText();
                if (playerName != null && !playerName.trim().isEmpty()) {
                    //
                    JOptionPane.showMessageDialog(GamePreparationFrame.this, "Starting game with " + playerName);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(GamePreparationFrame.this, "Please enter your name!");
                }
            }
        });

        add(nameLabel);
        add(playerNameField);
        add(startButton);

        setVisible(true);
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {

    private JCheckBox saveHistoryCheckBox;
    public SettingsFrame(){
        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20 , 20));

        JLabel volumeLabel1 = new JLabel("Volume:");
        JSlider volumeSlider = new JSlider(0, 100, 50);

        saveHistoryCheckBox = new JCheckBox("Save Game History");
        saveHistoryCheckBox.setSelected(true);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean saveHistory = saveHistoryCheckBox.isSelected();
                //
                System.out.println("Save History: " + saveHistory);
                JOptionPane.showMessageDialog(SettingsFrame.this, "Setting applied!");
                dispose();
            }
        });

        settingsPanel.add(volumeLabel1);
        settingsPanel.add(volumeSlider);
        settingsPanel.add(saveHistoryCheckBox);
        settingsPanel.add(applyButton);

        setVisible(true);
    }

}

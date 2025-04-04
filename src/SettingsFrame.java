import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

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

        Properties config = ConfigManager.loadConfig();

        saveHistoryCheckBox.setSelected(Boolean.parseBoolean(config.getProperty("saveHistory", "true")));
        volumeSlider.setValue(Integer.parseInt(config.getProperty("volume", "50")));

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean saveHistory = saveHistoryCheckBox.isSelected();
                //
                System.out.println("Save History: " + saveHistory);
                int volume = volumeSlider.getValue();
                ConfigManager.saveConfig(saveHistory, volume);
                
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

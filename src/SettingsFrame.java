import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SettingsFrame extends JFrame {

    private JCheckBox saveHistoryCheckBox;
    private JSlider volumeSlider;
    private SoundManager soundManager;

    public SettingsFrame(SoundManager soundManager){
        this.soundManager = soundManager;

        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20 , 20));

        JLabel volumeLabel1 = new JLabel("Volume:");
        volumeSlider = new JSlider(0, 100, 50);

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = volumeSlider.getValue() / 100f;
                soundManager.setVolume(volume);
            }
        });

        saveHistoryCheckBox = new JCheckBox("Save Game History");

        Properties config = ConfigManager.loadConfig();

        saveHistoryCheckBox.setSelected(Boolean.parseBoolean(config.getProperty("saveHistory", "true")));
        volumeSlider.setValue(Integer.parseInt(config.getProperty("volume", "50")));

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean saveHistory = saveHistoryCheckBox.isSelected();
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

        add(settingsPanel);

        setVisible(true);
    }

}

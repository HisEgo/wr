import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";

    public static void saveConfig(boolean saveHistory, int volume) {
        Properties props = new Properties();
        props.setProperty("saveHistory", String.valueOf(saveHistory));
        props.setProperty("volume", String.valueOf(volume));

        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "Game Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadConfig() {
        Properties props = new Properties();

        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            props.setProperty("saveHistory", "true");
            props.setProperty("volume", "50");
        }

        return props;
    }
}

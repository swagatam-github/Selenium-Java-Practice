package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesFileReader {
    private Properties properties;

    public PropertiesFileReader(String propFile) {
        properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get(propFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}

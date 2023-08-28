package utils;

import java.util.Properties;

public class Credentials {
    final static String PROPFILENAME = "src/main/resources/credentials.properties";
    final static Properties credentials = new PropertiesFileReader(PROPFILENAME).getProperties();

    public static String getUserName() {
        return credentials.getProperty("USERNAME");
    }

    public static String getPassword() {
        return credentials.getProperty("PASSWORD");
    }
}

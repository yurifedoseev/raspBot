package com.raspbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties props;
    static {
        props = new Properties();
        InputStream stream = Config.class.getClassLoader().getResourceAsStream("config.properties");

        if (stream != null) {
            try {
                props.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getBotToken() {
        return props.getProperty("botToken");
    }

    public static String getApiUrl() {
        return props.getProperty("apiUrl");
    }
}

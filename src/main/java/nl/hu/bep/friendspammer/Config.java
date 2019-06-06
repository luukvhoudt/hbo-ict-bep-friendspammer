package nl.hu.bep.friendspammer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Config {
    private static final Logger logger = LoggerFactory.getLogger(MongoSaver.class);

    private Config() {}

    static String getProp(String key) {
        String result = "";
        InputStream inputStream = null;
        try {
            Properties props = new Properties();
            String propFileName = "config.properties";

            inputStream = Config.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                props.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            result = props.getProperty(key);
        } catch (IOException e) {
            logger.error("Unable to read config properties", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Unable to close InputStream", e);
                }
            }
        }
        return result;
    }
}


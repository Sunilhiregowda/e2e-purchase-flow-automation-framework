package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

	private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);
	private static final Properties PROPERTIES = new Properties();

	static {
		loadProperties();
	}

	private ConfigReader() {
		// Prevent object creation
	}

	private static void loadProperties() {
		try (FileInputStream fileInputStream = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
			PROPERTIES.load(fileInputStream);
			LOGGER.info("Configuration properties loaded successfully from: {}", FrameworkConstants.CONFIG_FILE_PATH);
		} catch (IOException e) {
			LOGGER.error("Failed to load config properties from: {}", FrameworkConstants.CONFIG_FILE_PATH, e);
			throw new RuntimeException("Unable to load configuration file", e);
		}
	}

	public static String getProperty(String key) {
		String value = PROPERTIES.getProperty(key);

		if (value == null || value.trim().isEmpty()) {
			LOGGER.warn("No value found for config key: {}", key);
		} else {
			LOGGER.info("Fetched config value for key: {}", key);
		}

		return value;
	}

	public static int getInt(String key) {
		String value = getProperty(key);

		if (value == null || value.trim().isEmpty()) {
			throw new RuntimeException("No integer value found for config key: " + key);
		}

		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			LOGGER.error("Invalid integer value for config key: {} | Value: {}", key, value, e);
			throw new RuntimeException("Invalid integer value in config for key: " + key, e);
		}
	}
}
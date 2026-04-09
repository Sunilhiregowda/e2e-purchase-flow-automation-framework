package com.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public final class JsonDataReader {

	private static final Logger LOGGER = LogManager.getLogger(JsonDataReader.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static JsonNode jsonNode;

	static {
		loadJsonData();
	}

	private JsonDataReader() {
		// Prevent object creation
	}

	private static void loadJsonData() {
		try {
			jsonNode = OBJECT_MAPPER.readTree(new File(FrameworkConstants.TEST_DATA_PATH));
			LOGGER.info("JSON test data loaded successfully from: {}", FrameworkConstants.TEST_DATA_PATH);
		} catch (IOException e) {
			LOGGER.error("Failed to load JSON test data from: {}", FrameworkConstants.TEST_DATA_PATH, e);
			throw new RuntimeException("Failed to load JSON test data", e);
		}
	}

	public static String getValue(String key) {
		JsonNode node = getNode(key);

		if (node == null || node.isNull()) {
			LOGGER.warn("No value found in JSON for key: {}", key);
			return null;
		}

		return node.asText();
	}

	public static JsonNode getNode(String key) {
		if (jsonNode == null) {
			throw new RuntimeException("JSON test data is not loaded properly.");
		}

		JsonNode node = jsonNode.get(key);

		if (node == null) {
			LOGGER.warn("No JSON node found for key: {}", key);
		} else {
			LOGGER.info("Fetched JSON node for key: {}", key);
		}

		return node;
	}
}
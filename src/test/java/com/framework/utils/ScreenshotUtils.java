package com.framework.utils;

import com.framework.base.DriverFactory;
import com.framework.constants.FrameworkConstants;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

	private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtils.class);
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	private ScreenshotUtils() {
		// Prevent object creation
	}

	public static String captureScreenshot(String testName) {
		try {
			File sourceFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
			String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
			String screenshotPath = FrameworkConstants.SCREENSHOT_DIRECTORY + testName + "_" + timestamp + ".png";

			File destinationFile = new File(screenshotPath);
			FileUtils.copyFile(sourceFile, destinationFile);

			LOGGER.info("Screenshot captured successfully: {}", screenshotPath);
			return screenshotPath;

		} catch (IOException e) {
			LOGGER.error("Failed to capture screenshot for test: {}", testName, e);
			throw new RuntimeException("Unable to capture screenshot", e);
		}
	}
}
package com.framework.listeners;

import com.framework.utils.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

	private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

	@Override
	public void onStart(ITestContext context) {
		LOGGER.info("========== Test Suite Started: {} ==========", context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		LOGGER.info("========== Test Suite Finished: {} ==========", context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		LOGGER.info("Test Started: {}", result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		LOGGER.info("Test Passed: {}", result.getName());

		String screenshotPath = ScreenshotUtils.captureScreenshot(result.getName());
		passedAttachScreenshotToAllure(screenshotPath);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		LOGGER.error("Test Failed: {}", result.getName());

		String screenshotPath = ScreenshotUtils.captureScreenshot(result.getName());
		failedAttachScreenshotToAllure(screenshotPath);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		LOGGER.warn("Test Skipped: {}", result.getName());
	}

	@Attachment(value = "Failure Screenshot", type = "image/png")
	public byte[] failedAttachScreenshotToAllure(String screenshotPath) {
		try {
			return Files.readAllBytes(Paths.get(screenshotPath));
		} catch (IOException e) {
			LOGGER.error("Failed to attach screenshot to Allure: {}", screenshotPath, e);
			return new byte[0];
		}
	}

	@Attachment(value = "Passed Screenshot", type = "image/png")
	public byte[] passedAttachScreenshotToAllure(String screenshotPath) {
		try {
			return Files.readAllBytes(Paths.get(screenshotPath));
		} catch (IOException e) {
			LOGGER.error("Failed to attach screenshot to Allure: {}", screenshotPath, e);
			return new byte[0];
		}
	}
}
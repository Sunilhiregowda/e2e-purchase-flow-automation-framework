package com.framework.utils;

import com.framework.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

public final class ElementActions {

	private static final Logger LOGGER = LogManager.getLogger(ElementActions.class);

	private ElementActions() {
		// Prevent object creation
	}

	private static WebDriver getDriver() {
		return DriverFactory.getDriver();
	}

	public static void type(By locator, String value) {
		try {
			WebElement element = WaitUtils.waitForVisibility(locator);
			element.clear();
			element.sendKeys(value);
			LOGGER.info("Entered value '{}' into element: {}", value, locator);
		} catch (Exception e) {
			LOGGER.error("Failed to type into element: {}", locator, e);
			throw e;
		}
	}

	public static void click(By locator) {
		try {
			WebElement element = WaitUtils.waitForClickable(locator);
			element.click();
			LOGGER.info("Clicked on element: {}", locator);
		} catch (ElementClickInterceptedException e) {
			LOGGER.warn("Normal click failed, trying JavaScript click for: {}", locator);
			jsClick(locator);
		} catch (Exception e) {
			LOGGER.error("Failed to click element: {}", locator, e);
			throw e;
		}
	}

	public static String getText(By locator) {
		try {
			String text = WaitUtils.waitForVisibility(locator).getText().trim();
			LOGGER.info("Fetched text '{}' from element: {}", text, locator);
			return text;
		} catch (Exception e) {
			LOGGER.error("Failed to get text from element: {}", locator, e);
			throw e;
		}
	}

	public static boolean isDisplayed(By locator) {
		try {
			boolean status = WaitUtils.waitForVisibility(locator).isDisplayed();
			LOGGER.info("Element displayed status for {} is {}", locator, status);
			return status;
		} catch (Exception e) {
			LOGGER.warn("Element not displayed: {}", locator);
			return false;
		}
	}

	public static void jsClick(By locator) {
		try {
			WebElement element = WaitUtils.waitForVisibility(locator);
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].click();", element);
			LOGGER.info("Clicked using JavaScript on element: {}", locator);
		} catch (Exception e) {
			LOGGER.error("JavaScript click failed for element: {}", locator, e);
			throw e;
		}
	}
}
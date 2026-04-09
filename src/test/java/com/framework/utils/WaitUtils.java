package com.framework.utils;

import com.framework.base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public final class WaitUtils {

	private WaitUtils() {
		// Prevent object creation
	}

	private static WebDriverWait getWait() {
		return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
	}

	public static WebElement waitForVisibility(By locator) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement waitForClickable(By locator) {
		return getWait().until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static boolean waitForUrlContains(String partialUrl) {
		return getWait().until(ExpectedConditions.urlContains(partialUrl));
	}

	public static boolean waitForText(By locator, String text) {
		return getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public static boolean waitForInvisibility(By locator) {
		return getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
}
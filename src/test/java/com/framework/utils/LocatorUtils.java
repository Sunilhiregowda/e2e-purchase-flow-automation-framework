package com.framework.utils;

import com.framework.base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class LocatorUtils {
	
	//A fallback locator strategy
	public static WebElement findFirstVisible(List<By> locators) {
		for (By locator : locators) {
			try {
				List<WebElement> elements = DriverFactory.getDriver().findElements(locator);
				if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
					return elements.get(0);
				}
			} catch (Exception ignored) {
			}
		}
		throw new RuntimeException("No visible element found using fallback locators.");
	}

	/**
	 * Safely escapes a string for use in XPath expressions to prevent injection.
	 * Handles strings containing single quotes, double quotes, or both.
	 */
	public static String safeXPathString(String value) {
		if (!value.contains("'")) {
			return "'" + value + "'";
		} else if (!value.contains("\"")) {
			return "\"" + value + "\"";
		} else {
			// Contains both quotes — use concat()
			StringBuilder sb = new StringBuilder("concat(");
			String[] parts = value.split("'", -1);
			for (int i = 0; i < parts.length; i++) {
				if (i > 0) {
					sb.append(",\"'\",");
				}
				sb.append("'").append(parts[i]).append("'");
			}
			sb.append(")");
			return sb.toString();
		}
	}
}
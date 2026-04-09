package com.framework.base;

import com.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

	private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class);
	private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

	private DriverFactory() {
		// Prevent object creation
	}

	public static void initDriver(String browser) {
		LOGGER.info("Initializing browser: {}", browser);

		if (browser == null || browser.trim().isEmpty()) {
			throw new IllegalArgumentException("Browser value cannot be null or empty");
		}

		boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

		switch (browser.toLowerCase().trim()) {

		case "chrome":
			DRIVER.set(new ChromeDriver(getChromeOptions(headless)));
			break;

		case "edge":
			DRIVER.set(new EdgeDriver(getEdgeOptions(headless)));
			break;

		case "firefox":
			DRIVER.set(new FirefoxDriver(getFirefoxOptions(headless)));
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		int implicitWait = ConfigReader.getInt("implicit.wait");
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

		LOGGER.info("Browser launched successfully: {}", browser);
	}

	private static ChromeOptions getChromeOptions(boolean headless) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");
		options.addArguments("--remote-allow-origins=*");

		if (headless) {
			options.addArguments("--headless=new");
		}

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		prefs.put("autofill.profile_enabled", false);
		prefs.put("autofill.credit_card_enabled", false);

		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	private static EdgeOptions getEdgeOptions(boolean headless) {
		EdgeOptions options = new EdgeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");

		if (headless) {
			options.addArguments("--headless=new");
		}

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		prefs.put("autofill.profile_enabled", false);
		prefs.put("autofill.credit_card_enabled", false);

		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	private static FirefoxOptions getFirefoxOptions(boolean headless) {
		FirefoxOptions options = new FirefoxOptions();

		if (headless) {
			options.addArguments("--headless");
		}

		return options;
	}

	public static WebDriver getDriver() {
		return DRIVER.get();
	}

	public static void quitDriver() {
		if (getDriver() != null) {
			LOGGER.info("Closing browser session");
			getDriver().quit();
			DRIVER.remove();
		}
	}
}
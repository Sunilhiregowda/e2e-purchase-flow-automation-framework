package com.framework.base;

import com.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

	private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser" })
	public void setUp(@Optional("chrome") String browser) {
		LOGGER.info("========== Test Execution Started ==========");
		DriverFactory.initDriver(browser);
		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));
		LOGGER.info("Application launched successfully");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
		LOGGER.info("========== Test Execution Completed ==========");
	}
}
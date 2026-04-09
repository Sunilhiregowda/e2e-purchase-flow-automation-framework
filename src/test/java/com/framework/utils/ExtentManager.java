package com.framework.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;

	private ExtentManager() {
		// Prevent object creation
	}

	public static synchronized ExtentReports getInstance() {
		if (extent == null) {
			ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport.html");
			reporter.config().setReportName("Automation Results");
			reporter.config().setDocumentTitle("Test Report");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
		}
		return extent;
	}
}

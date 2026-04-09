package com.framework.listeners;

import org.testng.*;
import com.aventstack.extentreports.*;
import com.framework.utils.ExtentManager;

public class ReportListener implements ITestListener {

	private static final ExtentReports extent = ExtentManager.getInstance();
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().skip("Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}

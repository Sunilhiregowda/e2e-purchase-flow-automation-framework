package com.framework.pages;

import com.framework.utils.ElementActions;
import org.openqa.selenium.By;

public class SuccessPage {

	// Locators
	private static final By SUCCESS_HEADER = By.cssSelector(".complete-header");
	private static final By SUCCESS_MESSAGE = By.cssSelector(".complete-text");

	// Page Actions / Methods
	public String getSuccessHeader() {
		return ElementActions.getText(SUCCESS_HEADER);
	}

	public String getSuccessMessage() {
		return ElementActions.getText(SUCCESS_MESSAGE);
	}
}
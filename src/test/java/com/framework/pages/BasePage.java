package com.framework.pages;

import com.framework.utils.ElementActions;
import org.openqa.selenium.By;

public abstract class BasePage {

	// Common locator shared across multiple pages
	private static final By PAGE_TITLE = By.cssSelector(".title");

	public String getPageTitle() {
		return ElementActions.getText(PAGE_TITLE);
	}
}

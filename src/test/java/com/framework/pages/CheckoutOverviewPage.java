package com.framework.pages;

import com.framework.utils.ElementActions;
import com.framework.utils.LocatorUtils;
import org.openqa.selenium.By;

public class CheckoutOverviewPage extends BasePage {

	// Locators
	private static final By FINISH_BUTTON = By.id("finish");

	// Page Actions / Methods
	public String getOverviewTitle() {
		return getPageTitle();
	}

	public boolean isProductDisplayed(String productName) {
		By product = By.xpath("//div[@class='inventory_item_name' and text()=" + LocatorUtils.safeXPathString(productName) + "]");
		return ElementActions.isDisplayed(product);
	}

	public SuccessPage clickFinish() {
		ElementActions.click(FINISH_BUTTON);
		return new SuccessPage();
	}
}
package com.framework.pages;

import com.framework.utils.ElementActions;
import com.framework.utils.LocatorUtils;
import org.openqa.selenium.By;

public class YourCartPage extends BasePage {

	// Locators
	private static final By CHECKOUT_BUTTON = By.id("checkout");

	// Page Actions / Methods
	public String getCartTitle() {
		return getPageTitle();
	}

	public boolean isProductDisplayed(String productName) {
		By product = By.xpath("//div[@class='inventory_item_name' and text()=" + LocatorUtils.safeXPathString(productName) + "]");
		return ElementActions.isDisplayed(product);
	}

	public CheckoutPage clickCheckout() {
		ElementActions.click(CHECKOUT_BUTTON);
		return new CheckoutPage();
	}
}
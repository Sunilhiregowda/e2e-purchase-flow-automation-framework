package com.framework.pages;

import com.framework.utils.ElementActions;
import com.framework.utils.LocatorUtils;
import org.openqa.selenium.By;

public class ProductPage extends BasePage {

	// Locators
	private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
	private static final By CART_ICON = By.cssSelector(".shopping_cart_link");

	// Page Actions / Methods
	public ProductPage addProductToCart(String productName) {
		By addToCartButton = By.xpath("//div[text()=" + LocatorUtils.safeXPathString(productName)
				+ "]/ancestor::div[@class='inventory_item']//button");
		ElementActions.click(addToCartButton);
		return this;
	}

	public String getCartBadgeCount() {
		return ElementActions.getText(CART_BADGE);
	}

	public YourCartPage clickCart() {
		ElementActions.click(CART_ICON);
		return new YourCartPage();
	}
}
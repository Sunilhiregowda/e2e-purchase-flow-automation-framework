package com.framework.pages;

import com.framework.utils.ElementActions;
import org.openqa.selenium.By;

public class CheckoutPage {

	// Locators
	private static final By FIRST_NAME_INPUT = By.id("first-name");
	private static final By LAST_NAME_INPUT = By.id("last-name");
	private static final By POSTAL_CODE_INPUT = By.id("postal-code");
	private static final By CONTINUE_BUTTON = By.id("continue");

	// Validation / Page Load Check
	public boolean isCheckoutPageDisplayed() {
		return ElementActions.isDisplayed(FIRST_NAME_INPUT);
	}

	// Page Actions / Methods
	public CheckoutPage enterFirstName(String firstName) {
		ElementActions.type(FIRST_NAME_INPUT, firstName);
		return this;
	}

	public CheckoutPage enterLastName(String lastName) {
		ElementActions.type(LAST_NAME_INPUT, lastName);
		return this;
	}

	public CheckoutPage enterPostalCode(String postalCode) {
		ElementActions.type(POSTAL_CODE_INPUT, postalCode);
		return this;
	}

	public CheckoutOverviewPage clickContinue() {
		ElementActions.click(CONTINUE_BUTTON);
		return new CheckoutOverviewPage();
	}

	public CheckoutOverviewPage fillCheckoutDetails(String firstName, String lastName, String postalCode) {
		if (!isCheckoutPageDisplayed()) {
			throw new RuntimeException("Checkout page did not load successfully. First Name field is not visible.");
		}

		return enterFirstName(firstName).enterLastName(lastName).enterPostalCode(postalCode).clickContinue();
	}
}
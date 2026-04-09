package com.framework.pages;

import com.framework.utils.ElementActions;
import org.openqa.selenium.By;

public class LoginPage {

	// Locators
	private static final By USERNAME_INPUT = By.id("user-name");
	private static final By PASSWORD_INPUT = By.id("password");
	private static final By LOGIN_BUTTON = By.id("login-button");

	// Page Actions / Methods
	public LoginPage enterUsername(String username) {
		ElementActions.type(USERNAME_INPUT, username);
		return this;
	}

	public LoginPage enterPassword(String password) {
		ElementActions.type(PASSWORD_INPUT, password);
		return this;
	}

	public ProductPage clickLogin() {
		ElementActions.click(LOGIN_BUTTON);
		return new ProductPage();
	}

	public ProductPage login(String username, String password) {
		return enterUsername(username).enterPassword(password).clickLogin();
	}
}
package com.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.framework.base.BaseTest;
import com.framework.listeners.RetryAnalyzer;
import com.framework.pages.CheckoutOverviewPage;
import com.framework.pages.CheckoutPage;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductPage;
import com.framework.pages.SuccessPage;
import com.framework.pages.YourCartPage;
import com.framework.utils.JsonDataReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo E2E Purchase Flow")
@Feature("Complete Purchase Flow")
public class PurchaseFlowTest extends BaseTest {

	@Test(description = "Validate complete end-to-end purchase flow", retryAnalyzer = RetryAnalyzer.class)
	@Severity(SeverityLevel.CRITICAL)
	@Description("Login, add 2 products, validate cart, checkout, and confirm order")
	public void validateCompletePurchaseFlow() {

		// Test Data Preparation
		JsonNode products = JsonDataReader.getNode("products");
		String username = JsonDataReader.getValue("username");
		String password = JsonDataReader.getValue("password");
		String firstName = JsonDataReader.getValue("firstName");
		String lastName = JsonDataReader.getValue("lastName");
		String postalCode = JsonDataReader.getValue("postalCode");

		Assert.assertNotNull(products, "Products test data is missing in JSON");
		Assert.assertTrue(products.size() >= 2, "At least 2 products must be available in test data");

		String product1 = products.get(0).asText();
		String product2 = products.get(1).asText();

		// Step 1: Login
		LoginPage loginPage = new LoginPage();
		ProductPage productPage = loginPage.login(username, password);

		Assert.assertEquals(productPage.getPageTitle(), "Products", "Products page title mismatch after login");

		// Step 2: Add 2 Products to Cart
		productPage.addProductToCart(product1).addProductToCart(product2);

		Assert.assertEquals(productPage.getCartBadgeCount(), "2", "Cart badge count is incorrect");

		// Step 3: Validate Cart Details
		YourCartPage cartPage = productPage.clickCart();

		Assert.assertEquals(cartPage.getCartTitle(), "Your Cart", "Cart page title mismatch");

		Assert.assertTrue(cartPage.isProductDisplayed(product1), "First product not found in cart");

		Assert.assertTrue(cartPage.isProductDisplayed(product2), "Second product not found in cart");

		// Step 4: Complete Checkout
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page did not load successfully.");

		CheckoutOverviewPage overviewPage = checkoutPage.fillCheckoutDetails(firstName, lastName, postalCode);

		Assert.assertEquals(overviewPage.getOverviewTitle(), "Checkout: Overview",
				"Checkout Overview page title mismatch");

		Assert.assertTrue(overviewPage.isProductDisplayed(product1), "First product missing in checkout overview");

		Assert.assertTrue(overviewPage.isProductDisplayed(product2), "Second product missing in checkout overview");

		// Step 5: Validate Successful Order Confirmation
		SuccessPage successPage = overviewPage.clickFinish();

		Assert.assertEquals(successPage.getSuccessHeader(), "Thank you for your order!", "Success header mismatch");

		Assert.assertTrue(successPage.getSuccessMessage().contains("Your order has been dispatched"),
				"Success confirmation message mismatch");
	}
}
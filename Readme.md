# SauceDemo E2E Selenium Automation Framework

## Overview
This framework automates the end-to-end purchase flow on SauceDemo using Java, Selenium, TestNG, Maven, and Page Object Model.

## Covered Scenarios
1. Login
2. Add any 2 products to cart
3. Validate cart details
4. Complete checkout
5. Validate successful order confirmation

## Framework Design
- Hybrid Automation Framework
- Page Object Model
- TestNG for execution and assertions
- Allure for reporting
- Log4j2 for logging
- JSON + Properties for externalized data
- Parallel execution supported

## Resilience / Flakiness Handling
- Explicit wait strategy
- RetryAnalyzer for flaky test retry
- Custom wrapper methods for element actions
- JavaScript fallback click
- Fallback locator strategy support

## How to Run
```bash
mvn clean test
```

## Generate Allure Report
```bash
allure serve allure-results
```

## Cross Browser Execution
Configured via `testng.xml`:
- Chrome
- Edge
- Firefox
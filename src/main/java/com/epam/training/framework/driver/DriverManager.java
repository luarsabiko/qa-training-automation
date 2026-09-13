package com.epam.training.framework.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

  private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
  private static final DriverManager INSTANCE = new DriverManager();

  private WebDriver driver;

  private DriverManager() {
  }

  public static DriverManager getInstance() {
    return INSTANCE;
  }

  public void initDriver(BrowserType browserType) {
    if (driver == null) {
      LOGGER.info("Initializing WebDriver");
      driver = DriverFactory.createDriver(browserType);
    }
  }

  public WebDriver getDriver() {
    if (driver == null) {
      throw new IllegalStateException("Driver not initialized. Call initDriver() first.");
    }
    return driver;
  }

  public void quitDriver() {
    if (driver != null) {
      LOGGER.info("Quitting WebDriver");
      driver.quit();
      driver = null;
    }
  }
}
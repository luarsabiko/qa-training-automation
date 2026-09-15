package com.epam.training.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

  private static final DriverManager INSTANCE = new DriverManager();

  private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

  private DriverManager() {
  }

  public static DriverManager getInstance() {
    return INSTANCE;
  }

  public void initDriver(BrowserType browserType) {
    if (driver.get() == null) {
      WebDriver rawDriver = BrowserFactoryProvider
          .getFactory(browserType)
          .createDriver();

      driver.set(new LoggingWebDriver(rawDriver));
    }
  }

  public WebDriver getDriver() {
    WebDriver currentDriver = driver.get();

    if (currentDriver == null) {
      throw new IllegalStateException("WebDriver has not been initialized");
    }

    return currentDriver;
  }

  public void quitDriver() {
    WebDriver currentDriver = driver.get();

    if (currentDriver != null) {
      try {
        currentDriver.quit();
      } finally {
        driver.remove();
      }
    }
  }
}
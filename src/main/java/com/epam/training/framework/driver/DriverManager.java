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
      WebDriver webDriver = BrowserFactoryProvider
          .getFactory(browserType)
          .createDriver();

      driver.set(webDriver);
    }
  }

  public WebDriver getDriver() {
    WebDriver webDriver = driver.get();

    if (webDriver == null) {
      throw new IllegalStateException("WebDriver has not been initialized");
    }

    return webDriver;
  }

  public void quitDriver() {
    WebDriver webDriver = driver.get();

    if (webDriver != null) {
      try {
        webDriver.quit();
      } finally {
        driver.remove();
      }
    }
  }
}
package com.epam.training.framework.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class BrowserFactory {

  private static final Logger LOGGER = LogManager.getLogger(BrowserFactory.class);

  public final WebDriver createDriver() {
    LOGGER.info("Creating driver using {}", getClass().getSimpleName());

    WebDriver driver = buildDriver();
    configureDriver(driver);

    return driver;
  }

  protected abstract WebDriver buildDriver();

  protected void configureDriver(WebDriver driver) {
    driver.manage().window().maximize();
  }
}
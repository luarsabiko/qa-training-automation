package com.epam.training.stepdefinitions;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.framework.driver.BrowserType;
import com.epam.training.framework.driver.DriverManager;
import com.epam.training.framework.utils.CookieBannerHandler;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Hooks {

  private static final Logger LOGGER = LogManager.getLogger(Hooks.class);

  @Before
  public void setUp() {
    String browserName = System.getProperty("browser", "chrome");
    LOGGER.info("Setting up Cucumber scenario with browser: {}", browserName);

    BrowserType browserType = BrowserType.valueOf(browserName.toUpperCase());
    DriverManager.getInstance().initDriver(browserType);

    WebDriver driver = DriverManager.getInstance().getDriver();
    driver.manage().timeouts().implicitlyWait(
        Duration.ofSeconds(ConfigReader.getInstance().getImplicitWaitTimeout()));

    driver.get(ConfigReader.getInstance().getBaseUrl());
    CookieBannerHandler.dismiss(driver);
  }

  @After
  public void tearDown() {
    DriverManager.getInstance().quitDriver();
  }
}
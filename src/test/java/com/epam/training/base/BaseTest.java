package com.epam.training.base;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.framework.driver.BrowserType;
import com.epam.training.framework.driver.DriverManager;
import com.epam.training.framework.utils.CookieBannerHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

public abstract class BaseTest {

  private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

  @BeforeMethod
  @Parameters({"browser"})
  public void setUp(@org.testng.annotations.Optional String browser, ITestContext context) {
    String browserName = System.getProperty("browser",
        browser != null ? browser : "chrome");

    LOGGER.info("Setting up test with browser: {}", browserName);

    BrowserType browserType = BrowserType.valueOf(browserName.toUpperCase());
    DriverManager.getInstance().initDriver(browserType);

    WebDriver driver = getDriver();

    driver.manage().timeouts().implicitlyWait(
        Duration.ofSeconds(ConfigReader.getInstance().getImplicitWaitTimeout()));

    driver.get(ConfigReader.getInstance().getBaseUrl());

    CookieBannerHandler.dismiss(driver);
  }

  @AfterMethod
  public void tearDown() {
    DriverManager.getInstance().quitDriver();
  }

  protected WebDriver getDriver() {
    return DriverManager.getInstance().getDriver();
  }
}
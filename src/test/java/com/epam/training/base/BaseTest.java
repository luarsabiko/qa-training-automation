package com.epam.training.base;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.framework.driver.BrowserType;
import com.epam.training.framework.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;


public abstract class BaseTest {

  protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

  private static final By COOKIE_ACCEPT_BUTTON = By.id("onetrust-accept-btn-handler");
  private static final Duration COOKIE_BANNER_TIMEOUT = Duration.ofSeconds(5);

  @BeforeMethod
  @Parameters("browser")
  public void setUp(@Optional("chrome") String browserParam) {
    String browserName = System.getProperty("browser", browserParam);
    LOGGER.info("Setting up test with browser: {}", browserName);

    DriverManager.getInstance().initDriver(BrowserType.valueOf(browserName.toUpperCase()));
    getDriver().manage().timeouts().implicitlyWait(
        Duration.ofSeconds(ConfigReader.getInstance().getImplicitWaitTimeout()));
    getDriver().manage().window().maximize();
    getDriver().get(ConfigReader.getInstance().getBaseUrl());

    dismissCookieBannerIfPresent();
  }

  @AfterMethod
  public void tearDown() {
    LOGGER.info("Tearing down test.");
    DriverManager.getInstance().quitDriver();
  }

  protected WebDriver getDriver() {
    return DriverManager.getInstance().getDriver();
  }

  private void dismissCookieBannerIfPresent() {
    try {
      WebDriverWait shortWait = new WebDriverWait(getDriver(), COOKIE_BANNER_TIMEOUT);
      WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT_BUTTON));
      acceptButton.click();
      LOGGER.info("Cookie consent banner dismissed.");
    } catch (TimeoutException e) {
      LOGGER.debug("No cookie consent banner appeared within timeout; continuing.");
    }
  }
}

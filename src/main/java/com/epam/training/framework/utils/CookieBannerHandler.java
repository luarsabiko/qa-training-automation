package com.epam.training.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class CookieBannerHandler {

  private static final Logger LOGGER = LogManager.getLogger(CookieBannerHandler.class);
  private static final Duration BANNER_TIMEOUT = Duration.ofSeconds(5);
  private static final By ACCEPT_BUTTON = By.id("onetrust-accept-btn-handler");

  private CookieBannerHandler() {
  }

  public static void dismiss(WebDriver driver) {
    try {
      new WebDriverWait(driver, BANNER_TIMEOUT)
          .until(ExpectedConditions.elementToBeClickable(ACCEPT_BUTTON))
          .click();
      LOGGER.info("Cookie banner dismissed");
    } catch (TimeoutException e) {
      LOGGER.debug("Cookie banner not present, continuing");
    }
  }
}
package com.epam.training.pages;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.framework.utils.WebElementActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractPage {

  protected static final Logger LOGGER = LogManager.getLogger(AbstractPage.class);
  protected final WebDriver driver;
  protected final WebDriverWait wait;
  private final WebElementActions actions;

  protected AbstractPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver,
        Duration.ofSeconds(ConfigReader.getInstance().getExplicitWaitTimeout()));
    this.actions = new WebElementActions(driver);
  }

  public abstract boolean isPageLoaded();

  protected WebElement waitForVisibility(By locator) {
    LOGGER.debug("Waiting for visibility: {}", locator);
    return actions.waitForVisibility(locator);
  }

  protected List<WebElement> waitForAllVisible(By locator) {
    LOGGER.debug("Waiting for all elements visibility: {}", locator);
    return actions.waitForAllVisible(locator);
  }

  protected WebElement waitForFirstVisible(By locator) {
    LOGGER.debug("Waiting for first visible element among matches: {}", locator);
    return actions.waitForFirstVisible(locator);
  }

  protected void click(By locator) {
    LOGGER.info("Clicking element: {}", locator);
    actions.click(locator);
  }

  protected void clickFirstVisible(By locator) {
    LOGGER.info("Clicking first visible element among matches: {}", locator);
    actions.clickFirstVisible(locator);
  }

  protected void type(By locator, String text) {
    LOGGER.info("Typing '{}' into: {}", text, locator);
    actions.type(locator, text);
  }

  protected void typeSecret(By locator, String text) {
    LOGGER.info("Typing '******' into: {}", locator);
    actions.typeSecret(locator, text);
  }
}
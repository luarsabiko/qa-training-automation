package com.epam.training.pages;

import com.epam.training.framework.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractPage {

  protected static final Logger LOGGER = LogManager.getLogger(AbstractPage.class);
  protected final WebDriver driver;
  protected final WebDriverWait wait;

  protected AbstractPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver,
        Duration.ofSeconds(ConfigReader.getInstance().getExplicitWaitTimeout()));
  }

  protected WebElement waitForVisibility(By locator) {
    LOGGER.debug("Waiting for visibility: {}", locator);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected List<WebElement> waitForAllVisible(By locator) {
    LOGGER.debug("Waiting for all elements visibility: {}", locator);
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  protected WebElement waitForClickable(By locator) {
    LOGGER.debug("Waiting for element clickable: {}", locator);
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected WebElement waitForFirstVisible(By locator) {
    LOGGER.debug("Waiting for first visible element among matches: {}", locator);
    return wait.until(driver -> {
      List<WebElement> elements = driver.findElements(locator);
      for (WebElement element : elements) {
        if (element.isDisplayed()) {
          return element;
        }
      }
      return null;
    });
  }

  protected void click(By locator) {
    LOGGER.info("Clicking element: {}", locator);
    waitForClickable(locator).click();
  }

  protected void clickFirstVisible(By locator) {
    LOGGER.info("Clicking first visible element among matches: {}", locator);
    WebElement element = waitForFirstVisible(locator);
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.click();
  }

  protected void type(By locator, String text) {
    LOGGER.info("Typing '{}' into: {}", text, locator);
    WebElement element = waitForVisibility(locator);
    element.clear();
    element.sendKeys(text);
  }

  protected void typeSecret(By locator, String text) {
    LOGGER.info("Typing '******' into: {}", locator);
    WebElement element = waitForVisibility(locator);
    element.clear();
    element.sendKeys(text);
  }

  public abstract boolean isPageLoaded();
}

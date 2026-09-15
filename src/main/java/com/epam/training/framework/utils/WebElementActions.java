package com.epam.training.framework.utils;

import com.epam.training.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebElementActions {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public WebElementActions(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver,
        Duration.ofSeconds(ConfigReader.getInstance().getExplicitWaitTimeout()));
  }

  public WebElement waitForVisibility(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public List<WebElement> waitForAllVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  public WebElement waitForClickable(By locator) {
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  public WebElement waitForFirstVisible(By locator) {
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

  public void click(By locator) {
    waitForClickable(locator).click();
  }

  public void clickFirstVisible(By locator) {
    WebElement element = waitForFirstVisible(locator);
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.click();
  }

  public void type(By locator, String text) {
    WebElement element = waitForVisibility(locator);
    element.clear();
    element.sendKeys(text);
  }

  public void typeSecret(By locator, String text) {
    WebElement element = waitForVisibility(locator);
    element.clear();
    element.sendKeys(text);
  }
}
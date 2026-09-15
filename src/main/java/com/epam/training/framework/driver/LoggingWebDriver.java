package com.epam.training.framework.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class LoggingWebDriver
    implements WebDriver, JavascriptExecutor, TakesScreenshot, HasCapabilities {

  private static final Logger LOGGER = LogManager.getLogger(LoggingWebDriver.class);

  private final WebDriver delegate;

  public LoggingWebDriver(WebDriver delegate) {
    this.delegate = Objects.requireNonNull(
        delegate,
        "WebDriver delegate must not be null"
    );
  }

  @Override
  public void get(String url) {
    LOGGER.info("Navigate to URL: {}", url);
    delegate.get(url);
  }

  @Override
  public String getCurrentUrl() {
    return delegate.getCurrentUrl();
  }

  @Override
  public String getTitle() {
    return delegate.getTitle();
  }

  @Override
  public List<WebElement> findElements(By by) {
    LOGGER.debug("Find elements: {}", by);
    return delegate.findElements(by);
  }

  @Override
  public WebElement findElement(By by) {
    LOGGER.debug("Find element: {}", by);
    return delegate.findElement(by);
  }

  @Override
  public String getPageSource() {
    return delegate.getPageSource();
  }

  @Override
  public void close() {
    LOGGER.info("Close browser window");
    delegate.close();
  }

  @Override
  public void quit() {
    LOGGER.info("Quit browser");
    delegate.quit();
  }

  @Override
  public Set<String> getWindowHandles() {
    return delegate.getWindowHandles();
  }

  @Override
  public String getWindowHandle() {
    return delegate.getWindowHandle();
  }

  @Override
  public WebDriver.TargetLocator switchTo() {
    return delegate.switchTo();
  }

  @Override
  public WebDriver.Navigation navigate() {
    return delegate.navigate();
  }

  @Override
  public WebDriver.Options manage() {
    return delegate.manage();
  }

  @Override
  public Object executeScript(String script, Object... args) {
    LOGGER.debug("Execute JavaScript");
    return ((JavascriptExecutor) delegate).executeScript(script, args);
  }

  @Override
  public Object executeAsyncScript(String script, Object... args) {
    LOGGER.debug("Execute asynchronous JavaScript");
    return ((JavascriptExecutor) delegate).executeAsyncScript(script, args);
  }

  @Override
  public <X> X getScreenshotAs(OutputType<X> target) {
    LOGGER.debug("Capture screenshot");
    return ((TakesScreenshot) delegate).getScreenshotAs(target);
  }

  @Override
  public Capabilities getCapabilities() {
    return ((HasCapabilities) delegate).getCapabilities();
  }
}
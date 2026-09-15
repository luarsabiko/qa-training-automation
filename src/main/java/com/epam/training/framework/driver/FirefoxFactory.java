package com.epam.training.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxFactory extends BrowserFactory {

  @Override
  protected WebDriver buildDriver() {
    WebDriverManager.firefoxdriver().setup();
    return new FirefoxDriver();
  }
}
package com.epam.training.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeFactory extends BrowserFactory {

  @Override
  protected WebDriver buildDriver() {
    WebDriverManager.edgedriver().setup();
    return new EdgeDriver();
  }
}
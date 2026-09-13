package com.epam.training.framework.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class DriverFactory {

  private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class);

  private DriverFactory() {
  }

  public static WebDriver createDriver(BrowserType browserType) {
    LOGGER.info("Creating driver instance for browser: {}", browserType);

    WebDriver driver;
    switch (browserType) {
      case FIREFOX:
        io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        break;
      case EDGE:
        io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        break;
      case CHROME:
      default:
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        break;
    }

    LOGGER.debug("Driver instance created: {}", driver.getClass().getSimpleName());
    return driver;
  }
}

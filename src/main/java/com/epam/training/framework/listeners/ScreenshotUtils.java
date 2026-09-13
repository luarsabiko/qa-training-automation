package com.epam.training.framework.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

  private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtils.class);
  private static final Path SCREENSHOT_DIR =
      Paths.get(System.getProperty("user.dir"), "target", "test-output", "screenshots");

  private ScreenshotUtils() {
  }

  public static String captureScreenshot(WebDriver driver, String testName) {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    Path filePath = SCREENSHOT_DIR.resolve(testName + "_" + timestamp + ".png");

    try {
      Files.createDirectories(SCREENSHOT_DIR);
      File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      Files.copy(srcFile.toPath(), filePath);
      LOGGER.info("Screenshot saved to: {}", filePath.toAbsolutePath());
    } catch (IOException e) {
      LOGGER.error("Failed to save screenshot for test: {}", testName, e);
    }
    return filePath.toAbsolutePath().toString();
  }
}
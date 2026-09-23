package com.epam.training.framework.listeners;

import com.epam.reportportal.service.ReportPortal;
import com.epam.training.framework.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.Calendar;

public class TestListener implements ITestListener {

  private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.info("===== Starting test: {} =====", result.getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.info("===== Test PASSED: {} =====", result.getName());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.warn("===== Test SKIPPED: {} =====", result.getName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    LOGGER.error("===== Test FAILED: {} =====", result.getName());

    try {
      WebDriver driver = DriverManager.getInstance().getDriver();
      String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());

      if (screenshotPath != null) {
        File screenshotFile = new File(screenshotPath);

        if (screenshotFile.exists()) {
          ReportPortal.emitLog(
              "Screenshot on failure: " + result.getName(),
              "ERROR",
              Calendar.getInstance().getTime(),
              screenshotFile
          );
          LOGGER.info("Screenshot attached to Report Portal for: {}", result.getName());
        }
      }
    } catch (Exception e) {
      LOGGER.warn("Could not attach screenshot to Report Portal: {}", e.getMessage());
    }
  }
}
package com.epam.training.framework.listeners;

import com.epam.training.framework.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.info("===== Starting test: {} =====", result.getMethod().getMethodName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.info("===== Test PASSED: {} =====", result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    String testName = result.getMethod().getMethodName();
    LOGGER.error("===== Test FAILED: {} =====", testName, result.getThrowable());

    String screenshotPath = ScreenshotUtils.captureScreenshot(
        DriverManager.getInstance().getDriver(), testName);
    LOGGER.info("Screenshot for failed test '{}' saved at: {}", testName, screenshotPath);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LOGGER.warn("===== Test SKIPPED: {} =====", result.getMethod().getMethodName());
  }
}

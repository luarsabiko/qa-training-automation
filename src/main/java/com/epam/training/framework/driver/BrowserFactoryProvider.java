package com.epam.training.framework.driver;

public final class BrowserFactoryProvider {

  private BrowserFactoryProvider() {
  }

  public static BrowserFactory getFactory(BrowserType browserType) {
    if (browserType == null) {
      throw new IllegalArgumentException("Browser type must not be null");
    }

    return browserType.getFactory();
  }
}
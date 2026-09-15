package com.epam.training.framework.driver;

import java.util.Map;

public final class BrowserFactoryProvider {

  private static final Map<BrowserType, BrowserFactory> FACTORIES = Map.of(
      BrowserType.CHROME, new ChromeFactory(),
      BrowserType.FIREFOX, new FirefoxFactory(),
      BrowserType.EDGE, new EdgeFactory()
  );

  private BrowserFactoryProvider() {
  }

  public static BrowserFactory getFactory(BrowserType browserType) {
    if (browserType == null) {
      throw new IllegalArgumentException("Browser type must not be null");
    }

    BrowserFactory factory = FACTORIES.get(browserType);

    if (factory == null) {
      throw new IllegalArgumentException(
          "Unsupported browser type: " + browserType
      );
    }

    return factory;
  }
}





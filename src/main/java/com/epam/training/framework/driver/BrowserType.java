package com.epam.training.framework.driver;

import java.util.function.Supplier;

public enum BrowserType {

  CHROME(ChromeFactory::new),
  FIREFOX(FirefoxFactory::new),
  EDGE(EdgeFactory::new);

  private final Supplier<BrowserFactory> factorySupplier;

  BrowserType(Supplier<BrowserFactory> factorySupplier) {
    this.factorySupplier = factorySupplier;
  }

  public BrowserFactory getFactory() {
    return factorySupplier.get();
  }
}
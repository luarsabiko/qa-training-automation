package com.epam.training.framework.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

  private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);
  private static final ConfigReader INSTANCE = new ConfigReader();

  private final Properties properties = new Properties();

  private ConfigReader() {
    String env = System.getProperty("env", "qa");
    String fileName = "config/" + env + ".properties";
    LOGGER.info("Loading configuration file: {}", fileName);

    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Properties file not found: " + fileName);
      }
      properties.load(inputStream);
    } catch (IOException e) {
      LOGGER.error("Failed to load configuration file: {}", fileName, e);
      throw new RuntimeException("Could not load config file: " + fileName, e);
    }
  }

  public static ConfigReader getInstance() {
    return INSTANCE;
  }

  public String get(String key) {
    String value = properties.getProperty(key);
    if (value == null) {
      LOGGER.warn("Property '{}' not found in configuration.", key);
    }
    return value;
  }

  public String getBaseUrl() {
    return get("base.url");
  }

  public long getExplicitWaitTimeout() {
    return Long.parseLong(get("explicit.wait.timeout"));
  }

  public long getImplicitWaitTimeout() {
    return Long.parseLong(get("implicit.wait.timeout"));
  }

  public String getTestUserEmail() {
    return System.getProperty("test.user.email", get("test.user.email"));
  }

  public String getTestUserPassword() {
    return System.getProperty("test.user.password", get("test.user.password"));
  }
}

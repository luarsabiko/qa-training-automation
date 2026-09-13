package com.epam.training.pages;

import com.epam.training.models.LoginCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {

  private static final By EMAIL_INPUT = By.id("userName");
  private static final By CONTINUE_BUTTON = By.id("kc-login-next");
  private static final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
  private static final By SIGN_IN_BUTTON = By.id("kc-login");

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean isPageLoaded() {
    return waitForVisibility(EMAIL_INPUT).isDisplayed();
  }

  public LoginPage enterEmail(String email) {
    type(EMAIL_INPUT, email);
    return this;
  }

  public LoginPage clickContinue() {
    click(CONTINUE_BUTTON);
    waitForVisibility(PASSWORD_INPUT);
    return this;
  }

  public LoginPage enterPassword(String password) {
    typeSecret(PASSWORD_INPUT, password);
    return this;
  }

  public HomePage submitLogin() {
    click(SIGN_IN_BUTTON);
    wait.until(ExpectedConditions.urlContains("qa.training.epam.com"));
    return new HomePage(driver);
  }

  public HomePage login(LoginCredentials credentials) {
    enterEmail(credentials.getEmail());
    clickContinue();
    enterPassword(credentials.getPassword());
    return submitLogin();
  }
}

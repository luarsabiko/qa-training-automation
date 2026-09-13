package com.epam.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {

  private static final By SIGN_IN_BUTTON = By.cssSelector("[data-name='SignIn']");
  private static final By SKILLS_NAV_LINK = By.cssSelector("[data-name='Skills']");
  private static final By PROGRAMS_NAV_LINK = By.cssSelector("[data-name='Trainings']");
  private static final By AVATAR_MENU_BUTTON = By.cssSelector(".menu-button button[aria-haspopup='menu']");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean isPageLoaded() {
    return waitForVisibility(SKILLS_NAV_LINK).isDisplayed();
  }

  public boolean isSignedOut() {
    return waitForVisibility(SIGN_IN_BUTTON).isDisplayed();
  }

  public boolean isSignedIn() {
    return waitForVisibility(AVATAR_MENU_BUTTON).isDisplayed();
  }

  public LoginPage clickSignIn() {
    click(SIGN_IN_BUTTON);
    return new LoginPage(driver);
  }

  public SkillsPage clickSkills() {
    click(SKILLS_NAV_LINK);
    return new SkillsPage(driver);
  }

  public ProgramsPage clickPrograms() {
    click(PROGRAMS_NAV_LINK);
    return new ProgramsPage(driver);
  }
}

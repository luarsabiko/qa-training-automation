package com.epam.training.tests;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.models.LoginCredentials;
import com.epam.training.pages.HomePage;
import com.epam.training.pages.LoginPage;
import com.epam.training.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.training.framework.listeners.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

  @Test(description = "Verify user can log in successfully with valid credentials")
  public void testSuccessfulLogin() {
    HomePage homePage = new HomePage(getDriver());
    Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load correctly.");
    Assert.assertTrue(homePage.isSignedOut(), "Sign in button should be visible before login.");

    LoginPage loginPage = homePage.clickSignIn();
    Assert.assertTrue(loginPage.isPageLoaded(), "Login page did not load correctly.");

    LoginCredentials credentials = new LoginCredentials(
        ConfigReader.getInstance().getTestUserEmail(),
        ConfigReader.getInstance().getTestUserPassword()
    );

    HomePage loggedInHomePage = loginPage.login(credentials);

    Assert.assertTrue(loggedInHomePage.isPageLoaded(), "Home page did not load after login.");
    Assert.assertTrue(loggedInHomePage.isSignedIn(), "Avatar menu should be visible after successful login.");
  }
}

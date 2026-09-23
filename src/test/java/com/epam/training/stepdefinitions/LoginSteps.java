package com.epam.training.stepdefinitions;

import com.epam.training.framework.config.ConfigReader;
import com.epam.training.framework.driver.DriverManager;
import com.epam.training.models.LoginCredentials;
import com.epam.training.pages.HomePage;
import com.epam.training.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

  private HomePage homePage;

  @Given("^the training homepage is open$")
  public void theHomepageIsOpen() {
    homePage = new HomePage(DriverManager.getInstance().getDriver());
    Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load correctly.");
  }

  @When("^I log in as \"(user1|user2)\"$")
  public void iLogInAs(String userKey) {
    LoginCredentials credentials = "user1".equals(userKey)
        ? new LoginCredentials(
        ConfigReader.getInstance().getTestUserEmail(),
        ConfigReader.getInstance().getTestUserPassword())
        : new LoginCredentials(
            ConfigReader.getInstance().getTestUser2Email(),
            ConfigReader.getInstance().getTestUser2Password());

    LoginPage loginPage = homePage.clickSignIn();
    Assert.assertTrue(loginPage.isPageLoaded(), "Login page did not load correctly.");
    homePage = loginPage.login(credentials);
  }

  @Then("^I should be signed in successfully$")
  public void iShouldBeSignedIn() {
    Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load after login.");
    Assert.assertTrue(homePage.isSignedIn(), "Avatar menu should be visible after successful login.");
  }
}
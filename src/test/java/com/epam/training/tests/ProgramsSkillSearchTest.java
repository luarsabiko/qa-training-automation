package com.epam.training.tests;

import com.epam.training.pages.HomePage;
import com.epam.training.pages.ProgramsPage;
import com.epam.training.services.SearchResultValidator;
import com.epam.training.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProgramsSkillSearchTest extends BaseTest {

  private static final String SEARCH_SUBSTRING = "Jav";

  @Test(description = "Verify skill search filter returns only matching results")
  public void testSkillSearchFiltersResults() {
    HomePage homePage = new HomePage(getDriver());
    Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load correctly.");

    ProgramsPage programsPage = homePage.clickPrograms();
    Assert.assertTrue(programsPage.isPageLoaded(), "Programs page did not load correctly.");

    programsPage.openSkillsFilter();
    programsPage.searchSkill(SEARCH_SUBSTRING);

    List<String> filteredResults = programsPage.waitForFilteredSkillOptions(SEARCH_SUBSTRING);
    Assert.assertFalse(filteredResults.isEmpty(),
        "Expected at least one skill result for substring: " + SEARCH_SUBSTRING);

    SearchResultValidator validator = new SearchResultValidator();
    List<String> nonMatchingItems = validator.findItemsNotContainingSubstring(filteredResults, SEARCH_SUBSTRING);

    Assert.assertTrue(nonMatchingItems.isEmpty(),
        "Found results not matching substring '" + SEARCH_SUBSTRING + "': " + nonMatchingItems);
  }
}

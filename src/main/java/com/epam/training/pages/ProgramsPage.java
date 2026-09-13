package com.epam.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramsPage extends AbstractPage {

  private static final By PAGE_TITLE = By.xpath("//h1[normalize-space()='Training programs']");

  private static final By SKILL_FILTER_TOGGLE = By.xpath("//div[@role='button' and .//div[contains(text(),'Skill')]]");

  private static final By SELECTED_SKILL_VALUE =
      By.cssSelector(".uui-filters-panel-item-toggler-selection");

  private static final By SKILL_SEARCH_INPUT =
      By.cssSelector("input[type='search'][placeholder='Search']");

  private static final By SKILL_OPTION_ITEMS = By.cssSelector("div[role='option']");
  private static final By SKILL_OPTION_TEXT = By.cssSelector(".text");

  public ProgramsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean isPageLoaded() {
    return waitForVisibility(PAGE_TITLE).isDisplayed();
  }

  public void openSkillsFilter() {
    clickFirstVisible(SKILL_FILTER_TOGGLE);
  }

  public void searchSkill(String substring) {
    type(SKILL_SEARCH_INPUT, substring);
  }

  public List<String> getVisibleSkillOptions() {
    return waitForAllVisible(SKILL_OPTION_ITEMS).stream()
        .map(option -> option.findElement(SKILL_OPTION_TEXT).getText())
        .collect(Collectors.toList());
  }

  public List<String> waitForFilteredSkillOptions(String substring) {
    LOGGER.debug("Waiting for skill options to reflect filter: {}", substring);
    wait.until(driver -> {
      List<WebElement> options = driver.findElements(SKILL_OPTION_ITEMS);
      if (options.isEmpty()) {
        return false;
      }
      return options.stream()
          .map(option -> option.findElement(SKILL_OPTION_TEXT).getText())
          .allMatch(text -> text.toLowerCase().contains(substring.toLowerCase()));
    });
    return getVisibleSkillOptions();
  }

  public String getSelectedSkillFilterText() {
    return waitForFirstVisible(SELECTED_SKILL_VALUE).getText();
  }
}

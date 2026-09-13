package com.epam.training.pages;

import com.epam.training.models.Skill;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SkillsPage extends AbstractPage {

  private static final By PAGE_TITLE = By.cssSelector("h1.block-title");

  public SkillsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean isPageLoaded() {
    return waitForVisibility(PAGE_TITLE).getText().equalsIgnoreCase("Training skills");
  }

  public ProgramsPage openProgramsForSkill(Skill skill) {
    By programsButtonForSkill = By.xpath(
        "//h3[normalize-space()='" + skill.getName() + "']/ancestor::a//a[@data-name='programBtn']"
    );
    click(programsButtonForSkill);
    return new ProgramsPage(driver);
  }
}

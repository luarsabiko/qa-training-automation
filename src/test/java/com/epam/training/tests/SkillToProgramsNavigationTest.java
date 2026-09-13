package com.epam.training.tests;

import com.epam.training.models.Skill;
import com.epam.training.pages.HomePage;
import com.epam.training.pages.ProgramsPage;
import com.epam.training.pages.SkillsPage;
import com.epam.training.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SkillToProgramsNavigationTest extends BaseTest {

  @Test(description = "Verify navigating from a skill to Programs page pre-selects the same skill filter")
  public void testSkillFilterCarriesOverToPrograms() {
    Skill skill = new Skill(".NET");

    HomePage homePage = new HomePage(getDriver());
    Assert.assertTrue(homePage.isPageLoaded(), "Home page did not load correctly.");

    SkillsPage skillsPage = homePage.clickSkills();
    Assert.assertTrue(skillsPage.isPageLoaded(), "Skills page did not load correctly.");

    ProgramsPage programsPage = skillsPage.openProgramsForSkill(skill);
    Assert.assertTrue(programsPage.isPageLoaded(), "Programs page did not load correctly.");

    String selectedSkill = programsPage.getSelectedSkillFilterText();
    Assert.assertEquals(selectedSkill, skill.getName(),
        "Selected skill filter does not match the skill navigated from.");
  }
}

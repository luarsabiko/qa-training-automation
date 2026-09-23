package com.epam.training.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.epam.training.stepdefinitions",
    plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "com.epam.reportportal.cucumber.ScenarioReporter"
    }
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
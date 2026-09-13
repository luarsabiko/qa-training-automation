# QA Training Automation Framework

## Overview

This project is a Selenium WebDriver + Java + TestNG test automation framework built against the [EPAM Campus QA Training website](https://qa.training.epam.com/). It fulfills the practical tasks for **Module 8 (WebDriver)** and **Module 9 (Test Automation Framework)**.

The framework automates three linear, real-world user scenarios on the system under test, and wraps them in a reusable framework layer providing driver management, configuration handling, logging, and failure screenshot capture. Tests run sequentially (single-threaded) — no parallel execution is used.

---

## Tech Stack

- **Java 17**
- **Selenium WebDriver 4.21.0**
- **TestNG 7.10.2**
- **WebDriverManager (Bonigarcia) 5.9.1** — automatic browser driver binary management
- **Log4j2 2.23.1** — logging
- **Maven** — build and dependency management

---

## Project Structure

```
src
├── main
│   └── java
│       └── com
│           └── epam
│               └── training
│                   ├── framework
│                   │   ├── driver      # DriverManager (driver lifecycle), DriverFactory, BrowserType
│                   │   ├── config      # ConfigReader for environment-based properties
│                   │   └── listeners   # TestListener (TestNG lifecycle hooks), ScreenshotUtils
│                   ├── pages           # Page Objects (AbstractPage, HomePage, LoginPage, SkillsPage, ProgramsPage)
│                   ├── models          # Business objects (LoginCredentials, Skill)
│                   └── services        # Plain validation logic (SearchResultValidator)
└── test
    ├── java
    │   └── com
    │       └── epam
    │           └── training
    │               └── tests
    │                   ├── base        # BaseTest (TestNG setup/teardown, cookie banner handling)
    │                   └── *Test.java  # Test scenario classes
    └── resources
        ├── config                     # qa.properties, staging.properties
        ├── suites                     # smoke.xml, regression.xml
        └── log4j2.xml                 # Logging configuration
```

---

## How This Maps to the Task Requirements

| # | Requirement | Where it's implemented |
|---|---|---|
| 1 | WebDriverManager for managing drivers across browsers | `DriverFactory` (creates Chrome/Firefox/Edge instances via Bonigarcia WebDriverManager) + `DriverManager` (holds the active driver instance) |
| 2 | PageObject / PageFactory for abstract pages | `AbstractPage` centralizes waits, clicks, typing, text retrieval; `HomePage`, `LoginPage`, `SkillsPage`, `ProgramsPage` extend it |
| 3 | Business model / business objects | `LoginCredentials`, `Skill` in `models` |
| 4 | Property files with test data for ≥2 environments | `qa.properties`, `staging.properties` in `src/test/resources/config`, loaded via `ConfigReader` based on `-Denv` |
| 5 | XML suites for Smoke and Regression | `smoke.xml` (critical login path only), `regression.xml` (full scenario set, sequential) |
| 6 | Screenshot on failure + log of saved path | `TestListener.onTestFailure()` → `ScreenshotUtils`, saved under `/test-output/screenshots`, path logged via Log4j2 |
| 7 | Flexible parameters (browser, suite, environment) for CI | All controlled via Maven system properties: `-Dbrowser`, `-Denv`, `-DsuiteXmlFile` |
| 8 | Logging with format, levels, console + daily file | `log4j2.xml`: pattern layout with timestamp/thread/level/logger; `DEBUG` for waits, `INFO` for actions, `ERROR`/`WARN` for failures; console + rolling daily file appenders |
| 9 | Test results on job graphics + screenshots archived as CI artifacts | **Not yet implemented** — requires a CI pipeline config (e.g. GitHub Actions) that publishes the Surefire/TestNG report and archives `/test-output/screenshots`. See [Remaining Work](#remaining-work) below. |

### Bonus tasks
- Ready-made Selenium wrapper (Selenide/Serenity/JDI/HtmlElements): **not implemented**.
- Element highlighting during actions: **not implemented**.

---

## Test Scenarios

### 1. `LoginTest`
Logs into the EPAM Campus platform with valid credentials and verifies the UI reflects a successful, authenticated session (sign-in button disappears, avatar menu appears).

### 2. `ProgramsSkillSearchTest`
Navigates to the Programs page, opens the Skill filter, searches using a substring, and verifies that every returned skill option contains the searched substring.

### 3. `SkillToProgramsNavigationTest`
Navigates to the Skills page, selects a specific skill's "Programs" link, and verifies that the Programs page's skill filter is pre-populated with the same skill that was clicked from.

All three scenarios use Page Objects, multiple locator strategies (CSS selectors, ID, attribute selectors, and XPath), explicit and implicit waits, and contain clear assertions for every verification step.

---

## Locator Strategy

Locators avoid CSS-Modules-style auto-generated class names (e.g., `nav-bar_menu-items__UKU84`), which regenerate on rebuild and are fragile. Instead, they rely on:

- Stable semantic attributes (`data-name`, `id`, `name`, `role`, `aria-*`)
- Non-hashed utility classes where present
- XPath with visible text matching only where no stable attribute exists

---

## Framework Features (Module 9)

- **Driver Management** — `DriverManager` is a singleton holding a single active `WebDriver` instance for the current (single-threaded) test run.
- **Driver Factory** — `DriverFactory` creates the correct browser instance (Chrome, Firefox, Edge) based on a configurable parameter, using WebDriverManager for automatic driver binary resolution.
- **Page Object Model** — All pages extend `AbstractPage`, which centralizes WebDriver waits, clicks, typing, and text retrieval, eliminating duplication across pages.
- **Business Objects** — `LoginCredentials` and `Skill` model domain entities used across pages and tests.
- **Configuration Management** — `ConfigReader` loads environment-specific properties (`qa.properties`, `staging.properties`), selectable via the `-Denv` system property. Test credentials can be overridden via system properties instead of being hardcoded.
- **Logging** — Every meaningful action (clicks, typing, waits) is logged via Log4j2, at appropriate levels (`DEBUG` for waits, `INFO` for actions, `ERROR` for failures). Logs are written simultaneously to console and to a new dated file each day under `/logs`.
- **Screenshot on Failure** — `TestListener` (registered via `@Listeners` on `BaseTest`, so it applies regardless of how tests are executed) automatically captures a screenshot on any test failure via `ScreenshotUtils`, saving it under `/test-output/screenshots` and logging its absolute path.
- **Flexible Execution Parameters** — Browser, environment, and suite file are all configurable via Maven system properties, enabling CI integration.
- **TestNG Suites** — `smoke.xml` runs only the critical login path; `regression.xml` runs the full scenario set sequentially.

---

### Run the smoke suite

```bash
mvn test "-DsuiteXmlFile=src/test/resources/suites/smoke.xml"
```

### Run the regression suite

```bash
mvn test "-DsuiteXmlFile=src/test/resources/suites/regression.xml"
```
---

## Remaining Work

To fully satisfy the task's acceptance criteria, the following still need to be added:

- **CI pipeline** (e.g. a GitHub Actions workflow) that:
   - runs `mvn test -DsuiteXmlFile=...` on push/PR,
   - publishes the TestNG/Surefire results so they render as job-level test graphics,
   - archives the `/target/test-output/screenshots` directory as a build artifact.
- *(Bonus, optional)* Reimplementing part of the scenarios with a wrapper like Selenide.
- *(Bonus, optional)* Highlighting elements (e.g. injecting a temporary CSS outline via JavascriptExecutor) whenever `AbstractPage` performs a click or type action.

---

## Notes

- The site's cookie consent banner (OneTrust) is automatically dismissed during test setup (`BaseTest`) to prevent it from intercepting clicks on lower-positioned page elements.
- Driver management is intentionally single-threaded/sequential; no `parallel` attribute is set in either suite XML, and `DriverManager` holds one driver instance at a time (no `ThreadLocal`).
# QA Training Automation Framework

Selenium + Java 17 + TestNG automation framework for testing [qa.training.epam.com](https://qa.training.epam.com/), with results reported to ReportPortal and one scenario additionally implemented as a Cucumber BDD test.

---

## Project Structure

```text
src/main/java/com/epam/training/
├── framework/
│   ├── config/          ConfigReader — environment-aware property loading
│   ├── driver/          BrowserType, BrowserFactory hierarchy, BrowserFactoryProvider,
│   │                    DriverManager, LoggingWebDriver
│   ├── listeners/       TestListener (TestNG lifecycle), ScreenshotUtils
│   └── utils/           WebElementActions, CookieBannerHandler
├── models/               LoginCredentials, Skill — immutable data carriers
├── pages/                AbstractPage, HomePage, LoginPage, ProgramsPage, SkillsPage
└── services/             SearchResultValidator

src/main/resources/
└── reportportal.properties   ReportPortal endpoint, project, and launch configuration

src/test/java/com/epam/training/
├── base/                 BaseTest — shared setup/teardown
├── runners/              CucumberTestRunner — TestNG entry point for the Cucumber suite
├── stepdefinitions/      Hooks, LoginSteps — Cucumber step definitions for login.feature
└── tests/                LoginTest, ProgramsSkillSearchTest, SkillToProgramsNavigationTest

src/test/resources/
├── config/                qa.properties, staging.properties
├── features/              login.feature — Cucumber/Gherkin scenario for login
├── suites/                smoke.xml, regression.xml, cucumber.xml
└── log4j2.xml
```

---

## Design Patterns

| Pattern | Where | Purpose |
|---|---|---|
| **Singleton** | `DriverManager`, `ConfigReader` | Single point of access for WebDriver and configuration |
| **Factory Method** | `BrowserFactory` → `ChromeFactory`, `FirefoxFactory`, `EdgeFactory` via `BrowserFactoryProvider` | Browser-specific driver creation without modifying existing code |
| **Decorator** | `LoggingWebDriver` wrapping `WebDriver` | Transparently logs all browser interactions |

---

## Prerequisites

- Java 17
- Maven
- At least one supported browser installed: Chrome, Firefox, or Edge
- Internet access (WebDriverManager downloads the matching driver binary automatically)
- Access to a ReportPortal instance, with endpoint/project/API key configured in `src/main/resources/reportportal.properties`

---

## Configuration

Environment properties live under `src/test/resources/config/`:

| File | Key Differences |
|---|---|
| `qa.properties` | `explicit.wait.timeout=15`, includes test credentials |
| `staging.properties` | `explicit.wait.timeout=20` |

Select the environment with `-Denv=qa` or `-Denv=staging`. Default is `qa`.

ReportPortal connection settings (endpoint, project, launch name, API key) are configured separately in `src/main/resources/reportportal.properties` and apply to both the TestNG and Cucumber test runs.

---

## How to Run

### Default (smoke suite, Chrome, QA environment)

```bash
mvn clean test
```

### Specify suite

```bash
mvn clean test "-DsuiteXmlFile=regression.xml"
```

### Run the Cucumber suite

```bash
mvn clean test "-DsuiteXmlFile=cucumber.xml"
```

### Specify browser

```bash
mvn clean test "-Dbrowser=firefox"
```

### Specify environment

```bash
mvn clean test "-Denv=staging"
```

### Combined example

```bash
mvn clean test "-DsuiteXmlFile=regression.xml" "-Dbrowser=chrome" "-Denv=qa"
```

> **Note:** On Windows PowerShell, wrap each `-D` argument in quotes, e.g. `"-Dbrowser=chrome"`, since PowerShell parses `=` differently from bash.

---

## Test Suites

| Suite | File | Tests Included |
|---|---|---|
| Smoke | `smoke.xml` | `SkillToProgramsNavigationTest` |
| Regression | `regression.xml` | `LoginTest`, `ProgramsSkillSearchTest`, `SkillToProgramsNavigationTest` |
| Cucumber | `cucumber.xml` | `CucumberTestRunner` → `login.feature` |

---

## Test Scenarios

| Test | Description |
|---|---|
| `LoginTest` | Verifies successful login with valid credentials |
| `ProgramsSkillSearchTest` | Searches for a skill on the Programs page and validates filtered results |
| `SkillToProgramsNavigationTest` | Navigates from a skill on the Skills page to Programs and verifies the filter carries over |
| `login.feature` (Cucumber) | BDD version of the login scenario, adapted from `LoginTest`, run through `CucumberTestRunner` with steps defined in `LoginSteps` |

---

## Logging and Reporting

- **Log4j2** configured via `src/test/resources/log4j2.xml`
- **Console output** and **rolling file** logs written to `logs/test-log-YYYY-MM-DD.log`
- **ReportPortal**: every test run — both TestNG and Cucumber — is reported as a launch to ReportPortal, configured via `src/main/resources/reportportal.properties`
- **Screenshots on failure**: saved to `target/test-output/screenshots/` via `TestListener` + `ScreenshotUtils`, and attached to ReportPortal for failed **TestNG** tests only. The **Cucumber** suite does not currently capture or attach failure screenshots — failures there are visible in the ReportPortal launch log and console/file logs only.

---

## Cucumber BDD Framework

A Cucumber implementation of the login scenario was added alongside the existing TestNG suites:

- `src/test/resources/features/login.feature` — Gherkin scenario, adapted from `LoginTest`
- `src/test/java/com/epam/training/stepdefinitions/LoginSteps` — step definitions implementing the scenario
- `src/test/java/com/epam/training/stepdefinitions/Hooks` — Cucumber setup/teardown hooks
- `src/test/java/com/epam/training/runners/CucumberTestRunner` — TestNG runner that executes the feature file, wired into `suites/cucumber.xml`

Results from this suite are reported to ReportPortal in the same way as the TestNG suites, with the screenshot-on-failure limitation noted above.

---
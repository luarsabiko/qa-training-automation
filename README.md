# QA Training Automation Framework

Selenium + Java 17 + TestNG automation framework for testing [qa.training.epam.com](https://qa.training.epam.com/).

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

src/test/java/com/epam/training/
├── base/                 BaseTest — shared setup/teardown
└── tests/                LoginTest, ProgramsSkillSearchTest, SkillToProgramsNavigationTest

src/test/resources/
├── config/                qa.properties, staging.properties
├── suites/                smoke.xml, regression.xml
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

---

## Configuration

Environment properties live under `src/test/resources/config/`:

| File | Key Differences |
|---|---|
| `qa.properties` | `explicit.wait.timeout=15`, includes test credentials |
| `staging.properties` | `explicit.wait.timeout=20` |

Select the environment with `-Denv=qa` or `-Denv=staging`. Default is `qa`.

---

## How to Run

### Default (smoke suite, Chrome, QA environment)

```bash
mvn clean test
```

### Specify suite

```bash
mvn clean test -DsuiteXmlFile=regression.xml
```

### Specify browser

```bash
mvn clean test -Dbrowser=firefox
```

### Specify environment

```bash
mvn clean test -Denv=staging
```

### Combined example

```bash
mvn clean test -DsuiteXmlFile=regression.xml -Dbrowser=chrome -Denv=qa
```

> **Note:** On Windows PowerShell, wrap each `-D` argument in quotes, e.g. `"-Dbrowser=chrome"`, since PowerShell parses `=` differently from bash.

---

## Test Suites

| Suite | File | Tests Included |
|---|---|---|
| Smoke | `smoke.xml` | `SkillToProgramsNavigationTest` |
| Regression | `regression.xml` | `LoginTest`, `ProgramsSkillSearchTest`, `SkillToProgramsNavigationTest` |

---

## Test Scenarios

| Test | Description |
|---|---|
| `LoginTest` | Verifies successful login with valid credentials |
| `ProgramsSkillSearchTest` | Searches for a skill on the Programs page and validates filtered results |
| `SkillToProgramsNavigationTest` | Navigates from a skill on the Skills page to Programs and verifies the filter carries over |

---

## Logging and Reporting

- **Log4j2** configured via `src/test/resources/log4j2.xml`
- **Console output** and **rolling file** logs written to `logs/test-log-YYYY-MM-DD.log`
- **Screenshots on failure** saved to `target/test-output/screenshots/` via `TestListener` + `ScreenshotUtils`

---

## SOLID Improvements

| Class | Problem | Solution |
|---|---|---|
| `BaseTest` | SRP violation — cookie banner dismissal mixed into test setup | Extracted to `CookieBannerHandler` utility |
| `AbstractPage` | SRP/ISP violation — interaction helpers tightly coupled to page contract | Extracted to `WebElementActions` via composition |
| `DriverFactory` (old) | OCP violation — adding a browser required modifying a switch/case | Replaced with `BrowserFactory` hierarchy; `BrowserType` enum owns its factory via `Supplier` |
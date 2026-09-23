Feature: User login

  Background:
    Given the training homepage is open

  Scenario Outline: Successful login with valid credentials
    When I log in as "<user>"
    Then I should be signed in successfully

    Examples:
      | user  |
      | user1 |
      | user2 |
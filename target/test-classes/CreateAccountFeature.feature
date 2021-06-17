Feature: Account Tests

  Scenario: As a customer I would like to create a saving account
    Given I am customer
    When I want to open a new account
    Then I should be able to open a new account


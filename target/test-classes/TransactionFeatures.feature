Feature: Transaction test

  Scenario: Employee wants to see all the transactions
    Given I am an employee
    When I want to see all the transactions
    Then I will see all the transactions

  Scenario: Employee wants to get transaction by id
    Given I am an employee
    When I want to get transaction by id
    Then I will get transaction by id

  Scenario: User wants to get transaction by invalid id
    Given I am an user
    When I want to get transaction by invalid id
    Then Display Http Status 400 BAD_REQUEST

  Scenario: Customer wants to get transaction by id
    Given I am a customer
    When I want to get transaction by id
    Then I will get transaction by id

  Scenario: Customer wants to get transaction by wrong id
    Given I am a customer
    When I want to get transaction by wrong id
    Then Display Http Status 400 BAD_REQUEST

  Scenario: Employee wants to delete transaction by id
    Given I am an employee
    When I want to delete a transaction by id
    Then I will delete transaction by id

  Scenario: Employee wants to delete transaction by invalid id
    Given I am an employee
    When I want to delete a transaction by invalid id
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction
    Given I am an user
    When I want to create a transaction
    Then I will create a new transaction

  Scenario: User wants to create a transaction and sender user is null
    Given I am an user
    When I want to create a transaction and sender user is null
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and receiver user is null
    Given I am an user
    When I want to create a transaction and receiver user is null
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and transaction amount is invalid
    Given I am an user
    When I want to create a transaction and transaction amount is invalid
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and transaction amount is higher than user transaction limit
    Given I am an user
    When I want to create a transaction and transaction amount is higher than user transaction limit
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and the user's daily limit is exceeded
    Given I am an user
    When I want to create a transaction and the user's daily limit is exceeded
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and the transaction amount is bigger than the account balance
    Given I am an user
    When I want to create a transaction and the transaction amount is bigger than account balance
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and the sender account is a closed account
    Given I am an user
    When I want to create a transaction and sender account is closed
    Then Display Http Status 400 BAD_REQUEST

  Scenario: User wants to create a transaction and accounts are of different types
    Given I am an user
    When I want to create a transaction and accounts are of different types
    Then Display Http Status 400 BAD_REQUEST


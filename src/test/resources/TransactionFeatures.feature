Feature: Transaction test

  Scenario: Employee wants to see all the transactions
    Given I am an employee
    When I want to see all the transactions
    Then I will see all the transactions

  Scenario: Employee wants to get transaction by id
    Given I am an employee
    And Transaction exists
    When I want to get transaction by id
    Then I will get transaction by id

  Scenario: User wants to get transaction by invalid id
    Given I am an user
    And Transaction does not exist
    When I want to get transaction by id
    Then I will get "Transaction with the specified ID not found." exception

  Scenario: Customer wants to get transaction by id
    Given I am a customer
    And Transaction belongs to customer
    When I want to get transaction by id
    Then I will get transaction by id

  Scenario: Customer wants to get transaction by wrong id
    Given I am a customer
    And Transaction belongs to other customer
    When I want to get transaction by id
    Then I will get "You cannot access this transaction." exception

  Scenario: Employee wants to delete transaction by id
    Given I am an employee
    And Transaction exists
    When I want to delete a transaction by id
    Then I will delete transaction by id

  Scenario: Employee wants to delete transaction by invalid id
    Given I am an employee
    And Transaction ID is invalid
    When I want to delete a transaction by id
    Then I will get a "Transaction with the specified ID not found." exception

  Scenario: User wants to create a transaction
    Given I am a user
    When I want to create a transaction
    Then I will create a new transaction

  Scenario: User wants to create a transaction and sender user is null
    Given I am a user
    And Sender user is null
    When I want to create a transaction
    Then I will get a "Could not retrieve sender user!" exception

  Scenario: User wants to create a transaction and receiver user is null
    Given I am a user
    And Receiver user is null
    When I want to create a transaction
    Then I will get a "Could not retrieve receiver user!" exception

  Scenario: User wants to create a transaction and transaction amount is invalid
    Given I am a user
    And Transaction amount is bigger than transaction amount limit
    When I want to create a transaction
    Then I will get a "Invalid amount!" exception

  Scenario: User wants to create a transaction and transaction amount is higher than user transaction limit
    Given I am a user
    And Transaction amount is higher than the user's transaction limit
    When I want to create a transaction
    Then I will get a "Amount is higher than the limit!" exception

  Scenario: User wants to create a transaction and the user's daily limit is exceeded
    Given I am a user
    And The user has exceeded it's daily limit
    When I want to create a transaction
    Then I will get a "Daily limit amount exceeded!" exception

  Scenario: User wants to create a transaction and the transaction amount is bigger than the account balance
    Given I am a user
    And The transaction amount is bigger than the account's balance
    When I want to create a transaction
    Then I will get a "You can't have that little money in your account!" exception

  Scenario: User wants to create a transaction and the sender account is a closed account
    Given I am a user
    And The sending account is closed
    When I want to create a transaction
    Then I will get a "Account cannot be a CLOSED account." exception

  Scenario: User wants to create a transaction
    Given I am a user
    And User wants to send money to another user that does not have the same account TYPE
    When I want to create a transaction
    Then I will get a "Invalid request!" exception


Feature: Transaction test

  Scenario: Employee wants to see all the transactions
    Given I am an employee
    When I want to see all the transactions
    Then Show http status 200

  Scenario: Employee wants to get transaction by id
    Given I am an employee
    When I enter id 7 to get transaction
    Then Show http status 200

  Scenario: User wants to get transaction by invalid id
    Given I am an user
    When I enter id 10 to get transaction
    Then Show http status 404

  Scenario: Customer wants to get transaction by id
    Given I am a customer
    When I enter id 7 to get transaction
    Then Show http status 200

  Scenario: Customer wants to get transaction by wrong id
    Given I am a customer
    When I enter id 6 to get transaction
    Then Show http status 400

  Scenario: Employee wants to delete transaction by id
    Given I am an employee
    When I enter id 6 to delete transaction
    Then Show http status 200

  Scenario: Employee wants to delete transaction by invalid id
    Given I am an employee
    When I enter id 10 to delete transaction
    Then Show http status 400

  Scenario: User wants to create a transaction
    Given I am an user
    When I want to create a transaction with senderIBAN "NL03INHO0778852694" and receiverIBAN "NL04INHO0836583995" and amount 1000.00 and currencyType "EUR"
    Then Show http status 201

  Scenario: User wants to create a transaction and transaction amount is invalid
    Given I am an user
    When I want to create a transaction with senderIBAN "NL03INHO0778852694" and receiverIBAN "NL04INHO0836583995" and amount -1000.00 and currencyType "EUR"
    Then Show http status 400

  Scenario: User wants to create a transaction and transaction amount is higher than user transaction limit
    Given I am an user
    When I want to create a transaction and transaction amount is higher than user transaction limit
    Then Show http status 400

  Scenario: User wants to create a transaction and the user's daily limit is exceeded
    Given I am an user
    When I want to create a transaction and the user's daily limit is exceeded
    Then Show http status 400

  Scenario: User wants to create a transaction and the transaction amount is bigger than the account balance
    Given I am an user
    When I want to create a transaction and the transaction amount is bigger than account balance
    Then Show http status 400

  Scenario: User wants to create a transaction and the sender account is a closed account
    Given I am an user
    When I want to create a transaction and sender account is closed
    Then Show http status 400

  Scenario: User wants to create a transaction and accounts are of different types
    Given I am an user
    When I want to create a transaction and accounts are of different types
    Then Show http status 400


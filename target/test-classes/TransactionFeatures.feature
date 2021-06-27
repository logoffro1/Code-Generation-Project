Feature: Transaction test

  #successful
  Scenario: Employee wants to see all the transactions
    Given I am an employee
    When I want to see all the transactions
    Then Show http status 200

    #successful
  Scenario: Employee wants to get transaction by id
    Given I am an employee
    When I enter id 8 to get transaction
    Then Show http status 200

    #successful
  Scenario: Customer wants to get transaction by id
    Given I am a customer
    When I enter id 8 to get transaction
    Then Show http status 200

    #successful
  Scenario: Employee wants to delete transaction by id
    Given I am an employee
    When I enter id 8 to delete transaction
    Then Show http status 200

    #successful
  Scenario: User wants to create a transaction
    Given I am an user
    When I want to create a transaction with senderIBAN "NL03INHO0778852694" and receiverIBAN "NL04INHO0836583995" and amount 1000.00 and currencyType "EUR"
    Then Show http status 201


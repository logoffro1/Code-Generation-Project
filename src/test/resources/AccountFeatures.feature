Feature: Account Tests


    #  PUT account happy flow
  Scenario:  Employee updates the account by changing the type from current to savings
    Given I am Employee
    When Updating account with the iban "NL03INHO0778852693"  to  "SAVINGS"
    Then Display Http Status 200

    #   DELETE account happy flow
  Scenario: Employee Soft Deletes the account
    Given I am Employee
    When Soft Deletes the account by entering iban "NL03INHO0778852693"
    Then Display Http Status 200

    #  GET account happy flow
  Scenario:  Employee wants to get all accounts
    Given I am Employee
    When I want to get all acounts
    Then Display Http Status 200

   #  POST account happy flow
  Scenario: Employee creates a saving account for a customer
    Given I am Employee
    When I want to create a new account for Customer with user id of 1001 a balance of 2000 with an absolute limit of 200 with an enum of  "CURRENT" and with a status of "ACTIVE"
    Then Display Http Status 201

    # GET by iban happy flow
  Scenario:  Customer wants to see account balance
    Given I am Customer
    When  I enter my Iban "NL03INHO0778852694" to get my account
    Then  I see my balance 9000.00
    Then  Display Http Status 200

    # GET by iban happy flow
  Scenario: Customer tries to get an account by IBAN
    Given I am Customer
    When I enter my Iban "NL04INHO0836583995" to get my account
    Then Display Http Status 200

    # POST alternative flow
  Scenario: Employee tries to create an account with a balance lower than absolutelimit.
    Given I am Employee
    When  I want to create a new account for Customer with user id of 1001 a balance of 12 with an absolute limit of 200 with an enum of  "CURRENT" and with a status of "ACTIVE"
    Then I get ApiRequestException with Exception message "Balance can not be less than absoluteLimit"
    Then Display Http Status 400

    # POST alternative flow
  Scenario: Employee tries to create an account with an invalid user
    Given I am Employee
    When  I want to create a new account for Customer with user id of 120 a balance of 2000 with an absolute limit of 200 with an enum of  "CURRENT" and with a status of "ACTIVE"
    Then I get ApiRequestException with Exception message "Id less than 1001 doesn't exist.Try putting an id in the range of 1000"
    Then Display Http Status 400


    # POST alternative flow
  Scenario:  Employee tries to create an account with a closed status
    Given I am Employee
    When  I want to create a new account for Customer with user id of 1001 a balance of 2000 with an absolute limit of 200 with an enum of  "CURRENT" and with a status of "CLOSED"
    Then I get ApiRequestException with Exception message "Account must not be closed when it is created"
    Then Display Http Status 403

    # DELETE alternative flow
  Scenario: Employee Wants to delete account that belongs to the actual bank
    Given I am Employee
    When Soft Deletes the account by entering iban "NL01INHO00000001"
    Then Display Http Status 403
    Then I get ApiRequestException with Exception message "Bank's own account can not be updated by employees"

    # PUT alternative flow
  Scenario: Employee tries to update the bank account and gets rejected
    Given I am Employee
    When Updating account with the iban "NL01INHO00000001"  to  "SAVINGS"
    Then I get ApiRequestException with Exception message "Bank's own account can not be updated by employees"
    Then Display Http Status 403











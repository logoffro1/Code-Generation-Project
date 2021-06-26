Feature: Account Tests


    #    Done and done and done and done son
  Scenario:  Employee updates the account by changing the type from current to savings
    Given I am Employee
    When Updating account with the iban "NL03INHO0778852693"  to  "TypeEnum.SAVINGS"
    Then Display Http Status 200

    #    Done and done and done and done son
  Scenario: Employee Soft Deletes the account
    Given I am Employee
    When Soft Deletes the account by entering iban "NL03INHO0778852693"
    Then Display Http Status 200

    #    Done and done and done and done son
  Scenario:  Employee wants to get all accounts
    Given I am Employee
    When I want to get all acounts
    Then Display Http Status 200

   #    Done and done and done and done son
  Scenario: Employee creates a saving account for a customer
    Given I am Employee
    When I want to create a new account for Customer with user id of 1001 a balance of 2000 with an absolute limit of 200 with an enum of  "Account.TypeEnum.CURRENT" and with a status of "Account.StatusEnum.ACTIVE"
    Then Display Http Status 201


  Scenario:  Customer wants to see account balance
    Given I am Customer
    When  I enter my Iban "NL03INHO0778852693" to get my account
    Then  I see my balance 2500
    Then  Display Http Status 200


  Scenario: Customer tries to get an account by IBAN
    Given I am Customer
    When I enter my Iban"NL03INHO0778852693" to get my account
    Then Display Http Status 200


  Scenario: Employee tries to create an account with a balance lower than absolutelimit.
    Given I am Employee
    When  I want to create a new account for Customer with user id of 1001 a balance of 12 with an absolute limit of 200 with an enum of  "Account.TypeEnum.CURRENT" and with a status of "Account.StatusEnum.ACTIVE"
    Then I get ApiRequestException with Exception message "Balance can not be lower than absolute limit"
    Then Display Http Status 403

  Scenario: Customer tries to get an account that does not belong to his/her user
    Given I am Customer
    When I try to get an account by iban
    Then I get ApiRequestException with Exception message "Customers can not look for accounts that does not belong to them"
    Then Display Http Status 403

  Scenario: Employee tries to retrieve an account with an invalid iban
    Given I am Employee
    When I try to get an account by iban
    Then I get ApiRequestException with Exception message "Iban is invalid, please input a valid iban"
    Then Display Http Status 404

  Scenario: Employee tries to create an account with a null user
    Given I am Employee
    When  I want to create a new account for Customer with user id of 0 a balance of 2000 with an absolute limit of 200 with an enum of  "Account.TypeEnum.CURRENT" and with a status of "Account.StatusEnum.ACTIVE"
    Then I get ApiRequestException with Exception message "User can not be null.
    Then Display Http Status 400

  Scenario:  Employee tries to create an account with a closed status
    Given I am Employee
    When  I want to create a new account for Customer with user id of 0 a balance of 2000 with an absolute limit of 200 with an enum of  "Account.TypeEnum.CURRENT" and with a status of "Account.StatusEnum.CLOSED"
    Then I get ApiRequestException with Exception message "Account must not be closed when it is created"
    Then Display Http Status 403


  Scenario: Employee Wants to delete account that belongs to the actual bank
    Given I am Employee
    When Soft Deletes the account by entering iban "NL01INHO00000001"
    Then Display Http Status 403
    Then Display ApiRequestException "Bank's own account can not be updated by employees"

  Scenario: Customer tries to get his/her account by IBAN but enters an iban that belongs to another customer
    Given  I am Customer
    When I try to get an account by IBAN that does not belong to my account
    Then I get ApiRequestException with Exception message "Customers can not look for accounts that does not belong to them"
    Then I get HttpStatus 403

  Scenario: Employee  wants to receive all accounts between two indexes
    Given I am Employee
    When I try to get all accounts with the limit of 100 and offset of 0
    Then I receive the first 100 accounts from database
    Then Display Http Status 200


  Scenario: User tries to get an account that doesn't exist in the database by iban
    Given  I am User
    When I try to get an account by iban with an iban that does not exist in the database
    Then I get ApiRequestException with Exception message "Iban is not found, please input the correct iban to modify the account"
    Then I get HttpStatus 404

  Scenario: Employee tries to update the bank account and gets rejected
    Given I am Employee
    When I try to update Banks account to change type from current to savings
    Then I get ApiRequesException with Exceptionmessage "Bank's own account can not be updated by employees"
    Then I get HttpStatus 403









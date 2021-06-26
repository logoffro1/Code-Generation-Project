Feature: Account Tests


    #  successfull scenarios
  Scenario:  Employee updates the account by changing the type from current to savings
    Given I am Employee
    And Iban is valid
    When Updating account From TypeEnum.CURRENT to  TypeEnum.SAVINGS
    Then Display Http Status 200


    Scenario: Employee Soft Deletes the account
      Given I am Employee
      And Iban is valid
      When Soft Deletes the account by entering iban "NL02INHO0859674253"
      Then Display Http Status 200


    Scenario: Employee creates a saving account for a customer
      Given I am Employee
      And Iban is not present in database
      When I want to create a new account for Customer
      Then Display Http Status 200


    Scenario:  Customer wants to see account balance
      Given I am Customer
      When  I enter my Iban "NL03INHO0778852693" to get my account to see my balance
      Then  I see my balance 2500
      Then  Display Http Status 200


    Scenario: Customer tries to get an account by IBAN
      Given I am Customer
      When I try to get my account by Iban "NL03INHO0778852693"
      Then Display Http Status 200



      #  Unsuccessfull scenarios
      Scenario: Employee tries to create an account with a balance lower than absolutelimit.
        Given I am Employee
        When I try to create account with a balance of 250 lower than absolutelimit 1000
        Then I get "ApiRequestException"
        Then I get Exception message "Balance can not be lower than absolute limit"
        Then I get HttpStatus 403


      Scenario: Customer tries to get his/her account by IBAN but enters an iban that belongs to another customer
        Given  I am Customer
        When I try to get an account by IBAN that does not belong to my account
        Then I get "ApiRequestException"
        Then I get Exception message "Customers can not look for accounts that does not belong to them"
        Then I get HttpStatus 403

      Scenario: Employee  wants to receive all accounts between two indexes
        Given I am Employee
        When I try to get all accounts with the limit of 100 and offset of 0
        Then I receive the first 100 accounts from database


      Scenario: User tries to get an account that doesn't exist in the database by iban
        Given  I am User
        When I try to get an account by iban with an iban that does not exist in the database
        Then I get "ApiRequestException"
        Then I get Exception message "Iban is not found, please input the correct iban to modify the account"


      Scenario: Employee tries to update the bank account and gets rejected
        Given I am Employee
        When I try to update Banks account to change type from current to savings
        Then I get "ApiRequesException"
        Then I get Exception message "Bank's own account can not be updated by employees"









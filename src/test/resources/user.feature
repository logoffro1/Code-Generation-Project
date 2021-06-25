Feature: Testing Users

  Scenario: Retrieving all Users return http status ok
    Given the user is an employee
    When retrieving all users
    Then show http status 200

  Scenario: Creating a user returns http status created
    Given the user is an employee
    When creating a new user
    Then show http status 201

  Scenario: Retrieving a user with Id returns
    Given the user is an employee
    When retrieving a user with id 1
    Then show http status 200

  Scenario: Updating a user returns http status ok
    Given the user is an employee
    When updating a user with id 1
    Then show http status 200

  Scenario: Deleting a user returns http status ok
    Given the user is an employee
    When deleting a user with id 1
    Then show http status 200

  Scenario: Deleting a user as customer returns http status forbidden
    Given the user is a customer
    When deleting a user with id 1
    Then show http status 403

  Scenario: Updating a user as customer returns http status forbidden
    Given the user is a customer
    When updating a user with id 1
    Then show http status 403

  Scenario: Creating a user as customer returns http status forbidden
    Given the user is a customer
    When creating a user with id 1
    Then show http status 403

  Scenario: Retrieving all Users as customer return http status forbidden
    Given the user is a customer
    When retrieving all users
    Then show http status 403

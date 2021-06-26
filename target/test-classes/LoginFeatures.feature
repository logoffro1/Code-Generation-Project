Feature: Login features


  Scenario: User tries to login with correct credentials
    When User tries to enter "JohnDoe@gmail.com" as email and "johnnie123" as password
    Then Http Status 200 is displayed

  Scenario: User tries to login with incorrect credentials
    When User tries to enter "wrongemail@gmail.com" as email and "wrongpassword" as password
    Then Http Status 500 is displayed
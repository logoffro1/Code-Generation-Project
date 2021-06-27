Feature: Login features


  Scenario: User tries to login with correct credentials
    When User tries to enter "JohnDoe@gmail.com" as email and "johnnie123" as password
    Then Http Status 200 is displayed

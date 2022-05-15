Feature: Login

  @validData
  Scenario: Login with valid data
    Given Navigate to Home Page
    When Click on Login tab
    And Enter valid email and password
      | email   | password   |
      | <email> | <password> |
    And Click on Login button
    Then SignOut button displayed
    And Browser closed
    Examples:
      | email            | password   |
      | karl+1@gmail.com | Ka1234567$ |


  @invalidPassword
  Scenario Outline: Login with invalid data/password
    Given Navigate to Home Page
    When Click on Login tab
    And Enter a valid email and an invalid password
      | email   | password   |
      | <email> | <password> |
    And Click on Login button
    Then Alert appeared
    And Check Warning
    And Browser closed
    Examples:
      | email            | password  |
      | karl+1@gmail.com | Ka1234567 |
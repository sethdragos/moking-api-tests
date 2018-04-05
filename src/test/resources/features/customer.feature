Feature: Customer

  Scenario: User creates new customer with valid input data

    Given a valid request to create new customer
    When executing POST new customer request
    Then the status code is 201
    And response includes the following
      | id     | 1                    |
      | status | successfully created |


  Scenario: User creates new customer with valid input data but no properties

    Given a valid request to create new customer with no properties
    When executing POST new customer request
    Then the status code is 201
    And response includes the following
      | id 	   | 1 			          |
      | status | successfully created |


  Scenario: User creates new customer with incomplete input data, missing id

    Given a request to create new customer, missing the id
    When executing POST new customer request
    Then the status code is 401


  Scenario: User creates new customer with invalid input data, id data type

    Given a request to create new customer, invalid id type
    When executing POST new customer request
    Then the status code is 401


  Scenario: User creates new customer with incomplete input data, missing first name

    Given a request to create new customer, missing the first name
    When executing POST new customer request
    Then the status code is 401


  Scenario: User creates new customer with invalid input data, first name data type

    Given a request to create new customer, invalid first name type
    When executing POST new customer request
    Then the status code is 401


  Scenario: User creates new customer with incomplete input data, missing last name

    Given a request to create new customer, missing the last name
    When executing POST new customer request
    Then the status code is 401


  Scenario: User creates new customer with invalid input data, last name data type

    Given a request to create new customer, invalid last name type
    When executing POST new customer request
    Then the status code is 401

  Scenario: User creates new customer with invalid input data, body should not pass schema validation

    Given a request to create new customer, invalid data
    When executing POST new customer request
    Then the status code is 401


  Scenario: User gets a customer with valid id

    When executing GET request for customer with id '1'
    Then the status code is 200
    And response includes the following
      | id                       | 1          |
      | first_name               | Dragos     |
      | last_name                | Serghie    |
      | properties.age           | 29         |
      | properties.active        | true       |
      | properties.date_of_birth | 10/11/1988 |


  Scenario: User gets a customer with invalid id

    When executing GET request for customer with id '2'
    Then the status code is 404


  Scenario: User gets a customer without id

    When executing GET request for customer without id
    Then the status code is 404
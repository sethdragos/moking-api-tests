package com.example.cucumber.steps;

import com.example.cucumber.mock.MockServer;
import com.example.cucumber.util.FileReader;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

import static com.example.cucumber.mock.MockServer.SERVER_PORT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StepsDefinitions {

    private static final String CUSTOMER_ENDPOINT = "http://localhost:8090/rest/api/customer";

    private Response response;
    private ValidatableResponse jsonResponse;
    private RequestSpecification request;

    @Before
    public void setup() throws IOException {
        MockServer.start();
    }

    @After
    public void tearDown() {
        MockServer.stop();
    }

    @Given("a valid request to create new customer")
    public void create_new_valid_customer_request() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_valid_body.json"));
    }

    @And("a request to create new customer with no properties")
    public void create_new_valid_customer_with_no_properties_request() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_valid_body_empty_properties.json"));
    }

    @And("a request to create new customer, missing the id")
    public void create_new_invalid_customer_request_missing_id() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_missing_required_id.json"));
    }

    @And("a request to create new customer, invalid id type")
    public void create_new_invalid_customer_request_invalid_id_type() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_invalid_id_type.json"));
    }

    @And("a request to create new customer, missing the first name")
    public void create_new_invalid_customer_request_missing_first_name() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_missing_required_first_name.json"));
    }

    @And("a request to create new customer, invalid first name type")
    public void create_new_invalid_customer_request_invalid_first_name_type() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_invalid_first_name_type.json"));
    }

    @And("a request to create new customer, missing the last name")
    public void create_new_invalid_customer_request_missing_last_name() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_missing_required_last_name.json"));
    }

    @And("a request to create new customer, invalid last name type")
    public void create_new_invalid_customer_request_invalid_last_name_type() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_body_invalid_last_name_type.json"));
    }

    @And("a request to create new customer, invalid data")
    public void create_new_invalid_customer_request_invalid_data() throws IOException {
        RestAssured.baseURI = CUSTOMER_ENDPOINT;
        request = given()
                .port(SERVER_PORT)
                .body(FileReader.read("src/test/resources/input/post_invalid_body.json"));
    }

    @When("^executing GET request for customer with id '(\\d+)'$")
    public void get_customer_with_id(int id) {
        response = given().when().get(CUSTOMER_ENDPOINT + "/" + id);
    }

    @And("^executing GET request for customer without id$")
    public void get_customer_without_id() {
        response = given().when().get(CUSTOMER_ENDPOINT + "/");
    }

    @And("^executing POST new customer request$")
    public void post_new_customer_request() {
        response = request.when().post();
    }

    @Then("the status code is (\\d+)")
    public void verify_status_code(int statusCode) {
        jsonResponse = response.then().statusCode(statusCode);
    }

    @And("response includes the following$")
    public void response_equals(Map<String,String> responseFields) {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            String fieldKey = field.getKey();
            String fieldValue = field.getValue();
            if(StringUtils.isNumeric(fieldValue)) {
                jsonResponse.body(fieldKey, equalTo(Integer.parseInt(fieldValue)));
            } else {
                if (fieldValue.contentEquals("true") || fieldValue.contentEquals("false")) {
                    jsonResponse.body(fieldKey, equalTo(Boolean.parseBoolean(fieldValue)));
                } else {
                    jsonResponse.body(fieldKey, equalTo(field.getValue()));
                }
            }
        }
    }

}

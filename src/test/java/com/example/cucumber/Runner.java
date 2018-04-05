package com.example.cucumber;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        format = {"pretty", "html:target/cucumber"},
        glue = "com.example.cucumber.steps",
        features = {"classpath:features/customer.feature"}
)
public class Runner {
}

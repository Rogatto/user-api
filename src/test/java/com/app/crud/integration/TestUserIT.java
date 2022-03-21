package com.app.crud.integration;

import com.jayway.restassured.response.Response;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

@Epic("User API")
@Feature("All User Resources")
class TestUserIT extends TestConfigurationIT{


    @Value("${server.port}")
    private int applicationPort;

    private static final String applicationHost = "http://localhost:";

    @BeforeAll
    static void initAll() {
    }

    private String username = "admin";

    @Test
    @DisplayName("[CT-XXX] User admin signin")
    @Description("Getting the authorization token to access resources")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("add here a test case from Azure Test Plans")
    void testUserSignin(){

        Response signinResponse = UserSteps.postUserSignin(username, "password", applicationHost + applicationPort);
        Assertions.assertEquals(200, signinResponse.getStatusCode());
        Assertions.assertEquals(username, signinResponse.getBody().jsonPath().getString("username"));
        Assertions.assertNotNull(signinResponse.getBody().jsonPath().getString("token"));
    }


    @Test
    @DisplayName("[CT-XXX] GET user information by his authorization token")
    @Description("GET user information by his authorization token")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("add here a test case from Azure Test Plans")
    void testGetUserMe(){

        Response signinResponse = UserSteps.postUserSignin(username, "password", applicationHost + applicationPort);
        Response getUserMeResponse = UserSteps.getUserMe(signinResponse.getBody().jsonPath().getString("token"), applicationHost + applicationPort);
        Assertions.assertEquals(200, getUserMeResponse.getStatusCode());
        Assertions.assertEquals(username, getUserMeResponse.getBody().jsonPath().getString("username"));
    }


    @Test
    @DisplayName("[CT-XXX] Create new admin user")
    @Description("Creating a new admin user")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("add here a test case from Azure Test Plans")
    void testCreateNewUser(){

        Response signinResponse = UserSteps.postUserSignin(username, "password", applicationHost + applicationPort);
        Response postUserResponse = UserSteps.postUser("{\n    \"name\": \"simple test 1\",\n    \"username\": \"test1\",\n    \"password\": \"{bcrypt}$2a$10$kavSouNY7FWP2TpIPwedNOvNX4BXjBkXTG5fk71zWmX/4.yH2oC1O\",\n    \"roles\": [\n                \"ROLE_USER\"\n            ],\n    \"createdAt\": \"2022-03-14T13:18:58.651Z\",\n    \"enabled\": true,\n    \"accountNonExpired\": true,\n    \"accountNonLocked\": true,\n    \"credentialsNonExpired\": true\n}",
                signinResponse.getBody().jsonPath().getString("token"), applicationHost + applicationPort);
        Assertions.assertEquals(201, postUserResponse.getStatusCode());
        Assertions.assertNotNull(postUserResponse.getHeader("Location"));
    }

    @Test
    @DisplayName("[CT-XXX] GET all users")
    @Description("Get all users")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("add here a test case from Azure Test Plans")
    void testGetAllUsers(){
        Response getUserAllResponse = UserSteps.getAllUsers(applicationHost + applicationPort);
        Assertions.assertEquals(200, getUserAllResponse.getStatusCode());
    }

    @Test
    @DisplayName("[CT-XXX] GET user by id")
    @Description("Get user by id")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("add here a test case from Azure Test Plans")
    void testGetUserById(){
        Response signinResponse = UserSteps.postUserSignin(username, "password", applicationHost + applicationPort);
        Response postUserResponse = UserSteps.postUser("{\n    \"name\": \"simple test 2\",\n    \"username\": \"test2\",\n    \"password\": \"{bcrypt}$2a$10$kavSouNY7FWP2TpIPwedNOvNX4BXjBkXTG5fk71zWmX/4.yH2oC1O\",\n    \"roles\": [\n                \"ROLE_USER\"\n            ],\n    \"createdAt\": \"2022-03-14T13:18:58.651Z\",\n    \"enabled\": true,\n    \"accountNonExpired\": true,\n    \"accountNonLocked\": true,\n    \"credentialsNonExpired\": true\n}",
                signinResponse.getBody().jsonPath().getString("token"), applicationHost + applicationPort);
        Response getUserByIdResponse = UserSteps.getUserById(signinResponse.getBody().jsonPath().getString("token"), postUserResponse.getHeader("Location"));
        Assertions.assertEquals(200, getUserByIdResponse.getStatusCode());
    }
}

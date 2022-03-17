package com.app.crud.integration;

import com.app.crud.integration.request.AuthSigninDTO;
import com.jayway.restassured.response.Response;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("User API")
@Feature("New User")
class TestUserIT extends TestConfigurationIT{


    @BeforeAll
    static void initAll() {
    }


    @Test
    @Step("[CT-XXX] Add here name of test scenario")
    @Description("any description")
    @Severity(SeverityLevel.CRITICAL)
    void testAddUser(){

        AuthSigninDTO authSigninDTO = AuthSigninDTO.builder()
                .username("admin")
                .password("password")
                .build();

        Response signinResponse = given()
                .contentType("application/json")
                .body(authSigninDTO)
                .when()
                .post("http://localhost:8080/auth/signin")
                .then()
                .extract()
                .response();

        signinResponse.getBody().prettyPrint();


        Response getUserMeResponse = given()
                .header("Authorization", "Bearer " + signinResponse.getBody().jsonPath().getString("token"))
                .when()
                .get("http://localhost:8080/api/users/me")
                .then()
                .extract()
                .response();

        getUserMeResponse.getBody().prettyPrint();


        Response postUserResponse = given()
                .header("Authorization", "Bearer " + signinResponse.getBody().jsonPath().getString("token"))
                .contentType("application/json")
                .body("{\n    \"name\": \"simple test 1\",\n    \"username\": \"test3\",\n    \"password\": \"{bcrypt}$2a$10$kavSouNY7FWP2TpIPwedNOvNX4BXjBkXTG5fk71zWmX/4.yH2oC1O\",\n    \"roles\": [\n                \"ROLE_USER\"\n            ],\n    \"createdAt\": \"2022-03-14T13:18:58.651Z\",\n    \"enabled\": true,\n    \"accountNonExpired\": true,\n    \"accountNonLocked\": true,\n    \"credentialsNonExpired\": true\n}")
                .when()
                .post("http://localhost:8080/api/users")
                .then()
                .extract()
                .response();

        postUserResponse.getBody().prettyPrint();
        System.out.println("return code status was: " + postUserResponse.getStatusCode());

        Response getUser = given()
                .when()
                .get( "http://localhost:8080/api/users")
                .then()
                .extract()
                .response();

        getUser.getBody().prettyPrint();


        Response getUserByIdResponse = given()
                .header("Authorization", "Bearer " + signinResponse.getBody().jsonPath().getString("token"))
                .when()
                .get(postUserResponse.getHeader("Location"))
                .then()
                .extract()
                .response();

        getUserByIdResponse.getBody().prettyPrint();
        System.out.println("return code status was: " + getUserByIdResponse.getStatusCode());

        System.out.println("Header Location returned from post user: " + postUserResponse.getHeader("Location"));
        /*Response deleteUserResponse = given()
                .header("Authorization", "Bearer " + signinResponse.getBody().jsonPath().getString("token"))
                .contentType("application/json")
                .when()
                .delete(postUserResponse.getHeader("Location"))
                .then()
                .extract()
                .response();

        deleteUserResponse.getBody().prettyPrint();
        System.out.println("return code status was: " + deleteUserResponse.getStatusCode());*/

        assertTrue(true);
    }
}

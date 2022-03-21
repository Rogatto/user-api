package com.app.crud.integration;

import com.app.crud.integration.request.AuthSigninDTO;
import com.jayway.restassured.response.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import static com.jayway.restassured.RestAssured.given;

public class UserSteps {


    @Step("User signin")
    public static Response postUserSignin(String username, String password, String baseUrl) {
        /*user sign in */

        AuthSigninDTO authSigninDTO = AuthSigninDTO.builder()
                .username(username)
                .password(password)
                .build();

        Response signinResponse = given()
                .contentType("application/json")
                .body(authSigninDTO)
                .when()
                .post(baseUrl + "/auth/signin")
                .then()
                .extract()
                .response();

        signinResponse.getBody().prettyPrint();
        Allure.addAttachment("auth/signin response body was: ", signinResponse.getBody().asString());
        Allure.addAttachment("auth/signin status code was: ", String.valueOf(signinResponse.getStatusCode()));

        return signinResponse;
    }



    @Step("GET All Users")
    public static Response getAllUsers(String baseUrl) {

        Response getAllUsers = given()
                .when()
                .get( baseUrl + "/api/users")
                .then()
                .extract()
                .response();

        getAllUsers.getBody().prettyPrint();
        Allure.addAttachment("Get all users response body was: ", getAllUsers.getBody().asString());
        Allure.addAttachment("Get all users status code was: ", String.valueOf(getAllUsers.getStatusCode()));

        return getAllUsers;
    }


    @Step("GET User me")
    public static Response getUserMe(String token, String baseUrl) {

        Response getUserMeResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseUrl + "/api/users/me")
                .then()
                .extract()
                .response();

        getUserMeResponse.getBody().prettyPrint();
        Allure.addAttachment("Get user me response body was: ", getUserMeResponse.getBody().asString());
        Allure.addAttachment("Get user me status code was: ", String.valueOf(getUserMeResponse.getStatusCode()));

        return getUserMeResponse;
    }


    @Step("POST User")
    public static Response postUser(String userRequestBody, String token, String baseUrl) {

        Response postUserResponse = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(userRequestBody)
                .when()
                .post(baseUrl + "/api/users")
                .then()
                .extract()
                .response();

        postUserResponse.getBody().prettyPrint();
        Allure.addAttachment("Post user header location: ", postUserResponse.getHeader("Location"));
        Allure.addAttachment("Post user status code was: ", String.valueOf(postUserResponse.getStatusCode()));

        return postUserResponse;
    }


    @Step("GET User by ID")
    public static Response getUserById(String token, String url) {

        Response getUserByIdResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url)
                .then()
                .extract()
                .response();

        getUserByIdResponse.getBody().prettyPrint();

        Allure.addAttachment("Get user by id response body was: ", getUserByIdResponse.getBody().asString());
        Allure.addAttachment("Get user by id status code was: ", String.valueOf(getUserByIdResponse.getStatusCode()));

        return getUserByIdResponse;
    }
}

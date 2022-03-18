package com.app.crud.integration;

import com.jayway.restassured.response.Response;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("User API")
@Feature("New User")
class TestUserIT extends TestConfigurationIT{


    @Value("${server.port}")
    private int applicationPort;

    private static final String applicationHost = "http://localhost:";

    @BeforeAll
    static void initAll() {
    }

    @Test
    @Step("[CT-XXX] Executing all user resources")
    @Description("Testing all user's resources from scratch")
    @Severity(SeverityLevel.CRITICAL)
    void testAllUserResources(){


        Response signinResponse = UserSteps.postUserSignin("admin", "password", applicationHost + applicationPort);
        UserSteps.getUserMe(signinResponse.getBody().jsonPath().getString("token"), applicationHost + applicationPort);
        Response postUserResponse = UserSteps.postUser(signinResponse.getBody().jsonPath().getString("token"), applicationHost + applicationPort);
        UserSteps.getAllUsers(applicationHost + applicationPort);
        UserSteps.getUserById(signinResponse.getBody().jsonPath().getString("token"), postUserResponse.getHeader("Location"));

        assertTrue(true);
    }
}

package com.svp.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class NotificationResourceTest {

    @Test
    void fcmTestEndpointAcceptsRequest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .contentType("application/json")
                .body("""
                        {
                          "token": "test-token",
                          "title": "Titre",
                          "body": "Corps"
                        }
                        """)
                .when()
                .post("/api/notifications/test")
                .then()
                .statusCode(202);
    }
}

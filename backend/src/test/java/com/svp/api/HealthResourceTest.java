package com.svp.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class HealthResourceTest {

    @Test
    void healthEndpointIsUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .when()
                .get("/q/health")
                .then()
                .statusCode(200)
                .body("status", Matchers.equalTo("UP"));
    }
}

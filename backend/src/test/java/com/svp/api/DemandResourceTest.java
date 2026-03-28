package com.svp.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class DemandResourceTest {

    @Test
    void createDemandReturns201AndId() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .contentType("application/json")
                .body("""
                        {
                          "serviceType": "Plombier - fuite chauffe-eau",
                          "clientLatitude": 48.8566,
                          "clientLongitude": 2.3522
                        }
                        """)
                .when()
                .post("/api/demands")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue());
    }
}


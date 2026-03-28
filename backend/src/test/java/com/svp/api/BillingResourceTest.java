package com.svp.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class BillingResourceTest {

    @Test
    void monthlyCounterReturns200() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .when()
                .get("/api/billing/1/monthly-counter")
                .then()
                .statusCode(200)
                .body("artisanId", Matchers.equalTo(1))
                .body("interactionsCount", Matchers.notNullValue());
    }
}

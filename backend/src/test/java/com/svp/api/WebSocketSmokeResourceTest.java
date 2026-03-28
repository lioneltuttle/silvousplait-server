package com.svp.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class WebSocketSmokeResourceTest {

    @Test
    void demandDispatchBroadcastReturns202() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .contentType("application/json")
                .body("""
                        { "message": "ping-test" }
                        """)
                .when()
                .post("/api/dev/ws/demand-dispatch-broadcast")
                .then()
                .statusCode(202);
    }
}

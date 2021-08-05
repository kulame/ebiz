package net.feelapp.ebiz;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.Json;
import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonObject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;

@QuarkusTest
public class RegisterTest {

    @Test
    public void testRegisterEndpoint() {
        JsonObject json = new JsonObject();
        json.put("email",   "test01@live.com")
                .put("password","123123")
                .put("confirm","123123");

        given()
                .log()
                .all()
                .contentType("application/json")
                .body(json.toString())
                .when().post("/api/register")
                .then()
                .statusCode(200);

    }
}

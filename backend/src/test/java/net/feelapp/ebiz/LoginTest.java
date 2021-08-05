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
public class LoginTest {

    @Test
    public void testLoginEndpoint() {
        JsonObject json = new JsonObject();
        json.put("email",   "test01@live.com")
                .put("password","123123")
                .put("autoLogin",true)
                .put("type","account");

        given()
                .log()
                .all()
                .contentType("application/json")
                .body(json.toString())
                .when().post("/api/login/account")
                .then()
                .statusCode(200)
                .body(containsString("ok"));

    }
}

package HW3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTest
class HW3BackendPostTest {

    @Test
    @DisplayName("Status code is 200")
    void classifyCuisineTest1() {
        given()
                .post("/recipes/cuisine")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("We measure the response time")
    void classifyCuisineTest2() {
        Response response = RestAssured.post("/recipes/cuisine");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        assertEquals(timeInS, timeInMS / 1000);

    }

    @Test
    @DisplayName("Content-Type header is application/json")
    void classifyCuisineTest3() {
        given()
                .post("/recipes/cuisine")
                .then()
                .assertThat()
                .headers("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Let's check that all values correspond to our expected values")
    void classifyCuisineTest4() {
        given()
                .post("/recipes/cuisine")
                .then()
                .statusCode(200)
                .assertThat()
                .body("cuisine", equalTo("Mediterranean"))
                .body("cuisines", hasItems("Mediterranean", "European", "Italian"))
                .body("confidence", equalTo(0.0F));
    }

    @Test
    @DisplayName("We register only the body of the response")
    void classifyCuisineTest5() {
        given()
                .post("/recipes/cuisine")
                .then()
                .log().body().statusCode(200);
    }

    @Test
    @DisplayName("In case of failure, the request and response will be registered")
    void classifyCuisineTest6() {
        given()
                //.log().ifValidationFails()
                .post("/recipes/cuisine")
                .then()
                //.statusCode(200);
                .log().ifValidationFails().statusCode(200);

    }
}


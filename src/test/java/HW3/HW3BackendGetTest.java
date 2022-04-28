package HW3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTest
class HW3BackendGetTest {

    @Test
    @DisplayName("Status code is 200")
    void searchResipesTest1() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                        "query_itl", queryParam,
                        "maxFat", 25,
                        "number", 10,
                        "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking request parameters")
        void searchResipesTest2() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                "query_itl", queryParam,
                "maxFat", 25,
                "number", 10,
                "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .assertThat()
                .body("number", equalTo(10));
    }

    @Test
    @DisplayName("Type image: jpg")
    void searchResipesTest3() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                        "query_itl", queryParam,
                        "maxFat", 25,
                        "number", 10,
                        "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .assertThat()
                .body("results[0].imageType", equalTo("jpg"));
    }

    @Test
    @DisplayName("Nutrient name: fat")
    void searchResipesTest4() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                        "query_itl", queryParam,
                        "maxFat", 25,
                        "number", 10,
                        "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .assertThat()
                .body("results[0].nutrition.nutrients[0].name", equalTo("Fat"));
    }

    @Test
    @DisplayName("Checking the offset parameter")
    void searchResipesTest5() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                        "query_itl", queryParam,
                        "maxFat", 25,
                        "number", 10,
                        "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .assertThat()
                .body("offset", equalTo(0));
    }

    @Test
    @DisplayName("We measure the response time")
    void searchResipesTest6() {
        Response response = RestAssured.get("/recipes/complexSearch");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        assertEquals(timeInS, timeInMS / 1000);
    }

    @Test
    @DisplayName("Content-Type header is application/json")
    void searchResipesTest7() {
        String queryParam = "pasta";
        given()
                .queryParams(Map.of(
                        "query_itl", queryParam,
                        "maxFat", 25,
                        "number", 10,
                        "diet", "vegetarian"))
                .get("/recipes/complexSearch")
                .then()
                .assertThat()
                .headers("Content-Type", "application/json");
    }
}

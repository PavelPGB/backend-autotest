package HW4;

import HW4.ApiTestHW4;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestHW4
class HW4BackendGetTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        String queryParam = "pasta";
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("query_itl", queryParam)
                .addQueryParam("maxFat", 25)
                .addQueryParam("number", 10)
                .addQueryParam("diet", "vegetarian")
                .build();
    }

    @Test
    @DisplayName("Status code is 200")
    void searchResipesTest1() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking request parameters")
    void searchResipesTest2() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .statusCode(200)
                .assertThat()
                .body("number", equalTo(10));
    }

    @Test
    @DisplayName("Type image: jpg")
    void searchResipesTest3() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .statusCode(200)
                .assertThat()
                .body("results[0].imageType", equalTo("jpg"));
    }

    @Test
    @DisplayName("Nutrient name: fat")
    void searchResipesTest4() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .statusCode(200)
                .assertThat()
                .body("results[0].nutrition.nutrients[0].name", equalTo("Fat"));
    }

    @Test
    @DisplayName("Checking the offset parameter")
    void searchResipesTest5() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .statusCode(200)
                .assertThat()
                .body("offset", equalTo(0));
    }

    @Test
    @DisplayName("We measure the response time")
    void searchResipesTest6() {
        Response response = RestAssured.get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint());
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        assertEquals(timeInS, timeInMS / 1000);
    }

    @Test
    @DisplayName("Content-Type header is application/json")
    void searchResipesTest7() {
        given()
                .spec(requestSpecification)
                .get(Endpoints.RECIPES_COMPLEXSEARCH.getEndpoint())
                .then()
                .assertThat()
                .headers("Content-Type", "application/json");
    }
}

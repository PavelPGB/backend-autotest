package HW3;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import HW3.ApiTest;
import static io.restassured.RestAssured.given;

@ApiTest
class HW3BackendTest {
    private static String userName;
    private static String hash;
    private int id;

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();
        System.out.println(faker.chuckNorris().fact());
        JsonPath jsonPath = given()
                .body("{\n" +
                        "    \"username\": \"" + faker.funnyName() + "\",\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\"\n" +
                        "}")
                .post("/users/connect")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        userName = jsonPath.getString("username");
        hash = jsonPath.getString("hash");
    }

    @BeforeEach
    void setUp() {
        given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(0));
    }

    @ParameterizedTest
    @CsvSource(value = {"3 kg apples,Apple", "5 kg bananas,Banana"})
    void addToShoppingListTest(String item, String aisle) {
        given()
                .queryParam("hash", hash)
                .body("{\n" +
                        "    \"item\": \"" + item + "\",\n" +
                        "    \"aisle\": \"" + aisle + "\",\n" +
                        "    \"parse\": true\n" +
                        "}")
                .post("/mealplanner/{username}/shopping-list/items", userName)
                .then()
                .statusCode(200);

        id = given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))
                .body("aisles.aisle", Matchers.hasItems(aisle))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", hash)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", userName, id)
                .then()
                .statusCode(200);
    }
}


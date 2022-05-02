package HW4;

import HW4.dto.CrUserRequest;
import HW4.dto.CrUserResponse;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import HW4.dto.AddItemToShoppingListRequest;

import HW4.Endpoints;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@ApiTestHW4
class HW4BackendTest {
    private static CrUserResponse crUserResponse;
    private static RequestSpecification hashParam;
    private int id;

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();
        crUserResponse = given()
                .body(CrUserRequest.builder()
                        .username(faker.funnyName().name())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .build())
                .post(Endpoints.USER_CONNECT.getEndpoint())
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(CrUserResponse.class);
        hashParam = new RequestSpecBuilder()
                .addQueryParam("hash", crUserResponse.getHash())
                .build();
    }

    @BeforeEach
    void setUp() {
        given()
                .spec(hashParam)
                .get(Endpoints.MEALPLANNER_USERNAME_SHOPPING_LIST.getEndpoint(), crUserResponse.getUsername())
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(0));
    }

    public static Stream<AddItemToShoppingListRequest> shoppingListRequests() {
        return Stream.of(AddItemToShoppingListRequest.builder()
                        .item("3 kg apples")
                        .aisle("Apple")
                        .parse(true)
                        .build(),
                AddItemToShoppingListRequest.builder()
                        .item("5 kg bananas")
                        .aisle("Banana")
                        .parse(true)
                        .build());
    }

    @ParameterizedTest
    @MethodSource("shoppingListRequests")
    void addToShoppingListTest(AddItemToShoppingListRequest addItemToShoppingListRequest) {
        given()
                .log()
                .all()
                .spec(hashParam)
                .body(addItemToShoppingListRequest)
                .post(Endpoints.MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS.getEndpoint(), crUserResponse.getUsername())
                .then()
                .statusCode(200);

        id = given()
                .spec(hashParam)
                .get(Endpoints.MEALPLANNER_USERNAME_SHOPPING_LIST.getEndpoint(), crUserResponse.getUsername())
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))
                .body("aisles.aisle", Matchers.hasItems(addItemToShoppingListRequest.getAisle()))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void tearDown() {
        given()
                .spec(hashParam)
                .delete(Endpoints.MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS_ID.getEndpoint(), crUserResponse.getUsername(), id)
                .then()
                .statusCode(200);
    }
}

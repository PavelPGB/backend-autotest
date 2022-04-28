package HW3;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import static HW3.HW3Config.spoonConfig;

class ApiTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        RestAssured.baseURI = spoonConfig.baseURI();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // если тест упал, то он логируется и эти методы можно не писать .log() .all() .prettyPeek()
        RestAssured.filters(new AllureRestAssured());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("apiKey", spoonConfig.apiKey())
                .build();
    }
}

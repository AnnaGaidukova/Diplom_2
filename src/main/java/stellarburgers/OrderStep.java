package stellarburgers;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class OrderStep extends Config {
    private static final String CREATE_ORDER = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";
    private static final String GET_ORDER = "/api/orders";

    @Step("Create order without authorized user")
    public ValidatableResponse createOrderUnauthorizedUser() {
        return given()
                .spec(getSpecification())
                .when()
                .body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"," +
                        "\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all();
    }
    @Step("Create order with authorized user")
    public void createOrderAuthorizedUser(String accessToken) {
        given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"," +
                        "\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all();
    }
    @Step("Create order with incorrect ingredients")
    public void createOrderAuthorizedUserWithInvalidHash(String accessToken) {
        given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .body("{\n\"ingredients\": [\"9trytr\"," +
                        "\"61c070\",\"61c0a73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all();
    }
    @Step("Create order without ingredients")
    public ValidatableResponse createOrderAuthorizedUserWithoutIngredients(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDER)
                .then().log().all();
    }
    @Step("Get orders with registration")
    public ValidatableResponse getOrdersWithRegistration(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when().get(GET_ORDER)
                .then().log().all();

    }
    @Step("Get orders without registration")
    public ValidatableResponse getOrdersWithoutRegistration() {
        return given()
                .spec(getSpecification())
                .when()
                .get(GET_ORDER)
                .then().log().all();
    }
    @Step("Get ingredients")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpecification())
                .when()
                .get(INGREDIENTS)
                .then().log().all();
    }
}

package stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
public class UserSteps extends Config {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login ";
    private static final String USER_USER = "/api/auth/user";

    @Step("Create User")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpecification())
                .body(user)
                .when()
                .post(CREATE_USER)
                .then().log().all();
    }
    @Step("Authorize User")
    public ValidatableResponse loginUser(User user) {
        return given()
                .spec(getSpecification())
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then().log().all();
    }
    @Step("Refresh User")
    public ValidatableResponse updateUser(String accessToken, User user) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_USER)
                .then().log().all();
    }
    @Step("Get users data")
    public ValidatableResponse getDataUser(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(USER_USER)
                .then().log().all();
    }
    @Step("Delete user")
    public void deleteUser(String accessToken) {
        given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_USER)
                .then();
    }
}

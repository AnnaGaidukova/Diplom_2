package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.apache.http.HttpStatus.*;


public class OrderTest {
    private UserSteps userSteps;
    private OrderStep orderStep;
    private User user;
   // private User userLogin;
    private String accessToken;

    @Before
    public void setOrder() {
        user = CreateRandomUser.random();
        userSteps = new UserSteps();
        orderStep = new OrderStep();
       // userLogin = new User();
    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(user.getToken());
        }
    }
    @Test
    @DisplayName("Create order without authorized user")
    @Description("Create order without authorized user. Success checking.")
    public void createOrderWithoutAuthorizationTest() {
        ValidatableResponse response = orderStep.createOrderUnauthorizedUser();
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
    }
    @Test
    @DisplayName("Create order with incorrect ingredients")
    @Description("Create order with incorrect ingredients. Error checking.")
    public void createOrderWithInvalidHashTest() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.loginUser(user);
        accessToken = response.extract().path("accessToken").toString();
       //Response badOrders =  badOrders
        orderStep.createOrderAuthorizedUserWithInvalidHash(accessToken)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @Test
    @DisplayName("Create order with authorized user")
    @Description("Create order with authorized user. Success checking.")
    public void createOrderWithAuthorizationIngredientsTest() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.loginUser(user);
        accessToken = response.extract().path("accessToken").toString();
        orderStep.createOrderAuthorizedUser(accessToken);
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
    }
    @Test
    @DisplayName("Create order without ingredients")
    @Description("Create order without ingredients. Error checking.")
    public void createOrderWithAuthorizationAndWithoutIngredientTest() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.loginUser(user);
        accessToken = response.extract().path("accessToken").toString();
        ValidatableResponse emptyOrder = orderStep.createOrderAuthorizedUserWithoutIngredients(accessToken);
        emptyOrder
                .assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("success", is(false));
    }
}
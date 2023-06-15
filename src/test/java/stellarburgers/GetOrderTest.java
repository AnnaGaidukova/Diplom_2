package stellarburgers;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class GetOrderTest {
    private UserSteps userSteps;
    private OrderStep orderStep;
    private User user;                         //private User userLogin;
    //private String accessToken;

    @Before
    public void setOrder() {
        user = CreateRandomUser.random();
        userSteps = new UserSteps();
        orderStep = new OrderStep();         //  userLogin = new User();
    }
    @After
    public void cleanUp() {
            userSteps.deleteUser(user.getToken());
    }
    @Test
    @DisplayName("Check Get User Order With Authorization")
    @Description("Check Get User Order With Authorization. Success checking.")
    public void checkGetUserOrderWithAuthorization() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.loginUser(user);
        String accessToken = response.extract().path("accessToken").toString();
        orderStep.getOrdersWithRegistration(accessToken);
        response
                .assertThat().body("success", Matchers.is(true))
                .and().statusCode(SC_OK);
    }
    //@Description
    @Test
    @DisplayName("Check Get User Order Without Authorization")
    @Description("Check Get User Order Without Authorization. Error checking.")
    public void checkGetUserOrderWithoutAuthorization() {
        ValidatableResponse response = orderStep.getOrdersWithoutRegistration();
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}
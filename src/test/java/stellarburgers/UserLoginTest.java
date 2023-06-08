package stellarburgers;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.apache.http.HttpStatus.*;
public class UserLoginTest {
    private User user;
    private UserSteps userSteps;
    private User userLogin;
    private String accessToken;

    @Before
    public void setUser() {
        user = CreateRandomUser.random();
        userSteps = new UserSteps();
        userLogin = new User();
        userSteps.loginUser(userLogin);
    }
    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
    @Test
    @DisplayName("Check Correct Authorization")
    @Description("Check Correct Authorization. Success checking.")
    public void checkCorrectAuthorization() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.loginUser(user);
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
    }
    @Test
    @DisplayName("Check Authorization Without Login")
    @Description("Check Authorization Without Login. Error checking.")
    public void checkAuthorizationWithoutLogin() {
        user.setEmail("");
        ValidatableResponse response = userSteps.loginUser(user);
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Check Authorization Without Password")
    @Description("Check Authorization Without Password. Error checking.")
    public void checkAuthorizationWithoutPassword() {
        user.setPassword("");
        ValidatableResponse response = userSteps.loginUser(user);
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Check Authorization With Incorrect Login")
    @Description("Check Authorization With Incorrect Login. Error checking.")
    public void checkAuthorizationWithIncorrectLogin() {
        user.setEmail("testtest");
        ValidatableResponse response = userSteps.loginUser(user);
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Check Authorization With Incorrect Password")
    @Description("Check Authorization With Incorrect Password. Error checking.")
    public void checkAuthorizationWithIncorrectPassword() {
        user.setPassword("test");
        ValidatableResponse response = userSteps.loginUser(user);
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
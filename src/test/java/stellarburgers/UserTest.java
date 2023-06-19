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

public class UserTest {
    private User user;
    private UserSteps userSteps;

    @Before
    public void setUser() {
        user = CreateRandomUser.random();
        userSteps = new UserSteps();
    }
    @After
    public void cleanUp() {
            userSteps.deleteUser(user.getToken());
    }
        @Test
        @DisplayName("Check create user")
        @Description("Check create user. Success checking.")
        public void checkCreateUser() {
        ValidatableResponse response = userSteps.createUser(user);
            String accessToken =response
                    .extract()
                    .header("Authorization");
            user.setToken(accessToken);
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
        }
    @Test
    @DisplayName("Check Create User Without Name")
    @Description("Check Create User Without Name. Error checking.")
    public void checkCreateUserWithoutName() {
        user.setName("");
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Check Create User Without Email")
    @Description("Check Create User Without Email. Error checking.")
    public void checkCreateUserWithoutEmail() {
        user.setEmail("");
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Check Create User Without Password")
    @Description("Check Create User Without Password. Error checking.")
    public void checkCreateUserWithoutPassword() {
        user.setPassword("");
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Check Create User With similar Login")
    @Description("Check Create User With similar Login. Error checking.")
    public void checkCreateUserWithSimilarLogin() {
        userSteps.createUser(user);
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("User already exists"));
    }
}
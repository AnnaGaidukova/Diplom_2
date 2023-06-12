package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;


public class UserUpgradeTest {
    private User user;
    private User userForUpdate;
    private UserSteps userSteps;

    @Before
    public void setUser() {
        user = CreateRandomUser.random();
        userForUpdate = CreateRandomUser.random();
        userSteps = new UserSteps();
    }
    @After
    public void cleanUp() {
            userSteps.deleteUser(user);
    }
    @Test
    @DisplayName("Check Update Data Registered User")
    @Description("Check Update Data Registered User. Success checking.")
    public void checkUpdateDataRegisteredUser() {
        User initialUser = CreateRandomUser.random();
        User userForUpdate = initialUser.clone();
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(6) + "@changes.com");

        String accessToken = userSteps.createUser(initialUser)
                .extract()
                .header("Authorization");
        userSteps.updateUser(accessToken, userForUpdate);
        ValidatableResponse updatedUserResponse = userSteps.getDataUser(accessToken);
        updatedUserResponse.body("user.name", equalTo(initialUser.getName()))
                .and()
                .body("user.email", equalTo(userForUpdate.getEmail().toLowerCase()));
    }
    @Test
    @DisplayName("Check update Data Unregistered User")
    @Description("Check Update Data Unregistered User. Error checking.")
    public void checkUpdateDataUnregisteredUser() {
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(6) + "@changeableness.com");
        ValidatableResponse response = userSteps.updateUser("", userForUpdate);
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}
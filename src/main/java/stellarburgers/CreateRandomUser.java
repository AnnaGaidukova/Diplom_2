package stellarburgers;
import org.apache.commons.lang3.RandomStringUtils;
public class CreateRandomUser {
    public static User random() {
        String token = "";
        return new User(RandomStringUtils.randomAlphabetic(6) + "@yandex.ru", "Test123", "TestUserRandom", token);
    }
}

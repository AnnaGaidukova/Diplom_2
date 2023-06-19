package stellarburgers;

public class User {
    private String email;
    private String password;
    private String name;
    private String token;

    public User(String email, String password, String name, String token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.token = token;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setToken(String token) {
        this.token = token.substring(7);
    }
    public String getToken() {
        return token;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public User clone() {
        User newUser = new User();
        newUser.setName(this.name);
        newUser.setEmail(this.email);
        newUser.setPassword(this.password);
        return newUser;
    }
}

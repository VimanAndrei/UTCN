package Model.User;


public class UserBuilder {
    private String username;
    private String password;
    private String role;

    public UserBuilder() {
        this.username = "";
        this.password = "";
        this.role = "";
    }
    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }
    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
    public UserBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public User build(){
        return new User(username,password,role);
    }
}

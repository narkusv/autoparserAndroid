package prototype.vn.autoparser.BackendModels;

/**
 * Created by narku on 2017-06-20.
 */
public class LoginModel {

    private final int client_id = 2;
    private final String client_secret = "dthZNynlC549mhAYQlbNAhoLqjunWyoyf2UO9VmC";
    private final String grant_type = "password";

    public String username = "";
    public String password = "";

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

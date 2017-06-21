package prototype.vn.autoparser.BackendModels;

/**
 * Created by narku on 2017-06-20.
 */
public class RegisterModel extends LoginModel {


    public String email;


    public String name;

    public RegisterModel(String name, String password, String email) {
        super("", password);
        this.name = name;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

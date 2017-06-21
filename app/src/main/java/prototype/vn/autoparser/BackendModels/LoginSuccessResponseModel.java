package prototype.vn.autoparser.BackendModels;

/**
 * Created by narku on 2017-06-21.
 */

public class LoginSuccessResponseModel {
    public long expires_in;
    private String access_token;

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public LoginSuccessResponseModel(long expires_in, String access_token) {

        this.expires_in = expires_in;
        this.access_token = access_token;
    }

    public LoginSuccessResponseModel() {
    }
}

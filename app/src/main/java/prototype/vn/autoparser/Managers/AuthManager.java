package prototype.vn.autoparser.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import prototype.vn.autoparser.BackendModels.LoginModel;
import prototype.vn.autoparser.BackendModels.LoginSuccessResponseModel;
import prototype.vn.autoparser.BackendModels.RegisterModel;
import prototype.vn.autoparser.Fragments.LoginFragment;


public class AuthManager {
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://10.0.3.2:8000/";
    private static final String API_POSTFIX = "api/";
    private static final String LOGIN_POSTFIX = "oauth/token";
    private static final String REGISTER_USER_URL = "registerApiUser";

    private static final String KEY_USER_AUTH = "user_auth_details";

    private Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    public void Login(LoginModel loginModel, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }

        JSONObject jsonRequest = new JSONObject(new Gson().toJson(loginModel));
        JsonObjectRequest loginUserRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + LOGIN_POSTFIX, jsonRequest, responseListener, errorListener);

        Log.d(LoginFragment.TAG, jsonRequest.toString());

        requestQueue.add(loginUserRequest);
    }

    public void Register(RegisterModel registerModel, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }

        JSONObject jsonRequest = new JSONObject(new Gson().toJson(registerModel));
        JsonObjectRequest loginUserRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + REGISTER_USER_URL, jsonRequest, responseListener, errorListener);

        Log.d(LoginFragment.TAG, jsonRequest.toString());

        requestQueue.add(loginUserRequest);


    }

    public void saveUserDetails(LoginSuccessResponseModel loginSuccess) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        loginSuccess.expires_in += System.currentTimeMillis();

        prefs.edit().putString(KEY_USER_AUTH, new Gson().toJson(loginSuccess)).apply();
    }

    public LoginSuccessResponseModel getUserDetails() throws Exception {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        LoginSuccessResponseModel loginDetails = new Gson().fromJson(prefs.getString(KEY_USER_AUTH, "{}"), LoginSuccessResponseModel.class);
        if (loginDetails.getAccess_token().isEmpty() || loginDetails.getExpires_in() == 0) {
            throw new Exception("Missing login details");
        }

        if (System.currentTimeMillis() > loginDetails.getExpires_in()) {
            destroyUserDetails();
            throw new Exception("Session has expired");
        }

        return loginDetails;
    }

    public boolean isAuthenticated() {
        try {
            getUserDetails();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void destroyUserDetails() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        prefs.edit().remove(KEY_USER_AUTH).apply();
    }


}

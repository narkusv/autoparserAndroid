package prototype.vn.autoparser.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prototype.vn.autoparser.BackendModels.LoginModel;
import prototype.vn.autoparser.BackendModels.LoginSuccessResponseModel;
import prototype.vn.autoparser.MainActivity;
import prototype.vn.autoparser.R;
import prototype.vn.autoparser.RegisterActivity;

/**
 * Created by narku on 2017-06-20.
 */
public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick(R.id.btn_register)
    public void openRegisterFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, new RegisterFragment(), RegisterFragment.TAG).commit();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        if (et_username.length() > 0 && et_password.length() > 0) {
            LoginModel userLoginModel = new LoginModel(et_username.getText().toString(), et_password.getText().toString());

            try {
                ((RegisterActivity) getActivity()).authManager.Login(userLoginModel, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + response.toString());

                        LoginSuccessResponseModel successModel = new Gson().fromJson(response.toString(), LoginSuccessResponseModel.class);

                        ((RegisterActivity) getActivity()).authManager.saveUserDetails(successModel);
                        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 401) {
                            Toast.makeText(getActivity(), "Wrong credentials. Are you registered?", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to send login details. Are you connected to internet?", Toast.LENGTH_LONG).show();
                        }
                        Log.d(TAG, error.toString());
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Failed to send login details", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}

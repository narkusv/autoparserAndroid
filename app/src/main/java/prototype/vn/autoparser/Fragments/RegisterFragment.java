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
import prototype.vn.autoparser.BackendModels.LoginSuccessResponseModel;
import prototype.vn.autoparser.BackendModels.RegisterModel;
import prototype.vn.autoparser.MainActivity;
import prototype.vn.autoparser.R;
import prototype.vn.autoparser.RegisterActivity;

/**
 * Created by narku on 2017-06-20.
 */
public class RegisterFragment extends Fragment {
    public static final String TAG = "RegisterFragment";

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @BindView(R.id.btn_register)
    Button btn_register;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick(R.id.btn_register)
    public void register() {
        Log.d(TAG, "register: OnClick");
        if (et_email.length() <= 0 || et_username.length() <= 0 || et_password.length() <= 0 || et_confirm_password.length() <= 0) {
            Toast.makeText(getActivity(), "Some fields are missing", Toast.LENGTH_LONG).show();
            return;
        }
        if (!et_confirm_password.getText().toString().equals(et_password.getText().toString())) {
            Toast.makeText(getActivity(), "Password are not same", Toast.LENGTH_LONG).show();
            return;
        }
        RegisterModel registerModel = new RegisterModel(et_username.getText().toString(), et_password.getText().toString(), et_email.getText().toString());
        try {
            ((RegisterActivity) getActivity()).authManager.Register(registerModel, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getActivity(), "Registration success", Toast.LENGTH_SHORT).show();
                    LoginSuccessResponseModel successModel = new Gson().fromJson(response.toString(), LoginSuccessResponseModel.class);

                    ((RegisterActivity) getActivity()).authManager.saveUserDetails(successModel);
                    getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse.statusCode == 400) {
                        Toast.makeText(getActivity(), "This email propably already exists. Try a different one.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to send login details. Are you connected to internet?", Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, new String(error.networkResponse.data));
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Failed to send login details", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
}

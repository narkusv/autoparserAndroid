package prototype.vn.autoparser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import prototype.vn.autoparser.Fragments.LoginFragment;
import prototype.vn.autoparser.Managers.AuthManager;

/**
 * Created by narku on 2017-06-20.
 */
public class RegisterActivity extends AppCompatActivity {

    public AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), LoginFragment.TAG).commit();

        authManager = new AuthManager(this);
    }


}

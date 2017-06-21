package prototype.vn.autoparser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import prototype.vn.autoparser.Fragments.ParseUrlsFragment;
import prototype.vn.autoparser.Managers.AuthManager;
import prototype.vn.autoparser.Managers.ClassifiedsManager;

public class MainActivity extends AppCompatActivity {
    public AuthManager authManager;
    public ClassifiedsManager classifiedsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authManager = new AuthManager(this);

        if (authManager.isAuthenticated()) {
            classifiedsManager = new ClassifiedsManager(this);
            //getSupportFragmentManager().beginTransaction().add(R.id.new_classifieds_container, ClassifiedsPreviewFragment.newInstance(BASE_URL + API_POSTFIX + CLASSIFIEDS_PREVIEW_POSTFIX), ClassifiedsPreviewFragment.TAG).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ParseUrlsFragment(), ParseUrlsFragment.TAG).commit();

        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}

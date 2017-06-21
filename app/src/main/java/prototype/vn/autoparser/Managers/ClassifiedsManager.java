package prototype.vn.autoparser.Managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import prototype.vn.autoparser.Fragments.LoginFragment;
import prototype.vn.autoparser.OptionalJSONArrayRequest;

public class ClassifiedsManager {
    private RequestQueue requestQueue;
    public static final String BASE_URL = "http://10.0.3.2:8000/";
    public static final String API_POSTFIX = "api/";

    public static final String CLASSIFIEDS_PREVIEW_POSTFIX = "Preview_new_classifieds";
    public static final String NEW_CLASSIFIEDS_POSTFIX = "New_classifieds";
    public static final String CLASSIFIEDS_POSTFIX = "Classifieds/";
    public static final String PARSE_URLS_POSTFIX = "ParseURL/";


    private Context context;

    public ClassifiedsManager(Context context) {
        this.context = context;
    }

    public void RetrieveClassifiedsPreviewData(String url, AuthManager authManager, Response.Listener responseListener, Response.ErrorListener errorListener) throws Exception {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }


        OptionalJSONArrayRequest stringRequest = new OptionalJSONArrayRequest(Request.Method.GET, url, null, responseListener, errorListener);
        stringRequest.setHeaders("Authorization", "Bearer " + authManager.getUserDetails().getAccess_token());
        requestQueue.add(stringRequest);

        Log.d(LoginFragment.TAG, stringRequest.toString());
    }

    public void RetrieveParseUrlsData(AuthManager authManager, Response.Listener responseListener, Response.ErrorListener errorListener) throws Exception {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }


        OptionalJSONArrayRequest stringRequest = new OptionalJSONArrayRequest(Request.Method.GET, BASE_URL + API_POSTFIX + PARSE_URLS_POSTFIX, null, responseListener, errorListener);
        stringRequest.setHeaders("Authorization", "Bearer " + authManager.getUserDetails().getAccess_token());
        requestQueue.add(stringRequest);

        Log.d(LoginFragment.TAG, stringRequest.toString());
    }


}

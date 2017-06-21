package prototype.vn.autoparser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import prototype.vn.autoparser.Adapters.ClassifiedsAdapter;
import prototype.vn.autoparser.BackendModels.Classified;
import prototype.vn.autoparser.MainActivity;
import prototype.vn.autoparser.R;

/**
 * Created by narku on 2017-06-20.
 */
public class ClassifiedsPreviewFragment extends Fragment {
    public static final String TAG = "ClassifiedsPreview";

    @BindView(R.id.recycler_new_classifieds)
    RecyclerView newClassifiedsRecyler;

    ClassifiedsAdapter newClassifiedsAdapter;

    public static ClassifiedsPreviewFragment newInstance(String requestURL) {
        ClassifiedsPreviewFragment myFragment = new ClassifiedsPreviewFragment();

        Bundle args = new Bundle();
        args.putString("requestURL", requestURL);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_classifieds, container, false);
        ButterKnife.bind(this, view);

        newClassifiedsAdapter = new ClassifiedsAdapter(getContext());

        newClassifiedsRecyler.setHasFixedSize(true);
        newClassifiedsRecyler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        newClassifiedsRecyler.setAdapter(newClassifiedsAdapter);


        try {
            String url = getArguments().getString("requestURL");
            ((MainActivity) getActivity()).classifiedsManager.RetrieveClassifiedsPreviewData(url, ((MainActivity) getActivity()).authManager, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "onResponse: " + response.toString());
                    Classified[] classifieds = new Gson().fromJson(response.toString(), Classified[].class);
                    newClassifiedsAdapter.setDataset(classifieds);
                    newClassifiedsAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: data length" + classifieds.length);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        Log.d(TAG, "onErrorResponse: " + new String(error.networkResponse.data));
                    }
                    Log.d(TAG, "onErrorResponse: " + error.toString());
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "onCreateView: User not authenticated");
            e.printStackTrace();
        }

        return view;
    }
}

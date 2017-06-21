package prototype.vn.autoparser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import butterknife.OnClick;
import prototype.vn.autoparser.Adapters.ParseUrlsAdapter;
import prototype.vn.autoparser.BackendModels.ParseUrls;
import prototype.vn.autoparser.MainActivity;
import prototype.vn.autoparser.R;

import static prototype.vn.autoparser.Managers.ClassifiedsManager.API_POSTFIX;
import static prototype.vn.autoparser.Managers.ClassifiedsManager.BASE_URL;
import static prototype.vn.autoparser.Managers.ClassifiedsManager.CLASSIFIEDS_POSTFIX;
import static prototype.vn.autoparser.Managers.ClassifiedsManager.NEW_CLASSIFIEDS_POSTFIX;


public class ParseUrlsFragment extends Fragment {
    public static final String TAG = "ParseUrlsFragment";

    @BindView(R.id.recycler_parse_urls)
    RecyclerView parse_urls_recycler;
    ParseUrlsAdapter parseUrlsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parse_urls, container, false);
        ButterKnife.bind(this, view);

        parseUrlsAdapter = new ParseUrlsAdapter(getContext());
        parseUrlsAdapter.setParseUrlsListener(new ParseUrlsAdapter.IParseUrls() {
            @Override
            public void OnCheckClick(View v) {
                int pos = parse_urls_recycler.getChildLayoutPosition(v);
                String itemID = String.valueOf(parseUrlsAdapter.getItemId(pos));
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.new_classifieds_container, ClassifiedsPreviewFragment.newInstance(BASE_URL + API_POSTFIX + CLASSIFIEDS_POSTFIX + itemID), ClassifiedsPreviewFragment.TAG).commit();
            }

            @Override
            public void OnDeleteClick(View v) {
                Log.d(TAG, "OnDeleteClick: clicked position" + parse_urls_recycler.getChildLayoutPosition(v));
            }
        });

        parse_urls_recycler.setHasFixedSize(true);
        parse_urls_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        parse_urls_recycler.setAdapter(parseUrlsAdapter);

        try {
            ((MainActivity) getActivity()).classifiedsManager.RetrieveParseUrlsData(((MainActivity) getActivity()).authManager, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "onResponse: " + response.toString());
                    ParseUrls[] parseUrls = new Gson().fromJson(response.toString(), ParseUrls[].class);
                    parseUrlsAdapter.setDataset(parseUrls);
                    parseUrlsAdapter.notifyDataSetChanged();

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


    @OnClick(R.id.btn_new_classifieds)
    public void LoadNewClassifieds() {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.new_classifieds_container, ClassifiedsPreviewFragment.newInstance(BASE_URL + API_POSTFIX + NEW_CLASSIFIEDS_POSTFIX), ClassifiedsPreviewFragment.TAG).commit();
    }
}

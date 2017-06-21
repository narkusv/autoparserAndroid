package prototype.vn.autoparser.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prototype.vn.autoparser.BackendModels.ParseUrls;
import prototype.vn.autoparser.R;

/**
 * Created by narku on 2017-06-21.
 */

public class ParseUrlsAdapter extends RecyclerView.Adapter<ParseUrlsAdapter.ViewHolder> {


    public interface IParseUrls {
        void OnCheckClick(View v);

        void OnDeleteClick(View v);
    }

    private Context context;
    private ParseUrls[] mDataset;

    private IParseUrls mParseUrlsListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.tv_url)
        public TextView tv_url;

        @BindView(R.id.tv_name)
        public TextView tv_name;

        @BindView(R.id.btn_check)
        Button btn_check;


        @OnClick(R.id.btn_check)
        public void onCheckClicked() {

        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ParseUrlsAdapter(Context context, ParseUrls[] myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    public ParseUrlsAdapter(Context context) {
        this.context = context;
        mDataset = new ParseUrls[]{};
    }

    public void setDataset(ParseUrls[] mDataset) {
        this.mDataset = mDataset;
    }

    public void setParseUrlsListener(IParseUrls mParseUrlsListener) {
        this.mParseUrlsListener = mParseUrlsListener;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ParseUrlsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parse_url_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_name.setText(mDataset[position].pivot.name);
        holder.tv_url.setText(mDataset[position].url);

        holder.btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParseUrlsListener.OnCheckClick(holder.itemView);
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return mDataset[position].id;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public ParseUrls getItemAt(int pos) {
        return mDataset[pos];
    }
}


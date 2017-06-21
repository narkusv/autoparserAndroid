package prototype.vn.autoparser.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import prototype.vn.autoparser.BackendModels.Classified;
import prototype.vn.autoparser.R;

/**
 * Created by narku on 2017-06-21.
 */

public class ClassifiedsAdapter extends RecyclerView.Adapter<ClassifiedsAdapter.ViewHolder> {

    private Context context;
    private Classified[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.tv_url)
        public TextView tv_url;
        @BindView(R.id.tv_name)
        public TextView tv_name;
        @BindView(R.id.iv_ad)
        public ImageView iv_ad;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClassifiedsAdapter(Context context, Classified[] myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    public ClassifiedsAdapter(Context context) {
        this.context = context;
        mDataset = new Classified[]{};
    }

    public void setDataset(Classified[] mDataset) {

        this.mDataset = mDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ClassifiedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classified_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_name.setText(mDataset[position].getName());
        holder.tv_url.setText(mDataset[position].getUrl());
        Picasso.with(context).load(mDataset[position].getImg()).into(holder.iv_ad);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}


package com.fifzu.goalsetter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Nilanchala Panigrahy on 10/25/16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<Goal> goalList;
    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<Goal> goalList) {
        this.goalList = goalList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_goal_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Goal goalItem = goalList.get(i);
        /*
        //Download image using picasso library
        if (!TextUtils.isEmpty(goalItem.getThumbnail())) {
            Picasso.with(mContext).load(goalItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(goalItem.getName()));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(goalItem);
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.textView.setOnClickListener(listener);
        */
        customViewHolder.textView.setText(goalItem.getName());
    }

    @Override
    public int getItemCount() {
        return (null != goalList ? goalList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
    //    protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);

            //this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.list_goal_item_name);
        }
    }


    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
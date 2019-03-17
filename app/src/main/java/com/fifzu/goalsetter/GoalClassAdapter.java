package com.fifzu.goalsetter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoalClassAdapter extends ArrayAdapter {

    String[] spinnerTitles;
    int[] spinnerImages = {R.drawable.ic_attach_money,
            R.drawable.ic_school,R.drawable.ic_group,
            R.drawable.ic_favorite,R.drawable.ic_wc};
    Context mContext;

    public GoalClassAdapter(@NonNull Context context) {
        super(context, R.layout.goal_class_row);

        this.spinnerTitles = getContext().getResources().getStringArray(R.array.goalsClass_array);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return  getContext().getResources().getStringArray(R.array.goalsClass_array).length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.goal_class_row, parent, false);
            mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.ivImage);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mImage.setImageResource(spinnerImages[position]);
        mViewHolder.mTitle.setText(spinnerTitles[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    private static class ViewHolder {
        ImageView mImage;
        TextView mTitle;
    }

}

package com.fifzu.goalsetter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NavMood extends Fragment{
    ProgressBar moodBar;
    TextView tvStatusName;
    ImageView ivMood;
    TextView tvStatusMood;

    private int[] progressBarColors = {
            Color.WHITE,Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.LTGRAY
    };

    private static Integer[] moodDatabase = {R.drawable.ic_not_interested_3x,
            R.drawable.ic_sentiment_very_dissatisfied_3x,R.drawable.ic_sentiment_dissatisfied_3x,
            R.drawable.ic_sentiment_neutral_3x,R.drawable.ic_sentiment_satisfied_3x,R.drawable.ic_sentiment_very_satisfied_3x};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.nav_mood, container, false);
        int status =calculateStatus();
        moodBar = view.findViewById(R.id.mood_bar);
        tvStatusName = view.findViewById(R.id.statusID);
        ivMood = view.findViewById(R.id.moodImage);
        tvStatusMood = view.findViewById(R.id.statusMood);

        updateStatus(status);
        return view;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.mood_manager));
    }

    private void updateStatus(int s) {

        MainActivity myActivity = (MainActivity) getActivity();
        int mood = myActivity.getMood();

        tvStatusMood.setText(Integer.toString(mood));

        moodBar.setProgress(s);
        double dbMappedMood=  Math.ceil((double)s/2);

        int mappedMood = (int) dbMappedMood;

        int color = progressBarColors[mappedMood];
        moodBar.setProgressTintList(ColorStateList.valueOf(color));

        ivMood.setImageResource(moodDatabase[mappedMood]);

        tvStatusName.setText( getResources().getStringArray(R.array.mood_array)[mappedMood]);
    }

    private int calculateStatus() {
        int status=0;
        MainActivity myActivity = (MainActivity) getActivity();
        int mood = myActivity.getMood();

        if(mood>180) {
            status=10;
        } else if (mood>120) {
            status = 9;
        } else if (mood>80) {
            status = 8;
        } else if (mood>40) {
            status = 7;
        } else if (mood>0) {
            status = 6;
        } else if (mood>-40) {
            status = 5;
        } else if (mood>-80) {
            status = 4;
        } else if (mood>-120) {
            status = 3;
        } else if (mood>-180) {
            status = 2;
        } else {
            status = 1;
        }

        return status;
    }
}

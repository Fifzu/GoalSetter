package com.fifzu.goalsetter;

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
        getActivity().setTitle("Mood Manager");
    }

    private void updateStatus(int s) {

        MainActivity myActivity = (MainActivity) getActivity();
        int mood = myActivity.getMood();

        tvStatusMood.setText(Integer.toString(mood));

        moodBar.setProgress(s);
        ivMood.setImageResource(moodDatabase[s]);

        tvStatusName.setText( getResources().getStringArray(R.array.mood_array)[s]);
    }

    private int calculateStatus() {
        int status=0;
        MainActivity myActivity = (MainActivity) getActivity();
        int mood = myActivity.getMood();

        if(mood>200) {
            status=5;
        } else if (mood>40) {
            status = 4;
        } else if (mood>-40) {
            status = 3;
        } else if (mood>-200) {
            status = 2;
        } else {
            status = 1;
        }

        return status;
    }
}

package com.fifzu.goalsetter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalDateTime;

public class NavMood extends Fragment{
    ProgressBar moodBar;
    TextView tvStatusName;
    ImageView ivMood;
    TextView tvStatusMood;
    MainActivity myActivity;

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
        myActivity = (MainActivity) getActivity();
        int status =calculateStatus();
        moodBar = view.findViewById(R.id.mood_bar);
        tvStatusName = view.findViewById(R.id.statusID);
        ivMood = view.findViewById(R.id.moodImage);
        tvStatusMood = view.findViewById(R.id.statusMood);
        updateStatus(status);

        final Goal nextGoal = myActivity.getNextGoal();

        TextView goalName = view.findViewById(R.id.nextGoalName);
        goalName.setText(nextGoal.getName());

        if(nextGoal.getUniqueID()!=null){
            ImageView image = view.findViewById(R.id.nextGoalImage);
            image.setImageResource(nextGoal.getGoalIcon());

            TextView goalValid = view.findViewById(R.id.nextGoalValid);
            goalValid.setText(nextGoal.getValidUntil());

            TextView validUntilDays = view.findViewById(R.id.validUntilDays);
            String validUntil = myActivity.formatDuration(LocalDateTime.now(),nextGoal.getValidUntilDate());

            validUntilDays.setText(validUntil);

            LinearLayout llLayout = view.findViewById(R.id.nextGoalLayout);
            final MainActivity finalMainActivity = myActivity;

            llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    PopupMenu popup = new PopupMenu(getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.goal_popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    deleteGoal(nextGoal.getUniqueID(),nextGoal.getName(),finalMainActivity);
                                    break;
                                case R.id.confirm:
                                    confirmGoal(nextGoal.getUniqueID(),nextGoal.getName(),finalMainActivity);
                                    break;
                                case R.id.edit:
                                    editGoal(nextGoal.getUniqueID(),finalMainActivity);
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
            return view;
        }
        return view;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.mood_manager));
    }

    private void updateStatus(int s) {


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

    private void deleteGoal(final int index, String goatName, final MainActivity myActivity) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        myActivity.deleteGoalWithID(index);
                        changeFragment(R.id.nav_mood);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getString(R.string.delete_message) +"\n" + goatName).setPositiveButton(getContext().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getContext().getString(R.string.no), dialogClickListener).show();
    }

    private void confirmGoal(final int index, String goatName, final MainActivity myActivity) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        myActivity.confirmGoalWithID(index);
                        changeFragment(R.id.nav_mood);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getString(R.string.confirm_message) +"\n" + goatName).setPositiveButton(getContext().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getContext().getString(R.string.no), dialogClickListener).show();
    }
    private void editGoal(final int index, final MainActivity myActivity) {
        myActivity.editGoalWithID(index);
    }

    private void changeFragment(int fragmentId) {
        myActivity.displayFragment(fragmentId);
    }
}

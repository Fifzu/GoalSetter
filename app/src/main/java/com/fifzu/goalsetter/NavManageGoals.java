package com.fifzu.goalsetter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NavManageGoals extends Fragment {


    private ArrayList<Goal> shortGoalList;
    private ArrayList<Goal> mediumGoalList;
    private ArrayList<Goal> longGoalList;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nav_manage_goals, container, false);

        mainActivity = (MainActivity) getActivity();
        shortGoalList = mainActivity.getShortGoalList();
        mediumGoalList = mainActivity.getMediumGoalList();
        longGoalList = mainActivity.getLongGoalList();

        for (int i = 0; i < shortGoalList.size(); i++) {
            ImageView iv = new ImageView(getContext());
            TextView tvName = new TextView(getContext());
            TextView tvValid = new TextView(getContext());


            switch (i) {
                case 0:
                    iv = view.findViewById(R.id.shortGoal0Image);
                    tvName = view.findViewById(R.id.shortGoal0Name);
                    tvValid = view.findViewById(R.id.shortGoal0Valid);
                    break;
                case 1:
                    iv = view.findViewById(R.id.shortGoal1Image);
                    tvName = view.findViewById(R.id.shortGoal1Name);
                    tvValid = view.findViewById(R.id.shortGoal1Valid);
                    break;
                case 2:
                    iv = view.findViewById(R.id.shortGoal2Image);
                    tvName = view.findViewById(R.id.shortGoal2Name);
                    tvValid = view.findViewById(R.id.shortGoal2Valid);
                    break;
                case 3:
                    iv = view.findViewById(R.id.shortGoal3Image);
                    tvName = view.findViewById(R.id.shortGoal3Name);
                    tvValid = view.findViewById(R.id.shortGoal3Valid);
                    break;
            }
            tvName.setText(shortGoalList.get(i).getName());
            tvValid.setText(shortGoalList.get(i).getValidUntil());
            iv.setImageResource(shortGoalList.get(i).getGoalIcon());
        }


        for (int i = 0; i < mediumGoalList.size(); i++) {
            ImageView iv = new ImageView(getContext());
            TextView tvName = new TextView(getContext());
            TextView tvValid = new TextView(getContext());

            switch (i) {
                case 0:
                    iv = view.findViewById(R.id.mediumGoal0Image);
                    tvName = view.findViewById(R.id.mediumGoal0Name);
                    tvValid = view.findViewById(R.id.mediumGoal0Valid);
                    break;
                case 1:
                    iv = view.findViewById(R.id.mediumGoal1Image);
                    tvName = view.findViewById(R.id.mediumGoal1Name);
                    tvValid = view.findViewById(R.id.mediumGoal1Valid);
                    break;
                case 2:
                    iv = view.findViewById(R.id.mediumGoal2Image);
                    tvName = view.findViewById(R.id.mediumGoal2Name);
                    tvValid = view.findViewById(R.id.mediumGoal2Valid);
                    break;
            }
            tvName.setText(mediumGoalList.get(i).getName());
            tvValid.setText(mediumGoalList.get(i).getValidUntil());
            iv.setImageResource(mediumGoalList.get(i).getGoalIcon());
        }


        for (int i = 0; i < longGoalList.size(); i++) {
            ImageView iv = new ImageView(getContext());
            TextView tvName = new TextView(getContext());
            TextView tvValid = new TextView(getContext());

            switch (i) {
                case 0:
                    iv = view.findViewById(R.id.longGoal0Image);
                    tvName = view.findViewById(R.id.longGoal0Name);
                    tvValid = view.findViewById(R.id.longGoal0Valid);
                    break;
                case 1:
                    iv = view.findViewById(R.id.longGoal1Image);
                    tvName = view.findViewById(R.id.longGoal1Name);
                    tvValid = view.findViewById(R.id.longGoal1Valid);
                    break;
            }
            tvName.setText(longGoalList.get(i).getName());
            tvValid.setText(longGoalList.get(i).getValidUntil());
            iv.setImageResource(longGoalList.get(i).getGoalIcon());
        }



        Button btnAddGoal = view.findViewById(R.id.manageGoals_AddButton);
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              changeFragment(R.id.nav_add_goals);
                                          }
                                      }
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Manage Goals");
    }

    private void changeFragment(int fragmentId) {
        mainActivity.displayFragment(fragmentId);
    }
}

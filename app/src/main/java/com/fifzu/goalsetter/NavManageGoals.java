package com.fifzu.goalsetter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class NavManageGoals extends Fragment {

    private ArrayList<Goal> shortGoalList;
    private ArrayList<Goal> mediumGoalList;
    private ArrayList<Goal> longGoalList;
    private MainActivity mainActivity;
    LinearLayout llLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nav_manage_goals, container, false);

        mainActivity = (MainActivity) getActivity();
        shortGoalList = mainActivity.getShortGoalList();
        mediumGoalList = mainActivity.getMediumGoalList();
        longGoalList = mainActivity.getLongGoalList();
        llLayout = new LinearLayout((getContext()));


        for (int i = 0; i < shortGoalList.size(); i++) {
            ImageView iv = new ImageView(getContext());
            TextView tvName = new TextView(getContext());
            TextView tvValid = new TextView(getContext());

            switch (i) {
                case 0:
                    iv = view.findViewById(R.id.shortGoal0Image);
                    tvName = view.findViewById(R.id.shortGoal0Name);
                    tvValid = view.findViewById(R.id.shortGoal0Valid);
                    llLayout = view.findViewById(R.id.shortGoal0);
                    break;
                case 1:
                    iv = view.findViewById(R.id.shortGoal1Image);
                    tvName = view.findViewById(R.id.shortGoal1Name);
                    tvValid = view.findViewById(R.id.shortGoal1Valid);
                    llLayout = view.findViewById(R.id.shortGoal1);
                    break;
                case 2:
                    iv = view.findViewById(R.id.shortGoal2Image);
                    tvName = view.findViewById(R.id.shortGoal2Name);
                    tvValid = view.findViewById(R.id.shortGoal2Valid);
                    llLayout = view.findViewById(R.id.shortGoal2);
                    break;
                case 3:
                    iv = view.findViewById(R.id.shortGoal3Image);
                    tvName = view.findViewById(R.id.shortGoal3Name);
                    tvValid = view.findViewById(R.id.shortGoal3Valid);
                    llLayout = view.findViewById(R.id.shortGoal3);
                    break;
            }
            tvName.setText(shortGoalList.get(i).getName());
            tvValid.setText(shortGoalList.get(i).getValidUntil());
            iv.setImageResource(shortGoalList.get(i).getGoalIcon());

            final int goalID  = i;

            llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.goal_popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(final MenuItem item) {
                        //    Toast.makeText(getContext(),"You Clicked : " + item.getTitle() + "ON " + shortGoalList.get(goalID).getName(), Toast.LENGTH_LONG).show();
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    deleteGoal(shortGoalList.get(goalID).getUniqueID(),shortGoalList.get(goalID).getName());
                                    break;
                                case R.id.confirm:
                                    confirmGoal(shortGoalList.get(goalID).getUniqueID(),shortGoalList.get(goalID).getName());
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
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
                    llLayout = view.findViewById(R.id.mediumGoal0);
                    break;
                case 1:
                    iv = view.findViewById(R.id.mediumGoal1Image);
                    tvName = view.findViewById(R.id.mediumGoal1Name);
                    tvValid = view.findViewById(R.id.mediumGoal1Valid);
                    llLayout = view.findViewById(R.id.mediumGoal1);
                    break;
                case 2:
                    iv = view.findViewById(R.id.mediumGoal2Image);
                    tvName = view.findViewById(R.id.mediumGoal2Name);
                    tvValid = view.findViewById(R.id.mediumGoal2Valid);
                    llLayout = view.findViewById(R.id.mediumGoal2);
                    break;
            }
            tvName.setText(mediumGoalList.get(i).getName());
            tvValid.setText(mediumGoalList.get(i).getValidUntil());
            iv.setImageResource(mediumGoalList.get(i).getGoalIcon());

            final int goalID  = i;
            llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.goal_popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(final MenuItem item) {
                           switch (item.getItemId()) {
                                case R.id.delete:
                                    deleteGoal(mediumGoalList.get(goalID).getUniqueID(),mediumGoalList.get(goalID).getName());
                                    break;
                                case R.id.confirm:
                                    confirmGoal(mediumGoalList.get(goalID).getUniqueID(),mediumGoalList.get(goalID).getName());
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
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
                    llLayout = view.findViewById(R.id.longGoal0);
                    break;
                case 1:
                    iv = view.findViewById(R.id.longGoal1Image);
                    tvName = view.findViewById(R.id.longGoal1Name);
                    tvValid = view.findViewById(R.id.longGoal1Valid);
                    llLayout = view.findViewById(R.id.longGoal1);
                    break;
            }
            tvName.setText(longGoalList.get(i).getName());
            tvValid.setText(longGoalList.get(i).getValidUntil());
            iv.setImageResource(longGoalList.get(i).getGoalIcon());

            final int goalID  = i;

            llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.goal_popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    deleteGoal(longGoalList.get(goalID).getUniqueID(),longGoalList.get(goalID).getName());
                                    break;
                                case R.id.confirm:
                                    confirmGoal(longGoalList.get(goalID).getUniqueID(),longGoalList.get(goalID).getName());
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
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


    private void deleteGoal(final int index, String goatName) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        mainActivity.deleteGoalWithID(index);
                        changeFragment(R.id.nav_manage_goals);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.delete_message) +"\n" + goatName).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }
    private void confirmGoal(final int index, String goatName) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        mainActivity.confirmGoalWithID(index);
                        changeFragment(R.id.nav_manage_goals);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.confirm_message) +"\n" + goatName).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.goals_manager));
    }

    private void changeFragment(int fragmentId) {
        mainActivity.displayFragment(fragmentId);
    }
}

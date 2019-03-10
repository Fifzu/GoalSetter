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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class NavManageGoals extends Fragment {

    private ArrayList<Goal> shortGoalList;
    private ArrayList<Goal> mediumGoalList;
    private ArrayList<Goal> longGoalList;
    private MainActivity mainActivity;
    LinearLayout llLayout;

    ListView shortListView;
    ListView mediumListView;
    ListView longListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nav_manage_goals, container, false);

        mainActivity = (MainActivity) getActivity();
        shortGoalList = mainActivity.getShortGoalList();
        mediumGoalList = mainActivity.getMediumGoalList();
        longGoalList = mainActivity.getLongGoalList();

        shortListView = view.findViewById(R.id.shortListview);
        shortListView.setAdapter(new GoalAdapter(getContext(),shortGoalList,mainActivity));

        mediumListView = view.findViewById(R.id.middleListview);
        mediumListView.setAdapter(new GoalAdapter(getContext(),mediumGoalList,mainActivity));

        longListView = view.findViewById(R.id.longListview);
        longListView.setAdapter(new GoalAdapter(getContext(),longGoalList,mainActivity));


        justifyListViewHeightBasedOnChildren(shortListView);
        justifyListViewHeightBasedOnChildren(mediumListView);
        justifyListViewHeightBasedOnChildren(longListView);


        /*


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

        */

        Button btnAddGoal = view.findViewById(R.id.manageGoals_AddButton);
        btnAddGoal.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              changeFragment(R.id.nav_add_goals);
                                          }
                                      }
        );

        return view;
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
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

package com.fifzu.goalsetter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

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

package com.fifzu.goalsetter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class NavManageGoals extends Fragment {

    private Context context;
    private MyRecyclerViewAdapter adapter;
    View view;
    private ArrayList<Goal> goalList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.nav_manage_goals, container, false);



        context = container.getContext();

        MainActivity mainActivity = (MainActivity) getActivity();
        goalList = mainActivity.getGoalList();

        adapter = new MyRecyclerViewAdapter(getContext(), goalList);
        RecyclerView rv = view.findViewById(R.id.manageShortGoalsRecyclerView);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Manage Goals");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
        } else {

            //List<Goal> goalList;

        }
    }
}

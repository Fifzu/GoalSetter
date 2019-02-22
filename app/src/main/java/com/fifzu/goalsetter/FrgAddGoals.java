package com.fifzu.goalsetter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FrgAddGoals extends Fragment{
    private Goal goal;
    private Context context;

    private enum Duration {
        SHORT, MEDIUM, LONG
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.frg_add_goals, container, false);
        context = container.getContext();

        final Spinner goalSpinner = view.findViewById(R.id.spin_goalsType);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getBaseContext(), R.array.goalsType_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(staticAdapter);


        final EditText etGoalName = view.findViewById(R.id.goalName);

        Button btnSave = view.findViewById(R.id.btn_saveGoal);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        String goalName = etGoalName.getText().toString();
                                        Long spinnerSelectedId = goalSpinner.getSelectedItemId();
                                        saveGoal(goalName, spinnerSelectedId);
                                    }
                                }
        );

            return view;
        }

    private void saveGoal(String goalName, Long spinnerSelectedId) {
        Goal goal = new Goal();

        goal.setName(goalName);
        goal.setDuration(spinnerSelectedId);
        goal.setFixed(false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.addGoal(goal);
        mainActivity.displayFragment(R.id.nav_manage_goals);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add New Goal");
    }
}

package com.fifzu.goalsetter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class FrgEditGoal extends Fragment{
    private MainActivity mainActivity;
    private Goal goal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.frg_edit_goal, container, false);

        Bundle b = getArguments();
        int id = b.getInt("goalId");
        mainActivity = (MainActivity) getActivity();
        goal = mainActivity.getGoalWithId(id);

        final Spinner gsGoalClass = view.findViewById(R.id.spin_goalClass);
        ArrayAdapter<CharSequence> saGoalClass = ArrayAdapter
                .createFromResource(getActivity().getBaseContext(), R.array.goalsClass_array,
                        android.R.layout.simple_spinner_item);
        saGoalClass
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gsGoalClass.setAdapter(saGoalClass);
        gsGoalClass.setSelection(goal.getGoalClass());

        final EditText etGoalName = view.findViewById(R.id.goalName);
        etGoalName.setText(goal.getName());

        final EditText etGoalCount = view.findViewById(R.id.goalCount);
        etGoalCount.setText(goal.getCount().toString());

        Button btnSave = view.findViewById(R.id.btn_editGoal);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           String goalName = etGoalName.getText().toString();
                                           Long l = gsGoalClass.getSelectedItemId();
                                           Integer intGoalClass = l.intValue();
                                           Integer intGoalCount = parseInt(etGoalCount.getText().toString());

                                           editGoal(goalName, intGoalClass,intGoalCount);
                                       }
                                   }
        );

        View.OnFocusChangeListener ofcListener = new FrgEditGoal.MyFocusChangeListener();
        etGoalName.setOnFocusChangeListener(ofcListener);
        return view;
    }

    private void editGoal(String goalName, int intGoalClass,int GoalCount) {

        mainActivity = (MainActivity) getActivity();

        Goal editGoal = new Goal();

        editGoal.setName(goalName);
        editGoal.setFixed(false);
        editGoal.setGoalClass(intGoalClass);
        editGoal.setUniqueID(goal.getUniqueID());
        editGoal.setCount(GoalCount);

        MainActivity mainActivity = (MainActivity) getActivity();
        try {
            mainActivity.editGoal(editGoal);

        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(),e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.edit_goal));
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){
            if(v.getId() == R.id.goalName && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }
}

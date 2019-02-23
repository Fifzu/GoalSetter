package com.fifzu.goalsetter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class FrgAddGoals extends Fragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.frg_add_goals, container, false);

        final Spinner gsGoalType = view.findViewById(R.id.spin_goalType);
        ArrayAdapter<CharSequence> saGoalType = ArrayAdapter
                .createFromResource(getActivity().getBaseContext(), R.array.goalsType_array,
                        android.R.layout.simple_spinner_item);
        saGoalType
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gsGoalType.setAdapter(saGoalType);


        final Spinner gsGoalClass = view.findViewById(R.id.spin_goalClass);
        ArrayAdapter<CharSequence> saGoalClass = ArrayAdapter
                .createFromResource(getActivity().getBaseContext(), R.array.goalsClass_array,
                        android.R.layout.simple_spinner_item);
        saGoalClass
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gsGoalClass.setAdapter(saGoalClass);
                /*
                .createFromResource(getActivity().getBaseContext(), R.array.goalsClass_array,
                        android.R.layout.simple_spinner_item);
        saGoalClass
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gsGoalClass.setAdapter(saGoalClass);
*/
        final EditText etGoalName = view.findViewById(R.id.goalName);

        Button btnSave = view.findViewById(R.id.btn_saveGoal);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        String goalName = etGoalName.getText().toString();
                                        Long l = gsGoalType.getSelectedItemId();
                                        Integer intGoalType = l.intValue();
                                        l = gsGoalClass.getSelectedItemId();
                                        Integer intGoalClass = l.intValue();
                                        saveGoal(goalName, intGoalType, intGoalClass);
                                    }
                                }
        );

        EditText editText = view.findViewById(R.id.goalName);
        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        editText.setOnFocusChangeListener(ofcListener);



        return view;
    }

    private void saveGoal(String goalName, Integer goalType, Integer goalClass) {
        Goal goal = new Goal();

        goal.setName(goalName);
        goal.setDuration(goalType);
        goal.setFixed(false);
        goal.setGoalClass(goalClass);

        MainActivity mainActivity = (MainActivity) getActivity();
        try {
            mainActivity.addGoal(goal);
            mainActivity.displayFragment(R.id.nav_manage_goals);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(),e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add New Goal");
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

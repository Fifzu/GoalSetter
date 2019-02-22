package com.fifzu.goalsetter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NavTemplate  extends Fragment{
    private Goal goal;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_nav_template, container, false);

            context = container.getContext();
            // Abfragen von Benutzername und Ip-Adresse des Servers




            return view;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("NavTemplate");
    }
}

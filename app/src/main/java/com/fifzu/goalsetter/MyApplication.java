package com.fifzu.goalsetter;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private Context context;
    private List<Goal> goals;

    /**
     * initialize() setzt den Context und initialisiert die Liste der verbundenen TeilnehmerInnen
     * @param context aktueller Context
     */
    public void initialize(Context context) {
        this.context = context;
        goals = new ArrayList<Goal>();
    }


}

package com.fifzu.goalsetter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Goal> goalList;
    private int mood;
    private LocalDateTime lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goalList = new ArrayList<Goal>();
        mood = 0;

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadData();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayFragment(item.getItemId());
        return true;
    }

    protected void displayFragment(int fragmentId) {

        Fragment fragment = null;

        switch (fragmentId) {
            case R.id.nav_mood:
                fragment = new NavMood();
                break;

            case R.id.nav_add_goals:
                fragment = new FrgAddGoals();
                break;

            case R.id.nav_manage_goals:
                fragment = new NavManageGoals();
                break;

            case R.id.nav_placeholder:
                fragment = new NavTemplate();
                break;

            case R.id.nav_infos:
                fragment = new NavTemplate();
                break;

            case R.id.nav_settings:
                fragment = new NavTemplate();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        updateStatus();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addGoal(Goal goal){
        Integer goalType = goal.getGoalType();

        ArrayList<Goal> listToCompare = new ArrayList<Goal>();

        switch (goalType) {
            case 0:
                listToCompare = getShortGoalList();
                if(listToCompare.size()>3){
                    throw new IllegalArgumentException("Too many Goals defined. Delete one!");
                } else {
                    this.goalList.add(goal);
                }
                break;
            case 1:
                listToCompare = getMediumGoalList();
                if(listToCompare.size()>2){
                    throw new IllegalArgumentException("Too many Goals defined. Delete one!");
                } else {
                    this.goalList.add(goal);
                }
                break;
            case 2:
                listToCompare = getLongGoalList();
                if(listToCompare.size()>1){
                    throw new IllegalArgumentException("Too many Goals defined. Delete one!");
                } else {
                    this.goalList.add(goal);
                }
                break;
        }

    }

    public void updateStatus() {
        boolean platzhalter=true;

        if (lastUpdated==null){
            lastUpdated= LocalDateTime.now();
        }
        int daysSinceUpdate = calculateDaysSinceUpdate();
        mood = mood - (daysSinceUpdate*3);
        deleteOldGoals();

        lastUpdated = LocalDateTime.now();
    }

    private int calculateDaysSinceUpdate(){
        boolean updated = false;
        int daysSinceUpdate =0;
        LocalDateTime dateToday = LocalDateTime.now();



        updated = !isDayDifference(lastUpdated,dateToday);

        if (!updated) {
            daysSinceUpdate = getDayDifference(lastUpdated, dateToday);
        }
        return daysSinceUpdate;
    }

    private int getDayDifference(LocalDateTime date0, LocalDateTime dateTime1) {
        Duration dur = Duration.between(date0, dateTime1);
        int daysDif=0;
        Long days =  dur.toDays();
        daysDif =days.intValue();
        return daysDif;
    }

    private boolean isDayDifference(LocalDateTime date0, LocalDateTime dateTime1) {
        boolean dif = true;

        if(date0.getYear()==dateTime1.getYear()){
            if (date0.getMonth()==dateTime1.getMonth()) {
                if (date0.getDayOfMonth()==dateTime1.getDayOfMonth()) {
                    dif = false;
                }
            }
        }

        return dif;
    }

    private void deleteOldGoals() {
        for(int i =0; i< goalList.size();i++){
            if(goalList.get(i).getValidUntilDate().isBefore(LocalDateTime.now())){
                goalList.remove(i);
            }
        }
    }

    public ArrayList <Goal> getShortGoalList(){
        ArrayList<Goal> listToSend = new ArrayList<Goal>();

        for (Goal goal : goalList) {
            if (goal.getGoalType() == 0) {
                listToSend.add(goal);
            }
        }
        return listToSend;
    }
    public ArrayList <Goal> getMediumGoalList(){
        ArrayList<Goal> listToSend = new ArrayList<Goal>();

        for (Goal goal : goalList) {
            if (goal.getGoalType() == 1) {
                listToSend.add(goal);
            }
        }
        return listToSend;
    }
    public ArrayList <Goal> getLongGoalList(){
        ArrayList<Goal> listToSend = new ArrayList<Goal>();

        for (Goal goal : goalList) {
            if (goal.getGoalType() == 2) {
                listToSend.add(goal);
            }
        }
        return listToSend;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = getApplicationContext().getSharedPreferences("GOALSETTER", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = gson.toJson(goalList);
        editor = shref.edit();

        editor.remove("goalList").commit();
        editor.putString("goalList", json);
        editor.commit();

        editor.remove("mood").commit();
        editor.putInt("mood",mood);
        editor.commit();

        editor.remove("lastUpdated").commit();
        editor.putLong("lastUpdated",lastUpdated.atZone(ZoneId.systemDefault()).toEpochSecond());
        editor.commit();

    }

    private void loadData() {
        Gson gson = new Gson();
        SharedPreferences shref;
        shref = getApplicationContext().getSharedPreferences("GOALSETTER", Context.MODE_PRIVATE);


        String response=shref.getString("goalList" , "");
        goalList = gson.fromJson(response,
                new TypeToken<List<Goal>>(){}.getType());

        if (goalList==null) {
            goalList = new ArrayList<Goal>();
        }


        mood = shref.getInt("mood",0);
        long l = (shref.getLong("lastUpdated",LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()));

        lastUpdated =  LocalDateTime.ofInstant(Instant.ofEpochSecond(l),
                TimeZone.getDefault().toZoneId());
    }

}

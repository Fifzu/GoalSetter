package com.fifzu.goalsetter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Goal> goalList;
    private int mood;
    private LocalDateTime lastUpdated;
    private LocalDateTime daysSinceStart;
    private static int MAX_MOOD = 240;

    private int savedStatistics[];



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

        if (savedInstanceState == null) {
            MenuItem item =  navigationView.getMenu().getItem(0);
            item.setChecked(true);
            onNavigationItemSelected(item);
        }

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

            case R.id.frg_add_goals:
                fragment = new FrgAddGoals();
                break;

            case R.id.nav_manage_goals:
                fragment = new NavManageGoals();
                break;

            case R.id.nav_info:
                fragment = new NavInfo();
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
                    throw new IllegalArgumentException(getString(R.string.too_many_goals));
                } else {
                    this.goalList.add(goal);
                }
                break;
            case 1:
                listToCompare = getMediumGoalList();
                if(listToCompare.size()>2){
                    throw new IllegalArgumentException(getString(R.string.too_many_goals));
                } else {
                    this.goalList.add(goal);
                }
                break;
            case 2:
                listToCompare = getLongGoalList();
                if(listToCompare.size()>1){
                    throw new IllegalArgumentException(getString(R.string.too_many_goals));
                } else {
                    this.goalList.add(goal);
                }
                break;
        }

    }

    public void updateStatus() {

        if (lastUpdated==null){
            lastUpdated= LocalDateTime.now();
        }
        if (daysSinceStart==null){
            daysSinceStart= LocalDateTime.now();
        }
        int daysSinceUpdate = calculateDaysSinceUpdate();

        if (daysSinceUpdate>0) {
            int newMood = mood - (daysSinceUpdate*3);
            mood = Math.max(newMood,-1*MAX_MOOD);
            deleteOldGoals();
            lastUpdated = LocalDateTime.now();
        }
    }

    private int calculateDaysSinceUpdate(){
        int daysSinceUpdate =0;
        LocalDateTime dateToday = LocalDateTime.now();

        if (isDayDifference(lastUpdated,dateToday)) {
            daysSinceUpdate = getDayDifference(lastUpdated, dateToday);
        }
        return daysSinceUpdate;
    }

    public int getDayDifference(LocalDateTime date0, LocalDateTime dateTime1) {
        Duration dur = Duration.between(date0, dateTime1);
        int daysDif=0;
        Long days =  dur.toDays();
        daysDif =days.intValue();
        return daysDif;
    }

    public String formatDuration(LocalDateTime date0, LocalDateTime dateTime1) {
        Duration duration  = Duration.between(date0, dateTime1);

        String hms = String.format("%d d, %d h, %02d m",
                duration.toDays(),
                duration.toHours()% 24,
                duration.toMinutes()% 60);
        return hms;
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
            if(goalList.get(i).getValidUntilDate().isBefore(LocalDateTime.now())&&!goalList.get(i).getFixed()){
                goalList.remove(i);
            }
        }
    }

    public void deleteGoalWithID(int id) {
        for (int i = 0;i <goalList.size();i++){
            if(goalList.get(i).getUniqueID()==id){
                goalList.remove(i);
                break;
            }
        }
    }


    public void confirmGoalWithID(int id) {
        for (int i = 0;i <goalList.size();i++){
            if(goalList.get(i).getUniqueID()==id){
                if(goalList.get(i).getCount()<2) {
                    int newMood = mood + goalList.get(i).getValue();
                    mood = Math.min(newMood, MAX_MOOD);

                    savedStatistics[goalList.get(i).getGoalType()]++;
                    savedStatistics[goalList.get(i).getGoalClass()+3]++;

                    goalList.remove(i);
                } else {
                    Integer newCount = goalList.get(i).getCount()-1;
                    goalList.get(i).setCount(newCount);
                }
                break;
            }
        }
    }
    public void editGoalWithID(int id) {
        Bundle args = new Bundle();
        args.putInt("goalId",id);
        Fragment fragment = new FrgEditGoal ();
        fragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void checkGoal(int id, Boolean check) {
        for (int i = 0;i <goalList.size();i++) {
            if (goalList.get(i).getUniqueID() == id) {
                if (check) {
                    unCheckGoals(id);
                } else {
                    goalList.get(i).setFixed(false);
                }
            }
        }

    }

    private void unCheckGoals(int id) {
        for(int i = 0;i<goalList.size();i++) {
            if (goalList.get(i).getUniqueID() != id && goalList.get(i).getFixed()==true && goalList.get(i).getGoalType()==goalList.get(id).getGoalType()) {
                goalList.get(i).setFixed(false);
            }
        }
        goalList.get(id).setFixed(true);
    }

    public void editGoal(Goal editGoal) {

        for (int i = 0;i <goalList.size();i++) {
            if (goalList.get(i).getUniqueID() == editGoal.getUniqueID()) {
                goalList.get(i).setName(editGoal.getName());
                goalList.get(i).setFixed(editGoal.getFixed());
                goalList.get(i).setGoalClass(editGoal.getGoalClass());
                goalList.get(i).setCount(editGoal.getCount());
                break;
            }
        }
        FragmentManager fm = getSupportFragmentManager();
        int a = fm.getBackStackEntryCount();
        if (a > 0) {
            fm.popBackStackImmediate();
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

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < savedStatistics.length; i++) {
            str.append(savedStatistics[i]).append(",");
        }

        editor.remove("savedStatistics").commit();
        editor.putString("savedStatistics", str.toString());
        editor.commit();

        editor.remove("goalList").commit();
        editor.putString("goalList", json);
        editor.commit();

        editor.remove("mood").commit();
        editor.putInt("mood",mood);
        editor.commit();

        editor.remove("lastUpdated").commit();
        editor.putLong("lastUpdated",lastUpdated.atZone(ZoneId.systemDefault()).toEpochSecond());
        editor.commit();
        editor.remove("daysSinceStart").commit();
        editor.putLong("daysSinceStart",daysSinceStart.atZone(ZoneId.systemDefault()).toEpochSecond());
        editor.commit();
    }

    public int getUniqueId() {

        boolean found = true;

        int id = 0;
        while (found) {

            found = false;
            for (int i = 0; i < goalList.size(); i++) {

                if (goalList.get(i).getUniqueID() == id) {
                    id++;
                    found = true;
                    break;
                }
            }
        }
        return id;
    }

    public Goal getGoalWithId(int id) {
        for (int i = 0;i <goalList.size();i++) {
            if (goalList.get(i).getUniqueID() == id) {
                return goalList.get(i);
            }
        }
        return null;
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


        l = (shref.getLong("daysSinceStart",LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()));
        daysSinceStart =  LocalDateTime.ofInstant(Instant.ofEpochSecond(l),
                TimeZone.getDefault().toZoneId());

        String savedString = shref.getString("savedStatistics", "0,0,0,0,0,0,0,0");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        savedStatistics = new int[8];
        for (int i = 0; i < 8; i++) {
            savedStatistics[i] = Integer.parseInt(st.nextToken());
        }
    }

    public Goal getNextGoal() {

        Goal nextGoal = new Goal();
        nextGoal.setName("No Goal defined");

        for(int i =0; i< goalList.size(); i++) {
            if(!goalList.get(i).getFixed()){
                nextGoal = goalList.get(i);
                break;
            }
        }

        for(int i =0; i< goalList.size(); i++) {
            if(goalList.get(i).getValidUntilDate().isBefore(nextGoal.getValidUntilDate())&&!goalList.get(i).getFixed()) {
                nextGoal = goalList.get(i);
            }
        }

        return nextGoal;
    }

    public String getLastUpdated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = lastUpdated.format(formatter);
        return str;
    }
    public String getDaysSinceStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = daysSinceStart.format(formatter);
        return str;
    }


    public int[] getSavedStatistics() {
        return savedStatistics;
    }


}

package com.fifzu.goalsetter;

import android.content.Context;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Context context;
    public ArrayList<Goal> goalList;

    private enum Duration {
        SHORT, MEDIUM, LONG
    }

    private NavTemplate nav_main;



    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goalList = new ArrayList<Goal>();

        context = getApplicationContext();

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
            case R.id.nav_main:
                fragment = new NavTemplate();
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addGoal(Goal goal){
        this.goalList.add(goal);
    }
    public ArrayList <Goal> getGoalList(){
        return goalList;
    }

}

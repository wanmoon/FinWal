package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , Billing.OnFragmentInteractionListener
           , Goal.OnFragmentInteractionListener , Dashboard.OnFragmentInteractionListener
           , Home.OnFragmentInteractionListener , History.OnFragmentInteractionListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private TextView textViewTitle;

    FloatingActionButton fab_plus, fab_speech, fab_scan, fab_typing;
    Animation fab_open, fab_close,  fab_backward, fab_forward;
    boolean isopen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Home HomeFragment = new Home();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, HomeFragment);
        transaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewTitle = (TextView) findViewById(R.id.toolbar_title);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_speech = (FloatingActionButton) findViewById(R.id.fab_speech);
        fab_scan = (FloatingActionButton) findViewById(R.id.fab_scan);
        fab_typing = (FloatingActionButton) findViewById(R.id.fab_typing);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopen) {
                    fab_speech.startAnimation(fab_close);
                    fab_scan.startAnimation(fab_close);
                    fab_typing.startAnimation(fab_close);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(false);
                    fab_scan.setClickable(false);
                    fab_typing.setClickable(true);
                    isopen = false;
                } else {
                    fab_speech.startAnimation(fab_open);
                    fab_scan.startAnimation(fab_open);
                    fab_typing.startAnimation(fab_open);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(true);
                    fab_scan.setClickable(true);
                    fab_typing.setClickable(true);
                    isopen = true;
                }
            }
        });

        fab_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpeechToText.class);
                startActivity(i);
            }
        });
        fab_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTransaction.class);
                startActivity(i);
            }
        });
        fab_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AllDetailTransaction.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        textViewUserEmail = (TextView)headerview.findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header_main);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
               // Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_billing) {
            Billing BillingFragment = new Billing();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, BillingFragment);
            transaction.commit();

        } else if (id == R.id.nav_dashboard) {
            Dashboard DashboardFragment = new Dashboard();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, DashboardFragment);
            transaction.commit();

        } else if (id == R.id.nav_goal){
            Goal GoalFragment = new Goal();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, GoalFragment);
            transaction.commit();

        } else if (id == R.id.nav_history){
            History HistoryFragment = new History();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, HistoryFragment);
            transaction.commit();

        } else if (id == R.id.nav_logout){
            firebaseAuth.signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // set name of ActionBar
    public void setTitle(String title) {
        textViewTitle.setText(title);
    }

}

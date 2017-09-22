package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

/**
 * Created by pimpischaya on 9/22/2017 AD.
 */

public class EditGoal extends AppCompatActivity implements View.OnClickListener{

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    Goal.getHttp http;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "AllGoalActivity";

    String passedVar = null;
    private TextView passView = null;

    private TextView textViewFinish;
    private TextView textViewCancel;


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_goal);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);

        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        passedVar=getIntent().getStringExtra(Goal.ID_EXTRA);

        passView = (TextView) findViewById(R.id.textViewSavingPlan);

        passView.setText("value = " + passedVar);
    }


    @Override
    public void onClick(View v) {
        if (v == textViewFinish) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (v == textViewCancel) {
            // will open login activity here
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }

}

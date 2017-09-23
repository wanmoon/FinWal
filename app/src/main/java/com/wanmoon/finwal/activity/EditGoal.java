package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
    private EditText editTextCost;
    private Button buttonPay;
    private TextView textViewHowMuch;
    public String currentBudget;
    public double total;
    Double currentBudget1;


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_goal);

        textViewHowMuch = (TextView) findViewById(R.id.textViewHowMuch);
        editTextCost = (EditText)findViewById(R.id.editTextCost);

        buttonPay = (Button)findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBudget = editTextCost.getText().toString();
                currentBudget1 = Double.valueOf(currentBudget).doubleValue();
                Toast.makeText(getApplicationContext(), "You save money to your goal : " + currentBudget ,
                        Toast.LENGTH_LONG).show();
                total = total+currentBudget1;
                Log.d(TAG, "theText = " + currentBudget);
                textViewHowMuch.setText(""+total);
            }
        });

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

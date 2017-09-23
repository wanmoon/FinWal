package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pimpischaya on 9/22/2017 AD.
 */

public class EditGoal extends AppCompatActivity implements View.OnClickListener{

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    getHttpGetCurrentGoal httpGetCurrentGoal;
    getHttpUpdateCurrentGoal httpUpdateCurrentGoal;

    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "EditGoalActivity";

//    String passedVar = null;
//    private TextView passView = null;

    private TextView textViewFinish;
    private TextView textViewCancel;
    private EditText editTextCost;
    private Button buttonPay;
    private TextView textViewHowMuch;

    public String getMoney;
    public double current_goal;

    public int goal_id = 47; //เปลี่นนทีหลังด้วย

    public String total;
    public double getCost;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_goal);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        httpGetCurrentGoal = new getHttpGetCurrentGoal(getApplicationContext());
        httpUpdateCurrentGoal = new getHttpUpdateCurrentGoal(getApplicationContext());

        getCurrentGoal(cust_id,goal_id);

        textViewHowMuch = (TextView) findViewById(R.id.textViewHowMuch);

        editTextCost = (EditText)findViewById(R.id.editTextCost);

        buttonPay = (Button)findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoney = editTextCost.getText().toString();
                getCost = Double.valueOf(getMoney).doubleValue();
                Log.d(TAG, "getCost = " + getCost);
                current_goal = current_goal + getCost;
                Log.d(TAG, "current_goal = " + current_goal);
                textViewHowMuch.setText(String.format("%.2f", current_goal)+" Baht");
                Toast.makeText(getApplicationContext(), "Save = " + getCost + " Baht, now your goal is " + String.format("%.2f", current_goal) + " Baht" ,
                        Toast.LENGTH_LONG).show();
            }
        });



//        passedVar=getIntent().getStringExtra(Goal.ID_EXTRA);
//
//        passView = (TextView) findViewById(R.id.textViewSavingPlan);
//
//        passView.setText("value = " + passedVar);
    }


    @Override
    public void onClick(View v) {
        if (v == textViewFinish) {
            updateCurrentGoal(current_goal, cust_id, goal_id);
            Toast.makeText(getApplicationContext(), "Success to update your goal", Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
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

    ///////////////////////////////////get current goal
    public void getCurrentGoal(String cust_id, int goal_id){
        try {
            Log.d(TAG,"goal_id = " + goal_id);
            Log.d(TAG,"start select");
            httpGetCurrentGoal.run(BASE_URL + "/goalGetCurrentGoal.php?cust_id=" + cust_id + "&goal_id=" + goal_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showCurrentGoal(String total){
        current_goal = Double.parseDouble(total);
        Log.d(TAG,"current_goal = " + current_goal);
    }

    // ** must have for connect DB
    public class getHttpGetCurrentGoal {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpGetCurrentGoal(Context context) {
            this.context = context;
            client = new OkHttpClient();
            mainHandler = new Handler(context.getMainLooper());
        }

        void run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG,"onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                showCurrentGoal(response.body().string().trim());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                        }


                    });
                }
            });
        }
    }

    ///////////////////////// update current goal
    public void updateCurrentGoal(double current_goal, String cust_id, int goal_id){
        try {
            Log.d(TAG,"goal_id = " + goal_id);
            Log.d(TAG,"start select");
            httpUpdateCurrentGoal.run(BASE_URL + "/goalUpdateCurrentGoal.php?current_goal=" + current_goal + "&cust_id=" + cust_id + "&goal_id=" + goal_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    // ** must have for connect DB
    public class getHttpUpdateCurrentGoal {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpUpdateCurrentGoal(Context context) {
            this.context = context;
            client = new OkHttpClient();
            mainHandler = new Handler(context.getMainLooper());
        }

        void run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG,"onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            Log.d(TAG,"onResponse");
                            Log.d(TAG,"insert success");
                        }


                    });
                }
            });
        }
    }

}

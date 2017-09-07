package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by pimpischaya on 8/8/2017 AD.
 */

public class NewGoal extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewFinish;
    private TextView textViewCancel;

    private EditText editTextCost;
    private EditText editTextGoal;

    private CalendarView calendarView;

    private RadioGroup radioGroup;
    private RadioButton radioButtonDaily;
    private RadioButton radioButtonWeekly;
    private RadioButton radioButtonMonthly;

    private String ending_date;
    private String getDescription_goal;
    private String savingPlan;
    private String status_goal;
    private int getCost;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "AddGoalActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgoal);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);

        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        editTextGoal = (EditText) findViewById(R.id.editTextGoal);
        editTextCost = (EditText) findViewById(R.id.editTextCost);

        radioButtonDaily = (RadioButton) findViewById(R.id.radioButtonDaily);
        radioButtonWeekly = (RadioButton) findViewById(R.id.radioButtonWeekly);
        radioButtonMonthly = (RadioButton) findViewById(R.id.radioButtonMonthly);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioButtonDaily) {
                    Toast.makeText(getApplicationContext(), "Your Suggestion Plan : 'Daily'",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.radioButtonWeekly) {
                    Toast.makeText(getApplicationContext(), "Your Suggestion Plan : 'Weekly'",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Your Suggestion Plan : 'Monthly'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            addBill(cust_id);

            Toast.makeText(NewGoal.this,"Success Add Goal", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"insert success");
        }
        if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    public void addBill(String cust_id) {
        getDescription_goal = editTextGoal.getText().toString();
        String getMoney = editTextCost.getText().toString().trim();

        if(getDescription_goal.matches("")){
            Toast.makeText(this, "What is your transaction?", Toast.LENGTH_LONG).show();
        } else if (getMoney.isEmpty()){
            Toast.makeText(this, "How much?", Toast.LENGTH_LONG).show();
        } else {
            //set current date on calender
            calendarView = (CalendarView) findViewById(R.id.calendarView);
            Log.d(TAG, "start set current date in calendar");
            calendarView.setDate(System.currentTimeMillis(), false, true);
            Log.d(TAG, "finish set current date in calendar");

            //get date after selected
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                ending_date = dayOfMonth + "-" + month + "-" + year;
//                Log.d(TAG, "ending_date = " + ending_date);
//            }
//        });

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                                int date) {
                    Toast.makeText(getApplicationContext(), date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ending_date = " + date + "/" + month + "/" + year);
                }
            });

            getCost = Integer.parseInt(getMoney);

            Log.d(TAG, "get getDescription_goal, getCost");
            addBillToDB(cust_id, ending_date, getDescription_goal, 0, getCost);
            Log.d(TAG, "end addBillToDB");
        }
    }

    public String addBillToDB(String cust_id, String ending_date, String description_goal, int status_goal, int cost_goal){
        try {
            Log.d(TAG,"start bill");
            http.run(BASE_URL + "/insertGoal.php?cust_id=" + cust_id + "&ending_date="+ ending_date +"&description_goal="+ description_goal +"&status_goal=" + status_goal +"&cost_goal=" + cost_goal);
            Log.d(TAG,"end bill");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttp {
        OkHttpClient client = new OkHttpClient();

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
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG,"onResponse");
                    Log.d(TAG,"insert success");

                    // will open login activity here
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}

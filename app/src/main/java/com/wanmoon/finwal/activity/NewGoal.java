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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    private CalendarView calendarViewGoal;
    public Calendar selectedDay;

    private RadioGroup radioGroup;
    private RadioButton radioButtonDaily;
    private RadioButton radioButtonWeekly;
    private RadioButton radioButtonMonthly;

    private String ending_date;
    private String getDescription_goal;
    private String savingplan;
    private String status_goal;

    private double saveingDay;
    private long countDate;
    private double getCost;

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

        //set current date on calender
        calendarViewGoal = (CalendarView) findViewById(R.id.calendarViewGoal);
        Log.d(TAG, "start set current date in calendar");
        calendarViewGoal.setDate(System.currentTimeMillis(),false,true);
        Log.d(TAG, "finish set current date in calendar");
        calendarViewGoal.setOnClickListener(this);
        //get date after selected
        calendarViewGoal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //get endingdate
                month = month +1;
                ending_date = dayOfMonth + "-" + month + "-" + year;
                Log.d(TAG, "ending_date = " + ending_date);

                countDate(dayOfMonth,month,year); //return countdate
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            Log.d(TAG, "get getDescription_goal, getCost");
            addBillToDB(cust_id, ending_date, getDescription_goal, 0, getCost, savingplan);
            Log.d(TAG, "end addGoalToDB");

            Toast.makeText(NewGoal.this,"Success Add Goal", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"insert success");
        } if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), Goal.class);
            startActivity(i);
        } if (v == calendarViewGoal) {
            addGoal();
        }
    }

    public void addGoal() {
        getDescription_goal = editTextGoal.getText().toString();
        String getMoney = editTextCost.getText().toString().trim();

        if(getDescription_goal.matches("")){
            Toast.makeText(this, "What is your goal?", Toast.LENGTH_LONG).show();
        } else if (getMoney.isEmpty()){
            Toast.makeText(this, "Budged?", Toast.LENGTH_LONG).show();
        } else {
            getCost = Double.parseDouble(getMoney);
            Log.d(TAG,"getCost = " + getCost);

            saveEachDay(getCost, countDate);
        }
    }

    public void saveEachDay(final double getCost, final long countDate){
        Log.d(TAG, "getCost = " + getCost);
        Log.d(TAG, "countDate = " + countDate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioButtonDaily) {
                    savingplan = "Daily";
                    saveingDay = getCost/countDate;
                    Log.d(TAG, "savingDay = " + saveingDay);
                    Toast.makeText(getApplicationContext(), "'Daily' Saving Plan : " + saveingDay + " Baht",
                            Toast.LENGTH_LONG).show();
                } else if(checkedId == R.id.radioButtonWeekly) {
                    savingplan = "Weekly";
                    saveingDay = getCost/(countDate/7);
                    Log.d(TAG, "savingDay = " + saveingDay);
                    Toast.makeText(getApplicationContext(), "'Weekly' Saving Plan : " + saveingDay + " Baht",
                            Toast.LENGTH_LONG).show();
                } else {
                    savingplan = "Monthly";
                    saveingDay = getCost/(countDate/30);
                    Log.d(TAG, "savingDay = " + saveingDay);
                    Toast.makeText(getApplicationContext(),"'Monthly' Saving Plan : " + saveingDay + " Baht",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void countDate(int dayOfMonth, int month, int year){
        selectedDay = Calendar.getInstance();
        selectedDay.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        selectedDay.set(Calendar.MONTH,month+1); // 0-11 so 1 less
        selectedDay.set(Calendar.YEAR, year);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar today = Calendar.getInstance();
        Log.d(TAG, "today = " + dateFormat.format(today.getTime()));

        countDate = ((selectedDay.getTimeInMillis() - today.getTimeInMillis())/(24 * 60 * 60 * 1000)) -60;
        Log.d(TAG, "count day = " + countDate);

        if (countDate <= 0){
            Toast.makeText(getApplicationContext(), "You can't set goal date in past, selecte againg",
                    Toast.LENGTH_LONG).show();
        } else {
            addGoal();
        }

    }

    public String addBillToDB(String cust_id, String ending_date, String description_goal, int status_goal, double cost_goal, String savingplan){
        try {
            Log.d(TAG,"start goal");
            http.run(BASE_URL + "/insertGoal.php?cust_id=" + cust_id + "&ending_date="+ ending_date +"&description_goal="+ description_goal +"&status_goal=" + status_goal +"&cost_goal=" + cost_goal +"&savingplan=" + savingplan);
            Log.d(TAG,"end goal");
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

package com.wanmoon.finwal.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
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

    private String ending_date = "";
    private String getDescription_goal;
    private String savingplan = "";
    public String getMoney = "";

    private long countDate;

    public double suggest_cost;
    private double budget_goal;

    public AlertDialog suggestion;

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
                    savingplan = "Daily";
                    Log.d(TAG, "savingplan = " + savingplan);
                } else if(checkedId == R.id.radioButtonWeekly) {
                    savingplan = "Weekly";
                    Log.d(TAG, "savingplan = " + savingplan);
                } else {
                    savingplan = "Monthly";
                    Log.d(TAG, "savingplan = " + savingplan);
                }
            }
        });

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
                String month_str;
                String day_str;

                //day '01'
                if (dayOfMonth<10){
                    day_str = "0"+dayOfMonth;
                } else {
                    day_str = dayOfMonth + "";
                }

                //month '01'
                if(month<10) {
                    month_str = "0"+month;
                } else {
                    month_str = month + "";
                }

                ending_date = day_str + "-" + month_str + "-" + year;
                Log.d(TAG, "ending_date = " + ending_date);

                countDate(dayOfMonth,month,year); //return countdate
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v == textViewFinish) {
            getData();

            //noti
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 22);
            calendar.set(Calendar.MINUTE, 11);
            calendar.set(Calendar.SECOND, 1);

            Intent intent = new Intent(getApplicationContext(), EditGoal.EditGoalNoti.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
            Log.d(TAG, "get getDescription_goal, budget_goal");

        } if(v == textViewCancel){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newGoal", true);
            startActivity(i);
            finish();
        }
    }

    public void getData() {
        getDescription_goal = editTextGoal.getText().toString();
        getMoney = editTextCost.getText().toString().trim();

        //check empty
        if (getDescription_goal.matches("")) { //description
            Toast.makeText(this, "What is your goal?", Toast.LENGTH_LONG).show();
        } else if (getMoney.isEmpty()) { // money
            Toast.makeText(this, "Budget?", Toast.LENGTH_LONG).show();
        } else if (ending_date.isEmpty()) { // endingdate (calendar)
            Toast.makeText(this, "When is your ending date goal?", Toast.LENGTH_LONG).show();
        } else if (radioGroup.getCheckedRadioButtonId() == -1) { //saving plan
            Toast.makeText(this, "What is your saving plan?", Toast.LENGTH_LONG).show();
        } else {

            budget_goal = Double.parseDouble(getMoney);

            Log.d(TAG, "getDescription_goal = " + getDescription_goal);
            Log.d(TAG, "budget_goal = " + budget_goal);
            Log.d(TAG, "ending_date = " + ending_date);
            Log.d(TAG, "countDate = " + countDate);

            //alert dialog
            suggestion = new AlertDialog.Builder(NewGoal.this).create();
            suggestion.setTitle("Suggestion Plan");

            //do suggest cost
            if (savingplan == "Daily") {
                suggest_cost = budget_goal / countDate;
                Log.d(TAG, "suggest_cost = " + suggest_cost);
                suggestion.setMessage("'Daily' Saving Plan : " + String.format("%.2f", suggest_cost) + " Baht");

            } else if (savingplan == "Weekly") {
                suggest_cost = budget_goal / (countDate / 7);
                Log.d(TAG, "suggest_cost = " + suggest_cost);
                suggestion.setMessage("'Weekly' Saving Plan : " + String.format("%.2f", suggest_cost) + " Baht");

            } else if (savingplan == "Monthly") {
                suggest_cost = budget_goal / (countDate / 30);
                Log.d(TAG, "suggest_cost = " + suggest_cost);
                suggestion.setMessage("'Monthly' Saving Plan : " + String.format("%.2f", suggest_cost) + " Baht");
            } else {
                Toast.makeText(this, "Please choose your saving plan?", Toast.LENGTH_LONG).show();
            }

            suggestion.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            // will open login activity here
                            Intent i=new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("newGoal", true);
                            startActivity(i);
                            finish();
                        }
                    });

            String get_suggest_cost = String.format("%.2f", suggest_cost);
            Log.d(TAG, "get_suggest_cost = " + get_suggest_cost);

            addGoalToDB(cust_id, ending_date, getDescription_goal, budget_goal, savingplan, get_suggest_cost);
            Log.d(TAG, "end addGoalToDB");

            Toast.makeText(NewGoal.this, "Success Add Goal", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "insert success");

            suggestion.show();
        }
    }

    public double setSuggest_cost(double suggest_cost){
        this.suggest_cost = suggest_cost;
        return suggest_cost;
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
//        } else {
//            addGoal();
        }
    }

    public String addGoalToDB(String cust_id, String ending_date, String description_goal, double budget_goal, String savingplan, String suggest_cost){
        try {
            Log.d(TAG,"start goal");
            http.run(BASE_URL + "/insertGoal.php?cust_id=" + cust_id + "&ending_date="+ ending_date +"&description_goal="+ description_goal +"&budget_goal=" + budget_goal +"&savingplan=" + savingplan + "&suggest_cost=" + suggest_cost);
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
                }
            });
        }
    }

    public void onBackPressed() {
        Intent i=new Intent(this, MainActivity.class);
        i.putExtra("newGoal", true);
        startActivity(i);
        super.onBackPressed();
    }
}

package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pimpischaya on 8/8/2017 AD.
 */

public class NewBill extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewFinish;
    private TextView textViewCancel;

    private EditText editTextDescription_bill;

    private Button buttonWeekly;
    private Button buttonMonthly;
    private Button button6Monthly;
    private Button buttonYearly;

    private CalendarView calendarViewBill;

    private String description_bill;
    private String period;
    private String deadline;

    //get data from calendarBill
    private Calendar calendarBill;
    private int getDay;
    private int getMonth;
    private int getYear;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "AddBillActivity";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newbill);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);

        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        editTextDescription_bill = (EditText) findViewById(R.id.editTextDescription_bill);

        buttonWeekly = (Button) findViewById(R.id.buttonWeekly);
        buttonWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                period = buttonWeekly.getText().toString();
                Toast.makeText(NewBill.this,"Your Bill's Period is 'Weekly'", Toast.LENGTH_LONG).show();
                Log.d(TAG,"period = weekly");
            }
        });

        buttonMonthly = (Button) findViewById(R.id.buttonMonthly);
        buttonMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                period = buttonMonthly.getText().toString();
                Toast.makeText(NewBill.this,"Your Bill's Period is 'Monthly'", Toast.LENGTH_LONG).show();
                Log.d(TAG,"period = monthly");
            }
        });

        button6Monthly = (Button) findViewById(R.id.button6Monthly);
        button6Monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                period = button6Monthly.getText().toString();
                Toast.makeText(NewBill.this,"Your Bill's Period is '6 Monthly'", Toast.LENGTH_LONG).show();
                Log.d(TAG,"period = 6 monthly");
            }
        });

        buttonYearly = (Button) findViewById(R.id.buttonYearly);
        buttonYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                period = buttonYearly.getText().toString();
                Toast.makeText(NewBill.this,"Your Bill's Period is 'Yearly'", Toast.LENGTH_LONG).show();
                Log.d(TAG,"period = yearly");
            }
        });

        //set current date on calender
        calendarViewBill = (CalendarView) findViewById(R.id.calendarViewBill);
        Log.d(TAG, "start set current date in calendar");
        calendarViewBill.setDate(System.currentTimeMillis(),false,true);
        Log.d(TAG, "finish set current date in calendar");
        //get date after selected
        calendarViewBill.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //get deadline
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

                deadline = day_str + "-" + month_str + "-" + year;
                Log.d(TAG, "deadline = " + deadline);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            addBill(cust_id);
            finish();
        }
        if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newBill", true);
            startActivity(i);
            finish();
        }
    }

    public void addBill(String cust_id) {
        description_bill = editTextDescription_bill.getText().toString();
        Log.d(TAG,"get description_bill : " + description_bill);

        if(description_bill.matches("")){
            Toast.makeText(this, "What is your bill?", Toast.LENGTH_LONG).show();
        } else {
            addBillToDB(cust_id, period, description_bill, deadline);
            Log.d(TAG, "end addBillToDB");
        }
    }

    public String addBillToDB(String cust_id, String period, String description_bill, String deadline){
        try {
            Log.d(TAG,"start add bill");
            http.run(BASE_URL + "/insertBill.php?cust_id=" + cust_id +"&period=" + period +"&description_bill=" + description_bill  +"&deadline=" + deadline);
            Log.d(TAG,"end add bill");
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
                    Log.d(TAG,"insert bill success");

                    // will open main activity here
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("newBill", true);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
    public void onBackPressed() {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }


}

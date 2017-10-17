package com.wanmoon.finwal.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
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
import java.util.HashMap;

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
    getHttpUpdateCurrentGoal httpUpdateCurrentGoal;
    getHttpUpdateStatus httpUpdateStatus;
    getHttpDeleted httpDeleted;

    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "EditGoalActivity";

    private TextView textViewFinish;
    private TextView textViewCancel;
    private TextView textViewHowMuch;
    private TextView textViewTransaction;
    private TextView textViewBudget;
    private TextView textViewStatus;
    private TextView textViewDeadline;
    private TextView textViewSavingPlan;
    private TextView textViewSuggestCost;

    private EditText editTextCost;

    private Button buttonPay;
    private Button buttonDelete;

    public String get_goal_id;
    public String get_budget_goal; //get
    public String get_suggest_cost; //get
    public String get_current_goal; //get
    public String ending_date;
    public String description_goal;
    public String status_goal;
    public String savingplan;

    public int goal_id;

    public double budget_goal; //get
    public double suggest_cost; //get
    public double current_goal; //get
    public double getCost;

    public String getMoney;

    public HashMap<String, String> hashmap;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_goal);

        //get goal_id from listview
        hashmap = (HashMap<String, String>) getIntent().getExtras().getSerializable("hashmap");
        Log.d(TAG, "hashmap budget_goal = " + hashmap.get("goal_id"));

        get_goal_id = hashmap.get("goal_id");
        ending_date = hashmap.get("ending_date");
        description_goal = hashmap.get("description_goal");
        status_goal = hashmap.get("status_goal");
        savingplan = hashmap.get("savingplan");
        get_budget_goal = hashmap.get("budget_goal"); //get
        get_suggest_cost = hashmap.get("suggest_cost"); //get
        get_current_goal = hashmap.get("current_goal"); //get

        goal_id = Integer.parseInt(get_goal_id);
        budget_goal = Double.parseDouble(get_budget_goal);
        suggest_cost = Double.parseDouble(get_suggest_cost);
        current_goal = Double.parseDouble(get_current_goal);

        //http connect DB
        httpUpdateCurrentGoal = new getHttpUpdateCurrentGoal(getApplicationContext());
        httpUpdateStatus = new getHttpUpdateStatus(getApplicationContext());
        httpDeleted = new getHttpDeleted(getApplicationContext());

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        textViewSuggestCost = (TextView) findViewById(R.id.textViewSuggestCost);
        textViewSavingPlan = (TextView) findViewById(R.id.textViewSavingPlan);
        textViewDeadline = (TextView) findViewById(R.id.textViewDeadline);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewBudget = (TextView) findViewById(R.id.textViewBudget);
        textViewTransaction = (TextView) findViewById(R.id.textViewTransaction);
        textViewHowMuch = (TextView) findViewById(R.id.textViewHowMuch);

        editTextCost = (EditText)findViewById(R.id.editTextCost);

        //delete goal
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moethod deleted
                deleteGoal(cust_id,goal_id);
                //toast deleted goal
                Toast.makeText(getApplicationContext(), "Deleted Goal Successful", Toast.LENGTH_LONG).show();
            }
        });

        //update current_goal
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
                Toast.makeText(getApplicationContext(), "Save = " + getCost + " Baht" + "\n" + "Now your goal is " + String.format("%.2f", current_goal) + " Baht" ,
                        Toast.LENGTH_LONG).show();
                updateCurrentGoal(current_goal, cust_id, goal_id);
                Toast.makeText(getApplicationContext(), "Success to update your goal", Toast.LENGTH_LONG).show();

            }
        });

        setText();

        if (current_goal >= budget_goal){
            //change status
            updateStatus(cust_id, goal_id);
        }
    }

    public void setText(){
        String setBold_transaction = "<b>Title</b> : " + description_goal;
        textViewTransaction.setText(Html.fromHtml(setBold_transaction));

        String setBold_Status = "<b>Status</b> : " + status_goal;
        textViewStatus.setText(Html.fromHtml(setBold_Status));

        String setBold_Deadline = "<b>Deadline</b> : " + ending_date;
        textViewDeadline.setText(Html.fromHtml(setBold_Deadline));

        String setBold_SavingPlan = "<b>Type Plan</b> : " + savingplan;
        textViewSavingPlan.setText(Html.fromHtml(setBold_SavingPlan));

        String setBold_Budget = "<b>Budget</b> : " + String.format("%.2f", budget_goal)+" Baht";
        textViewBudget.setText(Html.fromHtml(setBold_Budget));

        String setBold_SuggestCost = "<b>Suggest Cost</b> : " + String.format("%.2f", suggest_cost)+" Baht";
        textViewSuggestCost.setText(Html.fromHtml(setBold_SuggestCost));

        textViewHowMuch.setText(String.format("%.2f", current_goal)+" Baht");
    }

    @Override
    public void onClick(View v) {
        if (v == textViewFinish) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newGoal", true);
            startActivity(i);
            finish();
        }
        if (v == textViewCancel) {
            // will open login activity here
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newGoal", true);
            startActivity(i);
            finish();
        }
    }

    ///////////////////////// delete goal
    public void deleteGoal(String cust_id, int goal_id){
        try {
            Log.d(TAG,"goal_id = " + goal_id);
            Log.d(TAG,"start select");
            httpDeleted.run(BASE_URL + "/goalDeleted.php?cust_id=" + cust_id + "&goal_id=" + goal_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public class getHttpDeleted {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpDeleted(Context context) {
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
                            Log.d(TAG,"update success");
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
                            Log.d(TAG,"update success");
                        }


                    });
                }
            });
        }
    }

    ///////////////////////// update status
    public void updateStatus(String cust_id, int goal_id){
        try {
            Log.d(TAG,"goal_id = " + goal_id);
            Log.d(TAG,"start select");
            httpUpdateStatus.run(BASE_URL + "/goalUpdateStatus.php?cust_id=" + cust_id + "&goal_id=" + goal_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public class getHttpUpdateStatus {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpUpdateStatus(Context context) {
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
                            Log.d(TAG,"update success");
                        }


                    });
                }
            });
        }
    }

    ///////////////////////// noti
    public class EditGoalNoti extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(context,EditGoal.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                    setSmallIcon(R.drawable.ic_add_circle).
                    setContentIntent(pendingIntent).
                    setContentText("this is my notification").
                    setContentTitle("my notificaton").
//                setSound(alarmSound).
        setAutoCancel(true);
            notificationManager.notify(100,builder.build());





        }
    }
}

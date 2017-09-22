package com.wanmoon.finwal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Wanmoon on 9/21/2017 AD.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public static final String YES_ACTION = "YES_ACTION";
    public static final String NO_ACTION = "NO_ACTION";

    public double period_budget;
    public double suggest_cost;

    NewGoal newgoal = new NewGoal();

    //**for log
    private final String TAG = "NotiActivity";

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.get_noti);
//
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("message");
//
//        TextView textView = (TextView) findViewById(R.id.textViewNoti);
//        textView.setText(message);
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "action = " + action);
        if (YES_ACTION.equals(action)) {
            Toast.makeText(context, "YES CALLED", Toast.LENGTH_SHORT).show();

            suggest_cost = newgoal.setSuggest_cost(suggest_cost);

            period_budget = period_budget + suggest_cost;
            //ค่าเกบเงินนี้ = ค่าเก็บเงิน + เงินที่เก็บเป็นรายอาทิตย์
                //update at progressbar

            // if เงินที่เก็บ == budged ที่ตั้งไว้

        }
        else  if (NO_ACTION.equals(action)) {
            Toast.makeText(context, "NO CALLED", Toast.LENGTH_SHORT).show();
            //not thing to do
        }
    }
}

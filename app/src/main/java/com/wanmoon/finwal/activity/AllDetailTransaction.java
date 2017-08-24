package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wanmoon.finwal.R;

/**
 * Created by pimpischaya on 8/7/2017 AD.
 */

public class AllDetailTransaction extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTransactionTitle;
    private EditText editTextTime;
    private TextView textViewPrice;
    private TextView textViewCancel;
    private TextView textViewFinish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.alldetailtransaction);

        /*Intent intent = getIntent();
        String s = intent.getStringExtra(AddTransaction.EXTRA_MESSAGE);

        TextView TextViewTransactionTitle = new TextView(this);
        TextViewTransactionTitle.setText(s);
        setContentView(R.layout.detaildaily);*/

        editTextTransactionTitle = (EditText) findViewById(R.id.editTextTransactionTitle);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        textViewPrice = (TextView)findViewById(R.id.editTextHowmuch);
        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);


        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }
        if(v == textViewCancel){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }
    }
}

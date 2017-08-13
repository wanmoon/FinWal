package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wanmoon.finwal.R;

/**
 * Created by pimpischaya on 5/27/2017 AD.
 */

public class AddTransaction extends AppCompatActivity implements View.OnClickListener{
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    private EditText editTextTransaction;
    private EditText editTextHowmuch;
    private TextView textViewFinish;
    private TextView textViewCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.addtransaction);

        editTextTransaction = (EditText) findViewById(R.id.editTextTransaction);
        editTextHowmuch = (EditText) findViewById(R.id.editTextHowmuch);
        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);


        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

//    public void sendMessage(View v){
//        Intent i = new Intent(this, DetailDaily.class);
//        editTextTransaction = (EditText) findViewById(R.id.editTextTransaction);
//        String s = editTextTransaction.getText().toString();
//        i.putExtra(EXTRA_MESSAGE, s);
//        startActivity(i);
//
//    }



    @Override
    public void onClick(View v) {
        if(v == textViewFinish){


//            Intent intent = new Intent(getBaseContext(), DetailDaily.class);
//            intent.putExtra("EXTRA_SESSION_ID", EditText.editTextTransaction);
//            startActivity(intent);

            // will open login activity here
            Intent i=new Intent(getApplicationContext(),DetailDaily.class);
            startActivity(i);

        }
        if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), Home.class);
            startActivity(i);

        }
    }
}

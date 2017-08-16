package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wanmoon.finwal.R;

/**
 * Created by pimpischaya on 8/8/2017 AD.
 */

public class NewGoal extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewFinish;
    private TextView textViewCancel;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgoal);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);


        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){


//            Intent intent = new Intent(getBaseContext(), DetailDaily.class);
//            intent.putExtra("EXTRA_SESSION_ID", EditText.editTextTransaction);
//            startActivity(intent);

            // will open login activity here
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

        }
        if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }
    }
}

package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

public class EditBill extends AppCompatActivity implements View.OnClickListener{

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();


    //**for log
    private final String TAG = "EditBillActivity";

    private TextView textViewFinish;
    private TextView textViewCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bill);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == textViewFinish) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newBill", true);
            startActivity(i);
            finish();
        }
        if (v == textViewCancel) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("newBill", true);
            startActivity(i);
            finish();
        }
    }
}

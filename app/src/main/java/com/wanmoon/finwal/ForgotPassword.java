package com.wanmoon.finwal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by pimpischaya on 8/6/2017 AD.
 */

public class ForgotPassword extends AppCompatActivity {
    private EditText etMail;
    private Button btnResetPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        etMail = (EditText)findViewById(R.id.editTextEmail);
        btnResetPsw = (Button)findViewById(R.id.buttonResetPSW);
    }

    public void Button(View view){

    }

}

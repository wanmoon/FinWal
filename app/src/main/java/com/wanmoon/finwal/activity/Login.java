package com.wanmoon.finwal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wanmoon.finwal.R;

/**
 * Created by Wanmoon on 6/2/2017 AD.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private Button buttonForgotPassword;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        firebaseAuth = FirebaseAuth.getInstance();



        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);



        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);


    }


    //login method
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)  ){
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }




        // if validation are ok
        // we will first show a progressbar

        progressDialog.setMessage("Login...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));


                        }else {
                            Toast.makeText(Login.this, "Your Email or password are incorrect" , Toast.LENGTH_LONG).show();
                        }



                    }
                });

    }


    //when click in the activity
    @Override
    public void onClick(View v) {
        if (v == buttonSignIn){
            userLogin();

        }
        if (v == textViewSignUp) {
            finish();
            startActivity(new Intent(this, SignUp.class));

        }
        if(v == buttonForgotPassword){
            finish();
            startActivity(new Intent(this, ForgotPassword.class));

        }

    }
}
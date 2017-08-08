package com.wanmoon.finwal;
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

/**
 * Created by Wanmoon on 6/2/2017 AD.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private Button buttonForgotPassword;


    private Button buttonHome;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        firebaseAuth = FirebaseAuth.getInstance();
      /*  if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Home.class));
        }
*/


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);

        // home remove when finish
        buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(this);


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

        if(TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }
        // if validation are ok
        // we will first show a progressbar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), Home.class));

                        }
                    }
                });
    }


    //when click in the activity
    @Override
    public void onClick(View v) {
        if (v == buttonHome) {
            startActivity(new Intent(this, Home.class));

        }
        if (v == buttonSignIn){
            userLogin();

        }
        if (v == textViewSignUp) {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }
        if(v == buttonForgotPassword){


        }

    }
}
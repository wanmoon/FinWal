package com.wanmoon.finwal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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


public class SignUp extends AppCompatActivity implements View.OnClickListener
        , Home.OnFragmentInteractionListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private Button buttonForgotPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(SignUp.this, MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >=6 ;
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

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
        if(!isPasswordValid(password)){
            // password is empty
            Toast.makeText(this, "Password must have more than 6", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        // if validation are ok
        // we will first show a progressbar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in , start home here
                            Toast.makeText(SignUp.this,"Register Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }else{
                            Toast.makeText(SignUp.this,"User with this email already exist ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister ){
            registerUser();
            finish();
        }

        if(v == textViewSignIn){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();
        }
        if(v == buttonForgotPassword){
            finish();
            startActivity(new Intent(this, ForgotPassword.class));
            finish();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}











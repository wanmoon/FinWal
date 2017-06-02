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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Wanmoon on 6/2/2017 AD.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    public EditText etUsername;
    public EditText etPassword;
    public Button btnForget;
    public Button btnLogin;
    public Button btnDntHvAcc;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        progressDialog = new ProgressDialog(this);

        etUsername = (EditText) findViewById(R.id.editTextUsername);
        etPassword = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnDntHvAcc = (Button) findViewById(R.id.btnDHAcc);
        btnForget = (Button) findViewById(R.id.btnForgetPass);

        btnLogin.setOnClickListener(this);
        btnDntHvAcc.setOnClickListener(this);
        btnForget.setOnClickListener(this);
    }

    //login method
    private void signIn(){
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //if email || password empty
        if (TextUtils.isEmpty(email)){
            //ขึ้นเตือนว่าต้องใส่เมลล์
            Toast.makeText(this, "Please enter your E-mail", Toast.LENGTH_SHORT).show();
            //stop the function execution furture
            return;
        } if(TextUtils.isEmpty(password)){
            //ขึ้นเตือนว่าต้องใส่พาสเวิด
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            //stop the function execution furture
            return;
        }

        progressDialog.setMessage("Login...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Could not login, please try again",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //correct user & password goto homepage
                            finish();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                    }
                });
    }


    //when click in the activity
    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            signIn();
        } if(v == btnDntHvAcc){
            //dont have account go to signup page
            startActivity(new Intent(this, SignUp.class ));
        } if(v == btnForget){
            //forget password go to ... page
            startActivity(new Intent(this, ForgetPSW.class ));
        }
    }
}

package com.wanmoon.finwal;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Wanmoon on 6/2/2017 AD.
 */

public class SignUp extends AppCompatActivity{

   /* public EditText editTextEmail;
    public EditText editTextPassword;
    public Button btnRegis;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        etUsername = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        etEmail = (EditText) findViewById(R.id.editTextEmail);

        btnRegis = (Button) findViewById(R.id.buttonSignin);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    //signup method
    private void signUp(){
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

        progressDialog.setMessage("Registering new user...");
        progressDialog.show();


    } */
}

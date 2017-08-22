package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wanmoon.finwal.R;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonSave;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

//        boolean child = isChild();
//        editTextName.setText(child);

        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(this);
    }

    public void saveUserInformation(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //editTextEmail.setText(user.getEmail());
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        String gender = editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(email, name, address, phoneNumber);



        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information saved..", Toast.LENGTH_LONG).show();


    }

    public void onRadioButtonClicked(View view) {
        String gender="";
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    gender = "male";
                    break;
            case R.id.radioButtonFemale:
                if (checked)
                    gender = "female";
                    break;
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonSave){
            saveUserInformation();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }

    }
}

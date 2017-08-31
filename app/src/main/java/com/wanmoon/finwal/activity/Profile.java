package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private ArrayList<String> mUsernames = new ArrayList<>();

    public String gender1 = "";
    private TextView TextViewEmail;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonSave;

    private TextView textViewGenderResult;

    //get current user and email
    private DatabaseReference databaseReference;
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();
    public final String email = currentFirebaseUser.getEmail();

    //connect DB
    String response = null;
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private static final String TAG = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference  = mFirebaseDatabase.getInstance().getReference("users");

        TextViewEmail = (TextView) findViewById(R.id.textViewEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        //textViewGenderResult = (TextView) findViewById(R.id.textViewGenderResult);
        //textViewGenderResult.setEnabled(false);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        checkName();
        checkPhone();
        checkAddress();
        TextViewEmail.setText(user.getEmail());
        checkGender();
    }


    public void saveUserInformation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

       // String email = textViewEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        String gender = gender1;


        UserInformation userInformation = new UserInformation(email, name, address, phoneNumber, gender);
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information saved..", Toast.LENGTH_LONG).show();
    }

    public void checkAddress(){
        //String[] nodeFire = {"email","name","address","phoneNumber"};
        //for(int i = 0;i<nodeFire.length;i++) {
            databaseReference.child(cust_id).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    //EditText[] button = {editTextEmail, editTextName, editTextAddress, editTextPhone};
                   // for (int j = 0; j < button.length; j++) {
                        if (value != null) {
                            editTextAddress.setText(value);
                     //  }
                    }
                }
                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            });
        }


    public void checkPhone(){

        //String[] nodeFire = {"email","name","address","phoneNumber"};
        //for(int i = 0;i<nodeFire.length;i++) {
        databaseReference.child(cust_id).child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                //EditText[] button = {editTextEmail, editTextName, editTextAddress, editTextPhone};
                // for (int j = 0; j < button.length; j++) {
                if (value != null) {
                    editTextPhone.setText(value);
                    //  }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }
    public void checkName(){

        //String[] nodeFire = {"email","name","address","phoneNumber"};
        //for(int i = 0;i<nodeFire.length;i++) {
        databaseReference.child(cust_id).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                //EditText[] button = {editTextEmail, editTextName, editTextAddress, editTextPhone};
                // for (int j = 0; j < button.length; j++) {
                if (value != null) {
                    editTextName.setText(value);
                    //  }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }

    public void checkGender(){

        //String[] nodeFire = {"email","name","address","phoneNumber"};
        //for(int i = 0;i<nodeFire.length;i++) {
        databaseReference.child(cust_id).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                //EditText[] button = {editTextEmail, editTextName, editTextAddress, editTextPhone};
                // for (int j = 0; j < button.length; j++) {
                // if (value.matches("Male") == true) {
                    radioButtonMale.setEnabled(false);
                    radioButtonFemale.setEnabled(true);
                    //  }
                // }else {
                    radioButtonFemale.setEnabled(true);
                // }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // This check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    //Do something when radio button is clicked
                    textViewGenderResult.setText("Male");
                    gender1 = "Male";
                    //Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButtonFemale:
                if (checked)
                    //Do something when radio button is clicked
                    textViewGenderResult.setText("Female");
                    gender1 = "Female";
                    //Toast.makeText(getApplicationContext(), "Female", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonSave){
            saveUserInformation();
            //add to db
            addUserToDB(cust_id, email);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    // call : getHttp() < addUserToDB() < registerUser()
    //insert UID to DB
    public String addUserToDB(String cust_id, String email){
        try {
            Log.d(TAG,"start insert user");
            http.run(BASE_URL + "/signup.php?cust_id=" + cust_id + "&email="+ email);
            Log.d(TAG,"end insert user");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttp {
        OkHttpClient client = new OkHttpClient();

        void run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG,"onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG,"onResponse");
                    Log.d(TAG,"insert success");
                }
            });
        }
    }
}

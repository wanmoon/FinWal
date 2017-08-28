package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wanmoon.finwal.R;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "Profile";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private ListView mListView;


    private FirebaseAuth firebaseAuth;



    private ArrayList<String> mUsernames = new ArrayList<>();


    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonSave;

    private TextView textViewGenderResult;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference  = mFirebaseDatabase.getInstance().getReference("users");



//        userID = user.getUid();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null){
//                    Log.d(TAG, "onAnthStateChanged:sign_in" + user.getUid());
//                }else{
//                    Log.d(TAG, "onAnthStateChanged:sign_out" + user.getUid());
//
//                }
//            }
//        };
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });





        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        textViewGenderResult = (TextView) findViewById(R.id.textViewGenderResult);
        textViewGenderResult.setEnabled(false);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);






    }
//
//    private void showData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()){
//            UserInformation uInfo = new UserInformation();
//            uInfo.setname(ds.child(userID).getValue(UserInformation.class).getname());
//
//            Log.d(TAG, "showData: name: " + uInfo.getname());
//
//            ArrayList<String> array = new ArrayList<>();
//            array.add(uInfo.getname());
//
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
//            mListView.setAdapter(adapter);
//        }
//    }
//
//
//    public void onStart(){
//        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);
//    }
//
//    public void onStop(){
//        super.onStop();
//        if(mAuthListener != null) {
//            firebaseAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//    private void toastMessage(String message){
//        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
//    }


    public void saveUserInformation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        String gender = textViewGenderResult.getText().toString().trim();


        UserInformation userInformation = new UserInformation(email, name, address, phoneNumber, gender);
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information saved..", Toast.LENGTH_LONG).show();


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        // This check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    //Do something when radio button is clicked
                    textViewGenderResult.setText("Male");
                    //Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButtonFemale:
                if (checked)
                    //Do something when radio button is clicked
                    textViewGenderResult.setText("Female");
                    //Toast.makeText(getApplicationContext(), "Female", Toast.LENGTH_SHORT).show();
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

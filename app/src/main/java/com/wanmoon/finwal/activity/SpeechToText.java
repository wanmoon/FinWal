package com.wanmoon.finwal.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wanmoon.finwal.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by pimpischaya on 5/27/2017 AD.
 */

public class SpeechToText extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewStatus;
    private EditText btnInput;
    private TextView resultTEXT;
    private ImageButton imageButton;

    private TextView textViewFinish;
    private TextView textViewCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speechtotext);
        resultTEXT = (TextView)findViewById(R.id.TvResult);
        imageButton = (ImageButton)findViewById(R.id.imageButton);

        // button cancel and finish
        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);


    }



    public void imageButton(View view){

        promptSpeechInput();

    }

//    public void btnClickme(View v) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//
//       // btnInput = (EditText)findViewById(R.id.btnInput); // get word from input button
//        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
//         final String val = btnInput.getText().toString(); //cast word from button to string ready for put in child method
//
//
//        DatabaseReference databaseReference = database.getReference();
//
//        databaseReference.child("Category").child(""+val).child("caType").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        String value = dataSnapshot.getValue(String.class);
//
//                textViewStatus.setText("Cetegory is " + value);
//                        //Log.d("", "value is" + value);
//                    }
//
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("", "Failed to read value.");
//            }
//        });
//
//    }

    public void  promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "say something");

        try{
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException a)
        {
            Toast.makeText(SpeechToText.this ,"Sorry your device don't suppose language", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int request_code , int result_code , Intent i){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        textViewStatus = (TextView)findViewById(R.id.textViewStatus);
        super.onActivityResult(request_code,result_code,i);

        switch(request_code){
            case 100: if(result_code == RESULT_OK &&  i != null)
            {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultTEXT.setText(result.get(0));

                String val = result.get(0).toString();
                String[] test = {"ค่าไฟ","ต้มยำ"};
                for(int j=0;j<test.length;j++) {
                    if (val.matches(".*" + test[j] + ".*") == true) {

                        DatabaseReference databaseReference = database.getReference();
                        databaseReference.child("Category").child("" + test[j]).child("caType").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                @Override
                                                                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                    String value = dataSnapshot.getValue(String.class);
                                                                                                                                    textViewStatus.setText("Cetegory is " + value);
                                                                                                                                    Log.d("", "value is" + value);
                                                                                                                                }

                            @Override
                                                                                                                                public void onCancelled(DatabaseError databaseError) {
                                                                                                                                    Log.w("", "Failed to read value.");
                                                                                                                                }
                                                                                                                            }

                        );
                    }
                }

                }

                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if(v == textViewFinish){

                // will open login activity here
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);

            }
            if(v == textViewCancel){
                // will open login activity here
                Intent i=new Intent(getApplicationContext(), Home.class);
                startActivity(i);

            }
        }
        public static void keepPrice(String val){


            val = val.replaceAll("[^0-9]+", " ");
        }

    }
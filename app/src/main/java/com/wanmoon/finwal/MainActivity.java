package com.wanmoon.finwal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

import static com.wanmoon.finwal.R.id.btnStatus;
import static com.wanmoon.finwal.R.id.imageButton;


public class MainActivity extends AppCompatActivity {
    private TextView mbtnStatus;
    private EditText btnInput;
    private TextView resultTEXT;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTEXT = (TextView)findViewById(R.id.TvResult);
        imageButton = (ImageButton)findViewById(R.id.imageButton);


    }

    public void imageButton(View view){

        promptSpeechInput();



    }

    public void btnClickme(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        btnInput = (EditText)findViewById(R.id.btnInput); // get word from input button
        mbtnStatus = (TextView)findViewById(R.id.btnStatus);
        String val = btnInput.getText().toString(); //cast word from button to string ready for put in child method


        DatabaseReference databaseReference = database.getReference();
        databaseReference.child(""+val).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                mbtnStatus.setText("Cetegory is " + value);
                Log.d("", "value is" + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("", "Failed to read value.");
            }
        });

    }

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
                Toast.makeText(MainActivity.this ,"Sorry your deive dosen't suppose language", Toast.LENGTH_LONG).show();
            }

    }

    public void onActivityResult(int request_code , int result_code , Intent i){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mbtnStatus = (TextView)findViewById(R.id.btnStatus);
        super.onActivityResult(request_code,result_code,i);

        switch(request_code){
            case 100: if(result_code == RESULT_OK &&  i != null)
            {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultTEXT.setText(result.get(0));
                String val = result.get(0).toString();
                DatabaseReference databaseReference = database.getReference();
                databaseReference.child(""+val).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        mbtnStatus.setText("Cetegory is " + value);
                        Log.d("", "value is" + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("", "Failed to read value.");
                    }
                }
                );

            }

                break;
        }
    }


}











package com.wanmoon.finwal.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.wanmoon.finwal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by pimpischaya on 5/27/2017 AD.
 */


public class SpeechToText extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewStatus;
    private EditText btnInput;
    private TextView resultTEXT;
    private ImageButton imageButton;
    private static String val = "";
    private TextView textViewFinish;
    private TextView textViewCancel;

    private Dialog incomeCate;
    private Dialog expenseCate;


    private Button buttonPlus;
    private Button buttonMinus;


    private Button buttonSalary;
    private Button buttonLoan;
    private Button buttonGift;
    private Button buttonFamilyIncome;
    private Button buttonExtra;

    //button expense
    private Button buttonBill;
    private Button buttonEducation;
    private Button buttonEntertainment;
    private Button buttonFood;
    private Button buttonShopping;
    private Button buttonTransport;
    private Button buttonTravel;
    private Button buttonFamilyExpense;
    private Button buttonHealthCare;
    private Button buttonSaving;

    private String transaction;
    private String cate;

    private TextView textViewCategories;
    private TextView textViewTransaction;



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


        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);

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

                val = result.get(0).toString();
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
                button();


            }

                break;
        }

    }

    public void button(){

        textViewTransaction = (TextView) findViewById(R.id.textViewTransaction);
        textViewCategories = (TextView) findViewById(R.id.textViewCategories);

        buttonPlus.setVisibility(View.VISIBLE);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeCate = new Dialog(SpeechToText.this);
                incomeCate.getWindow();
                incomeCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                incomeCate.setContentView(R.layout.income_cate);
                incomeCate.setCancelable(true);
                incomeCate.show();

                transaction = "Income";
                textViewTransaction.setText("Transaction : " + transaction);


                // income button
                buttonGift = (Button) incomeCate.findViewById(R.id.buttonGift);
                buttonGift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonGift.getText().toString();
                        Toast.makeText(SpeechToText.this, cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonSalary = (Button) incomeCate.findViewById(R.id.buttonSalary);
                buttonSalary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonSalary.getText().toString();
                        Toast.makeText(SpeechToText.this, cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonLoan = (Button) incomeCate.findViewById(R.id.buttonLoan);
                buttonLoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonLoan.getText().toString();
                        Toast.makeText(SpeechToText.this, cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFamilyIncome = (Button) incomeCate.findViewById(R.id.buttonFamilyIncome);
                buttonFamilyIncome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonFamilyIncome.getText().toString();
                        Toast.makeText(SpeechToText.this, cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonExtra = (Button) incomeCate.findViewById(R.id.buttonExtra);
                buttonExtra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonExtra.getText().toString();
                        Toast.makeText(SpeechToText.this, cate, Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

            }
        });

        buttonMinus.setVisibility(View.VISIBLE);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseCate = new Dialog(SpeechToText.this);
                expenseCate.getWindow();
                expenseCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                expenseCate.setContentView(R.layout.expense_cate);
                expenseCate.setCancelable(true);
                expenseCate.show();

                transaction = "Expense";
                textViewTransaction.setText("Transaction : " + transaction);

                //expense's button
                buttonBill = (Button) expenseCate.findViewById(R.id.buttonBill);
                buttonBill.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonBill.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonEducation = (Button) expenseCate.findViewById(R.id.buttonEducation);
                buttonEducation.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        cate = buttonEducation.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonEntertainment = (Button) expenseCate.findViewById(R.id.buttonEntertainment);
                buttonEntertainment.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonEntertainment.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFood = (Button) expenseCate.findViewById(R.id.buttonFood);
                buttonFood.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFood.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonShopping = (Button) expenseCate.findViewById(R.id.buttonShopping);
                buttonShopping.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonShopping.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonTransport = (Button) expenseCate.findViewById(R.id.buttonTransport);
                buttonTransport.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTransport.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonTravel = (Button) expenseCate.findViewById(R.id.buttonTravel);
                buttonTravel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTravel.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFamilyExpense = (Button) expenseCate.findViewById(R.id.buttonFamilyExpense);
                buttonFamilyExpense.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFamilyExpense.getText().toString();
                        Toast.makeText(SpeechToText.this,cate, Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonHealthCare = (Button) expenseCate.findViewById(R.id.buttonHealthCare);
                buttonHealthCare.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonHealthCare.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonSaving = (Button) expenseCate.findViewById(R.id.buttonSaving);
                buttonSaving.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonSaving.getText().toString();
                        Toast.makeText(SpeechToText.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });
            }
        });


    }


    @Override
    public void onClick(View v) {
        if(v == textViewFinish){

             //will open login activity here
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }
        if(v == textViewCancel){
            // will open login activity here
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }
    }
    public List keepPrice(){
        val = val.replaceAll("[^0-9]+", " ");
        List price = Arrays.asList(val.trim().split(" "));
        return price;
    }
}
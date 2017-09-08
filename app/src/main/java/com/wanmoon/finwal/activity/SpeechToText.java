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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pimpischaya on 5/27/2017 AD.
 */

public class SpeechToText extends AppCompatActivity implements View.OnClickListener{
    private TextView resultTEXT;
    private static String val = "";
    private TextView textViewFinish;
    private Button editOk;
    private TextView textViewCancel;
    private TextView textPrice;
    public ImageButton imageButton;

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

    private ImageView imageViewFrame;

    private double getHowMuch;
    private int j;
    private String getTransac;

    private String transaction;
    private String transaction1;

    private String cate;

    private TextView textViewCategories;
    private TextView textViewTransaction;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "SpeechToText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speechtotext);
        resultTEXT = (TextView)findViewById(R.id.TvResult);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        //button cancel and finish
        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
        editOk = (Button)findViewById(R.id.editOk);
        editOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = resultTEXT.getText().toString();
                fetchFirebase();

            }
        });

        textPrice = (TextView)findViewById(R.id.textPrice);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        imageViewFrame = (ImageView) findViewById(R.id.imageViewFrame);
    }


    public void imageButton(View view){

        promptSpeechInput();
        if(val != ""){
            textViewCategories.setText("");
            textPrice.setText("");
            textViewTransaction.setText("");

        }
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
            Toast.makeText(SpeechToText.this ,"Sorry your device don't suppose language", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int request_code , int result_code , Intent i){
        super.onActivityResult(request_code,result_code,i);

        switch(request_code){
            case 100: if(result_code == RESULT_OK &&  i != null)
            {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultTEXT.setText(result.get(0));
                val = result.get(0).toString();
                fetchFirebase();
            }
        }
    }

    public void button(){

        textViewTransaction = (TextView) findViewById(R.id.textViewTransaction);
        textViewCategories = (TextView) findViewById(R.id.textViewCategories);

        imageViewFrame.setVisibility(View.VISIBLE);
        buttonMinus.setVisibility(View.VISIBLE);
        buttonPlus.setVisibility(View.VISIBLE);
        textViewTransaction.bringToFront();
        textViewCategories.bringToFront();
        textPrice.bringToFront();
        textViewTransaction.setVisibility(View.VISIBLE);
        textViewCategories.setVisibility(View.VISIBLE);
        textPrice.setVisibility(View.VISIBLE);

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




    public void addTransaction(String cust_id) {
        getTransac = resultTEXT.getText().toString().replace("0","").replace("1","").replace("2","").replace("3","").replace("4","").replace("5","").replace("6","").replace("7","").replace("8","").replace("9","").replace("บาท","");
        getHowMuch = Integer.parseInt(textPrice.getText().toString().replaceAll("[^0-9]+", " ").trim());

        Log.d(TAG,"get transac, getmoney");

        addTransactionToDB(cust_id, getTransac, getHowMuch, transaction, cate);
        Log.d(TAG,"end addTransactionToDB");
    }


    public void fetchFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String[] test = {"ค่าไฟ","ต้มยำ","ค่าเรียน","น้ำ", "ข้าว","ดื่ม","นม","ขนม","เนย", "ผลไม้", "ผัดกะเพรา", "ค่ารักษา","ค่ายา","ค่าโรงพยาบาล", "ค่าหมอ","หุ้น", "เสื้อผ้า", "ต่างหู", "กำไล", "แหวน", "กางเกง", "เสื้อ", "ชุดชั้นใน", "ชุดนอน", "ค่ารถเมล์", "ค่ารถไฟ", "ค่าเรือ", "ค่าเครื่องบิน", "ค่าแท๊กซี่", "ค่าอูเบ้อ", "ค่าแกร้บ", "เที่ยว", "ค่าทัวร์", "สวนสนุก", "สวนน้ำ"};
        for(int j=0;j<test.length;j++) {
            if (val.matches(".*" + test[j] + ".*") == true) {
                DatabaseReference databaseReference = database.getReference();
                databaseReference.child("Category").child("" + test[j]).child("caType").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                           @Override
                                                                                                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                               cate = dataSnapshot.getValue(String.class);
                                                                                                                               buttonMinus.setVisibility(View.INVISIBLE);
                                                                                                                               buttonPlus.setVisibility(View.INVISIBLE);
                                                                                                                               //transaction = dataSnapshot.child("").getValue(String.class);
                                                                                                                               //textViewTransaction.setText("Transaction : " + transaction);
                                                                                                                               textViewCategories.setText("Cetegory is : " + cate);
                                                                                                                               textViewTransaction.setText("Transaction : " + transaction);
                                                                                                                               textPrice.setText(keepPrice()+" Bath");

                                                                                                                               Log.d("", "value is" + cate);
                                                                                                                           }
                                                                                                                           @Override
                                                                                                                           public void onCancelled(DatabaseError databaseError) {
                                                                                                                               Log.w("", "Failed to read value.");
                                                                                                                           }
                                                                                                                       }
                );DatabaseReference databaseReference1 = database.getReference();
                databaseReference1.child("Category").child("" + test[j]).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                          @Override
                                                                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                              transaction = dataSnapshot.getValue(String.class);
                                                                                                                              textViewTransaction.setText("Transaction : " + transaction);
                                                                                                                              Log.d("", "value is" + transaction);
                                                                                                                          }

                                                                                                                          @Override
                                                                                                                          public void onCancelled(DatabaseError databaseError) {
                                                                                                                              Log.w("", "Failed to read value.");
                                                                                                                          }
                                                                                                                      }
                );
            }
        }
        textPrice.setText(keepPrice()+" BATH");
        button();

    }










    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            addTransaction(cust_id);
        }
        if(v == textViewCancel){
            Intent i=new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }

    public String keepPrice(){
        val = val.replaceAll("[^0-9]+", " ");
        List price = Arrays.asList(val.trim().split(" "));
        String price1 = price.toString().replace("[","").replace("]","");

        return price1;
    }

    public String addTransactionToDB(String cust_id, String description, double cost, String transaction, String category){
        try {
            Log.d(TAG,"start transaction");
            http.run(BASE_URL + "/insertTransaction.php?cust_id=" + cust_id+"&description="+ description +"&cost=" + cost +"&transaction=" + transaction +"&category="+category);
            Log.d(TAG,"end transaction");
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
                    Log.d(TAG, "onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse");
                    Log.d(TAG, "insert success");

                    Intent i = new Intent(getApplicationContext(), AllDetailTransaction.class);
                    startActivity(i);
                }
            });
        }
    }
}
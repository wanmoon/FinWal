package com.wanmoon.finwal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pimpischaya on 5/27/2017 AD.
 */

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextTransaction;
    private EditText editTextHowmuch;

    private Button inc;
    private Button de;

    private TextView showValue;
    int counter = 1;
    private TextView Sum;


    private TextView textViewCategories;
    private TextView textViewTransaction;
    private TextView textViewFinish;
    private TextView textViewCancel;

    //button income || expense
    private Button buttonPlus;
    private Button buttonMinus;

    //button income
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

    private Dialog incomeCate;
    private Dialog expenseCate;

    private ImageView imageViewFrame;

    private String transaction;
    private String cate;
    private String getTransac;
    private String setBold;

    public double getHowMuch;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttp http;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "AddTransactionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtransaction);

        http = new getHttp(getApplicationContext());

        showValue = (TextView) findViewById(R.id.textViewCount);
        Sum = (TextView) findViewById(R.id.textViewSum);
        inc = (Button)findViewById(R.id.Inc);
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                showValue.setText(Integer.toString(counter));
                Sum.setText(String.valueOf(Integer.parseInt(editTextHowmuch.getText().toString())*counter));
            }
        });
        de = (Button)findViewById(R.id.Minus);
        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter > 0) {

                    counter--;
                    showValue.setText(Integer.toString(counter));
                    Sum.setText(String.valueOf(Integer.parseInt(editTextHowmuch.getText().toString())*counter));
                }
            }
        });

        textViewFinish = (TextView) findViewById(R.id.textViewFinish);
        textViewCancel = (TextView) findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        editTextTransaction = (EditText) findViewById(R.id.editTextTransaction);
        editTextHowmuch = (EditText) findViewById(R.id.editTextHowmuch);

        textViewTransaction = (TextView) findViewById(R.id.textViewTransaction);
        textViewCategories = (TextView) findViewById(R.id.textViewCategories);

        imageViewFrame = (ImageView) findViewById(R.id.imageViewFrame);

        //transaction button : click then have popup
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeCate = new Dialog(AddTransaction.this);
                incomeCate.getWindow();
                incomeCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                incomeCate.setContentView(R.layout.income_cate);
                incomeCate.setCancelable(true);
                incomeCate.show();

                transaction = "Income";
                setBold = "Transaction : " + "<b>" + transaction + "</b>";
                textViewTransaction.setText(Html.fromHtml(setBold));

                // income button
                buttonGift = (Button) incomeCate.findViewById(R.id.buttonGift);
                buttonGift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonGift.getText().toString();
                        incomeCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonSalary = (Button) incomeCate.findViewById(R.id.buttonSalary);
                buttonSalary.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonSalary.getText().toString();
                        incomeCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonLoan = (Button) incomeCate.findViewById(R.id.buttonLoan);
                buttonLoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonLoan.getText().toString();
                        incomeCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonFamilyIncome = (Button) incomeCate.findViewById(R.id.buttonFamilyIncome);
                buttonFamilyIncome.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonFamilyIncome.getText().toString();
                        incomeCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonExtra = (Button) incomeCate.findViewById(R.id.buttonExtra);
                buttonExtra.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonExtra.getText().toString();
                        incomeCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

            }
        });

        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseCate = new Dialog(AddTransaction.this);
                expenseCate.getWindow();
                expenseCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                expenseCate.setContentView(R.layout.expense_cate);
                expenseCate.setCancelable(true);
                expenseCate.show();

                transaction = "Expense";
                setBold = "Transaction : " + "<b>" + transaction + "</b>";
                textViewTransaction.setText(Html.fromHtml(setBold));

                //expense's button
                buttonBill = (Button) expenseCate.findViewById(R.id.buttonBill);
                buttonBill.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonBill.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonEducation = (Button) expenseCate.findViewById(R.id.buttonEducation);
                buttonEducation.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        cate = buttonEducation.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonEntertainment = (Button) expenseCate.findViewById(R.id.buttonEntertainment);
                buttonEntertainment.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonEntertainment.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonFood = (Button) expenseCate.findViewById(R.id.buttonFood);
                buttonFood.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFood.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonShopping = (Button) expenseCate.findViewById(R.id.buttonShopping);
                buttonShopping.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonShopping.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonTransport = (Button) expenseCate.findViewById(R.id.buttonTransport);
                buttonTransport.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTransport.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonTravel = (Button) expenseCate.findViewById(R.id.buttonTravel);
                buttonTravel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTravel.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonFamilyExpense = (Button) expenseCate.findViewById(R.id.buttonFamilyExpense);
                buttonFamilyExpense.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFamilyExpense.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonHealthCare = (Button) expenseCate.findViewById(R.id.buttonHealthCare);
                buttonHealthCare.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonHealthCare.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });

                buttonSaving = (Button) expenseCate.findViewById(R.id.buttonSaving);
                buttonSaving.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonSaving.getText().toString();
                        expenseCate.cancel();
                        setBold = "Categories : " + "<b>" + cate + "</b>";
                        textViewCategories.setText((Html.fromHtml(setBold)));
                        visible();
                    }
                });
            }
        });
    }


    public void visible(){
        //show textview : transaction, category
        imageViewFrame.setVisibility(View.VISIBLE);
        textViewTransaction.bringToFront();
        textViewCategories.bringToFront();
        textViewTransaction.setVisibility(View.VISIBLE);
        textViewCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        if (v == textViewFinish) {
            addTransaction(cust_id);
        }
        if (v == textViewCancel) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void addTransaction(String cust_id) {
        getTransac = editTextTransaction.getText().toString();
        String getMoney = editTextHowmuch.getText().toString().trim();

        if(getTransac.matches("")){
            Toast.makeText(this, "What is your transaction?", Toast.LENGTH_LONG).show();
        } else if (getMoney.isEmpty()){
            Toast.makeText(this, "How much?", Toast.LENGTH_LONG).show();
        } else {
            getHowMuch = Double.parseDouble(getMoney);

            Log.d(TAG, "get transac, getmoney");
            addTransactionToDB(cust_id, getTransac, getHowMuch, transaction, cate);
            Log.d(TAG, "end addTransactionToDB");
        }
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
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttp(Context context) {
            this.context = context;
            client = new OkHttpClient();
            mainHandler = new Handler(context.getMainLooper());
        }

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
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            Log.d(TAG,"onResponse");
                            Log.d(TAG,"insert success");

                            Intent i = new Intent(getApplicationContext(), AllDetailTransaction.class);
                            startActivity(i);
                            finish();
                        }


                    });
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }



    public void countIn(View view1){
        if(view1 == inc) {
            counter++;
            showValue.setText(Integer.toString(counter));
        }

    }
    public void countDE(View view2){
        counter--;
        showValue.setText(Integer.toString(counter));
    }
}



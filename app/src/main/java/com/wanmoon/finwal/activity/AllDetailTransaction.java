package com.wanmoon.finwal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pimpischaya on 8/7/2017 AD.
 */

public class AllDetailTransaction extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewCancel;
    private TextView textViewFinish;
    private Spinner spinnerSort;
    String defaultTextForSpinner = "text here";


    private ArrayList<String> mspinnerSort = new ArrayList<String>();

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "Show All Transaction";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.alldetailtransaction);

        /*Intent intent = getIntent();
        String s = intent.getStringExtra(AddTransaction.EXTRA_MESSAGE);

        TextView TextViewTransactionTitle = new TextView(this);
        TextViewTransactionTitle.setText(s);
        setContentView(R.layout.detaildaily);*/

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        // spinner to sort
        spinnerSort = (Spinner) findViewById(R.id.spinnerSort);
        String[] spinnerValue = new String[]{
                "Category",
                "Time",
        };
        final List<String> mspinnerSort = new ArrayList<>(Arrays.asList(spinnerValue));
        ArrayAdapter<String> aSpinnerSort = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mspinnerSort);
        spinnerSort.setAdapter(aSpinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(AllDetailTransaction.this, "Select : " + mspinnerSort.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        

        Log.d(TAG, "onCreate");

        getAllTransaction(cust_id);



        ///////////start select transaction

//        String allTransaction = getAllTransaction(cust_id);
//        Log.d(TAG, "allTransaction " + allTransaction);
//
//        String[] transactionInfo;
//        String timestamp;
//        String description;
//        String cost;
//        String transaction;
//        String category;
//        ArrayList<HashMap<String, String>> transactionList = null;
//
//        transactionList = new ArrayList<HashMap<String, String>>();
//        HashMap<String, String> map;
//
//        Scanner scanner = new Scanner(allTransaction);
//
//        for(int i = 0; scanner.hasNext(); i++){
//            String data = scanner.nextLine();
//            System.out.println(data);
//
//            transactionInfo = data.split(",");
//
//            timestamp = transactionInfo[0];
//            description = transactionInfo[1];
//            cost = transactionInfo[2];
//            transaction = transactionInfo[3];
//            category = transactionInfo[4];
//
//            map = new HashMap<String, String>();
//            map.put("timestamp", timestamp);
//            map.put("description", description);
//            map.put("cost", cost);
//            map.put("transaction", transaction);
//            map.put("category", category);
//            transactionList.add(map);
//        }
//
//        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), transactionList);
//
//        //listview for show alltransaction
//        ListView transactionListView = (ListView) findViewById(R.id.listViewTransaction);
//        transactionListView.setAdapter(adapter);
//        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//            }
//        });
    }



    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }
        if(v == textViewCancel){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }
    }

    public void getAllTransaction(String cust_id){
        try {
            Log.d(TAG,"start select");
            http.run(BASE_URL + "/showAllTransaction.php?cust_id=" + cust_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showTransactionToListView(String allTransaction){
        //String allTransaction = response.body().string();
        Log.d(TAG, "allTransaction " + allTransaction);

        String[] transactionInfo;
        String timestamp;
        String description;
        String cost;
        String transaction;
        String category;
        ArrayList<HashMap<String, String>> transactionList = null;

        transactionList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;



        Scanner scanner = new Scanner(allTransaction);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            transactionInfo = data.split(",");

            timestamp = transactionInfo[0];
            description = transactionInfo[1];
            cost = transactionInfo[2];
            transaction = transactionInfo[3];
            category = transactionInfo[4];

            map = new HashMap<String, String>();
            map.put("timestamp", timestamp);
            map.put("description", description);
            map.put("cost", cost);
            map.put("transaction", transaction);
            map.put("category", category);
            transactionList.add(map);
        }

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), transactionList);

        //listview for show alltransaction
        ListView transactionListView = (ListView) findViewById(R.id.listViewTransaction);
        transactionListView.setAdapter(adapter);
        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
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
                    showTransactionToListView(response.body().string());
                    Log.d(TAG,"onResponse");
                }
            });
        }
    }
}
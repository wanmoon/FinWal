package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
 * Created by Wanmoon on 9/6/2017 AD.
 */

public class AllExpense extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewCancel;
    private TextView textViewFinish;
    private Spinner spinnerSort;

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    getHttp http;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "AllExpenseActivity";

    ArrayList<HashMap<String, String>> expenseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.all_expense);

        textViewFinish = (TextView)findViewById(R.id.textViewFinish);
        textViewCancel = (TextView)findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        http = new getHttp(AllExpense.this);

        // spinner to sort
        spinnerSort = (Spinner) findViewById(R.id.spinnerSort);
        String[] spinnerValue = new String[]{
                "Time",
                "Category : A-Z",
                "Price : Low-High",
                "Price : High-Low"
        };

        final List<String> mspinnerSort = new ArrayList<>(Arrays.asList(spinnerValue));
        ArrayAdapter<String> aSpinnerSort = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mspinnerSort);
        spinnerSort.setAdapter(aSpinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected = " + selected);

                if (selected.equals("Time")){
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 3);
                } else if (selected.equals("Category : A-Z")) {
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 0);
                } else if (selected.equals("Price : Low-High")) {
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 1);
                } else if (selected.equals("Price : High-Low")) {
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        //listview for show alltransaction
//        ListView expenseListView = (ListView) findViewById(R.id.listViewExpense);
//        TextView TextViewEmptyResult = (TextView)findViewById(R.id.TextViewEmptyResult);
//        expenseListView.setEmptyView(TextViewEmptyResult);
    }

    @Override
    public void onClick(View v) {
        if(v == textViewFinish){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
        if(v == textViewCancel){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void getAllExpense(String cust_id, int flagSort){
        try {
            Log.d(TAG,"flagSort = " + flagSort);
            Log.d(TAG,"start select");
            http.run(BASE_URL + "/showExpense.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showExpenseToListView(String allExpense){
        Log.d(TAG, "allExpense " + allExpense);

        String[] expenseInfo;
        String timestamp;
        String description;
        String cost;
        String transaction;
        String category;

        expenseList = new ArrayList<HashMap<String, String>>();

        expenseList.clear();

        HashMap<String, String> map;
        Scanner scanner = new Scanner(allExpense);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            expenseInfo = data.split(",");

            if(expenseInfo.length >= 4) {

                timestamp = expenseInfo[0];
                description = expenseInfo[1];
                cost = expenseInfo[2];
                transaction = expenseInfo[3];
                category = expenseInfo[4];

                map = new HashMap<String, String>();
                map.put("timestamp", timestamp);
                map.put("description", description);
                map.put("cost", cost);
                map.put("transaction", transaction);
                map.put("category", category);
                expenseList.add(map);

            }
        }

        ExpenseAdapter adapter = new ExpenseAdapter(getApplicationContext(), expenseList);

        //listview for show alltransaction
        ListView expenseListView = (ListView) findViewById(R.id.listViewExpense);

        TextView TextViewEmptyResult = (TextView)findViewById(R.id.TextViewEmptyResult);
        expenseListView.setEmptyView(TextViewEmptyResult);

        expenseListView.setAdapter(adapter);
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
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
                            try {
                                showExpenseToListView(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                        }
                    });
                }
            });
        }
    }
    public void onBackPressed() {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}

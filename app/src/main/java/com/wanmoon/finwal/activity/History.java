package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wanmoon.finwal.R.id.tab2;

public class History extends android.support.v4.app.Fragment  {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   // private static View rootView;

    private View bView;

    private Typeface tf;

    private Spinner spinnerAll;
    private Spinner spinnerAllExpense;
    private Spinner spinnerAllIncome;

    private double sumIncomeAll;
    private double sumExpenseAll;
    private double balanceAll;

    private String setWalletBalance;
    private String setIncomeAll;
    private String setExpenseAll;

    public TextView textViewMyWallet;
    public TextView textViewMyIncomeAll;
    public TextView textViewMyExpenseAll;

    private View mView;

    //get current user
    private FirebaseAuth firebaseAuth;
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttpIncomeAll httpIncomeAll;
    getHttpExpenseAll httpExpenseAll;
    getHttpIncome httpIncome;
    getHttpExpense httpExpense;
    getHttpAll httpAll;

    ArrayList<HashMap<String, String>> transactionList;
    CustomAdapter adapter;
    ListView transactionListView;

    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "HistoryActivity";

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        setHasOptionsMenu(true);

        ((MainActivity)getActivity()).setTitle("History");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        mView = rootView;


        // for tabHost
        TabHost host = (TabHost) rootView.findViewById(R.id.tabHost);
        host.setup();

        //Tab1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Show All");
        host.addTab(spec);

        //Tab2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(tab2);
        spec.setIndicator("All income");
        host.addTab(spec);

        //Tab3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("All expense");
        host.addTab(spec);

        //listview for show alltransaction
        transactionList = new ArrayList<HashMap<String, String>>();
        adapter = new CustomAdapter(getContext(), transactionList);
        transactionListView = (ListView) mView.findViewById(R.id.listViewTransaction);

        TextView TextViewEmptyResult = (TextView) mView.findViewById(R.id.TextViewEmptyResult);
        transactionListView.setEmptyView(TextViewEmptyResult);

        transactionListView.setAdapter(adapter);
        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });

        //get http
        httpIncomeAll = new getHttpIncomeAll(getContext());
        httpExpenseAll = new getHttpExpenseAll(getContext());

        httpIncome = new getHttpIncome(getContext());
        httpExpense = new getHttpExpense(getContext());
        httpAll = new getHttpAll(getContext());

        //spinner all
        spinnerAll = (Spinner) rootView.findViewById(R.id.spinnerAll);
        spinnerAll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected spinnerAll = " + selected);

                if (selected.equals("All")){ //0
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 0);
                }

                // income
                else if (selected.equals("Gift")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 1);
                } else if (selected.equals("Salary")) {
                    getAllTransaction(cust_id, 2);
                } else if (selected.equals("Loan")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 3);
                } else if (selected.equals("Income : Family and Personal")) {
                    getAllTransaction(cust_id, 4);
                } else if (selected.equals("Extra income")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 5);
                }

                // expense
                else if (selected.equals("Bill")) {
                    getAllTransaction(cust_id, 6);
                } else if (selected.equals("Transport")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 7);
                } else if (selected.equals("Education")) {
                    getAllTransaction(cust_id, 8);
                } else if (selected.equals("Travel")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 9);
                } else if (selected.equals("Entertainment")) {
                    getAllTransaction(cust_id, 10);
                } else if (selected.equals("Expense : Family and Personal")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 11);
                } else if (selected.equals("Food and Drink")) {
                    getAllTransaction(cust_id, 12);
                } else if (selected.equals("Healthcare")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllTransaction(cust_id, 13);
                } else if (selected.equals("Shopping")) {
                    getAllTransaction(cust_id, 14);
                } else if (selected.equals("Saving and Investment")) {
                    getAllTransaction(cust_id, 15);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner income
        spinnerAllIncome = (Spinner) rootView.findViewById(R.id.spinnerAllIncome);
        spinnerAllIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected spinnerAllIncome = " + selected);

                if (selected.equals("All")){ //0
                    Log.d(TAG, "selected = " + selected);
                    getAllIncome(cust_id, 0);
                }

                // income
                else if (selected.equals("Gift")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllIncome(cust_id, 1);
                } else if (selected.equals("Salary")) {
                    getAllIncome(cust_id, 2);
                } else if (selected.equals("Loan")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllIncome(cust_id, 3);
                } else if (selected.equals("Family and Personal")) {
                    getAllIncome(cust_id, 4);
                } else if (selected.equals("Extra income")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllIncome(cust_id, 5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner expense
        spinnerAllExpense = (Spinner) rootView.findViewById(R.id.spinnerAllExpense);
        spinnerAllExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected spinnerAll = " + selected);

                if (selected.equals("All")){ //0
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 0);
                }

                // expense
                else if (selected.equals("Bill")) {
                    getAllExpense(cust_id, 6);
                } else if (selected.equals("Transport")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 7);
                } else if (selected.equals("Education")) {
                    getAllExpense(cust_id, 8);
                } else if (selected.equals("Travel")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 9);
                } else if (selected.equals("Entertainment")) {
                    getAllExpense(cust_id, 10);
                } else if (selected.equals("Expense : Family and Personal")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 11);
                } else if (selected.equals("Food and Drink")) {
                    getAllExpense(cust_id, 12);
                } else if (selected.equals("Healthcare")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllExpense(cust_id, 13);
                } else if (selected.equals("Shopping")) {
                    getAllExpense(cust_id, 14);
                } else if (selected.equals("Saving and Investment")) {
                    getAllExpense(cust_id, 15);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sumExpenseToDB(cust_id);
        sumIncomeToDB(cust_id);

        Log.d(TAG,"start findviewbyid");
        textViewMyWallet = (TextView) view.findViewById(R.id.textViewMyWallet);
        textViewMyIncomeAll = (TextView) view.findViewById(R.id.textViewMyIncomeAll);
        textViewMyExpenseAll = (TextView) view.findViewById(R.id.textViewMyExpenseAll);
        Log.d(TAG,"end findviewbyid");


        sumBalance();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String sumIncomeToDB(String cust_id){
        try {
            Log.d(TAG,"start transaction");
            httpIncomeAll.run(BASE_URL + "/sumIncome.php?cust_id=" + cust_id);
            Log.d(TAG,"end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public String sumExpenseToDB(String cust_id){
        try {
            Log.d(TAG,"start show");
            httpExpenseAll.run(BASE_URL + "/sumExpense.php?cust_id=" + cust_id);
            Log.d(TAG,"end show");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB Income
    public class getHttpIncomeAll {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpIncomeAll(Context context) {
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
                                String income = response.body().string();
                                sumIncomeAll = Double.parseDouble(income.trim());
                                Log.d(TAG,"sumIncome = " + sumIncomeAll);

                                setIncomeAll = "Total Income : " + "<b>" + sumIncomeAll + " Baht</b>";
                                Log.d(TAG,"Total Income = " + setIncomeAll);
                                textViewMyIncomeAll.setText((Html.fromHtml(setIncomeAll)));

                                Log.d(TAG,"onResponse");
                                Log.d(TAG,"show");

                                if(sumExpenseAll != 0 && sumIncomeAll != 0) {
                                    sumBalance();
                                }

                            } catch (NumberFormatException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "IOException");
                            }
                            Log.d(TAG,"onResponse");
                        }


                    });
                }
            });
        }
    }

    // ** must have for connect DB expense
    public class getHttpExpenseAll {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpExpenseAll(Context context) {
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
                                String expense = response.body().string();
                                sumExpenseAll = Double.parseDouble(expense.trim());
                                Log.d(TAG,"sumExpense = " + sumExpenseAll);
                                setExpenseAll = "Total Expense : " + "<b>" + sumExpenseAll + " Baht</b>";

                                setExpenseAll = "Total Expense : " + "<b>" + sumExpenseAll + " Baht</b>";
                                textViewMyExpenseAll.setText((Html.fromHtml(setExpenseAll)));
                                Log.d(TAG,"end settext");

                                Log.d(TAG,"onResponse");
                                Log.d(TAG,"show");

                                if(sumExpenseAll != 0 && sumIncomeAll != 0) {
                                    sumBalance();
                                }

                            } catch (NumberFormatException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "IOException");
                            }
                            Log.d(TAG,"onResponse");
                        }


                    });
                }
            });
        }
    }

    public void sumBalance(){
        balanceAll = sumIncomeAll - sumExpenseAll;
        Log.d(TAG, "balance = " + balanceAll);

        Log.d(TAG,"start settext");
        setIncomeAll = "Total Income : " + "<b>" + sumIncomeAll + " Baht</b>";
        Log.d(TAG,"Total Income = " + setIncomeAll);
        textViewMyIncomeAll.setText((Html.fromHtml(setIncomeAll)));

        setExpenseAll = "Total Expense : " + "<b>" + sumExpenseAll + " Baht</b>";
        textViewMyExpenseAll.setText((Html.fromHtml(setExpenseAll)));

        setWalletBalance = "Wallet Balance : " + "<b>" + balanceAll + " Baht</b>";
        textViewMyWallet.setText((Html.fromHtml(setWalletBalance)));
        Log.d(TAG,"end settext");
    }




    //listview AllIncome
    public void getAllIncome(String cust_id, int flagSort){
        try {
            Log.d(TAG,"start select");
            httpIncome.run(BASE_URL + "/showIncomeHistory.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showIncomeToListView(String allIncome){
        Log.d(TAG, "allIncome " + allIncome);

        String[] incomeInfo;
        String timestamp;
        String description;
        String cost;
        String transaction;
        String category;
        ArrayList<HashMap<String, String>> incomeList = null;

        incomeList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        Scanner scanner = new Scanner(allIncome);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            incomeInfo = data.split(",");

            if (incomeInfo.length >= 4) {

                timestamp = incomeInfo[0];
                description = incomeInfo[1];
                cost = incomeInfo[2];
                transaction = incomeInfo[3];
                category = incomeInfo[4];

                map = new HashMap<String, String>();
                map.put("timestamp", timestamp);
                map.put("description", description);
                map.put("cost", cost);
                map.put("transaction", transaction);
                map.put("category", category);
                incomeList.add(map);
            }
        }

        IncomeAdapter adapter = new IncomeAdapter(getContext(), incomeList);

        //listview for show income transaction
        ListView incomeListView = (ListView) mView.findViewById(R.id.listViewIncome);
        incomeListView.setAdapter(adapter);
        incomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }

    // ** must have for connect DB All income
    public class getHttpIncome {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpIncome(Context context) {
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
                                showIncomeToListView(response.body().string());
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

    //listview AllExpense
    public void getAllExpense(String cust_id, int flagSort){
        try {
            Log.d(TAG,"start select");
            httpExpense.run(BASE_URL + "/showExpenseHistory.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
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
        ArrayList<HashMap<String, String>> expenseList = null;

        expenseList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        Scanner scanner = new Scanner(allExpense);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            expenseInfo = data.split(",");

            if (expenseInfo.length >= 4) {

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

        ExpenseAdapter adapter = new ExpenseAdapter(getContext(), expenseList);

        //listview for show espense transaction
        ListView expenseListView = (ListView) mView.findViewById(R.id.listViewExpense);
        expenseListView.setAdapter(adapter);
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }

    // ** must have for connect DB All expense
    public class getHttpExpense {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpExpense(Context context) {
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

    //listview AllTransaction
    public void getAllTransaction(String cust_id, int flagSort){
        try {
            Log.d(TAG,"start select");
            httpAll.run(BASE_URL + "/showAllTransactionHistory.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showTransactionToListView(String allTransaction){
        Log.d(TAG, "allTransaction " + allTransaction);

        String[] transactionInfo;
        String timestamp;
        String description;
        String cost;
        String transaction;
        String category;

        transactionList.clear();

        HashMap<String, String> map;
        Scanner scanner = new Scanner(allTransaction);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            transactionInfo = data.split(",");
            if (transactionInfo.length >= 4) {

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
        }
        adapter.notifyDataSetChanged();
    }

    // ** must have for connect DB all transaction
    public class getHttpAll {

        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpAll(Context context) {
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
                    Log.d(TAG, "onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                showTransactionToListView(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onResponse");
                        }
                    });
                }
            });
        }
    }
}
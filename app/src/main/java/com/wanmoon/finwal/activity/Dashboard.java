package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wanmoon.finwal.R.id.tab2;


public class Dashboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //for line chart
    private float[] yData = {};
    private String[] xData = {};
    LineChart lineChart;



    //for pie chart
    private final String TAG = "Dashboard";
    PieChart pieChart;
    private View mView;

    private double sumIncomeMonth = -1;
    private double sumExpenseMonth = -1;
    private double monthBalance;
    private double sumIncomeYear = -1;
    private double sumExpenseYear = -1;
    private double YearBalance;


    //income month
    private double sumIncomeSalaryMonth;
    private double sumIncomeGiftMonth;
    private double sumIncomeLoanMonth;
    private double sumIncomeFamilyAndPersonalMonth;
    private double sumIncomeExtraMonth;

    private float incomeExtraMonthPercent;
    private float incomeGiftMonthPercent;
    private float incomeLoanMonthPercent;
    private float incomeFamilyAndPersonalMonthPercent;
    private float incomeSalaryMonthPercent;

    private String categoryExtraIncomeMonth;
    private String categoryFamilyAndPersonalIncomeMonth;
    private String categoryGiftMonth;
    private String categoryLoanMonth;
    private String categorySalaryMonth;

    //expense month
    private double sumExpenseBillMonth;
    private double sumExpenseEducationMonth;
    private double sumExpenseEntertainmentMonth;
    private double sumExpenseFoodAndDrinkMonth;
    private double sumExpenseShoppingMonth;

    private double sumExpenseTransportMonth;
    private double sumExpenseTravelMonth;
    private double sumExpenseFamilyAndPersonalMonth;
    private double sumExpenseHealthCareMonth;
    private double sumExpenseSavingAndInvestmentMonth;


    private float expenseBillMonthPercent;
    private float expenseTravelMonthPercent;
    private float expenseFamilyAndPersonalMonthPercent;
    private float expenseFoodAndDrinkMonthPercent;
    private float expenseShoppingMonthPercent;

    private float expenseTransportMonthPercent;
    private float expenseEducationMonthPercent;
    private float expenseEntertainmentMonthPercent;
    private float expenseHealthCareMonthPercent;
    private float expenseSavingAndInvestmentMonthPercent;


    private String categoryBillMonth;
    private String categoryTravelMonth;
    private String categoryFamilyAndPersonalMonthExpense;
    private String categoryFoodAndDrinkMonth;
    private String categoryShoppingMonth;

    private String categoryTransportMonth;
    private String categoryEducationMonth;
    private String categoryEntertainmentMonth;
    private String categoryHealthCareMonth;
    private String categorySavingAndInvestmentMonth;


    //income year
    private double sumIncomeSalaryYear;
    private double sumIncomeGiftYear;
    private double sumIncomeLoanYear;
    private double sumIncomeFamilyAndPersonalYear;
    private double sumIncomeExtraYear;

    private float incomeExtraYearPercent;
    private float incomeGiftYearPercent;
    private float incomeLoanYearPercent;
    private float incomeFamilyAndPersonalYearPercent;
    private float incomeSalaryYearPercent;

    private String categoryExtraIncomeYear;
    private String categoryFamilyAndPersonalIncomeYear;
    private String categoryGiftYear;
    private String categoryLoanYear;
    private String categorySalaryYear;


    //expense year
    private double sumExpenseBillYear;
    private double sumExpenseEducationYear;
    private double sumExpenseEntertainmentYear;
    private double sumExpenseFoodAndDrinkYear;
    private double sumExpenseShoppingYear;

    private double sumExpenseTransportYear;
    private double sumExpenseTravelYear;
    private double sumExpenseFamilyAndPersonalYear;
    private double sumExpenseHealthCareYear;
    private double sumExpenseSavingAndInvestmentYear;


    private float expenseBillYearPercent;
    private float expenseTravelYearPercent;
    private float expenseFamilyAndPersonalYearPercent;
    private float expenseFoodAndDrinkYearPercent;
    private float expenseShoppingYearPercent;

    private float expenseTransportYearPercent;
    private float expenseEducationYearPercent;
    private float expenseEntertainmentYearPercent;
    private float expenseHealthCareYearPercent;
    private float expenseSavingAndInvestmentYearPercent;


    private String categoryBillYear;
    private String categoryTravelYear;
    private String categoryFamilyAndPersonalYearExpense;
    private String categoryFoodAndDrinkYear;
    private String categoryShoppingYear;

    private String categoryTransportYear;
    private String categoryEducationYear;
    private String categoryEntertainmentYear;
    private String categoryHealthCareYear;
    private String categorySavingAndInvestmentYear;


    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttpIncomeMonth httpIncomeMonth;
    getHttpExpenseMonth httpExpenseMonth;
    getHttpSumIncomeMonthCategory httpSumIncomeMonthCategory;
    getHttpSumExpenseMonthCategory httpSumExpenseMonthCategory;

    getHttpIncomeYear httpIncomeYear;
    getHttpExpenseYear httpExpenseYear;
    getHttpSumIncomeYearCategory httpSumIncomeYearCategory;
    getHttpSumExpenseYearCategory httpSumExpenseYearCategory;


    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";




    public Dashboard() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        setHasOptionsMenu(true);

        ((MainActivity)getActivity()).setTitle("Dashboard");

        Log.d(TAG, "onCreate: Start to create chart ");




    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mView = rootView;




        // for tabHost
        TabHost host = (TabHost) rootView.findViewById(R.id.tabHost);
        host.setup();

        //Tab1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Monthly");
        host.addTab(spec);

        //Tab2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(tab2);
        spec.setIndicator("Yearly");
        host.addTab(spec);



        httpExpenseMonth = new getHttpExpenseMonth(getContext());
        httpIncomeMonth = new getHttpIncomeMonth(getContext());
        httpSumIncomeMonthCategory = new getHttpSumIncomeMonthCategory(getContext());
        httpSumExpenseMonthCategory = new getHttpSumExpenseMonthCategory(getContext());

        httpExpenseYear = new getHttpExpenseYear(getContext());
        httpIncomeYear = new getHttpIncomeYear(getContext());
        httpSumIncomeYearCategory = new getHttpSumIncomeYearCategory(getContext());
        httpSumExpenseYearCategory = new getHttpSumExpenseYearCategory(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sumExpenseMonthToDB(cust_id);
        sumIncomeMonthToDB(cust_id);
        sumIncomeMonthCategory(cust_id);
        sumExpenseMonthCategory(cust_id);

        sumIncomeYearToDB(cust_id);
        sumExpenseYearToDB(cust_id);
        sumIncomeYearCategory(cust_id);
        sumExpenseYearCategory(cust_id);


    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
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





    //////////////////////for month balance/////////////////////

    public String sumIncomeMonthToDB(String cust_id){
        try {
            Log.d(TAG,"start sumIncomeMonth");
            httpIncomeMonth.run(BASE_URL + "/sumIncomeMonth.php?cust_id=" + cust_id);
            Log.d(TAG,"end sumIncomeMonth");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpIncomeMonth {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpIncomeMonth(Context context) {
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
                                String incomeMonth = response.body().string();
                                sumIncomeMonth = Double.parseDouble(incomeMonth.trim());
                                Log.d(TAG,"sumIncome = " + sumIncomeMonth);

                                Log.d(TAG,"onResponse");
                                Log.d(TAG,"show");

                                if(sumExpenseMonth != -1 && sumIncomeMonth != -1 ) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    });
                }
            });
        }
    }


    public String sumExpenseMonthToDB(String cust_id){
        try {
            Log.d(TAG,"start sumExpenseMonth");
            httpExpenseMonth.run(BASE_URL + "/sumExpenseMonth.php?cust_id=" + cust_id);
            Log.d(TAG,"end sumExpenseMonth");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpExpenseMonth {

        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpExpenseMonth(Context context) {
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
                                String expenseMonth = response.body().string();
                                sumExpenseMonth = Double.parseDouble(expenseMonth.trim());
                                Log.d(TAG, "sumExpense = " + sumExpenseMonth);

                                Log.d(TAG, "onResponse");
                                Log.d(TAG, "show");

                                if (sumExpenseMonth != -1 && sumIncomeMonth != -1  ) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    });
                }
            });
        }


    }

    //////////////////////for year balance/////////////////////

    public String sumIncomeYearToDB(String cust_id){
        try {
            Log.d(TAG,"start transaction");
            httpIncomeYear.run(BASE_URL + "/sumIncomeYear.php?cust_id=" + cust_id);
            Log.d(TAG,"end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpIncomeYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpIncomeYear(Context context) {
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
                public void onResponse(Call call, Response response) throws IOException {
                    try {

                        String income = response.body().string();
                        sumIncomeYear = Double.parseDouble(income.trim());
                        Log.d(TAG,"sumIncomeYear = " + sumIncomeYear);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseYear != -1 && sumIncomeYear != -1)   {
                            sumAllBalance();
                        }
                    } catch (NumberFormatException e){
                        //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "NumberFormatException");
                    }
                }
            });
        }
    }


    public String sumExpenseYearToDB(String cust_id){
        try {
            Log.d(TAG,"start show");
            httpExpenseYear.run(BASE_URL + "/sumExpenseYear.php?cust_id=" + cust_id);
            Log.d(TAG,"end show");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpExpenseYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpExpenseYear(Context context) {
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
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String expense = response.body().string();
                        sumExpenseYear = Double.parseDouble(expense.trim());
                        Log.d(TAG,"sumExpenseYear = " + sumExpenseYear);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseYear != -1 && sumIncomeYear != -1 ) {
                            sumAllBalance();
                        }
                    } catch (NumberFormatException e){
                        //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "NumberFormatException");
                    }
                }
            });
        }
    }






    //////////////////////// Income Month ////////////////////////

    public String sumIncomeMonthCategory(String cust_id){
        try {
            Log.d(TAG,"start sumIncomeMonthCategory");
            httpSumIncomeMonthCategory.run(BASE_URL + "/sumIncomeMonthCategory.php?cust_id=" + cust_id);
            //Log.d(TAG,"end sumIncomeMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public void showIncomeMonthCategory(String allIncomeMonth){
        Log.d(TAG, "allIncomeMonth " + allIncomeMonth);
        List<String> items = Arrays.asList(allIncomeMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerCollector = new ArrayList<Double>();
        int pointer = 0;
        for(String num: items){
            if(pointer%2==1){
                integerCollector.add(Double.parseDouble(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for(String num: items){
            if(pointerString%2==0){
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for(int i = 0 ; i<=stringCollector.size()-1;i++) {

            String check = stringCollector.get(i).trim();
            //Log.d(TAG, "check " + check);

            if(check.equals("Extra Income")){
                Log.d(TAG, "check " + check);

                categoryExtraIncomeMonth = "Extra Income";
                Log.d(TAG, "categoryExtraIncomeMonth " + categoryExtraIncomeMonth);

                sumIncomeExtraMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeExtraMonth " + sumIncomeExtraMonth);

            } //family
            if(check.equals("Family and Personal")){
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalIncomeMonth = "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalIncomeMonth " + categoryFamilyAndPersonalIncomeMonth);

                sumIncomeFamilyAndPersonalMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeFamilyAndPersonalMonth " + sumIncomeFamilyAndPersonalMonth);

            } //gift
            if(check.equals("Gift")){
                Log.d(TAG, "check " + check);

                categoryGiftMonth = "Gift";
                Log.d(TAG, "categoryGiftMonth " + categoryGiftMonth);

                sumIncomeGiftMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeGiftMonth " + sumIncomeGiftMonth);

            } //loan
            if (check.equals("Loan")) {
                Log.d(TAG, "check " + check);

                categoryLoanMonth = "Loan";
                Log.d(TAG, "categoryLoanMonth " + categoryLoanMonth);

                sumIncomeLoanMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeLoanMonth " + sumIncomeLoanMonth);

            } //salary
            if(check.equals("Salary")){
                Log.d(TAG, "check " + check);

                categorySalaryMonth = "Salary";
                Log.d(TAG, "categorySalaryMonth " + categorySalaryMonth);

                sumIncomeSalaryMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeSalaryMonth " + sumIncomeSalaryMonth);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumIncomeMonthCategory {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumIncomeMonthCategory(Context context) {
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
                                showIncomeMonthCategory(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    //////////////////////// Expense Month ////////////////////////
    public String sumExpenseMonthCategory(String cust_id){
        try {
            Log.d(TAG,"start sumExpenseMonthCategory");
            httpSumExpenseMonthCategory.run(BASE_URL + "/sumExpenseMonthCategory.php?cust_id=" + cust_id);
           // Log.d(TAG,"end sumExpenseMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public void showExpenseMonthCategory(String allExpenseMonth){
        Log.d(TAG, "allExpenseMonth " + allExpenseMonth);
        List<String> itemsExpense = Arrays.asList(allExpenseMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerExpenseCollector = new ArrayList<Double>();
        int pointerExpense = 0;
        for(String num: itemsExpense){
            if(pointerExpense%2==1){
                integerExpenseCollector.add(Double.parseDouble(itemsExpense.get(pointerExpense)));
            }
            pointerExpense++;
        }

        ArrayList<String> stringExpenseCollector = new ArrayList<String>();
        int pointerStringExpense = 0;
        for(String num: itemsExpense){
            if(pointerStringExpense%2==0){
                stringExpenseCollector.add(itemsExpense.get(pointerStringExpense));
            }
            pointerStringExpense++;
        }


        for(int i = 0 ; i<=stringExpenseCollector.size()-1;i++) {

            String check = stringExpenseCollector.get(i).trim();
            //Log.d(TAG, "check " + check);
            // Bill
            if(check.equals("Bill")){
                Log.d(TAG, "check " + check);

                categoryBillMonth = "Bill";
                Log.d(TAG, "categoryBillMonth " + categoryBillMonth);

                sumExpenseBillMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseBillMonth " + sumExpenseBillMonth);

            } //Education
            if(check.equals("Education")){
                Log.d(TAG, "check " + check);

                categoryEducationMonth = "Education";
                Log.d(TAG, "categoryEducationMonth " + categoryEducationMonth);

                sumExpenseEducationMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEducationMonth " + sumExpenseEducationMonth);

            } //Entertainment
            if(check.equals("Entertainment")){
                Log.d(TAG, "check " + check);

                categoryEntertainmentMonth= "Entertainment";
                Log.d(TAG, "categoryEntertainmentMonth " + categoryEntertainmentMonth);

                sumExpenseEntertainmentMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEntertainmentMonth " + sumExpenseEntertainmentMonth);

            } //Food and Drink
            if (check.equals("Food and Drink")) {
                Log.d(TAG, "check " + check);

                categoryFoodAndDrinkMonth = "Food and Drink";
                Log.d(TAG, "categoryFoodAndDrinkMonth " + categoryFoodAndDrinkMonth);

                sumExpenseFoodAndDrinkMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseFoodAndDrinkMonth " + sumExpenseFoodAndDrinkMonth);

            } //Shopping
            if(check.equals("Shopping")){
                Log.d(TAG, "check " + check);

                categoryShoppingMonth = "Shopping";
                Log.d(TAG, "categoryShoppingMonth " + categoryShoppingMonth);

                sumExpenseShoppingMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseShoppingMonth " + sumExpenseShoppingMonth);

            } //Transport
            if(check.equals("Transport")){
                Log.d(TAG, "check " + check);

                categoryTransportMonth = "Transport";
                Log.d(TAG, "categoryTransportMonth " + categoryTransportMonth);

                sumExpenseTransportMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTransportMonth " + sumExpenseTransportMonth);

            } //Travel
            if(check.equals("Travel")){
                Log.d(TAG, "check " + check);

                categoryTravelMonth = "Travel";
                Log.d(TAG, "categoryTravelMonth " + categoryTravelMonth);

                sumExpenseTravelMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTravelMonth " + sumExpenseTravelMonth);

            } //Family and Personal
            if(check.equals("Family and Personal")){
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalMonthExpense= "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalMonthExpense " + categoryFamilyAndPersonalMonthExpense);

                sumExpenseFamilyAndPersonalMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseFamilyAndPersonalMonth " + sumExpenseFamilyAndPersonalMonth);

            } //Healthcare
            if (check.equals("Healthcare")) {
                Log.d(TAG, "check " + check);

                categoryHealthCareMonth = "Healthcare";
                Log.d(TAG, "categoryHealthCareMonth " + categoryHealthCareMonth);

                sumExpenseHealthCareMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseHealthCareMonth " + sumExpenseHealthCareMonth);

            } //Saving and Investment
            if(check.equals("Saving and Investment")){
                Log.d(TAG, "check " + check);

                categorySavingAndInvestmentMonth = "Saving and Investment";
                Log.d(TAG, "categorySavingAndInvestmentMonth " + categorySavingAndInvestmentMonth);

                sumExpenseSavingAndInvestmentMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseSavingAndInvestmentMonth " + sumExpenseSavingAndInvestmentMonth);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumExpenseMonthCategory {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumExpenseMonthCategory(Context context) {
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
                                showExpenseMonthCategory(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }









    //////////////////////// Income Year ////////////////////////
    public String sumIncomeYearCategory(String cust_id){
        try {
            Log.d(TAG,"start httpSumIncomeYearCategory");
            httpSumIncomeYearCategory.run(BASE_URL + "/sumIncomeYearCategory.php?cust_id=" + cust_id);
            //Log.d(TAG,"end sumIncomeMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public void showIncomeYearCategory(String allIncomeYear){
        Log.d(TAG, "allIncomeYear " + allIncomeYear);
        List<String> items = Arrays.asList(allIncomeYear.split("\\s*,\\s*"));


        ArrayList<Double> integerCollector = new ArrayList<Double>();
        int pointer = 0;
        for(String num: items){
            if(pointer%2==1){
                integerCollector.add(Double.parseDouble(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for(String num: items){
            if(pointerString%2==0){
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for(int i = 0 ; i<=stringCollector.size()-1;i++) {

            String check = stringCollector.get(i).trim();
            //Log.d(TAG, "check " + check);

            if(check.equals("Extra Income")){
                Log.d(TAG, "check " + check);

                categoryExtraIncomeYear = "Extra Income";
                Log.d(TAG, "categoryExtraIncomeYear " + categoryExtraIncomeYear);

                sumIncomeExtraYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeExtraYear " + sumIncomeExtraYear);

            } //family
            if(check.equals("Family and Personal")){
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalIncomeYear = "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalIncomeYear " + categoryFamilyAndPersonalIncomeYear);

                sumIncomeFamilyAndPersonalYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeFamilyAndPersonalYear " + sumIncomeFamilyAndPersonalYear);

            } //gift
            if(check.equals("Gift")){
                Log.d(TAG, "check " + check);

                categoryGiftYear = "Gift";
                Log.d(TAG, "categoryGiftYear " + categoryGiftYear);

                sumIncomeGiftYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeGiftYear " + sumIncomeGiftYear);

            } //loan
            if (check.equals("Loan")) {
                Log.d(TAG, "check " + check);

                categoryLoanYear = "Loan";
                Log.d(TAG, "categoryLoanYear " + categoryLoanYear);

                sumIncomeLoanYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeLoanYear " + sumIncomeLoanYear);

            } //salary
            if(check.equals("Salary")){
                Log.d(TAG, "check " + check);

                categorySalaryYear = "Salary";
                Log.d(TAG, "categorySalaryYear " + categorySalaryYear);

                sumIncomeSalaryYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeSalaryYear " + sumIncomeSalaryYear);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumIncomeYearCategory {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumIncomeYearCategory(Context context) {
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
                                showIncomeYearCategory(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    //////////////////////// Expense Year ////////////////////////
    public String sumExpenseYearCategory(String cust_id){
        try {
            Log.d(TAG,"start sumExpenseYearCategory");
            httpSumExpenseYearCategory.run(BASE_URL + "/sumExpenseYearCategory.php?cust_id=" + cust_id);
            // Log.d(TAG,"end sumExpenseMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public void showExpenseYearCategory(String allExpenseMonth){
        Log.d(TAG, "allExpenseYear " + allExpenseMonth);
        List<String> itemsExpense = Arrays.asList(allExpenseMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerExpenseCollector = new ArrayList<Double>();
        int pointerExpense = 0;
        for(String num: itemsExpense){
            if(pointerExpense%2==1){
                integerExpenseCollector.add(Double.parseDouble(itemsExpense.get(pointerExpense)));
            }
            pointerExpense++;
        }

        ArrayList<String> stringExpenseCollector = new ArrayList<String>();
        int pointerStringExpense = 0;
        for(String num: itemsExpense){
            if(pointerStringExpense%2==0){
                stringExpenseCollector.add(itemsExpense.get(pointerStringExpense));
            }
            pointerStringExpense++;
        }


        for(int i = 0 ; i<=stringExpenseCollector.size()-1;i++) {

            String check = stringExpenseCollector.get(i).trim();
            //Log.d(TAG, "check " + check);
            // Bill
            if(check.equals("Bill")){
                Log.d(TAG, "check " + check);

                categoryBillYear = "Bill";
                Log.d(TAG, "categoryBillYear " + categoryBillYear);

                sumExpenseBillYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseBillYear " + sumExpenseBillYear);

            } //Education
            if(check.equals("Education")){
                Log.d(TAG, "check " + check);

                categoryEducationYear = "Education";
                Log.d(TAG, "categoryEducationYear " + categoryEducationYear);

                sumExpenseEducationYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEducationYear " + sumExpenseEducationYear);

            } //Entertainment
            if(check.equals("Entertainment")){
                Log.d(TAG, "check " + check);

                categoryEntertainmentYear= "Entertainment";
                Log.d(TAG, "categoryEntertainmentYear " + categoryEntertainmentYear);

                sumExpenseEntertainmentYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEntertainmentYear " + sumExpenseEntertainmentYear);

            } //Food and Drink
            if (check.equals("Food and Drink")) {
                Log.d(TAG, "check " + check);

                categoryFoodAndDrinkYear = "Food and Drink";
                Log.d(TAG, "categoryFoodAndDrinkYear " + categoryFoodAndDrinkYear);

                sumExpenseFoodAndDrinkYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseFoodAndDrinkYear " + sumExpenseFoodAndDrinkYear);

            } //Shopping
            if(check.equals("Shopping")){
                Log.d(TAG, "check " + check);

                categoryShoppingYear = "Shopping";
                Log.d(TAG, "categoryShoppingYear " + categoryShoppingYear);

                sumExpenseShoppingYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseShoppingYear " + sumExpenseShoppingYear);

            } //Transport
            if(check.equals("Transport")){
                Log.d(TAG, "check " + check);

                categoryTransportYear = "Transport";
                Log.d(TAG, "categoryTransportYear " + categoryTransportYear);

                sumExpenseTransportYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTransportYear " + sumExpenseTransportYear);

            } //Travel
            if(check.equals("Travel")){
                Log.d(TAG, "check " + check);

                categoryTravelYear = "Travel";
                Log.d(TAG, "categoryTravelYear " + categoryTravelYear);

                sumExpenseTravelYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTravelYear " + sumExpenseTravelYear);

            } //Family and Personal
            if(check.equals("Family and Personal")){
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalYearExpense= "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalYearExpense " + categoryFamilyAndPersonalYearExpense);

                sumExpenseFamilyAndPersonalYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseFamilyAndPersonalYear " + sumExpenseFamilyAndPersonalYear);

            } //Healthcare
            if (check.equals("Healthcare")) {
                Log.d(TAG, "check " + check);

                categoryHealthCareYear = "Healthcare";
                Log.d(TAG, "categoryHealthCareYear " + categoryHealthCareYear);

                sumExpenseHealthCareYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseHealthCareYear " + sumExpenseHealthCareYear);

            } //Saving and Investment
            if(check.equals("Saving and Investment")){
                Log.d(TAG, "check " + check);

                categorySavingAndInvestmentYear = "Saving and Investment";
                Log.d(TAG, "categorySavingAndInvestmentYear " + categorySavingAndInvestmentYear);

                sumExpenseSavingAndInvestmentYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseSavingAndInvestmentYear " + sumExpenseSavingAndInvestmentYear);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumExpenseYearCategory {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumExpenseYearCategory(Context context) {
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
                                showExpenseYearCategory(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }







    public void sumAllBalance(){

        //income month
        incomeExtraMonthPercent = (float) (sumIncomeExtraMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeExtraMonthCPercent = " + incomeExtraMonthPercent);

        incomeFamilyAndPersonalMonthPercent = (float) (sumIncomeFamilyAndPersonalMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeFamilyAndPersonalMonthPercent = " + incomeFamilyAndPersonalMonthPercent);

        incomeGiftMonthPercent = (float) (sumIncomeGiftMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeGiftMonthPercent = " + incomeGiftMonthPercent);

        incomeLoanMonthPercent = (float) (sumIncomeLoanMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);

        incomeSalaryMonthPercent = (float) (sumIncomeSalaryMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);


        //expense month
        expenseBillMonthPercent = (float) (sumExpenseBillMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseBillMonthPercent = " + expenseBillMonthPercent);

        expenseEducationMonthPercent = (float) (sumExpenseEducationMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseEducationMonthPercent = " + expenseEducationMonthPercent);

        expenseEntertainmentMonthPercent = (float) (sumExpenseEntertainmentMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseEntertainmentMonthPercent = " + expenseEntertainmentMonthPercent);

        expenseFoodAndDrinkMonthPercent = (float) (sumExpenseFoodAndDrinkMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseFoodAndDrinkMonthPercent = " + expenseFoodAndDrinkMonthPercent);


        expenseShoppingMonthPercent = (float) (sumExpenseShoppingMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseShoppingMonthPercent = " + expenseShoppingMonthPercent);

        expenseTransportMonthPercent = (float) (sumExpenseTransportMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseTransportMonthPercent = " + expenseTransportMonthPercent);

        expenseTravelMonthPercent = (float) (sumExpenseTravelMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseTravelMonthPercent = " + expenseTravelMonthPercent);

        expenseFamilyAndPersonalMonthPercent = (float) (sumExpenseFamilyAndPersonalMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseFamilyAndPersonalMonthPercent = " + expenseFamilyAndPersonalMonthPercent);

        expenseHealthCareMonthPercent = (float) (sumExpenseHealthCareMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseHealthCareMonthPercent = " + expenseHealthCareMonthPercent);

        expenseSavingAndInvestmentMonthPercent = (float) (sumExpenseSavingAndInvestmentMonth * (100/sumExpenseMonth));
        Log.d(TAG, "Wallet expenseSavingAndInvestmentMonthPercent = " + expenseSavingAndInvestmentMonthPercent);





        //income year
        incomeExtraYearPercent = (float) (sumIncomeExtraYear * (100/sumIncomeYear));
        Log.d(TAG, "Wallet incomeExtraYearPercent = " + incomeExtraYearPercent);

        incomeFamilyAndPersonalYearPercent = (float) (sumIncomeFamilyAndPersonalYear * (100/sumIncomeYear));
        Log.d(TAG, "Wallet incomeFamilyAndPersonalYearPercent = " + incomeFamilyAndPersonalYearPercent);

        incomeGiftYearPercent = (float) (sumIncomeGiftYear * (100/sumIncomeYear));
        Log.d(TAG, "Wallet incomeGiftYearPercent = " + incomeGiftYearPercent);

        incomeLoanYearPercent = (float) (sumIncomeLoanYear * (100/sumIncomeYear));
        Log.d(TAG, "Wallet incomeLoanYearPercent = " + incomeLoanYearPercent);

        incomeSalaryYearPercent = (float) (sumIncomeSalaryYear * (100/sumIncomeYear));
        Log.d(TAG, "Wallet incomeSalaryYearPercent = " + incomeSalaryYearPercent);


        //expense year
        expenseBillYearPercent = (float) (sumExpenseBillYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseBillYearPercent = " + expenseBillYearPercent);

        expenseEducationYearPercent = (float) (sumExpenseEducationYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseEducationYearPercent = " + expenseEducationYearPercent);

        expenseEntertainmentYearPercent = (float) (sumExpenseEntertainmentYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseEntertainmentYearPercent = " + expenseEntertainmentYearPercent);

        expenseFoodAndDrinkYearPercent = (float) (sumExpenseFoodAndDrinkYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseFoodAndDrinkYearPercent = " + expenseFoodAndDrinkYearPercent);


        expenseShoppingYearPercent = (float) (sumExpenseShoppingYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseShoppingYearPercent = " + expenseShoppingYearPercent);

        expenseTransportYearPercent = (float) (sumExpenseTransportYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseTransportYearPercent = " + expenseTransportYearPercent);

        expenseTravelYearPercent = (float) (sumExpenseTravelYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseTravelYearPercent = " + expenseTravelYearPercent);

        expenseFamilyAndPersonalYearPercent = (float) (sumExpenseFamilyAndPersonalYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseFamilyAndPersonalYearPercent = " + expenseFamilyAndPersonalYearPercent);

        expenseHealthCareYearPercent = (float) (sumExpenseHealthCareYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseHealthCareYearPercent = " + expenseHealthCareYearPercent);

        expenseSavingAndInvestmentYearPercent = (float) (sumExpenseSavingAndInvestmentYear * (100/sumExpenseYear));
        Log.d(TAG, "Wallet expenseSavingAndInvestmentYearPercent = " + expenseSavingAndInvestmentYearPercent);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        //////for pie chart
        initDataIncome();
        initDataExpense();
        initDataIncomeYear();
        initDataExpenseYear();





    }




    // line chart
//    private void initData() {
//
//        lineChart = (LineChart) mView.findViewById(R.id.lineChart);
//        ArrayList<String> xAXES = new ArrayList<>();
//        ArrayList<Entry> yAXESsin = new ArrayList<>();
//        ArrayList<Entry> yAXEScos = new ArrayList<>();
//        double x  = 0;
//        int numDataPoints = 1000;
//        for (int i = 0; i< numDataPoints ; i++){
//            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
//            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
//            x = x + 0.1;
//            yAXESsin.add(new Entry(sinFunction,i));
//            yAXEScos.add(new Entry(cosFunction,i));
//            xAXES.add(i, String.valueOf(x));
//        }
//
//        String[] xaxes = new String[xAXES.size()];
//        for (int i = 0; i< xAXES.size() ; i++){
//            xaxes[i] = xAXES.get(i).toString();
//        }
//
//        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
//
//        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
//        lineDataSet1.setDrawCircles(false);
//        lineDataSet1.setColor(Color.GREEN);
//
//        LineDataSet lineDataSet2 = new LineDataSet(yAXEScos, "sin");
//        lineDataSet2.setDrawCircles(false);
//        lineDataSet2.setColor(Color.RED);
//
//        lineDataSets.add(lineDataSet1);
//        lineDataSets.add(lineDataSet2);
//
//        lineChart.setData(new LineData(lineDataSets));
//        lineChart.setVisibleXRangeMaximum(100f);
//
//
//    }


    // pie chart income month
    private void initDataIncome() {
        Log.d(TAG, "initDataIncome");

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncomeMonth);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(7);

        //pieChart.setDrawEntryLabels(true);

        addDataSetIncome();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetIncome() {
        Log.d(TAG, "addDataSet income month started");

        float[] yDataIncomeMonth = {incomeExtraMonthPercent, incomeFamilyAndPersonalMonthPercent
                ,incomeGiftMonthPercent, incomeLoanMonthPercent, incomeSalaryMonthPercent};
        Log.d(TAG, "Wallet incomeExtraMonthPercent = " + incomeExtraMonthPercent);
        Log.d(TAG, "Wallet incomeFamilyAndPersonalMonthPercent = " + incomeFamilyAndPersonalMonthPercent);
        Log.d(TAG, "Wallet incomeGiftMonthPercent = " + incomeGiftMonthPercent);
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);
        String[] xDataIncomeMonth = { "Extra income", "Family and Personal","Gift","Loan","Salary" };

        ArrayList<PieEntry> yEntrysIncomeMonth = new ArrayList<>();
        ArrayList<String> xEntrysIncomeMonth = new ArrayList<>();

        for (int i =0 ; i < yDataIncomeMonth.length ; i++){
            yEntrysIncomeMonth.add(new PieEntry(yDataIncomeMonth[i], i));
        }
        for (int i =0 ; i < xDataIncomeMonth.length ; i++){
            xEntrysIncomeMonth.add(xDataIncomeMonth[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysIncomeMonth, "Income");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);
//        pieDataSet.setValueTextColor(Color.BLACK);
//        pieDataSet.setValueLinePart1OffsetPercentage(90.f);
//        pieDataSet.setValueLinePart1Length(1f);
//        pieDataSet.setValueLinePart2Length(.2f);
//        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);


        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    // pie chart expense month
    private void initDataExpense() {
        Log.d(TAG, "initDataExpense");
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpenseMonth);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(7);
        //pieChart.setDrawEntryLabels(true);

        addDataSetExpense();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetExpense() {
        Log.d(TAG, "addDataSet expense month started");
        ArrayList<PieEntry> yEntrysExpenseMonth = new ArrayList<>();
        ArrayList<String> xEntrysExpenseMonth = new ArrayList<>();

        float[] yDataExpenseMonth = {expenseBillMonthPercent, expenseEducationMonthPercent
                ,expenseEntertainmentMonthPercent, expenseFoodAndDrinkMonthPercent, expenseShoppingMonthPercent
                ,expenseTransportMonthPercent, expenseTravelMonthPercent
                ,expenseFamilyAndPersonalMonthPercent, expenseHealthCareMonthPercent, expenseSavingAndInvestmentMonthPercent};
        Log.d(TAG, "Wallet expenseBillMonthPercent = " + expenseBillMonthPercent);
        Log.d(TAG, "Wallet expenseEducationMonthPercent = " + expenseEducationMonthPercent);
        Log.d(TAG, "Wallet expenseEntertainmentMonthPercent = " + expenseEntertainmentMonthPercent);
        Log.d(TAG, "Wallet expenseFoodAndDrinkMonthPercent = " + expenseFoodAndDrinkMonthPercent);
        Log.d(TAG, "Wallet expenseShoppingMonthPercent = " + expenseShoppingMonthPercent);

        Log.d(TAG, "Wallet expenseTransportMonthPercent = " + expenseTransportMonthPercent);
        Log.d(TAG, "Wallet expenseTravelMonthPercent = " + expenseTravelMonthPercent);
        Log.d(TAG, "Wallet expenseFamilyAndPersonalMonthPercent = " + expenseFamilyAndPersonalMonthPercent);
        Log.d(TAG, "Wallet expenseHealthCareMonthPercent = " + expenseHealthCareMonthPercent);
        Log.d(TAG, "Wallet expenseSavingAndInvestmentMonthPercent = " + expenseSavingAndInvestmentMonthPercent);

        String[] xDataExpenseMonth = { "Bill","Education" , "Entertainment" , "Food and Drink",
                "Shopping", "Transport", "Travel", "Family and Personal","Healthcare","Saving and Investment","Salary"};

        for (int i =0 ; i < yDataExpenseMonth.length ; i++){
            yEntrysExpenseMonth.add(new PieEntry(yDataExpenseMonth[i], i));
        }
        for (int i =0 ; i < xDataExpenseMonth.length ; i++){
            xEntrysExpenseMonth.add(xDataExpenseMonth[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysExpenseMonth, "Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLACK);
        colors.add(Color.DKGRAY);
        colors.add(Color.GRAY);
        colors.add(Color.LTGRAY);

        pieDataSet.setColors(colors);

        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }




    // pie chart income year
    private void initDataIncomeYear() {

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncomeYear);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(7);
        //pieChart.setDrawEntryLabels(true);

        addDataSetIncomeYear();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetIncomeYear() {
        Log.d(TAG, "addDataSet income year started");

        float[] yDataIncomeYear = {incomeExtraYearPercent, incomeFamilyAndPersonalYearPercent
                ,incomeGiftYearPercent, incomeLoanYearPercent, incomeSalaryYearPercent};
        Log.d(TAG, "Wallet incomeExtraYearPercent = " + incomeExtraYearPercent);
        Log.d(TAG, "Wallet incomeFamilyAndPersonalYearPercent = " + incomeFamilyAndPersonalYearPercent);
        Log.d(TAG, "Wallet incomeGiftYearPercent = " + incomeGiftYearPercent);
        Log.d(TAG, "Wallet incomeLoanYearPercent = " + incomeLoanYearPercent);
        Log.d(TAG, "Wallet incomeSalaryYearPercent = " + incomeSalaryYearPercent);
        String[] xDataIncomeYear = { "Extra income", "Family and Personal","Gift","Loan","Salary" };

        ArrayList<PieEntry> yEntrysIncomeYear = new ArrayList<>();
        ArrayList<String> xEntrysIncomeYear = new ArrayList<>();

        for (int i =0 ; i < yDataIncomeYear.length ; i++){
            yEntrysIncomeYear.add(new PieEntry(yDataIncomeYear[i], i));
        }
        for (int i =0 ; i < xDataIncomeYear.length ; i++){
            xEntrysIncomeYear.add(xDataIncomeYear[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysIncomeYear, "Income");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }


    // pie chart expense year
    private void initDataExpenseYear() {
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpenseYear);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(7);
        //pieChart.setDrawEntryLabels(true);

        addDataSetExpenseYear();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetExpenseYear() {
        Log.d(TAG, "addDataSet expense year started");
        ArrayList<PieEntry> yEntrysExpenseYear = new ArrayList<>();
        ArrayList<String> xEntrysExpenseYear = new ArrayList<>();

        float[] yDataExpenseYear = {expenseBillYearPercent, expenseEducationYearPercent
                ,expenseEntertainmentYearPercent, expenseFoodAndDrinkYearPercent, expenseShoppingYearPercent
                ,expenseTransportYearPercent, expenseTravelYearPercent
                ,expenseFamilyAndPersonalYearPercent, expenseHealthCareYearPercent, expenseSavingAndInvestmentYearPercent};
        Log.d(TAG, "Wallet expenseBillYearPercent = " + expenseBillYearPercent);
        Log.d(TAG, "Wallet expenseEducationYearPercent = " + expenseEducationYearPercent);
        Log.d(TAG, "Wallet expenseEntertainmentYearPercent = " + expenseEntertainmentYearPercent);
        Log.d(TAG, "Wallet expenseFoodAndDrinkYearPercent = " + expenseFoodAndDrinkYearPercent);
        Log.d(TAG, "Wallet expenseShoppingYearPercent = " + expenseShoppingYearPercent);

        Log.d(TAG, "Wallet expenseTransportYearPercent = " + expenseTransportYearPercent);
        Log.d(TAG, "Wallet expenseTravelYearPercent = " + expenseTravelYearPercent);
        Log.d(TAG, "Wallet expenseFamilyAndPersonalYearPercent = " + expenseFamilyAndPersonalYearPercent);
        Log.d(TAG, "Wallet expenseHealthCareYearPercent = " + expenseHealthCareYearPercent);
        Log.d(TAG, "Wallet expenseSavingAndInvestmentYearPercent = " + expenseSavingAndInvestmentYearPercent);
        String[] xDataExpenseYear = { "Bill","Education" , "Entertainment" , "Food and Drink",
                "Shopping", "Transport", "Travel", "Family and Personal","Healthcare","Saving and Investment","Salary"};

        for (int i =0 ; i < yDataExpenseYear.length ; i++){
            yEntrysExpenseYear.add(new PieEntry(yDataExpenseYear[i], i));
            Log.d(TAG, "yEntrysExpenseYear = " + yEntrysExpenseYear);
        }
        for (int i =0 ; i < xDataExpenseYear.length ; i++){
            xEntrysExpenseYear.add(xDataExpenseYear[i]);
            Log.d(TAG, "xEntrysExpenseYear = " + xEntrysExpenseYear);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysExpenseYear, "Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);
        Log.d(TAG, "pieDataSet = " + pieDataSet);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLACK);
        colors.add(Color.DKGRAY);
        colors.add(Color.GRAY);
        colors.add(Color.LTGRAY);

        pieDataSet.setColors(colors);

        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }



}

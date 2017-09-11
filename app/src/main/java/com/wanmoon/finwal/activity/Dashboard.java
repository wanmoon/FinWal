package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;

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
    private float[] yData = {10.0f, 90.0f};
    private String[] xData = {"Income", "Expense"};
    LineChart lineChart;



    //for pie chart
    private final String TAG = "Dashboard";
    private float[] yDataIncome = {};
    private String[] xDataIncome = {};

    private float[] yDataExpense = {10.0f, 10.0f, 20.0f, 40.0f ,20.0f};
    private String[] xDataExpense = {"Bill", "Education","Entertainment", "Food and Drink", "Shopping"};
    PieChart pieChart;
    private View mView;

    private double sumIncomeMonth;
    private double sumExpenseMonth;
    private double monthBalance;

    private String setMonthBalance;
    private String setIncomeMonth;
    private String setExpenseMonth;

    public TextView textViewMyIncome;
    public TextView textViewMyExpense;
    public TextView textViewMonthBalance;

    private float incomePercent ;
    private float expensePercent ;

    private double sumIncomeSalaryMonth;
    private double sumIncomeGiftMonth;
    private double sumIncomeLoanMonth;
    private double sumIncomeFamilyAndPersonalMonth;
    private double sumIncomeExtraMonth;

    private float incomeSalaryMonthPercent;
    private float incomeGiftMonthPercent;
    private float incomeLoanMonthPercent;
    private float incomeFamilyAndPersonalPercent;
    private float incomeExtraPercent;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttpIncomeMonth httpIncomeMonth = new getHttpIncomeMonth();
    getHttpExpenseMonth httpExpenseMonth = new getHttpExpenseMonth();
    getHttpSumIncomeSalaryMonth httpSumIncomeSalaryMonth = new getHttpSumIncomeSalaryMonth();
    getHttpSumIncomeGiftMonth httpSumIncomeGiftMonth = new getHttpSumIncomeGiftMonth();
    getHttpSumIncomeLoanMonth httpSumIncomeLoanMonth = new getHttpSumIncomeLoanMonth();

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


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sumExpenseMonthToDB(cust_id);
        sumIncomeMonthToDB(cust_id);

        sumIncomeSalaryMonthToDB(cust_id);
        sumIncomeGiftMonthToDB(cust_id);
        sumIncomeLoanMonthToDB(cust_id);


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
                    try {
                        String expenseMonth = response.body().string();
                        sumExpenseMonth = Double.parseDouble(expenseMonth.trim());
                        Log.d(TAG,"sumExpense = " + sumExpenseMonth);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
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

    public class getHttpIncomeMonth {
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
                    try {
                        String incomeMonth = response.body().string();
                        sumIncomeMonth = Double.parseDouble(incomeMonth.trim());
                        Log.d(TAG,"sumIncome = " + sumIncomeMonth);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
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

    //////////////////////// Income ////////////////////////

    // SalaryMonth
    public String sumIncomeSalaryMonthToDB(String cust_id){
        try {
            Log.d(TAG,"start sumIncomeSalaryMonthToDB");
            httpSumIncomeSalaryMonth.run(BASE_URL + "/incomeMonth/sumIncomeSalaryMonth.php?cust_id=" + cust_id);
            Log.d(TAG,"end sumIncomeSalaryMonthToDB");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpSumIncomeSalaryMonth {
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
                    try {
                        String expenseMonth = response.body().string();
                        sumIncomeSalaryMonth = Double.parseDouble(expenseMonth.trim());
                        Log.d(TAG,"sumIncomeSalaryMonth = " + sumIncomeSalaryMonth);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
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


    // GiftMonth
    public String sumIncomeGiftMonthToDB(String cust_id){
        try {
            Log.d(TAG,"start sumIncomeGiftMonthToDB");
            httpSumIncomeGiftMonth.run(BASE_URL + "/incomeMonth/sumIncomeGiftMonth.php?cust_id=" + cust_id);
            Log.d(TAG,"end sumIncomeGiftMonthToDB");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpSumIncomeGiftMonth {
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
                    try {
                        String expenseMonth = response.body().string();
                        sumIncomeGiftMonth = Double.parseDouble(expenseMonth.trim());
                        Log.d(TAG,"sumIncomeGiftMonth = " + sumIncomeGiftMonth);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
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

    // LoanMonth
    public String sumIncomeLoanMonthToDB(String cust_id){
        try {
            Log.d(TAG,"start sumIncomeLoanMonthToDB");
            httpSumIncomeLoanMonth.run(BASE_URL + "/incomeMonth/sumIncomeLoanMonth.php?cust_id=" + cust_id);
            Log.d(TAG,"end sumIncomeLoanMonthToDB");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpSumIncomeLoanMonth {
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
                    try {
                        String expenseMonth = response.body().string();
                        sumIncomeLoanMonth = Double.parseDouble(expenseMonth.trim());
                        Log.d(TAG,"sumIncomeLoanMonth = " + sumIncomeLoanMonth);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
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



    public void sumAllBalance(){
        monthBalance = sumIncomeMonth - sumExpenseMonth;
        Log.d(TAG, "Month balance = " + monthBalance);

        Log.d(TAG,"start settext");
        setIncomeMonth = "Total Income : " + "<b>" + sumIncomeMonth + " Baht</b>";
      //  textViewMyIncome.setText((Html.fromHtml(setIncomeMonth)));
        Log.d(TAG, "sumIncomeMonth = " + sumIncomeMonth);

        setExpenseMonth = "Total Expense : " + "<b>" + sumExpenseMonth + " Baht</b>";
     //   textViewMyExpense.setText((Html.fromHtml(setExpenseMonth)));
        Log.d(TAG, "sumExpenseMonth = " + sumExpenseMonth);

        //in a month
        setMonthBalance = "Month Balance : " + "<b>" + monthBalance + " Baht</b>";
      //  textViewMonthBalance.setText((Html.fromHtml(setMonthBalance)));



        incomePercent = (float) (monthBalance * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomePercent = " + incomePercent);

        expensePercent = (float) ( sumExpenseMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet expensePercent = " + expensePercent);

        incomeSalaryMonthPercent = (float) (sumIncomeSalaryMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);

        incomeGiftMonthPercent = (float) (sumIncomeGiftMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeSalaryGiftPercent = " + incomeGiftMonthPercent);

        incomeLoanMonthPercent = (float) (sumIncomeLoanMonth * (100/sumIncomeMonth));
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);


        //for pie chart
        initDataIncome();
       // initDataExpense();
       // initData();


    }

    // line chart
    private void initData() {

        lineChart = (LineChart) mView.findViewById(R.id.lineChart);
        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x  = 0;
        int numDataPoints = 1000;
        for (int i = 0; i< numDataPoints ; i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            xAXES.add(i, String.valueOf(x));
        }

        String[] xaxes = new String[xAXES.size()];
        for (int i = 0; i< xAXES.size() ; i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.GREEN);

        LineDataSet lineDataSet2 = new LineDataSet(yAXEScos, "sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));
        lineChart.setVisibleXRangeMaximum(100f);


    }


    // pie chart income
    private void initDataIncome() {

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncome);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(8);
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
        Log.d(TAG, "addDataSet started");

        float[] yDataIncome = {incomeSalaryMonthPercent, incomeGiftMonthPercent, incomeLoanMonthPercent};
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);
        Log.d(TAG, "Wallet incomeGiftMonthPercent = " + incomeGiftMonthPercent);
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeGiftMonthPercent);
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);
        String[] xDataIncome = {"Salary", "Gift","Loan", "Family and Personal", "Extra income"};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i =0 ; i < yDataIncome.length ; i++){
            yEntrys.add(new PieEntry(yDataIncome[i], i));
        }
        for (int i =0 ; i < xDataIncome.length ; i++){
            xEntrys.add(xDataIncome[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "DD");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
//        colors.add(Color.GREEN);
//        colors.add(Color.RED);

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

    private void addDataSetExpense() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i =0 ; i < yDataExpense.length ; i++){
            yEntrys.add(new PieEntry(yDataExpense[i], i));
        }
        for (int i =0 ; i < xDataExpense.length ; i++){
            xEntrys.add(xDataExpense[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "DD");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

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



    private void initDataExpense() {
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpense);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(8);
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



}

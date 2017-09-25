package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Billing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Billing#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth firebaseAuth;

    private static View rootView;
    private OnFragmentInteractionListener mListener;

    private double sumIncomeMonth ;
    private double sumExpenseMonth ;
    private double monthBalance ;
    private double walletBalance ;
    private double sumIncome ;
    private double sumExpense ;

    private String setWalletBalance;
    private String setMonthBalance;
    private String setIncomeMonth;
    private String setExpenseMonth;

    public TextView textViewMyWallet;
    public TextView textViewMyIncome;
    public TextView textViewMyExpense;
    public TextView textViewMonthBalance;
    public TextView textViewStatusGoal;
    public TextView textViewPercentGoal;
    public TextView textViewMoneyLeftGoal;
    public TextView textViewGoalTitle;
    public TextView textViewDateLeft;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    public RoundCornerProgressBar progress;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

//    EditGoal editGoal = new EditGoal();

    //connect DB
    String response = null;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    getHttpIncomeMonth httpIncomeMonth;
    getHttpExpenseMonth httpExpenseMonth;
    getHttpIncome httpIncome;
    getHttpExpense httpExpense;
    getHttpProgressBar httpProgressBar;

    //for log
    private final String TAG = "HomeActivity";

    private float incomePercent;
    private float expensePercent;

    public int goal_id = 47;

    //for pie chart
    public PieChart pieChart;
    private View mView;

    //progress
    public String data_goal_id;
    public String data_cust_id;
    public String ending_date;
    public String description_goal;
    public String status_goal;
    public String savingplan;

    public double budget_goal;
    public double suggest_cost;
    public double current_goal;
    public double current_goalPercent;
    public double moneyleft;

    public float float_current_goalPercent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Billing.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        ((MainActivity)getActivity()).setTitle("My FinWal");

        Log.d(TAG,"end onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mView = rootView;

        httpIncomeMonth = new getHttpIncomeMonth(getContext());
        httpExpenseMonth = new getHttpExpenseMonth(getContext());

        httpIncome = new getHttpIncome(getContext());
        httpExpense = new getHttpExpense(getContext());

        httpProgressBar = new getHttpProgressBar(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sumIncomeMonth = 0;
        sumExpenseMonth = 0;
        sumIncome = 0;
        sumExpense = 0;

        sumExpenseMonthToDB(cust_id);
        sumIncomeMonthToDB(cust_id);

        sumIncomeToDB(cust_id);
        sumExpenseToDB(cust_id);

        getProgressbar(cust_id, goal_id);

        Log.d(TAG,"start findviewbyid");
        textViewMyWallet = (TextView) view.findViewById(R.id.textViewMyWallet);
        textViewMyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditGoal.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        textViewMyIncome = (TextView) view.findViewById(R.id.textViewMyIncome);
        textViewMyExpense = (TextView) view.findViewById(R.id.textViewMyExpense);
        textViewMonthBalance = (TextView) view.findViewById(R.id.textViewMonthBalance);
        textViewMonthBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NotificationPage.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        linearLayout1 = (LinearLayout)  view.findViewById(R.id.layout_home4_11);
        linearLayout2 = (LinearLayout)  view.findViewById(R.id.layout_home4_12);

        Log.d(TAG,"end findviewbyid");

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllIncome.class);
                startActivity(i);
                getActivity().finish();
            }

        });


        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllExpense.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        textViewGoalTitle = (TextView) view.findViewById(R.id.textViewGoalTitle);
        textViewDateLeft = (TextView) view.findViewById(R.id.textViewDateLeft);
        textViewStatusGoal = (TextView) view.findViewById(R.id.textViewStatusGoal);
        textViewPercentGoal = (TextView) view.findViewById(R.id.textViewPercentGoal);
        textViewMoneyLeftGoal = (TextView) view.findViewById(R.id.textViewMoneyLeftGoal);

        progress = (RoundCornerProgressBar) view.findViewById(R.id.progress_1);
        progressBar(float_current_goalPercent);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.billing_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
                    Log.d(TAG,"onFailure" + e.toString());
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

                                if (sumExpenseMonth != 0 && sumIncomeMonth != 0) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
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

                                if(sumExpenseMonth != 0 && sumIncomeMonth != 0) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
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

    //////////////////////for wallet balance/////////////////////

    public String sumIncomeToDB(String cust_id){
        try {
            Log.d(TAG,"start transaction");
            httpIncome.run(BASE_URL + "/sumIncome.php?cust_id=" + cust_id);
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
            httpExpense.run(BASE_URL + "/sumExpense.php?cust_id=" + cust_id);
            Log.d(TAG,"end show");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
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
                                String expense = response.body().string();
                                sumExpense = Double.parseDouble(expense.trim());
                                Log.d(TAG,"sumExpense = " + sumExpense);

                                Log.d(TAG,"onResponse");
                                Log.d(TAG,"show");

                                if(sumExpense != 0 && sumIncome != 0) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
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
                public void onResponse(Call call,final Response response) throws IOException {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String income = response.body().string();
                                sumIncome = Double.parseDouble(income.trim());
                                Log.d(TAG,"sumIncome = " + sumIncome);

                                Log.d(TAG,"onResponse");
                                Log.d(TAG,"show");

                                if(sumExpense != 0 && sumIncome != 0) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
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

    public void sumAllBalance(){

        monthBalance = sumIncomeMonth - sumExpenseMonth;
        Log.d(TAG, "Month balance = " + monthBalance);

        Log.d(TAG,"start settext");

        // follow UI
        ////////////////////////for wallet balance//////////////////////
        walletBalance = sumIncome - sumExpense;
        Log.d(TAG, "Wallet balance = " + walletBalance);

        //in a month
        setMonthBalance = "Month Balance : " + "<b>" + monthBalance + " Baht</b>";
        textViewMonthBalance.setText((Html.fromHtml(setMonthBalance)));

        setIncomeMonth ="<b>" + sumIncomeMonth + " Baht</b>";
        textViewMyIncome.setText((Html.fromHtml(setIncomeMonth)));
        Log.d(TAG, "sumIncomeMonth = " + sumIncomeMonth);

        setExpenseMonth = "<b>" + sumExpenseMonth + " Baht</b>";
        textViewMyExpense.setText((Html.fromHtml(setExpenseMonth)));
        Log.d(TAG, "sumExpenseMonth = " + sumExpenseMonth);


        //all of my life
        setWalletBalance = "[Since 2017-08-29] Wallet Balance : " + "<b>" + walletBalance + " Baht</b>";
        textViewMyWallet.setText((Html.fromHtml(setWalletBalance)));
        Log.d(TAG,"end settext");



        incomePercent = (float) (monthBalance * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomePercent = " + incomePercent);

        expensePercent = (float) ( sumExpenseMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet expensePercent = " + expensePercent);


        //for pie chart
        initData();


    }

    //////////////////////// for pie chart //////////////////////
    private void initData() {

        pieChart = (PieChart) mView.findViewById(R.id.Piechart);

        pieChart.setDescription("");
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("My Finwal");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);

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
        Log.d(TAG, "addDataSet started pie chart");

        ArrayList<Float> yData = new ArrayList<>();
        if(incomePercent > 0) yData.add(incomePercent);
        if(expensePercent > 0) yData.add(expensePercent);

        Log.d(TAG, "addDataSet started incomePercent" + incomePercent);
        Log.d(TAG, "addDataSet started expensePercent" + expensePercent);
        String[] xData = {"Income", "Expense"};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.size(); i++) {
            yEntrys.add(new PieEntry(yData.get(i), i));
        }
        for (int i = 0; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Income , Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(15);
        pieDataSet.setValueFormatter(new PercentFormatter());


        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.buttonColorGreen));
        colors.add(getResources().getColor(R.color.buttonColorRed));
        pieDataSet.setColors(colors);

        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12f);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    //////////////////////progress bar/////////////////////
    public void progressdata(String data){
        Log.d(TAG, "data " + data);
        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));

        data_goal_id = items.get(0);
        data_cust_id = items.get(1);
        ending_date = items.get(2);
        description_goal = items.get(3);
        status_goal = items.get(4);
        String data_budget_goal = items.get(5); //change to double
        savingplan = items.get(6);
        String data_suggest_cost = items.get(7);//change to double
        String data_current_goal = items.get(8);//change to double

        budget_goal = Double.parseDouble(data_budget_goal);
        suggest_cost = Double.parseDouble(data_suggest_cost);
        current_goal = Double.parseDouble(data_current_goal);

        Log.d(TAG, "data_goal_id " + data_goal_id);
        Log.d(TAG, "data_cust_id " + data_cust_id);
        Log.d(TAG, "ending_date " + ending_date);
        Log.d(TAG, "description_goal " + description_goal);
        Log.d(TAG, "status_goal " + status_goal);
        Log.d(TAG, "budget_goal " + budget_goal);
        Log.d(TAG, "savingplan " + savingplan);
        Log.d(TAG, "suggest_cost " + suggest_cost);
        Log.d(TAG, "current_goal " + current_goal);

        current_goalPercent = (current_goal/budget_goal)*100;
        float_current_goalPercent = Float.parseFloat(String.format("%.2f", current_goalPercent));

        progressBar(float_current_goalPercent);
        setDataProcessBar(ending_date, description_goal, status_goal, budget_goal, current_goal);
    }

    public void progressBar(float float_current_goalPercent){
        Log.d(TAG,"float_current_goalPercent = " + float_current_goalPercent);

        if (float_current_goalPercent >=50){
            progress.setProgressColor(Color.parseColor("#088A4B"));
        } else {
            progress.setProgressColor(Color.parseColor("#e54649"));
        }

        progress.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));
        progress.setMax(100);
        progress.setProgress(float_current_goalPercent);
        progress.setContentDescription(float_current_goalPercent+"");

        Log.d(TAG,"finish draw progressbar");

    }

    public void setDataProcessBar(String ending_date, String description_goal, String status_goal, double budget_goal, double current_goal){
        //count day left
        //settext : description, status, percent, count, moneyleft

        textViewGoalTitle.setText(description_goal);
        textViewPercentGoal.setText(float_current_goalPercent+"%");
        textViewStatusGoal.setText(status_goal);
        if(status_goal.equals("Achieved")){
            textViewStatusGoal.setTextColor(Color.parseColor("#088A4B"));
        } else {
            textViewStatusGoal.setTextColor(Color.parseColor("#e54649"));
        }

        moneyleft = budget_goal - current_goal;
        Log.d(TAG,"moneyleft = " + moneyleft);
        textViewMoneyLeftGoal.setText("money left " + moneyleft + " baht");

        if (current_goal >= budget_goal){
            textViewStatusGoal.setText("Achieve");
            textViewStatusGoal.setTextColor(Color.parseColor("#088A4B"));
        }

//        //textViewDateLeft
//        java.text.DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Calendar today = Calendar.getInstance();
//        Log.d(TAG, "today = " + dateFormat.format(today.getTime()));
//
//        String ending_date_day = ending_date.substring(0,2);
//        String ending_date_month = ending_date.substring(3,5);
//        String ending_date_year = ending_date.substring(6);
//
//        Calendar lastday = Calendar.getInstance();
//        lastday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ending_date_day);
//        lastday.set(Calendar.MONTH, Integer.parseInt(ending_date_month);
//        lastday.set(Calendar.YEAR, Integer.parseInt(ending_date_year);
//        Date secondDate = lastday.getTime();
//
//        long diff = secondDate.getTime() - dateFormat.format(today.getTime());
    }

    public String getProgressbar(String cust_id, int goal_id){
        try {
            Log.d(TAG,"start progressbar");
            httpProgressBar.run(BASE_URL + "/progressbar.php?cust_id=" + cust_id + "&goal_id=" + goal_id);
            Log.d(TAG,"end progressbar");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpProgressBar {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpProgressBar(Context context) {
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
                                progressdata(response.body().string().trim());
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
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
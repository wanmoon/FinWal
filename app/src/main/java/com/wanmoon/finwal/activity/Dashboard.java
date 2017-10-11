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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
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


    //line chart
    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String oct;
    private String nov;
    private String dec;

    private float sumIncomeJan;
    private float sumIncomeFeb;
    private float sumIncomeMar;
    private float sumIncomeApr;
    private float sumIncomeMay;
    private float sumIncomeJun;
    private float sumIncomeJul;
    private float sumIncomeAug;
    private float sumIncomeSep;
    private float sumIncomeOct;
    private float sumIncomeNov;
    private float sumIncomeDec;

    private float sumExpenseJan;
    private float sumExpenseFeb;
    private float sumExpenseMar;
    private float sumExpenseApr;
    private float sumExpenseMay;
    private float sumExpenseJun;
    private float sumExpenseJul;
    private float sumExpenseAug;
    private float sumExpenseSep;
    private float sumExpenseOct;
    private float sumExpenseNov;
    private float sumExpenseDec;



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

    getHttpSumIncomeYear httpSumIncomeYear;
    getHttpSumExpenseYear httpSumExpenseYear;


    getHttpSumIncomeMonthCategory httpSumIncomeMonthCategory;
    getHttpSumExpenseMonthCategory httpSumExpenseMonthCategory;


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

        ((MainActivity) getActivity()).setTitle("Dashboard");

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
        spec.setIndicator("Over all");
        host.addTab(spec);

        //Tab2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Category");
        host.addTab(spec);

        //Tab3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Monthly");
        host.addTab(spec);

        //Tab4
        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Yearly");
        host.addTab(spec);



        httpSumIncomeYear = new getHttpSumIncomeYear(getContext());
        httpSumExpenseYear = new getHttpSumExpenseYear(getContext());


        httpSumIncomeMonthCategory = new getHttpSumIncomeMonthCategory(getContext());
        httpSumExpenseMonthCategory = new getHttpSumExpenseMonthCategory(getContext());


        httpSumIncomeYearCategory = new getHttpSumIncomeYearCategory(getContext());
        httpSumExpenseYearCategory = new getHttpSumExpenseYearCategory(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        sumIncomeYear(cust_id);
        sumExpenseYear(cust_id);

        sumIncomeMonthCategory(cust_id);
        sumExpenseMonthCategory(cust_id);

        sumIncomeYearCategory(cust_id);
        sumExpenseYearCategory(cust_id);


    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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



    //////////////////////// Income Year line chart ////////////////////////

    public String sumIncomeYear(String cust_id) {
        try {
            Log.d(TAG, "start sumIncomeYearLine");
            httpSumIncomeYear.run(BASE_URL + "/totalIncome.php?cust_id=" + cust_id);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showIncomeYear(String allIncomeYearLine) {
        Log.d(TAG, "allIncomeYearLine " + allIncomeYearLine);
        List<String> items = Arrays.asList(allIncomeYearLine.split("\\s*,\\s*"));


        ArrayList<Float> integerCollector = new ArrayList<Float>();
        int pointer = 0;
        for (String num : items) {
            if (pointer % 2 == 1) {
                integerCollector.add(Float.parseFloat(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for (String num : items) {
            if (pointerString % 2 == 0) {
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for (int i = 0; i <= stringCollector.size() - 1; i++) {

            String check = stringCollector.get(i).trim();
            Log.d(TAG, "check " + check);
            // jan
            if (check.equals("1")) {
                Log.d(TAG, "check " + check);

                jan = "JAN";
                Log.d(TAG, "jan " + jan);

                sumIncomeJan = integerCollector.get(i);
                Log.d(TAG, "sumIncomeJan " + sumIncomeJan);

            } //feb
            if (check.equals("2")) {
                Log.d(TAG, "check " + check);

                feb = "FEB";
                Log.d(TAG, "feb " + feb);

                sumIncomeFeb = integerCollector.get(i);
                Log.d(TAG, "sumIncomeFeb " + sumIncomeFeb);

            } //3
            if (check.equals("3")) {
                Log.d(TAG, "check " + check);

                mar = "MAR";
                Log.d(TAG, "mar " + mar);

                sumIncomeMar = integerCollector.get(i);
                Log.d(TAG, "sumIncomeMar " + sumIncomeMar);

            } //4
            if (check.equals("4")) {
                Log.d(TAG, "check " + check);

                apr = "APR";
                Log.d(TAG, "apr " + apr);

                sumIncomeApr = integerCollector.get(i);
                Log.d(TAG, "sumIncomeApr " + sumIncomeApr);

            } //5
            if (check.equals("5")) {
                Log.d(TAG, "check " + check);

                may = "MAY";
                Log.d(TAG, "may " + may);

                sumIncomeMay = integerCollector.get(i);
                Log.d(TAG, "sumIncomeMay " + sumIncomeMay);

            } //6
            if (check.equals("6")) {
                Log.d(TAG, "check " + check);

                jun = "JUN";
                Log.d(TAG, "jun " + jun);

                sumIncomeJun = integerCollector.get(i);
                Log.d(TAG, "sumIncomeJun " + sumIncomeJun);

            } //7
            if (check.equals("7")) {
                Log.d(TAG, "check " + check);

                jul = "JUL";
                Log.d(TAG, "jul " + jul);

                sumIncomeJul = integerCollector.get(i);
                Log.d(TAG, "sumIncomeJul " + sumIncomeJul);

            }//8
            if (check.equals("8")) {
                Log.d(TAG, "check " + check);

                aug = "AUG";
                Log.d(TAG, "aug " + aug);

                sumIncomeAug = integerCollector.get(i);
                Log.d(TAG, "incomeAug " + sumIncomeAug);

            } //9
            if (check.equals("9")) {
                Log.d(TAG, "check " + check);

                sep = "SEP";
                Log.d(TAG, "sep " + sep);

                sumIncomeSep = integerCollector.get(i);
                Log.d(TAG, "sumIncomeSep " + sumIncomeSep);

            } //10
            if (check.equals("10")) {
                Log.d(TAG, "check " + check);

                oct = "OCT";
                Log.d(TAG, "oct " + oct);

                sumIncomeOct = integerCollector.get(i);
                Log.d(TAG, "sumIncomeOct " + sumIncomeOct);

            } //11
            if (check.equals("11")) {
                Log.d(TAG, "check " + check);

                nov = "NOV";
                Log.d(TAG, "nov " + nov);

                sumIncomeNov = integerCollector.get(i);
                Log.d(TAG, "sumIncomeNov " + sumIncomeNov);

            }//12
            if (check.equals("12")) {
                Log.d(TAG, "check " + check);

                dec = "DEC";
                Log.d(TAG, "dec " + dec);

                sumIncomeDec = integerCollector.get(i);
                Log.d(TAG, "sumIncomeDec " + sumIncomeDec);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumIncomeYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumIncomeYear(Context context) {
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
                                showIncomeYear(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }

    //////////////////////// Expense Year line chart ////////////////////////

    public String sumExpenseYear(String cust_id) {
        try {
            Log.d(TAG, "start sumExpenseYear");
            httpSumExpenseYear.run(BASE_URL + "/totalExpense.php?cust_id=" + cust_id);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showExpenseYear(String allExpenseYearLine) {
        Log.d(TAG, "allExpenseYearLine " + allExpenseYearLine);
        List<String> items = Arrays.asList(allExpenseYearLine.split("\\s*,\\s*"));


        ArrayList<Float> integerCollector = new ArrayList<Float>();
        int pointer = 0;
        for (String num : items) {
            if (pointer % 2 == 1) {
                integerCollector.add(Float.parseFloat(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for (String num : items) {
            if (pointerString % 2 == 0) {
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for (int i = 0; i <= stringCollector.size() - 1; i++) {

            String check = stringCollector.get(i).trim();
            Log.d(TAG, "check " + check);
            // jan
            if (check.equals("1")) {
                Log.d(TAG, "check " + check);

                jan = "JAN";
                Log.d(TAG, "jan " + jan);

                sumExpenseJan = integerCollector.get(i);
                Log.d(TAG, "sumExpenseJan " + sumExpenseJan);

            } //feb
            if (check.equals("2")) {
                Log.d(TAG, "check " + check);

                feb = "FEB";
                Log.d(TAG, "feb " + feb);

                sumExpenseFeb = integerCollector.get(i);
                Log.d(TAG, "sumExpenseFeb " + sumExpenseFeb);

            } //3
            if (check.equals("3")) {
                Log.d(TAG, "check " + check);

                mar = "MAR";
                Log.d(TAG, "mar " + mar);

                sumExpenseMar = integerCollector.get(i);
                Log.d(TAG, "sumExpenseMar " + sumExpenseMar);

            } //4
            if (check.equals("4")) {
                Log.d(TAG, "check " + check);

                apr = "APR";
                Log.d(TAG, "apr " + apr);

                sumExpenseApr = integerCollector.get(i);
                Log.d(TAG, "sumExpenseApr " + sumExpenseApr);

            } //5
            if (check.equals("5")) {
                Log.d(TAG, "check " + check);

                may = "MAY";
                Log.d(TAG, "may " + may);

                sumExpenseMay = integerCollector.get(i);
                Log.d(TAG, "sumExpenseMay " + sumExpenseMay);

            } //6
            if (check.equals("6")) {
                Log.d(TAG, "check " + check);

                jun = "JUN";
                Log.d(TAG, "jun " + jun);

                sumExpenseJun = integerCollector.get(i);
                Log.d(TAG, "sumExpenseJun " + sumExpenseJun);

            } //7
            if (check.equals("7")) {
                Log.d(TAG, "check " + check);

                jul = "JUL";
                Log.d(TAG, "jul " + jul);

                sumExpenseJul = integerCollector.get(i);
                Log.d(TAG, "sumExpenseJul " + sumExpenseJul);

            }//8
            if (check.equals("8")) {
                Log.d(TAG, "check " + check);

                aug = "AUG";
                Log.d(TAG, "aug " + aug);

                sumExpenseAug = integerCollector.get(i);
                Log.d(TAG, "sumExpenseAug " + sumExpenseAug);

            } //9
            if (check.equals("9")) {
                Log.d(TAG, "check " + check);

                sep = "SEP";
                Log.d(TAG, "sep " + sep);

                sumExpenseSep = integerCollector.get(i);
                Log.d(TAG, "sumExpenseSep " + sumExpenseSep);

            } //10
            if (check.equals("10")) {
                Log.d(TAG, "check " + check);

                oct = "OCT";
                Log.d(TAG, "oct " + oct);

                sumExpenseOct = integerCollector.get(i);
                Log.d(TAG, "sumExpenseOct " + sumExpenseOct);

            } //11
            if (check.equals("11")) {
                Log.d(TAG, "check " + check);

                nov = "NOV";
                Log.d(TAG, "nov " + nov);

                sumExpenseNov = integerCollector.get(i);
                Log.d(TAG, "sumExpenseNov " + sumExpenseNov);

            }//12
            if (check.equals("12")) {
                Log.d(TAG, "check " + check);

                dec = "DEC";
                Log.d(TAG, "dec " + dec);

                sumExpenseDec = integerCollector.get(i);
                Log.d(TAG, "sumExpenseDec " + sumExpenseDec);

            }

        }

    }

    // ** must have for connect DB
    public class getHttpSumExpenseYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumExpenseYear(Context context) {
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
                                showExpenseYear(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }




    //////////////////////// Income Month ////////////////////////

    public String sumIncomeMonthCategory(String cust_id) {
        try {
            Log.d(TAG, "start sumIncomeMonthCategory");
            httpSumIncomeMonthCategory.run(BASE_URL + "/sumIncomeMonthCategory.php?cust_id=" + cust_id);
            //Log.d(TAG,"end sumIncomeMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showIncomeMonthCategory(String allIncomeMonth) {
        Log.d(TAG, "allIncomeMonth " + allIncomeMonth);
        List<String> items = Arrays.asList(allIncomeMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerCollector = new ArrayList<Double>();
        int pointer = 0;
        for (String num : items) {
            if (pointer % 2 == 1) {
                integerCollector.add(Double.parseDouble(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for (String num : items) {
            if (pointerString % 2 == 0) {
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for (int i = 0; i <= stringCollector.size() - 1; i++) {

            String check = stringCollector.get(i).trim();
            //Log.d(TAG, "check " + check);

            if (check.equals("Extra Income")) {
                Log.d(TAG, "check " + check);

                categoryExtraIncomeMonth = "Extra Income";
                Log.d(TAG, "categoryExtraIncomeMonth " + categoryExtraIncomeMonth);

                sumIncomeExtraMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeExtraMonth " + sumIncomeExtraMonth);

            } //family
            if (check.equals("Family and Personal")) {
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalIncomeMonth = "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalIncomeMonth " + categoryFamilyAndPersonalIncomeMonth);

                sumIncomeFamilyAndPersonalMonth = integerCollector.get(i);
                Log.d(TAG, "sumIncomeFamilyAndPersonalMonth " + sumIncomeFamilyAndPersonalMonth);

            } //gift
            if (check.equals("Gift")) {
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
            if (check.equals("Salary")) {
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
                    Log.d(TAG, "onFailure" + e.toString());
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
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    //////////////////////// Expense Month ////////////////////////
    public String sumExpenseMonthCategory(String cust_id) {
        try {
            Log.d(TAG, "start sumExpenseMonthCategory");
            httpSumExpenseMonthCategory.run(BASE_URL + "/sumExpenseMonthCategory.php?cust_id=" + cust_id);
            // Log.d(TAG,"end sumExpenseMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showExpenseMonthCategory(String allExpenseMonth) {
        Log.d(TAG, "allExpenseMonth " + allExpenseMonth);
        List<String> itemsExpense = Arrays.asList(allExpenseMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerExpenseCollector = new ArrayList<Double>();
        int pointerExpense = 0;
        for (String num : itemsExpense) {
            if (pointerExpense % 2 == 1) {
                integerExpenseCollector.add(Double.parseDouble(itemsExpense.get(pointerExpense)));
            }
            pointerExpense++;
        }

        ArrayList<String> stringExpenseCollector = new ArrayList<String>();
        int pointerStringExpense = 0;
        for (String num : itemsExpense) {
            if (pointerStringExpense % 2 == 0) {
                stringExpenseCollector.add(itemsExpense.get(pointerStringExpense));
            }
            pointerStringExpense++;
        }


        for (int i = 0; i <= stringExpenseCollector.size() - 1; i++) {

            String check = stringExpenseCollector.get(i).trim();
            //Log.d(TAG, "check " + check);
            // Bill
            if (check.equals("Bill")) {
                Log.d(TAG, "check " + check);

                categoryBillMonth = "Bill";
                Log.d(TAG, "categoryBillMonth " + categoryBillMonth);

                sumExpenseBillMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseBillMonth " + sumExpenseBillMonth);

            } //Education
            if (check.equals("Education")) {
                Log.d(TAG, "check " + check);

                categoryEducationMonth = "Education";
                Log.d(TAG, "categoryEducationMonth " + categoryEducationMonth);

                sumExpenseEducationMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEducationMonth " + sumExpenseEducationMonth);

            } //Entertainment
            if (check.equals("Entertainment")) {
                Log.d(TAG, "check " + check);

                categoryEntertainmentMonth = "Entertainment";
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
            if (check.equals("Shopping")) {
                Log.d(TAG, "check " + check);

                categoryShoppingMonth = "Shopping";
                Log.d(TAG, "categoryShoppingMonth " + categoryShoppingMonth);

                sumExpenseShoppingMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseShoppingMonth " + sumExpenseShoppingMonth);

            } //Transport
            if (check.equals("Transport")) {
                Log.d(TAG, "check " + check);

                categoryTransportMonth = "Transport";
                Log.d(TAG, "categoryTransportMonth " + categoryTransportMonth);

                sumExpenseTransportMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTransportMonth " + sumExpenseTransportMonth);

            } //Travel
            if (check.equals("Travel")) {
                Log.d(TAG, "check " + check);

                categoryTravelMonth = "Travel";
                Log.d(TAG, "categoryTravelMonth " + categoryTravelMonth);

                sumExpenseTravelMonth = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTravelMonth " + sumExpenseTravelMonth);

            } //Family and Personal
            if (check.equals("Family and Personal")) {
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalMonthExpense = "Family and Personal";
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
            if (check.equals("Saving and Investment")) {
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
                    Log.d(TAG, "onFailure" + e.toString());
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
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    ////////////////////// Income Year ////////////////////////
    public String sumIncomeYearCategory(String cust_id) {
        try {
            Log.d(TAG, "start httpSumIncomeYearCategory");
            httpSumIncomeYearCategory.run(BASE_URL + "/sumIncomeYearCategory.php?cust_id=" + cust_id);
            //Log.d(TAG,"end sumIncomeMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showIncomeYearCategory(String allIncomeYear) {
        Log.d(TAG, "allIncomeYear " + allIncomeYear);
        List<String> items = Arrays.asList(allIncomeYear.split("\\s*,\\s*"));


        ArrayList<Double> integerCollector = new ArrayList<Double>();
        int pointer = 0;
        for (String num : items) {
            if (pointer % 2 == 1) {
                integerCollector.add(Double.parseDouble(items.get(pointer)));
            }
            pointer++;
        }

        ArrayList<String> stringCollector = new ArrayList<String>();
        int pointerString = 0;
        for (String num : items) {
            if (pointerString % 2 == 0) {
                stringCollector.add(items.get(pointerString));
            }
            pointerString++;
        }


        for (int i = 0; i <= stringCollector.size() - 1; i++) {

            String check = stringCollector.get(i).trim();
            //Log.d(TAG, "check " + check);

            if (check.equals("Extra Income")) {
                Log.d(TAG, "check " + check);

                categoryExtraIncomeYear = "Extra Income";
                Log.d(TAG, "categoryExtraIncomeYear " + categoryExtraIncomeYear);

                sumIncomeExtraYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeExtraYear " + sumIncomeExtraYear);

            } //family
            if (check.equals("Family and Personal")) {
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalIncomeYear = "Family and Personal";
                Log.d(TAG, "categoryFamilyAndPersonalIncomeYear " + categoryFamilyAndPersonalIncomeYear);

                sumIncomeFamilyAndPersonalYear = integerCollector.get(i);
                Log.d(TAG, "sumIncomeFamilyAndPersonalYear " + sumIncomeFamilyAndPersonalYear);

            } //gift
            if (check.equals("Gift")) {
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
            if (check.equals("Salary")) {
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
                    Log.d(TAG, "onFailure" + e.toString());
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
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    //////////////////////// Expense Year ////////////////////////
    public String sumExpenseYearCategory(String cust_id) {
        try {
            Log.d(TAG, "start sumExpenseYearCategory");
            httpSumExpenseYearCategory.run(BASE_URL + "/sumExpenseYearCategory.php?cust_id=" + cust_id);
            // Log.d(TAG,"end sumExpenseMonthCategory" + sumIncomeSalaryMonth);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showExpenseYearCategory(String allExpenseMonth) {
        Log.d(TAG, "allExpenseYear " + allExpenseMonth);
        List<String> itemsExpense = Arrays.asList(allExpenseMonth.split("\\s*,\\s*"));


        ArrayList<Double> integerExpenseCollector = new ArrayList<Double>();
        int pointerExpense = 0;
        for (String num : itemsExpense) {
            if (pointerExpense % 2 == 1) {
                integerExpenseCollector.add(Double.parseDouble(itemsExpense.get(pointerExpense)));
            }
            pointerExpense++;
        }

        ArrayList<String> stringExpenseCollector = new ArrayList<String>();
        int pointerStringExpense = 0;
        for (String num : itemsExpense) {
            if (pointerStringExpense % 2 == 0) {
                stringExpenseCollector.add(itemsExpense.get(pointerStringExpense));
            }
            pointerStringExpense++;
        }


        for (int i = 0; i <= stringExpenseCollector.size() - 1; i++) {

            String check = stringExpenseCollector.get(i).trim();
            //Log.d(TAG, "check " + check);
            // Bill
            if (check.equals("Bill")) {
                Log.d(TAG, "check " + check);

                categoryBillYear = "Bill";
                Log.d(TAG, "categoryBillYear " + categoryBillYear);

                sumExpenseBillYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseBillYear " + sumExpenseBillYear);

            } //Education
            if (check.equals("Education")) {
                Log.d(TAG, "check " + check);

                categoryEducationYear = "Education";
                Log.d(TAG, "categoryEducationYear " + categoryEducationYear);

                sumExpenseEducationYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseEducationYear " + sumExpenseEducationYear);

            } //Entertainment
            if (check.equals("Entertainment")) {
                Log.d(TAG, "check " + check);

                categoryEntertainmentYear = "Entertainment";
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
            if (check.equals("Shopping")) {
                Log.d(TAG, "check " + check);

                categoryShoppingYear = "Shopping";
                Log.d(TAG, "categoryShoppingYear " + categoryShoppingYear);

                sumExpenseShoppingYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseShoppingYear " + sumExpenseShoppingYear);

            } //Transport
            if (check.equals("Transport")) {
                Log.d(TAG, "check " + check);

                categoryTransportYear = "Transport";
                Log.d(TAG, "categoryTransportYear " + categoryTransportYear);

                sumExpenseTransportYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTransportYear " + sumExpenseTransportYear);

            } //Travel
            if (check.equals("Travel")) {
                Log.d(TAG, "check " + check);

                categoryTravelYear = "Travel";
                Log.d(TAG, "categoryTravelYear " + categoryTravelYear);

                sumExpenseTravelYear = integerExpenseCollector.get(i);
                Log.d(TAG, "sumExpenseTravelYear " + sumExpenseTravelYear);

            } //Family and Personal
            if (check.equals("Family and Personal")) {
                Log.d(TAG, "check " + check);

                categoryFamilyAndPersonalYearExpense = "Family and Personal";
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
            if (check.equals("Saving and Investment")) {
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
                    Log.d(TAG, "onFailure" + e.toString());
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
                            Log.d(TAG, "onResponse");
                            sumAllBalance();
                        }


                    });
                }
            });
        }

    }


    public void sumAllBalance() {
        // parse value from main activity month for % pie chart
        Double sumIncomeMonth = getArguments().getDouble("sumIncomeMonth");
        Log.d(TAG, "get sumIncomeMonth = " + sumIncomeMonth);

        Double sumExpenseMonth = getArguments().getDouble("sumExpenseMonth");
        Log.d(TAG, "get sumExpenseMonth = " + sumExpenseMonth);


        // parse value from main activity year for % pie chart
        Double sumIncomeYear = getArguments().getDouble("sumIncomeYear");
        Log.d(TAG, "get sumIncomeYear = " + sumIncomeYear);

        Double sumExpenseYear = getArguments().getDouble("sumExpenseYear");
        Log.d(TAG, "get sumExpenseYear = " + sumExpenseYear);


        //income month
        incomeExtraMonthPercent = (float) (sumIncomeExtraMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomeExtraMonthPercent = " + incomeExtraMonthPercent);

        incomeFamilyAndPersonalMonthPercent = (float) (sumIncomeFamilyAndPersonalMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomeFamilyAndPersonalMonthPercent = " + incomeFamilyAndPersonalMonthPercent);

        incomeGiftMonthPercent = (float) (sumIncomeGiftMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomeGiftMonthPercent = " + incomeGiftMonthPercent);

        incomeLoanMonthPercent = (float) (sumIncomeLoanMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);

        incomeSalaryMonthPercent = (float) (sumIncomeSalaryMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);


        //expense month
        expenseBillMonthPercent = (float) (sumExpenseBillMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseBillMonthPercent = " + expenseBillMonthPercent);

        expenseEducationMonthPercent = (float) (sumExpenseEducationMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseEducationMonthPercent = " + expenseEducationMonthPercent);

        expenseEntertainmentMonthPercent = (float) (sumExpenseEntertainmentMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseEntertainmentMonthPercent = " + expenseEntertainmentMonthPercent);

        expenseFoodAndDrinkMonthPercent = (float) (sumExpenseFoodAndDrinkMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseFoodAndDrinkMonthPercent = " + expenseFoodAndDrinkMonthPercent);


        expenseShoppingMonthPercent = (float) (sumExpenseShoppingMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseShoppingMonthPercent = " + expenseShoppingMonthPercent);

        expenseTransportMonthPercent = (float) (sumExpenseTransportMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseTransportMonthPercent = " + expenseTransportMonthPercent);

        expenseTravelMonthPercent = (float) (sumExpenseTravelMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseTravelMonthPercent = " + expenseTravelMonthPercent);

        expenseFamilyAndPersonalMonthPercent = (float) (sumExpenseFamilyAndPersonalMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseFamilyAndPersonalMonthPercent = " + expenseFamilyAndPersonalMonthPercent);

        expenseHealthCareMonthPercent = (float) (sumExpenseHealthCareMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseHealthCareMonthPercent = " + expenseHealthCareMonthPercent);

        expenseSavingAndInvestmentMonthPercent = (float) (sumExpenseSavingAndInvestmentMonth * (100 / sumExpenseMonth));
        Log.d(TAG, "Wallet expenseSavingAndInvestmentMonthPercent = " + expenseSavingAndInvestmentMonthPercent);


        //income year
        incomeExtraYearPercent = (float) (sumIncomeExtraYear * (100 / sumIncomeYear));
        Log.d(TAG, "Wallet incomeExtraYearPercent = " + incomeExtraYearPercent);

        incomeFamilyAndPersonalYearPercent = (float) (sumIncomeFamilyAndPersonalYear * (100 / sumIncomeYear));
        Log.d(TAG, "Wallet incomeFamilyAndPersonalYearPercent = " + incomeFamilyAndPersonalYearPercent);

        incomeGiftYearPercent = (float) (sumIncomeGiftYear * (100 / sumIncomeYear));
        Log.d(TAG, "Wallet incomeGiftYearPercent = " + incomeGiftYearPercent);

        incomeLoanYearPercent = (float) (sumIncomeLoanYear * (100 / sumIncomeYear));
        Log.d(TAG, "Wallet incomeLoanYearPercent = " + incomeLoanYearPercent);

        incomeSalaryYearPercent = (float) (sumIncomeSalaryYear * (100 / sumIncomeYear));
        Log.d(TAG, "Wallet incomeSalaryYearPercent = " + incomeSalaryYearPercent);


        //expense year
        expenseBillYearPercent = (float) (sumExpenseBillYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseBillYearPercent = " + expenseBillYearPercent);

        expenseEducationYearPercent = (float) (sumExpenseEducationYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseEducationYearPercent = " + expenseEducationYearPercent);

        expenseEntertainmentYearPercent = (float) (sumExpenseEntertainmentYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseEntertainmentYearPercent = " + expenseEntertainmentYearPercent);

        expenseFoodAndDrinkYearPercent = (float) (sumExpenseFoodAndDrinkYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseFoodAndDrinkYearPercent = " + expenseFoodAndDrinkYearPercent);


        expenseShoppingYearPercent = (float) (sumExpenseShoppingYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseShoppingYearPercent = " + expenseShoppingYearPercent);

        expenseTransportYearPercent = (float) (sumExpenseTransportYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseTransportYearPercent = " + expenseTransportYearPercent);

        expenseTravelYearPercent = (float) (sumExpenseTravelYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseTravelYearPercent = " + expenseTravelYearPercent);

        expenseFamilyAndPersonalYearPercent = (float) (sumExpenseFamilyAndPersonalYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseFamilyAndPersonalYearPercent = " + expenseFamilyAndPersonalYearPercent);

        expenseHealthCareYearPercent = (float) (sumExpenseHealthCareYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseHealthCareYearPercent = " + expenseHealthCareYearPercent);

        expenseSavingAndInvestmentYearPercent = (float) (sumExpenseSavingAndInvestmentYear * (100 / sumExpenseYear));
        Log.d(TAG, "Wallet expenseSavingAndInvestmentYearPercent = " + expenseSavingAndInvestmentYearPercent);


        // for line graph
        initData();

        ////for pie chart
        initDataIncome();
        initDataExpense();
        initDataIncomeYear();
        initDataExpenseYear();


    }


    // line graph
    private void initData() {
        Log.d(TAG, "initDataLineChart");

        Log.d(TAG, "addDataSet lineChart started");
        final ArrayList<Float> yDataIncome = new ArrayList<>();

        final ArrayList<Float> yDataExpense = new ArrayList<>();


        if(sumIncomeJan > 0){
            yDataIncome.add(sumIncomeJan);

            yDataExpense.add(sumExpenseJan);
            Log.d(TAG, "sumIncomeJan = " + sumIncomeJan);
        }
        if(sumIncomeFeb > 0){
            yDataIncome.add(sumIncomeFeb);

            yDataExpense.add(sumExpenseFeb);
            Log.d(TAG, "sumIncomeFeb = " + sumIncomeFeb);
        }
        if(sumIncomeMar > 0){
            yDataIncome.add(sumIncomeMar);

            yDataExpense.add(sumExpenseMar);
            Log.d(TAG, "sumIncomeMar = " + sumIncomeMar);
        }
        if(sumIncomeApr > 0){
            yDataIncome.add(sumIncomeApr);

            yDataExpense.add(sumExpenseApr);
            Log.d(TAG, "sumIncomeApr = " + sumIncomeApr);
        }
        if(sumIncomeMay > 0){
            yDataIncome.add(sumIncomeMay);

            yDataExpense.add(sumExpenseMay);
            Log.d(TAG, "sumIncomeMay = " + sumIncomeMay);
        }
        if(sumIncomeJun > 0){
            yDataIncome.add(sumIncomeJun);

            yDataExpense.add(sumExpenseJun);
            Log.d(TAG, "sumIncomeJun = " + sumIncomeJun);
        }
        if(sumIncomeJul > 0){
            yDataIncome.add(sumIncomeJul);

            yDataExpense.add(sumExpenseJul);
            Log.d(TAG, "sumIncomeJul = " + sumIncomeJul);
        }
        if(sumIncomeAug > 0){
            yDataIncome.add(sumIncomeAug);

            yDataExpense.add(sumExpenseAug);
            Log.d(TAG, "sumIncomeAug = " + sumIncomeAug);
        }
        if(sumIncomeSep > 0){
            yDataIncome.add(sumIncomeSep);

            yDataExpense.add(sumExpenseSep);
            Log.d(TAG, "sumIncomeSep = " + sumIncomeSep);
        }
        if(sumIncomeOct > 0){
            yDataIncome.add(sumIncomeOct);

            yDataExpense.add(sumExpenseOct);
            Log.d(TAG, "sumIncomeOct = " + sumIncomeOct);
        }
        if(sumIncomeNov > 0){
            yDataIncome.add(sumIncomeNov);

            yDataExpense.add(sumExpenseNov);
            Log.d(TAG, "sumIncomeNov = " + sumIncomeNov);
        }
        if(sumIncomeDec > 0){
            yDataIncome.add(sumIncomeDec);

            yDataExpense.add(sumExpenseDec);
            Log.d(TAG, "sumIncomeDec = " + sumIncomeDec);
        }


        GraphView graph = (GraphView) mView.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> seriesIncome = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJan),
                new DataPoint(2, sumIncomeFeb),
                new DataPoint(3, sumIncomeMar),
                new DataPoint(4, sumIncomeApr),

                new DataPoint(5, sumIncomeMay),
                new DataPoint(6, sumIncomeJun),
                new DataPoint(7, sumIncomeJul),
                new DataPoint(8, sumIncomeAug),
                new DataPoint(9, sumIncomeSep),

                new DataPoint(10, sumIncomeOct),
                new DataPoint(11, sumIncomeNov),
                new DataPoint(12, sumIncomeDec),

        });

        LineGraphSeries<DataPoint> seriesExpense = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJan),
                new DataPoint(2, sumExpenseFeb),
                new DataPoint(3, sumExpenseMar),
                new DataPoint(4, sumExpenseApr),

                new DataPoint(5, sumExpenseMay),
                new DataPoint(6, sumExpenseJun),
                new DataPoint(7, sumExpenseJul),
                new DataPoint(8, sumExpenseAug),
                new DataPoint(9, sumExpenseSep),

                new DataPoint(10, sumExpenseOct),
                new DataPoint(11, sumExpenseNov),
                new DataPoint(12, sumExpenseDec),

        });



        seriesIncome.setColor(getResources().getColor(R.color.buttonColorGreen));
        graph.addSeries(seriesIncome);
        seriesIncome.setDrawDataPoints(true);
        seriesIncome.setDataPointsRadius(10);
     //   seriesIncome.setThickness(8);


        seriesExpense.setColor(getResources().getColor(R.color.buttonColorRed));
        graph.addSeries(seriesExpense);
        seriesExpense.setDrawDataPoints(true);
        seriesExpense.setDataPointsRadius(10);
     //   seriesExpense.setThickness(8);


        // set manual y bounds
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
       // staticLabelsFormatter.setVerticalLabels(new String[] {"0","100", "1k", "2k", "5k", "10k", "20k"});
       // graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        // set manual x bounds
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","Jan","Feb","Mar","Apr","May","Jun"
                ,"Jul","Aug","Sep","Oct","Nov","Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setXAxisBoundsManual(true);

//        graph.getGridLabelRenderer().setVerticalAxisTitle("How much");
//        graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");

        graph.getGridLabelRenderer().setTextSize(40);
        graph.getGridLabelRenderer().setNumHorizontalLabels(12);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);





    }










    // pie chart income month
    private void initDataIncome() {
        Log.d(TAG, "initDataIncome");

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncomeMonth);

        pieChart.setDescription("");
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);




        Log.d(TAG, "addDataSet income month started");

        final ArrayList<Float> yDataIncomeMonth = new ArrayList<>();
        final ArrayList<String> xDataIncomeMonth = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(incomeExtraMonthPercent > 0){
            yDataIncomeMonth.add(incomeExtraMonthPercent);
            xDataIncomeMonth.add("Extra Income");
            colors.add(getResources().getColor(R.color.extraIncome));
        }
        if(incomeFamilyAndPersonalMonthPercent > 0){
            yDataIncomeMonth.add(incomeFamilyAndPersonalMonthPercent);
            xDataIncomeMonth.add("Family and Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if(incomeGiftMonthPercent > 0){
            yDataIncomeMonth.add(incomeGiftMonthPercent);
            xDataIncomeMonth.add("Gift");
            colors.add(getResources().getColor(R.color.gift));
        }
        if(incomeLoanMonthPercent > 0){
            yDataIncomeMonth.add(incomeLoanMonthPercent);
            xDataIncomeMonth.add("Loan");
            colors.add(getResources().getColor(R.color.loan));
        }
        if(incomeSalaryMonthPercent > 0){
            yDataIncomeMonth.add(incomeSalaryMonthPercent );
            xDataIncomeMonth.add("Salary");
            colors.add(getResources().getColor(R.color.salary));
        }

        Log.d(TAG, "Wallet incomeExtraMonthPercent = " + incomeExtraMonthPercent);
        Log.d(TAG, "Wallet incomeFamilyAndPersonalMonthPercent = " + incomeFamilyAndPersonalMonthPercent);
        Log.d(TAG, "Wallet incomeGiftMonthPercent = " + incomeGiftMonthPercent);
        Log.d(TAG, "Wallet incomeLoanMonthPercent = " + incomeLoanMonthPercent);
        Log.d(TAG, "Wallet incomeSalaryMonthPercent = " + incomeSalaryMonthPercent);


        ArrayList<PieEntry> yEntrysIncomeMonth = new ArrayList<>();
        ArrayList<String> xEntrysIncomeMonth = new ArrayList<>();

        for (int i = 0; i < yDataIncomeMonth.size(); i++) {
            yEntrysIncomeMonth.add(new PieEntry(yDataIncomeMonth.get(i), xDataIncomeMonth.get(i)));
            // xEntrysIncomeMonth.add(xDataIncomeMonth.get(i));
        }


        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysIncomeMonth, "" );
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(90.f);


        pieDataSet.setColors(colors);


        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
        legend.getCalculatedLineSizes();
        legend.setEnabled(false);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.buttonColorBlack));
        pieChart.setData(pieData);
        pieChart.invalidate();

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

    // pie chart expense month
    private void initDataExpense() {
        Log.d(TAG, "initDataExpense");
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpenseMonth);

        pieChart.setDescription("");
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);


        Log.d(TAG, "addDataSet expense month started");

        ArrayList<Float> yDataExpenseMonth = new ArrayList<>();
        ArrayList<String> xDataExpenseMonth = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if (expenseBillMonthPercent > 0) {
            yDataExpenseMonth.add(expenseBillMonthPercent);
            xDataExpenseMonth.add("Bill");
            colors.add(getResources().getColor(R.color.bill));
        }
        if (expenseEducationMonthPercent > 0) {
            yDataExpenseMonth.add(expenseEducationMonthPercent);
            xDataExpenseMonth.add("Education");
            colors.add(getResources().getColor(R.color.education));
        }
        if (expenseEntertainmentMonthPercent > 0) {
            yDataExpenseMonth.add(expenseEntertainmentMonthPercent);
            xDataExpenseMonth.add("Entertainment");
            colors.add(getResources().getColor(R.color.entertainment));
        }
        if (expenseFoodAndDrinkMonthPercent > 0) {
            yDataExpenseMonth.add(expenseFoodAndDrinkMonthPercent);
            xDataExpenseMonth.add("Food and Drink");
            colors.add(getResources().getColor(R.color.foodAndDrink));
        }
        if (expenseShoppingMonthPercent > 0) {
            yDataExpenseMonth.add(expenseShoppingMonthPercent);
            xDataExpenseMonth.add("Shopping");
            colors.add(getResources().getColor(R.color.shopping));
        }
        if (expenseTransportMonthPercent > 0) {
            yDataExpenseMonth.add(expenseTransportMonthPercent);
            xDataExpenseMonth.add("Transport");
            colors.add(getResources().getColor(R.color.transport));
        }
        if (expenseTravelMonthPercent > 0) {
            yDataExpenseMonth.add(expenseTravelMonthPercent);
            xDataExpenseMonth.add("Travel");
            colors.add(getResources().getColor(R.color.travel));
        }
        if (expenseFamilyAndPersonalMonthPercent > 0) {
            yDataExpenseMonth.add(expenseFamilyAndPersonalMonthPercent);
            xDataExpenseMonth.add("Family and Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if (expenseHealthCareMonthPercent > 0) {
            yDataExpenseMonth.add(expenseHealthCareMonthPercent);
            xDataExpenseMonth.add("Healthcare");
            colors.add(getResources().getColor(R.color.health));
        }
        if (expenseSavingAndInvestmentMonthPercent > 0) {
            yDataExpenseMonth.add(expenseSavingAndInvestmentMonthPercent);
            xDataExpenseMonth.add("Saving and Investment");
            colors.add(getResources().getColor(R.color.savingAndInvestment));
        }

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


        ArrayList<PieEntry> yEntrysExpenseMonth = new ArrayList<>();
        ArrayList<String> xEntrysExpenseMonth = new ArrayList<>();

        for (int i = 0; i < yDataExpenseMonth.size(); i++) {
            yEntrysExpenseMonth.add(new PieEntry(yDataExpenseMonth.get(i), xDataExpenseMonth.get(i)));
            //xEntrysExpenseMonth.add(xDataExpenseMonth.get(i));
        }


        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysExpenseMonth, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(90.f);


        // add color to dataset
        pieDataSet.setColors(colors);


        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
        legend.getCalculatedLineSizes();
        legend.setEnabled(false);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.buttonColorBlack));
        pieChart.setData(pieData);
        pieChart.invalidate();
    }




    // pie chart income year
    private void initDataIncomeYear() {

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncomeYear);


        pieChart.setDescription("");
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);



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

        Log.d(TAG, "addDataSet income year started");

        final ArrayList<Float> yDataIncomeYear = new ArrayList<>();
        final ArrayList<String> xDataIncomeYear = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(incomeExtraYearPercent > 0){
            yDataIncomeYear.add(incomeExtraYearPercent);
            xDataIncomeYear.add("Extra Income");
            colors.add(getResources().getColor(R.color.extraIncome));

        }
        if(incomeFamilyAndPersonalYearPercent > 0){
            yDataIncomeYear.add(incomeFamilyAndPersonalYearPercent);
            xDataIncomeYear.add("Family and Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if(incomeGiftYearPercent > 0){
            yDataIncomeYear.add(incomeGiftYearPercent);
            xDataIncomeYear.add("Gift");
            colors.add(getResources().getColor(R.color.gift));
        }
        if(incomeLoanYearPercent > 0){
            yDataIncomeYear.add(incomeLoanYearPercent);
            xDataIncomeYear.add("Loan");
            colors.add(getResources().getColor(R.color.loan));
        }
        if(incomeSalaryYearPercent > 0){
            yDataIncomeYear.add(incomeSalaryYearPercent );
            xDataIncomeYear.add("Salary");
            colors.add(getResources().getColor(R.color.salary));
        }

        Log.d(TAG, "Wallet incomeExtraYearPercent = " + incomeExtraYearPercent);
        Log.d(TAG, "Wallet incomeFamilyAndPersonalYearPercent = " + incomeFamilyAndPersonalYearPercent);
        Log.d(TAG, "Wallet incomeGiftYearPercent = " + incomeGiftYearPercent);
        Log.d(TAG, "Wallet incomeLoanYearPercent = " + incomeLoanYearPercent);
        Log.d(TAG, "Wallet incomeSalaryYearPercent = " + incomeSalaryYearPercent);


        ArrayList<PieEntry> yEntrysIncomeYear = new ArrayList<>();
        ArrayList<String> xEntrysIncomeYear = new ArrayList<>();

        for (int i = 0; i < yDataIncomeYear.size(); i++) {
            yEntrysIncomeYear.add(new PieEntry(yDataIncomeYear.get(i), xDataIncomeYear.get(i)));
            // xEntrysIncomeYear.add(xDataIncomeYear.get(i));
        }


        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysIncomeYear, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(90.f);



        // add color to dataset
        pieDataSet.setColors(colors);


        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
        legend.getCalculatedLineSizes();
        legend.setEnabled(false);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.buttonColorBlack));
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    // pie chart expense year
    private void initDataExpenseYear() {
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpenseYear);

        pieChart.setDescription("");
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);

        Log.d(TAG, "addDataSet expense year started");

        ArrayList<Float> yDataExpenseYear = new ArrayList<>();
        ArrayList<String> xDataExpenseYear = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(expenseBillYearPercent > 0) {
            yDataExpenseYear.add(expenseBillYearPercent) ;
            xDataExpenseYear.add("Bill");
            colors.add(getResources().getColor(R.color.bill));
        }
        if(expenseEducationYearPercent > 0) {
            yDataExpenseYear.add(expenseEducationYearPercent);
            xDataExpenseYear.add("Education");
            colors.add(getResources().getColor(R.color.education));
        }
        if(expenseEntertainmentYearPercent > 0) {
            yDataExpenseYear.add(expenseEntertainmentYearPercent);
            xDataExpenseYear.add("Entertainment");
            colors.add(getResources().getColor(R.color.entertainment));
        }
        if(expenseFoodAndDrinkYearPercent > 0) {
            yDataExpenseYear.add(expenseFoodAndDrinkYearPercent);
            xDataExpenseYear.add("Food and Drink");
            colors.add(getResources().getColor(R.color.foodAndDrink));
        }
        if(expenseShoppingYearPercent > 0) {
            yDataExpenseYear.add(expenseShoppingYearPercent);
            xDataExpenseYear.add("Shopping");
            colors.add(getResources().getColor(R.color.shopping));
        }
        if(expenseTransportYearPercent > 0) {
            yDataExpenseYear.add(expenseTransportYearPercent);
            xDataExpenseYear.add("Transport");
            colors.add(getResources().getColor(R.color.transport));
        }
        if(expenseTravelYearPercent > 0) {
            yDataExpenseYear.add(expenseTravelYearPercent);
            xDataExpenseYear.add("Travel");
            colors.add(getResources().getColor(R.color.travel));
        }
        if(expenseFamilyAndPersonalYearPercent > 0) {
            yDataExpenseYear.add(expenseFamilyAndPersonalYearPercent);
            xDataExpenseYear.add("Family and Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if(expenseHealthCareYearPercent > 0) {
            yDataExpenseYear.add(expenseHealthCareYearPercent);
            xDataExpenseYear.add("Healthcare");
            colors.add(getResources().getColor(R.color.health));
        }
        if(expenseSavingAndInvestmentYearPercent > 0) {
            yDataExpenseYear.add(expenseSavingAndInvestmentYearPercent);
            xDataExpenseYear.add("Saving and Investment");
            colors.add(getResources().getColor(R.color.savingAndInvestment));
        }

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


        ArrayList<PieEntry> yEntrysExpenseYear = new ArrayList<>();
        ArrayList<String> xEntrysExpenseYear = new ArrayList<>();

        for (int i = 0; i < yDataExpenseYear.size(); i++) {
            yEntrysExpenseYear.add(new PieEntry(yDataExpenseYear.get(i), xDataExpenseYear.get(i)));
            // xEntrysExpenseYear.add(xDataExpenseYear.get(i));
        }


        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrysExpenseYear, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.5f);
        pieDataSet.setValueLinePart1OffsetPercentage(90.f);


        // add color to dataset
        pieDataSet.setColors(colors);

        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //  legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
        legend.getCalculatedLineSizes();
        legend.setEnabled(false);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);

        int colorBlack = Color.parseColor("#000000");
        pieChart.setEntryLabelColor(colorBlack);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }





}

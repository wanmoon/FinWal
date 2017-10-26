package com.wanmoon.finwal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
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
import java.text.NumberFormat;
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


    //for pie chart
    private final String TAG = "Dashboard";
    PieChart pieChart;
    private View mView;

    //income
    private TextView textViewIncomeJan;
    private TextView textViewIncomeFeb;
    private TextView textViewIncomeMar;
    private TextView textViewIncomeApr;
    private TextView textViewIncomeMay;
    private TextView textViewIncomeJun;
    private TextView textViewIncomeJul;
    private TextView textViewIncomeAug;
    private TextView textViewIncomeSep;
    private TextView textViewIncomeOct;
    private TextView textViewIncomeNov;
    private TextView textViewIncomeDec;
    //expense
    private TextView textViewExpenseJan;
    private TextView textViewExpenseFeb;
    private TextView textViewExpenseMar;
    private TextView textViewExpenseApr;
    private TextView textViewExpenseMay;
    private TextView textViewExpenseJun;
    private TextView textViewExpenseJul;
    private TextView textViewExpenseAug;
    private TextView textViewExpenseSep;
    private TextView textViewExpenseOct;
    private TextView textViewExpenseNov;
    private TextView textViewExpenseDec;





    //line chart
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



    //line chart category
    private float sumIncomeJanGift;
    private float sumIncomeFebGift;
    private float sumIncomeMarGift;
    private float sumIncomeAprGift;
    private float sumIncomeMayGift;
    private float sumIncomeJunGift;
    private float sumIncomeJulGift;
    private float sumIncomeAugGift;
    private float sumIncomeSepGift;
    private float sumIncomeOctGift;
    private float sumIncomeNovGift;
    private float sumIncomeDecGift;

    private float sumIncomeJanSalary;
    private float sumIncomeFebSalary;
    private float sumIncomeMarSalary;
    private float sumIncomeAprSalary;
    private float sumIncomeMaySalary;
    private float sumIncomeJunSalary;
    private float sumIncomeJulSalary;
    private float sumIncomeAugSalary;
    private float sumIncomeSepSalary;
    private float sumIncomeOctSalary;
    private float sumIncomeNovSalary;
    private float sumIncomeDecSalary;

    private float sumIncomeJanExtra;
    private float sumIncomeFebExtra;
    private float sumIncomeMarExtra;
    private float sumIncomeAprExtra;
    private float sumIncomeMayExtra;
    private float sumIncomeJunExtra;
    private float sumIncomeJulExtra;
    private float sumIncomeAugExtra;
    private float sumIncomeSepExtra;
    private float sumIncomeOctExtra;
    private float sumIncomeNovExtra;
    private float sumIncomeDecExtra;

    private float sumIncomeJanLoan;
    private float sumIncomeFebLoan;
    private float sumIncomeMarLoan;
    private float sumIncomeAprLoan;
    private float sumIncomeMayLoan;
    private float sumIncomeJunLoan;
    private float sumIncomeJulLoan;
    private float sumIncomeAugLoan;
    private float sumIncomeSepLoan;
    private float sumIncomeOctLoan;
    private float sumIncomeNovLoan;
    private float sumIncomeDecLoan;

    private float sumIncomeJanFamily;
    private float sumIncomeFebFamily;
    private float sumIncomeMarFamily;
    private float sumIncomeAprFamily;
    private float sumIncomeMayFamily;
    private float sumIncomeJunFamily;
    private float sumIncomeJulFamily;
    private float sumIncomeAugFamily;
    private float sumIncomeSepFamily;
    private float sumIncomeOctFamily;
    private float sumIncomeNovFamily;
    private float sumIncomeDecFamily;


    private float sumExpenseJanBill;
    private float sumExpenseFebBill;
    private float sumExpenseMarBill;
    private float sumExpenseAprBill;
    private float sumExpenseMayBill;
    private float sumExpenseJunBill;
    private float sumExpenseJulBill;
    private float sumExpenseAugBill;
    private float sumExpenseSepBill;
    private float sumExpenseOctBill;
    private float sumExpenseNovBill;
    private float sumExpenseDecBill;

    private float sumExpenseJanEducation;
    private float sumExpenseFebEducation;
    private float sumExpenseMarEducation;
    private float sumExpenseAprEducation;
    private float sumExpenseMayEducation;
    private float sumExpenseJunEducation;
    private float sumExpenseJulEducation;
    private float sumExpenseAugEducation;
    private float sumExpenseSepEducation;
    private float sumExpenseOctEducation;
    private float sumExpenseNovEducation;
    private float sumExpenseDecEducation;

    private float sumExpenseJanEntertainment;
    private float sumExpenseFebEntertainment;
    private float sumExpenseMarEntertainment;
    private float sumExpenseAprEntertainment;
    private float sumExpenseMayEntertainment;
    private float sumExpenseJunEntertainment;
    private float sumExpenseJulEntertainment;
    private float sumExpenseAugEntertainment;
    private float sumExpenseSepEntertainment;
    private float sumExpenseOctEntertainment;
    private float sumExpenseNovEntertainment;
    private float sumExpenseDecEntertainment;

    private float sumExpenseJanFood;
    private float sumExpenseFebFood;
    private float sumExpenseMarFood;
    private float sumExpenseAprFood;
    private float sumExpenseMayFood;
    private float sumExpenseJunFood;
    private float sumExpenseJulFood;
    private float sumExpenseAugFood;
    private float sumExpenseSepFood;
    private float sumExpenseOctFood;
    private float sumExpenseNovFood;
    private float sumExpenseDecFood;

    private float sumExpenseJanShopping;
    private float sumExpenseFebShopping;
    private float sumExpenseMarShopping;
    private float sumExpenseAprShopping;
    private float sumExpenseMayShopping;
    private float sumExpenseJunShopping;
    private float sumExpenseJulShopping;
    private float sumExpenseAugShopping;
    private float sumExpenseSepShopping;
    private float sumExpenseOctShopping;
    private float sumExpenseNovShopping;
    private float sumExpenseDecShopping;

    private float sumExpenseJanTransport;
    private float sumExpenseFebTransport;
    private float sumExpenseMarTransport;
    private float sumExpenseAprTransport;
    private float sumExpenseMayTransport;
    private float sumExpenseJunTransport;
    private float sumExpenseJulTransport;
    private float sumExpenseAugTransport;
    private float sumExpenseSepTransport;
    private float sumExpenseOctTransport;
    private float sumExpenseNovTransport;
    private float sumExpenseDecTransport;

    private float sumExpenseJanTravel;
    private float sumExpenseFebTravel;
    private float sumExpenseMarTravel;
    private float sumExpenseAprTravel;
    private float sumExpenseMayTravel;
    private float sumExpenseJunTravel;
    private float sumExpenseJulTravel;
    private float sumExpenseAugTravel;
    private float sumExpenseSepTravel;
    private float sumExpenseOctTravel;
    private float sumExpenseNovTravel;
    private float sumExpenseDecTravel;

    private float sumExpenseJanFamilyEx;
    private float sumExpenseFebFamilyEx;
    private float sumExpenseMarFamilyEx;
    private float sumExpenseAprFamilyEx;
    private float sumExpenseMayFamilyEx;
    private float sumExpenseJunFamilyEx;
    private float sumExpenseJulFamilyEx;
    private float sumExpenseAugFamilyEx;
    private float sumExpenseSepFamilyEx;
    private float sumExpenseOctFamilyEx;
    private float sumExpenseNovFamilyEx;
    private float sumExpenseDecFamilyEx;

    private float sumExpenseJanHealthcare;
    private float sumExpenseFebHealthcare;
    private float sumExpenseMarHealthcare;
    private float sumExpenseAprHealthcare;
    private float sumExpenseMayHealthcare;
    private float sumExpenseJunHealthcare;
    private float sumExpenseJulHealthcare;
    private float sumExpenseAugHealthcare;
    private float sumExpenseSepHealthcare;
    private float sumExpenseOctHealthcare;
    private float sumExpenseNovHealthcare;
    private float sumExpenseDecHealthcare;

    private float sumExpenseJanSaving;
    private float sumExpenseFebSaving;
    private float sumExpenseMarSaving;
    private float sumExpenseAprSaving;
    private float sumExpenseMaySaving;
    private float sumExpenseJunSaving;
    private float sumExpenseJulSaving;
    private float sumExpenseAugSaving;
    private float sumExpenseSepSaving;
    private float sumExpenseOctSaving;
    private float sumExpenseNovSaving;
    private float sumExpenseDecSaving;



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



    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;

    getHttpSumLineYear httpSumLineYear;


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


        //income
        textViewIncomeJan = (TextView) rootView.findViewById(R.id.textViewIncomeJan);
        textViewIncomeFeb = (TextView) rootView.findViewById(R.id.textViewIncomeFeb);
        textViewIncomeMar = (TextView) rootView.findViewById(R.id.textViewIncomeMar);
        textViewIncomeApr = (TextView) rootView.findViewById(R.id.textViewIncomeApr);
        textViewIncomeMay = (TextView) rootView.findViewById(R.id.textViewIncomeMay);
        textViewIncomeJun = (TextView) rootView.findViewById(R.id.textViewIncomeJun);
        textViewIncomeJul = (TextView) rootView.findViewById(R.id.textViewIncomeJul);
        textViewIncomeAug = (TextView) rootView.findViewById(R.id.textViewIncomeAug);
        textViewIncomeSep = (TextView) rootView.findViewById(R.id.textViewIncomeSep);
        textViewIncomeOct = (TextView) rootView.findViewById(R.id.textViewIncomeOct);
        textViewIncomeNov = (TextView) rootView.findViewById(R.id.textViewIncomeNov);
        textViewIncomeDec = (TextView) rootView.findViewById(R.id.textViewIncomeDec);
        //expense
        textViewExpenseJan = (TextView) rootView.findViewById(R.id.textViewExpenseJan);
        textViewExpenseFeb = (TextView) rootView.findViewById(R.id.textViewExpenseFeb);
        textViewExpenseMar = (TextView) rootView.findViewById(R.id.textViewExpenseMar);
        textViewExpenseApr = (TextView) rootView.findViewById(R.id.textViewExpenseApr);
        textViewExpenseMay = (TextView) rootView.findViewById(R.id.textViewExpenseMay);
        textViewExpenseJun = (TextView) rootView.findViewById(R.id.textViewExpenseJun);
        textViewExpenseJul = (TextView) rootView.findViewById(R.id.textViewExpenseJul);
        textViewExpenseAug = (TextView) rootView.findViewById(R.id.textViewExpenseAug);
        textViewExpenseSep = (TextView) rootView.findViewById(R.id.textViewExpenseSep);
        textViewExpenseOct = (TextView) rootView.findViewById(R.id.textViewExpenseOct);
        textViewExpenseNov = (TextView) rootView.findViewById(R.id.textViewExpenseNov);
        textViewExpenseDec = (TextView) rootView.findViewById(R.id.textViewExpenseDec);



        httpSumLineYear = new getHttpSumLineYear(getContext());


        httpSumIncomeMonthCategory = new getHttpSumIncomeMonthCategory(getContext());
        httpSumExpenseMonthCategory = new getHttpSumExpenseMonthCategory(getContext());


        httpSumIncomeYearCategory = new getHttpSumIncomeYearCategory(getContext());
        httpSumExpenseYearCategory = new getHttpSumExpenseYearCategory(getContext());


        //transaction button : click then have popup
        buttonSalary = (Button) rootView.findViewById(R.id.buttonSalary);
        buttonSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeCate = new Dialog(getContext());
                incomeCate.getWindow();
                incomeCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                incomeCate.setContentView(R.layout.detail_linechart);
                incomeCate.setCancelable(true);
                incomeCate.show();


            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sumLineYear(cust_id);

        sumIncomeMonthCategory(cust_id);
        sumExpenseMonthCategory(cust_id);

        sumIncomeYearCategory(cust_id);
        sumExpenseYearCategory(cust_id);


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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_done) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public String sumLineYear(String cust_id) {
        try {
            Log.d(TAG, "start sumLineYear");
            httpSumLineYear.run(BASE_URL + "/sumLineYear.php?cust_id=" + cust_id);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public void showLineYear(String allLineYear) {
        Log.d(TAG, "allLineYear " + allLineYear);
        List<String> itemsCate = Arrays.asList(allLineYear.split("\\s*-\\s*"));

        ArrayList<String> stringCate = new ArrayList();
        int pointerCate = 0;
        for (String num : itemsCate) {
            stringCate.add(itemsCate.get(pointerCate));
            Log.d(TAG, "stringCate " + stringCate);

            String stringCateCheck = itemsCate.get(pointerCate).trim();
            Log.d(TAG, "stringCateCheck " + stringCateCheck);

            pointerCate++;

            List<String> itemsCate1 = Arrays.asList(stringCateCheck.split("\\s*,\\s*"));
            Log.d(TAG, "itemsCate1 " + itemsCate1);
            int pointerCate1 = 0;

            //income//
            if (itemsCate1.get(0).equals("Income")) {
                Log.d(TAG, "itemsCateGift" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {
                    String check = stringCollector.get(i).trim();
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJan " + sumIncomeJan);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFeb = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFeb " + sumIncomeFeb);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMar = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMar " + sumIncomeMar);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeApr = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeApr " + sumIncomeApr);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMay = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMay " + sumIncomeMay);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJun = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJun " + sumIncomeJun);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJul = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJul " + sumIncomeJul);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAug = integerCollector.get(i);
                        Log.d(TAG, "incomeAug " + sumIncomeAug);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeSep = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSep " + sumIncomeSep);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOct = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOct " + sumIncomeOct);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNov = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNov " + sumIncomeNov);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDec = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDec " + sumIncomeDec);

                    }
                }

            }

            //expense//
            if (itemsCate1.get(0).equals("Expense")) {
                Log.d(TAG, "itemsCateGift" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJan = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJan " + sumExpenseJan);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFeb = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFeb " + sumExpenseFeb);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMar = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMar " + sumExpenseMar);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseApr = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseApr " + sumExpenseApr);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMay = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMay " + sumExpenseMay);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJun = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJun " + sumExpenseJun);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJul = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJul " + sumExpenseJul);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAug = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAug " + sumExpenseAug);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSep = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSep " + sumExpenseSep);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOct = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOct " + sumExpenseOct);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNov = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNov " + sumExpenseNov);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDec = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDec " + sumExpenseDec);

                    }

                }

            }



            if (itemsCate1.get(0).equals("Gift")) {
                Log.d(TAG, "itemsCateGift" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    //jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJanGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJanGift " + sumIncomeJanGift);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFebGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFebGift " + sumIncomeFebGift);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMarGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMarGift " + sumIncomeMarGift);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAprGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeAprGift " + sumIncomeAprGift);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMayGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMayGift " + sumIncomeMayGift);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJunGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJunGift " + sumIncomeJunGift);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJulGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJulGift " + sumIncomeJulGift);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAugGift = integerCollector.get(i);
                        Log.d(TAG, "incomeAugGift " + sumIncomeAugGift);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);


                        sumIncomeSepGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSepGift " + sumIncomeSepGift);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOctGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOctGift " + sumIncomeOctGift);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNovGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNovGift " + sumIncomeNovGift);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDecGift = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDecGift " + sumIncomeDecGift);

                    }

                }

            }
            if (itemsCate1.get(0).equals("Salary")) {
                Log.d(TAG, "itemsCateSalary" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJanSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJanSalary " + sumIncomeJanSalary);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFebSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFebSalary " + sumIncomeFebSalary);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMarSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMarSalary " + sumIncomeMarSalary);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAprSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeAprSalary " + sumIncomeAprSalary);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMaySalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMaySalary " + sumIncomeMaySalary);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJunSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJunSalary " + sumIncomeJunSalary);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJulSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJulSalary " + sumIncomeJulSalary);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAugSalary = integerCollector.get(i);
                        Log.d(TAG, "incomeAugSalary " + sumIncomeAugSalary);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeSepSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSepSalary " + sumIncomeSepSalary);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOctSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOctSalary " + sumIncomeOctSalary);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNovSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNovSalary " + sumIncomeNovSalary);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDecSalary = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDecSalary " + sumIncomeDecSalary);

                    }

                }

            }
            if (itemsCate1.get(0).equals("Extra income")) {
                Log.d(TAG, "itemsCateExtra income" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJanExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJanExtra " + sumIncomeJanExtra);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFebExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFebExtra " + sumIncomeFebExtra);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMarExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMarExtra " + sumIncomeMarExtra);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAprExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeAprExtra " + sumIncomeAprExtra);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMayExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMayExtra " + sumIncomeMayExtra);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);


                        sumIncomeJunExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJunExtra " + sumIncomeJunExtra);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJulExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJulExtra " + sumIncomeJulExtra);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAugExtra = integerCollector.get(i);
                        Log.d(TAG, "incomeAugExtra " + sumIncomeAugExtra);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeSepExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSepExtra " + sumIncomeSepExtra);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOctExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOctExtra " + sumIncomeOctExtra);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNovExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNovExtra " + sumIncomeNovExtra);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDecExtra = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDecExtra " + sumIncomeDecExtra);

                    }

                }

            }
            if (itemsCate1.get(0).equals("Loan")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJanLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJanLoan " + sumIncomeJanLoan);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFebLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFebLoan " + sumIncomeFebLoan);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMarLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMarLoan " + sumIncomeMarLoan);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAprLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeAprLoan " + sumIncomeAprLoan);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMayLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMayLoan " + sumIncomeMayLoan);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJunLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJunLoan " + sumIncomeJunLoan);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJulLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJulLoan " + sumIncomeJulLoan);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAugLoan = integerCollector.get(i);
                        Log.d(TAG, "incomeAugLoan " + sumIncomeAugLoan);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeSepLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSepLoan " + sumIncomeSepLoan);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOctLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOctLoan" + sumIncomeOctLoan);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNovLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNovLoan " + sumIncomeNovLoan);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDecLoan = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDecLoan " + sumIncomeDecLoan);

                    }

                }

            }
            if (itemsCate1.get(0).equals("Family and Personal")) {
                Log.d(TAG, "itemsCateFamily and Personal" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }


                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJanFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJanFamily " + sumIncomeJanFamily);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeFebFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeFebFamily " + sumIncomeFebFamily);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMarFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMarFamily " + sumIncomeMarFamily);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAprFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeAprFamily " + sumIncomeAprFamily);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeMayFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeMayFamily " + sumIncomeMayFamily);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJunFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJunFamily " + sumIncomeJunFamily);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeJulFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeJulFamily " + sumIncomeJulFamily);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeAugFamily = integerCollector.get(i);
                        Log.d(TAG, "incomeAugFamily " + sumIncomeAugFamily);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeSepFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeSepFamily " + sumIncomeSepFamily);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeOctFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeOctFamily " + sumIncomeOctFamily);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeNovFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeNovFamily " + sumIncomeNovFamily);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumIncomeDecFamily = integerCollector.get(i);
                        Log.d(TAG, "sumIncomeDecFamily " + sumIncomeDecFamily);

                    }
                }
            }



///////////////////////////////Expense////////////////////////////
            if (itemsCate1.get(0).equals("Bill")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanBill " + sumExpenseJanBill);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebBill " + sumExpenseFebBill);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarBill " + sumExpenseMarBill);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprBill " + sumExpenseAprBill);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayBill " + sumExpenseMayBill);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunBill " + sumExpenseJunBill);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulBill " + sumExpenseJulBill);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugBill " + sumExpenseAugBill);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepBill " + sumExpenseSepBill);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctBill " + sumExpenseOctBill);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNov = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovBill " + sumExpenseNovBill);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecBill = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecBill " + sumExpenseDecBill);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Education")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for (int i = 1; i < itemsCate1.size(); i++) {
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanEducation " + sumExpenseJanEducation);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebEducation " + sumExpenseFebEducation);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarEducation " + sumExpenseMarEducation);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprEducation " + sumExpenseAprEducation);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayEducation " + sumExpenseMayEducation);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunEducation " + sumExpenseJunEducation);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulEducation " + sumExpenseJulEducation);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugEducation " + sumExpenseAugEducation);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepEducation " + sumExpenseSepEducation);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctEducation " + sumExpenseOctEducation);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovEducation " + sumExpenseNovEducation);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecEducation = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecEducation " + sumExpenseDecEducation);

                    }
                }
            }

            if (itemsCate1.get(0).equals("Entertainment")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanEntertainment " + sumExpenseJanEntertainment);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebEntertainment " + sumExpenseFebEntertainment);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarEntertainment " + sumExpenseMarEntertainment);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprEntertainment " + sumExpenseAprEntertainment);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayEntertainment " + sumExpenseMayEntertainment);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunEntertainment " + sumExpenseJunEntertainment);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulEntertainment " + sumExpenseJulEntertainment);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugEntertainment " + sumExpenseAugEntertainment);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepEntertainment " + sumExpenseSepEntertainment);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctEntertainment " + sumExpenseOctEntertainment);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovEntertainment " + sumExpenseNovEntertainment);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecEntertainment = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecEntertainment " + sumExpenseDecEntertainment);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Food")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanFood " + sumExpenseJanFood);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebFood " + sumExpenseFebFood);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);


                        sumExpenseMarFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarFood " + sumExpenseMarFood);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprFood " + sumExpenseAprFood);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayFood " + sumExpenseMayFood);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunFood " + sumExpenseJunFood);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulFood " + sumExpenseJulFood);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugFood " + sumExpenseAugFood);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepFood " + sumExpenseSepFood);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctFood " + sumExpenseOctFood);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovFood " + sumExpenseNovFood);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecFood = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecFood " + sumExpenseDecFood);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Shopping")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanShopping " + sumExpenseJanShopping);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebShopping " + sumExpenseFebShopping);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarShopping " + sumExpenseMarShopping);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprShopping " + sumExpenseAprShopping);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayShopping " + sumExpenseMayShopping);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunShopping " + sumExpenseJunShopping);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulShopping " + sumExpenseJulShopping);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugShopping " + sumExpenseAugShopping);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepShopping " + sumExpenseSepShopping);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctShopping " + sumExpenseOctShopping);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovShopping " + sumExpenseNovShopping);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecShopping = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecShopping " + sumExpenseDecShopping);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Transport")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanTransport " + sumExpenseJanTransport);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebTransport " + sumExpenseFebTransport);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarTransport " + sumExpenseMarTransport);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprTransport " + sumExpenseAprTransport);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayTransport " + sumExpenseMayTransport);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunTransport " + sumExpenseJunTransport);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulTransport " + sumExpenseJulTransport);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugTransport " + sumExpenseAugTransport);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepTransport " + sumExpenseSepTransport);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctTransport " + sumExpenseOctTransport);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovTransport" + sumExpenseNovTransport);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecTransport = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecTransport " + sumExpenseDecTransport);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Travel")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanTravel " + sumExpenseJanTravel);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebTravel " + sumExpenseFebTravel);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarTravel " + sumExpenseMarTravel);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprTravel " + sumExpenseAprTravel);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayTravel " + sumExpenseMayTravel);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunTravel " + sumExpenseJunTravel);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulTravel " + sumExpenseJulTravel);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugTravel " + sumExpenseAugTravel);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepTravel= integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepTravel " + sumExpenseSepTravel);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctTravel " + sumExpenseOctTravel);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovTravel " + sumExpenseNovTravel);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecTravel = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecTravel " + sumExpenseDecTravel);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Family and Personal expens")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanFamilyEx " + sumExpenseJanFamilyEx);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebFamilyEx " + sumExpenseFebFamilyEx);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarFamilyEx " + sumExpenseMarFamilyEx);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprFamilyEx " + sumExpenseAprFamilyEx);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayFamilyEx " + sumExpenseMayFamilyEx);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunFamilyEx " + sumExpenseJunFamilyEx);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulFamilyEx " + sumExpenseJulFamilyEx);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugFamilyEx " + sumExpenseAugFamilyEx);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepFamilyEx " + sumExpenseSepFamilyEx);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctFamilyEx " + sumExpenseOctFamilyEx);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovFamilyEx " + sumExpenseNovFamilyEx);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecFamilyEx = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecFamilyEx " + sumExpenseDecFamilyEx);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Healthcare")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanHealthcare " + sumExpenseJanHealthcare);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebHealthcare " + sumExpenseFebHealthcare);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarHealthcare " + sumExpenseMarHealthcare);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprHealthcare " + sumExpenseAprHealthcare);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMayHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMayHealthcare " + sumExpenseMayHealthcare);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunHealthcare " + sumExpenseJunHealthcare);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulHealthcare " + sumExpenseJulHealthcare);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugHealthcare " + sumExpenseAugHealthcare);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepHealthcare " + sumExpenseSepHealthcare);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctHealthcare " + sumExpenseOctHealthcare);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovHealthcare " + sumExpenseNovHealthcare);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecHealthcare = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecHealthcare " + sumExpenseDecHealthcare);

                    }
                }

            }

            if (itemsCate1.get(0).equals("Saving and Investment")) {
                Log.d(TAG, "itemsCateLoan" + itemsCate1.get(pointerCate1));

                ArrayList<String> stringCateData = new ArrayList();
                ArrayList<String> stringCollector = new ArrayList();
                int pointerString = 0;
                ArrayList<Float> integerCollector = new ArrayList();
                int pointer = 0;
                Log.d(TAG, "stringCateData " + stringCateData);
                for(int i =1 ; i<itemsCate1.size() ;i++){
                    stringCateData.add(itemsCate1.get(i).trim());
                    //month
                    if (pointerString % 2 == 0) {
                        stringCollector.add(stringCateData.get(pointerString));
                    }
                    pointerString++;
                    Log.d(TAG, "stringCollector " + stringCollector);

                    //money
                    if (pointer % 2 == 1) {
                        integerCollector.add(Float.parseFloat(stringCateData.get(pointer)));
                    }
                    pointer++;
                    Log.d(TAG, "integerCollector " + integerCollector);

                }

                for (int i = 0; i <= stringCollector.size() - 1; i++) {

                    String check = stringCollector.get(i).trim();
                    Log.d(TAG, "check " + check);
                    // jan
                    if (check.equals("1")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJanSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJanSaving " + sumExpenseJanSaving);

                    } //feb
                    if (check.equals("2")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseFebSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseFebSaving " + sumExpenseFebSaving);

                    } //3
                    if (check.equals("3")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMarSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMarSaving " + sumExpenseMarSaving);

                    } //4
                    if (check.equals("4")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAprSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAprSaving " + sumExpenseAprSaving);

                    } //5
                    if (check.equals("5")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseMaySaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseMaySaving " + sumExpenseMaySaving);

                    } //6
                    if (check.equals("6")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJunSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJunSaving " + sumExpenseJunSaving);

                    } //7
                    if (check.equals("7")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseJulSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseJulSaving " + sumExpenseJulSaving);

                    }//8
                    if (check.equals("8")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseAugSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseAugSaving " + sumExpenseAugSaving);

                    } //9
                    if (check.equals("9")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseSepSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseSepSaving " + sumExpenseSepSaving);

                    } //10
                    if (check.equals("10")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseOctSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseOctSaving " + sumExpenseOctSaving);

                    } //11
                    if (check.equals("11")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseNovSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseNovSaving " + sumExpenseNovSaving);

                    }//12
                    if (check.equals("12")) {
                        Log.d(TAG, "check " + check);

                        sumExpenseDecSaving = integerCollector.get(i);
                        Log.d(TAG, "sumExpenseDecSaving " + sumExpenseDecSaving);

                    }
                }

            }



        }

    }

    // ** must have for connect DB
    public class getHttpSumLineYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpSumLineYear(Context context) {
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
                                showLineYear(response.body().string());
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

            if (check.equals("Extra income")) {
                Log.d(TAG, "check " + check);

                categoryExtraIncomeMonth = "Extra income";
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

            if (check.equals("Extra income")) {
                Log.d(TAG, "check " + check);

                categoryExtraIncomeYear = "Extra income";
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
        setText();
        initDataLineCategory();

         //for pie chart
        initDataIncome();
        initDataExpense();
        initDataIncomeYear();
        initDataExpenseYear();


    }

    public void setText(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        //income
        textViewIncomeJan.setText("1 Jan : " + nf.format(sumIncomeJan) );
        textViewIncomeFeb.setText("2 Feb : " + nf.format(sumIncomeFeb) );
        textViewIncomeMar.setText("3 Mar : " + nf.format(sumIncomeMar) );
        textViewIncomeApr.setText("4 Apr : " + nf.format(sumIncomeApr) );
        textViewIncomeMay.setText("5 May : " + nf.format(sumIncomeMay) );
        textViewIncomeJun.setText("6 Jun : " + nf.format(sumIncomeJun) );
        textViewIncomeJul.setText("7 Jul : " + nf.format(sumIncomeJul) );
        textViewIncomeAug.setText("8 Aug : " + nf.format(sumIncomeAug) );
        textViewIncomeSep.setText("9 Sep : " + nf.format(sumIncomeSep) );
        textViewIncomeOct.setText("10 Oct : " + nf.format(sumIncomeOct) );
        textViewIncomeNov.setText("11 Nov : " + nf.format(sumIncomeNov) );
        textViewIncomeDec.setText("12 Dec : " + nf.format(sumIncomeDec) );


        //expense
        textViewExpenseJan.setText("1 Jan : " + nf.format(sumExpenseJan) );
        textViewExpenseFeb.setText("2 Feb : " + nf.format(sumExpenseFeb) );
        textViewExpenseMar.setText("3 Mar : " + nf.format(sumExpenseMar) );
        textViewExpenseApr.setText("4 Apr : " + nf.format(sumExpenseApr) );
        textViewExpenseMay.setText("5 May : " + nf.format(sumExpenseMay) );
        textViewExpenseJun.setText("6 Jun : " + nf.format(sumExpenseJun) );
        textViewExpenseJul.setText("7 Jul : " + nf.format(sumExpenseJul) );
        textViewExpenseAug.setText("8 Aug : " + nf.format(sumExpenseAug) );
        textViewExpenseSep.setText("9 Sep : " + nf.format(sumExpenseSep) );
        textViewExpenseOct.setText("10 Oct : " + nf.format(sumExpenseOct) );
        textViewExpenseNov.setText("11 Nov : " + nf.format(sumExpenseNov) );
        textViewExpenseDec.setText("12 Dec : " + nf.format(sumExpenseDec) );
    }


    // line graph
    private void initData() {
        Log.d(TAG, "addDataSet lineChart started");

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

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0),

                new DataPoint(5, 0),
                new DataPoint(6, 0),
                new DataPoint(7, 0),
                new DataPoint(8, 0),
                new DataPoint(9, 0),

                new DataPoint(10, 0),
                new DataPoint(11, 0),
                new DataPoint(12, 0),

        });


        graph.addSeries(seriesIncome);
        seriesIncome.setThickness(8);
        seriesIncome.setColor(getResources().getColor(R.color.buttonColorGreen));
//        seriesIncome.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                double pointY = dataPoint.getY();
//                double pointX = dataPoint.getX();
//
//                    Toast.makeText(graph.getContext(), "[Month/Income ] " + pointY, Toast.LENGTH_SHORT).show();
//
//            }
//        });



        graph.addSeries(seriesExpense);
        seriesExpense.setThickness(8);
        seriesExpense.setColor(getResources().getColor(R.color.buttonColorRed));
        //seriesExpense.setThickness(10);
     //   graph.getGridLabelRenderer().setHorizontalLabelsColor(R.color.buttonColorBlack);
      //  seriesExpense.setDrawDataPoints(true);
//        seriesExpense.setDataPointsRadius(10);
//        seriesExpense.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                double pointY = dataPoint.getY();
//                double pointX = dataPoint.getX();
//                    Toast.makeText(graph.getContext(), "[Month/Expense ] " + pointY, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

        graph.addSeries(series);
        series.setColor(getResources().getColor(R.color.buttonColorBlack));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        series.setThickness(5);




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

        graph.getGridLabelRenderer().setNumHorizontalLabels(12);




    }


    // line graph category
    private void initDataLineCategory() {
        Log.d(TAG, "addDataSet initDataLineCategory started");

        GraphView graph = (GraphView) mView.findViewById(R.id.lineGraphIncomeCategory);

        LineGraphSeries<DataPoint> seriesGift = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJanGift),
                new DataPoint(2, sumIncomeFebGift),
                new DataPoint(3, sumIncomeMarGift),
                new DataPoint(4, sumIncomeAprGift),

                new DataPoint(5, sumIncomeMayGift),
                new DataPoint(6, sumIncomeJunGift),
                new DataPoint(7, sumIncomeJulGift),
                new DataPoint(8, sumIncomeAugGift),
                new DataPoint(9, sumIncomeSepGift),

                new DataPoint(10, sumIncomeOctGift),
                new DataPoint(11, sumIncomeNovGift),
                new DataPoint(12, sumIncomeDecGift),

        });

        LineGraphSeries<DataPoint> seriesSalary = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJanSalary),
                new DataPoint(2, sumIncomeFebSalary),
                new DataPoint(3, sumIncomeMarSalary),
                new DataPoint(4, sumIncomeAprSalary),

                new DataPoint(5, sumIncomeMaySalary),
                new DataPoint(6, sumIncomeJunSalary),
                new DataPoint(7, sumIncomeJulSalary),
                new DataPoint(8, sumIncomeAugSalary),
                new DataPoint(9, sumIncomeSepSalary),

                new DataPoint(10, sumIncomeOctSalary),
                new DataPoint(11, sumIncomeNovSalary),
                new DataPoint(12, sumIncomeDecSalary),

        });

        LineGraphSeries<DataPoint> seriesExtra = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJanExtra),
                new DataPoint(2, sumIncomeFebExtra),
                new DataPoint(3, sumIncomeMarExtra),
                new DataPoint(4, sumIncomeAprExtra),

                new DataPoint(5, sumIncomeMayExtra),
                new DataPoint(6, sumIncomeJunExtra),
                new DataPoint(7, sumIncomeJulExtra),
                new DataPoint(8, sumIncomeAugExtra),
                new DataPoint(9, sumIncomeSepExtra),

                new DataPoint(10, sumIncomeOctExtra),
                new DataPoint(11, sumIncomeNovExtra),
                new DataPoint(12, sumIncomeDecExtra),

        });

        LineGraphSeries<DataPoint> seriesLoan = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJanLoan),
                new DataPoint(2, sumIncomeFebLoan),
                new DataPoint(3, sumIncomeMarLoan),
                new DataPoint(4, sumIncomeAprLoan),

                new DataPoint(5, sumIncomeMayLoan),
                new DataPoint(6, sumIncomeJunLoan),
                new DataPoint(7, sumIncomeJulLoan),
                new DataPoint(8, sumIncomeAugLoan),
                new DataPoint(9, sumIncomeSepLoan),

                new DataPoint(10, sumIncomeOctLoan),
                new DataPoint(11, sumIncomeNovLoan),
                new DataPoint(12, sumIncomeDecLoan),

        });

        LineGraphSeries<DataPoint> seriesFamily = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumIncomeJanFamily),
                new DataPoint(2, sumIncomeFebFamily),
                new DataPoint(3, sumIncomeMarFamily),
                new DataPoint(4, sumIncomeAprFamily),

                new DataPoint(5, sumIncomeMayFamily),
                new DataPoint(6, sumIncomeJunFamily),
                new DataPoint(7, sumIncomeJulFamily),
                new DataPoint(8, sumIncomeAugFamily),
                new DataPoint(9, sumIncomeSepFamily),

                new DataPoint(10, sumIncomeOctFamily),
                new DataPoint(11, sumIncomeNovFamily),
                new DataPoint(12, sumIncomeDecFamily),

        });


        ////////////////expense////////////////
        GraphView graphEx = (GraphView) mView.findViewById(R.id.lineGraphExpenseCategory);
        LineGraphSeries<DataPoint> seriesBill = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanBill),
                new DataPoint(2, sumExpenseFebBill),
                new DataPoint(3, sumExpenseMarBill),
                new DataPoint(4, sumExpenseAprBill),

                new DataPoint(5, sumExpenseMayBill),
                new DataPoint(6, sumExpenseJunBill),
                new DataPoint(7, sumExpenseJulBill),
                new DataPoint(8, sumExpenseAugBill),
                new DataPoint(9, sumExpenseSepBill),

                new DataPoint(10, sumExpenseOctBill),
                new DataPoint(11, sumExpenseNovBill),
                new DataPoint(12, sumExpenseDecBill),

        });

        LineGraphSeries<DataPoint> seriesEducation = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanEducation),
                new DataPoint(2, sumExpenseFebEducation),
                new DataPoint(3, sumExpenseMarEducation),
                new DataPoint(4, sumExpenseAprEducation),

                new DataPoint(5, sumExpenseMayEducation),
                new DataPoint(6, sumExpenseJunEducation),
                new DataPoint(7, sumExpenseJulEducation),
                new DataPoint(8, sumExpenseAugEducation),
                new DataPoint(9, sumExpenseSepEducation),

                new DataPoint(10, sumExpenseOctEducation),
                new DataPoint(11, sumExpenseNovEducation),
                new DataPoint(12, sumExpenseDecEducation),

        });

        LineGraphSeries<DataPoint> seriesEntertainment = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanEntertainment),
                new DataPoint(2, sumExpenseFebEntertainment),
                new DataPoint(3, sumExpenseMarEntertainment),
                new DataPoint(4, sumExpenseAprEntertainment),

                new DataPoint(5, sumExpenseMayEntertainment),
                new DataPoint(6, sumExpenseJunEntertainment),
                new DataPoint(7, sumExpenseJulEntertainment),
                new DataPoint(8, sumExpenseAugEntertainment),
                new DataPoint(9, sumExpenseSepEntertainment),

                new DataPoint(10, sumExpenseOctEntertainment),
                new DataPoint(11, sumExpenseNovEntertainment),
                new DataPoint(12, sumExpenseDecEntertainment),

        });

        LineGraphSeries<DataPoint> seriesFood = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanFood),
                new DataPoint(2, sumExpenseFebFood),
                new DataPoint(3, sumExpenseMarFood),
                new DataPoint(4, sumExpenseAprFood),

                new DataPoint(5, sumExpenseMayFood),
                new DataPoint(6, sumExpenseJunFood),
                new DataPoint(7, sumExpenseJulFood),
                new DataPoint(8, sumExpenseAugFood),
                new DataPoint(9, sumExpenseSepFood),

                new DataPoint(10, sumExpenseOctFood),
                new DataPoint(11, sumExpenseNovFood),
                new DataPoint(12, sumExpenseDecFood),

        });

        LineGraphSeries<DataPoint> seriesShopping = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanShopping),
                new DataPoint(2, sumExpenseFebShopping),
                new DataPoint(3, sumExpenseMarShopping),
                new DataPoint(4, sumExpenseAprShopping),

                new DataPoint(5, sumExpenseMayShopping),
                new DataPoint(6, sumExpenseJunShopping),
                new DataPoint(7, sumExpenseJulShopping),
                new DataPoint(8, sumExpenseAugShopping),
                new DataPoint(9, sumExpenseSepShopping),

                new DataPoint(10, sumExpenseOctShopping),
                new DataPoint(11, sumExpenseNovShopping),
                new DataPoint(12, sumExpenseDecShopping),

        });

        LineGraphSeries<DataPoint> seriesTransport = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanTransport),
                new DataPoint(2, sumExpenseFebTransport),
                new DataPoint(3, sumExpenseMarTransport),
                new DataPoint(4, sumExpenseAprTransport),

                new DataPoint(5, sumExpenseMayTransport),
                new DataPoint(6, sumExpenseJunTransport),
                new DataPoint(7, sumExpenseJulTransport),
                new DataPoint(8, sumExpenseAugTransport),
                new DataPoint(9, sumExpenseSepTransport),

                new DataPoint(10, sumExpenseOctTransport),
                new DataPoint(11, sumExpenseNovTransport),
                new DataPoint(12, sumExpenseDecTransport),

        });

        LineGraphSeries<DataPoint> seriesTravel = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanTravel),
                new DataPoint(2, sumExpenseFebTravel),
                new DataPoint(3, sumExpenseMarTravel),
                new DataPoint(4, sumExpenseAprTravel),

                new DataPoint(5, sumExpenseMayTravel),
                new DataPoint(6, sumExpenseJunTravel),
                new DataPoint(7, sumExpenseJulTravel),
                new DataPoint(8, sumExpenseAugTravel),
                new DataPoint(9, sumExpenseSepTravel),

                new DataPoint(10, sumExpenseOctTravel),
                new DataPoint(11, sumExpenseNovTravel),
                new DataPoint(12, sumExpenseDecTravel),

        });

        LineGraphSeries<DataPoint> seriesFamilyEx = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanFamilyEx),
                new DataPoint(2, sumExpenseFebFamilyEx),
                new DataPoint(3, sumExpenseMarFamilyEx),
                new DataPoint(4, sumExpenseAprFamilyEx),

                new DataPoint(5, sumExpenseMayFamilyEx),
                new DataPoint(6, sumExpenseJunFamilyEx),
                new DataPoint(7, sumExpenseJulFamilyEx),
                new DataPoint(8, sumExpenseAugFamilyEx),
                new DataPoint(9, sumExpenseSepFamilyEx),

                new DataPoint(10, sumExpenseOctFamilyEx),
                new DataPoint(11, sumExpenseNovFamilyEx),
                new DataPoint(12, sumExpenseDecFamilyEx),

        });

        LineGraphSeries<DataPoint> seriesHealthcare = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanHealthcare),
                new DataPoint(2, sumExpenseFebHealthcare),
                new DataPoint(3, sumExpenseMarHealthcare),
                new DataPoint(4, sumExpenseAprHealthcare),

                new DataPoint(5, sumExpenseMayHealthcare),
                new DataPoint(6, sumExpenseJunHealthcare),
                new DataPoint(7, sumExpenseJulHealthcare),
                new DataPoint(8, sumExpenseAugHealthcare),
                new DataPoint(9, sumExpenseSepHealthcare),

                new DataPoint(10, sumExpenseOctHealthcare),
                new DataPoint(11, sumExpenseNovHealthcare),
                new DataPoint(12, sumExpenseDecHealthcare),

        });

        LineGraphSeries<DataPoint> seriesSaving = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, sumExpenseJanSaving),
                new DataPoint(2, sumExpenseFebSaving),
                new DataPoint(3, sumExpenseMarSaving),
                new DataPoint(4, sumExpenseAprSaving),

                new DataPoint(5, sumExpenseMaySaving),
                new DataPoint(6, sumExpenseJunSaving),
                new DataPoint(7, sumExpenseJulSaving),
                new DataPoint(8, sumExpenseAugSaving),
                new DataPoint(9, sumExpenseSepSaving),

                new DataPoint(10, sumExpenseOctSaving),
                new DataPoint(11, sumExpenseNovSaving),
                new DataPoint(12, sumExpenseDecSaving),

        });

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0),

                new DataPoint(5, 0),
                new DataPoint(6, 0),
                new DataPoint(7, 0),
                new DataPoint(8, 0),
                new DataPoint(9, 0),

                new DataPoint(10, 0),
                new DataPoint(11, 0),
                new DataPoint(12, 0),

        });




        //income
        graph.addSeries(seriesGift);
        seriesGift.setThickness(8);
        seriesGift.setColor(getResources().getColor(R.color.gift));

        graph.addSeries(seriesSalary);
        seriesSalary.setThickness(8);
        seriesSalary.setColor(getResources().getColor(R.color.salary));

        graph.addSeries(seriesExtra);
        seriesExtra.setThickness(8);
        seriesExtra.setColor(getResources().getColor(R.color.extraIncome));

        graph.addSeries(seriesLoan);
        seriesLoan.setThickness(8);
        seriesLoan.setColor(getResources().getColor(R.color.loan));

        graph.addSeries(seriesFamily);
        seriesFamily.setThickness(8);
        seriesFamily.setColor(getResources().getColor(R.color.familyAndPersonal));

        // set manual y bounds
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        // set manual x bounds
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","Jan","Feb","Mar","Apr","May","Jun"
                ,"Jul","Aug","Sep","Oct","Nov","Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setNumHorizontalLabels(12);



        //expense
        graphEx.addSeries(seriesBill);
        seriesBill.setThickness(8);
        seriesBill.setColor(getResources().getColor(R.color.bill));

        graphEx.addSeries(seriesEducation);
        seriesEducation.setThickness(8);
        seriesEducation.setColor(getResources().getColor(R.color.education));

        graphEx.addSeries(seriesEntertainment);
        seriesEntertainment.setThickness(8);
        seriesEntertainment.setColor(getResources().getColor(R.color.entertainment));

        graphEx.addSeries(seriesShopping);
        seriesShopping.setThickness(8);
        seriesShopping.setColor(getResources().getColor(R.color.shopping));

        graphEx.addSeries(seriesFood);
        seriesFood.setThickness(8);
        seriesFood.setColor(getResources().getColor(R.color.foodAndDrink));

        graphEx.addSeries(seriesTransport);
        seriesTransport.setThickness(8);
        seriesTransport.setColor(getResources().getColor(R.color.transport));

        graphEx.addSeries(seriesTravel);
        seriesTravel.setThickness(8);
        seriesTravel.setColor(getResources().getColor(R.color.travel));

        graphEx.addSeries(seriesFamilyEx);
        seriesFamilyEx.setThickness(8);
        seriesFamilyEx.setColor(getResources().getColor(R.color.familyAndPersonal));

        graphEx.addSeries(seriesHealthcare);
        seriesHealthcare.setThickness(8);
        seriesHealthcare.setColor(getResources().getColor(R.color.health));

        graphEx.addSeries(seriesSaving);
        seriesSaving.setThickness(8);
        seriesSaving.setColor(getResources().getColor(R.color.savingAndInvestment));


        graph.addSeries(series);
        series.setColor(getResources().getColor(R.color.buttonColorBlack));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        series.setThickness(5);

        graphEx.addSeries(series);
        series.setColor(getResources().getColor(R.color.buttonColorBlack));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        series.setThickness(5);

        // set manual y bounds
        StaticLabelsFormatter staticLabelsFormatterEx = new StaticLabelsFormatter(graphEx);
        // set manual x bounds
        staticLabelsFormatterEx.setHorizontalLabels(new String[] {"","Jan","Feb","Mar","Apr","May","Jun"
                ,"Jul","Aug","Sep","Oct","Nov","Dec"});
        graphEx.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatterEx);
        graphEx.getViewport().setXAxisBoundsManual(true);

        graphEx.getGridLabelRenderer().setNumHorizontalLabels(12);




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
            xDataIncomeMonth.add("Family & Personal");
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
        pieDataSet.setValueTextSize(15);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.9f);



        pieDataSet.setColors(colors);


        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setWordWrapEnabled(true);
//        legend.setDrawInside(false);
//        legend.setTextSize(12f);
//        legend.getCalculatedLineSizes();
//        legend.setEnabled(false);

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
            xDataExpenseMonth.add("Food & Drink");
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
            xDataExpenseMonth.add("Family & Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if (expenseHealthCareMonthPercent > 0) {
            yDataExpenseMonth.add(expenseHealthCareMonthPercent);
            xDataExpenseMonth.add("Healthcare");
            colors.add(getResources().getColor(R.color.health));
        }
        if (expenseSavingAndInvestmentMonthPercent > 0) {
            yDataExpenseMonth.add(expenseSavingAndInvestmentMonthPercent);
            xDataExpenseMonth.add("Saving & Investment");
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
        pieDataSet.setValueTextSize(15);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.9f);


        // add color to dataset
        pieDataSet.setColors(colors);


        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        //legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setWordWrapEnabled(true);
//        legend.setDrawInside(false);
//        legend.setTextSize(12f);
//        legend.getCalculatedLineSizes();
//        legend.setEnabled(false);

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
            xDataIncomeYear.add("Family & Personal");
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
        PieDataSet pieDataSet = new PieDataSet(yEntrysIncomeYear, "" );
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextSize(15);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.9f);


        // add color to dataset
        pieDataSet.setColors(colors);


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
            xDataExpenseYear.add("Food & Drink");
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
            xDataExpenseYear.add("Family & Personal");
            colors.add(getResources().getColor(R.color.familyAndPersonal));
        }
        if(expenseHealthCareYearPercent > 0) {
            yDataExpenseYear.add(expenseHealthCareYearPercent);
            xDataExpenseYear.add("Healthcare");
            colors.add(getResources().getColor(R.color.health));
        }
        if(expenseSavingAndInvestmentYearPercent > 0) {
            yDataExpenseYear.add(expenseSavingAndInvestmentYearPercent);
            xDataExpenseYear.add("Saving & Investment");
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
        pieDataSet.setValueTextSize(15);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setValueLinePart1Length(0.7f);
        pieDataSet.setValueLinePart2Length(0.9f);


        // add color to dataset
        pieDataSet.setColors(colors);


        // create pie data object
        PieData pieData = new PieData(pieDataSet);

        int colorBlack = Color.parseColor("#000000");
        pieChart.setEntryLabelColor(colorBlack);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }





}

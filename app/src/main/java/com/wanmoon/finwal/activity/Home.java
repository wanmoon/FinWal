package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
    private static View rootView;
    private OnFragmentInteractionListener mListener;

    private double sumIncomeMonth;
    private double sumExpenseMonth;
    private double monthBalance;
    private double walletBalance;
    private double sumIncome;
    private double sumExpense;
    public double budget_goal;
    public double suggest_cost;
    public double current_goal;
    public double current_goalPercent;
    public double moneyleft;
    public double percent_money;

    public Double dayGoal;
    public Double dayGoal25;
    public Double checkMoneyGoal25;
    public Double checkSavingplan;
    public Double checkDayGoal25;
    public Double checkGoalHowLong;

    public Double dayGoal50;
    public Double checkMoneyGoal50;
    public Double checkDayGoal50;

    public Double dayGoal75;
    public Double checkMoneyGoal75;
    public Double checkDayGoal75;

    public long days;
    public long diff;

    private String setWalletBalance;
    private String setMonthBalance;
    private String setIncomeMonth;
    private String setExpenseMonth;
    public String date_start;
    public String see_more;

    //progress
    public String data_goal_id;
    public String data_cust_id;
    public String ending_date;
    public String description_goal;
    public String status_goal;
    public String savingplan;

    //next bill
    private String data_bill_id;
    private String period;
    private String description_bill;
    private String status_bill;
    private String deadline;

    public TextView textViewMyWallet;
    public TextView textViewMyIncome;
    public TextView textViewMyExpense;
    public TextView textViewMonthBalance;
    public TextView textViewStatusGoal;
    public TextView textViewPercentGoal;
    public TextView textViewMoneyLeftGoal;
    public TextView textViewGoalTitle;
    public TextView textViewDateLeft;
    public TextView textViewProgressBarNoData;
    private TextView textViewBillDescription;
    private TextView textViewBillStatus;
    private TextView textViewBillDeadline;
    private TextView textViewBillPeriod;
    private TextView textViewBillNoData;
    public TextView textViewSeemore;
    public TextView textViewSeeMoreBill;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private RelativeLayout relativeBill;

    public RoundCornerProgressBar progress;

    public float float_current_goalPercent;
    private float incomePercent;
    private float expensePercent;
    private float float_percent_money;

    //get current user
    private FirebaseAuth firebaseAuth;
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "HomeActivity";

    //for pie chart
    public PieChart pieChart;
    private View mView;

    getHttpIncomeMonth httpIncomeMonth;
    getHttpExpenseMonth httpExpenseMonth;
    getHttpIncome httpIncome;
    getHttpExpense httpExpense;
    getHttpProgressBar httpProgressBar;
    getHttpDateStart httpDateStart;
    getHttpNextBill httpNextBill;
    getHttpUpdateStatus httpUpdateStatus;

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

        ((MainActivity) getActivity()).setTitle("My FinWal");


        Log.d(TAG, "end onCreate");
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

        httpDateStart = new getHttpDateStart(getContext());

        httpNextBill = new getHttpNextBill(getContext());

        httpUpdateStatus = new getHttpUpdateStatus(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sumIncomeMonth = 0;
        sumExpenseMonth = 0;
        sumIncome = 0;
        sumExpense = 0;

        dateStart(cust_id);
        sumExpenseMonthToDB(cust_id);
        sumIncomeMonthToDB(cust_id);

        sumIncomeToDB(cust_id);
        sumExpenseToDB(cust_id);

        getProgressbar(cust_id);
        getNextBill(cust_id);

        Log.d(TAG, "start findviewbyid");
        textViewMyWallet = (TextView) view.findViewById(R.id.textViewMyWallet);
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

        linearLayout1 = (LinearLayout) view.findViewById(R.id.btnMonthIncome1);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.btnMonthExpense2);

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

        //goal
        textViewGoalTitle = (TextView) view.findViewById(R.id.textViewGoalTitle);
        textViewDateLeft = (TextView) view.findViewById(R.id.textViewDateLeft);
        textViewStatusGoal = (TextView) view.findViewById(R.id.textViewStatusGoal);
        textViewPercentGoal = (TextView) view.findViewById(R.id.textViewPercentGoal);
        textViewMoneyLeftGoal = (TextView) view.findViewById(R.id.textViewMoneyLeftGoal);
        textViewProgressBarNoData = (TextView) view.findViewById(R.id.textViewProgressBarNoData);
        textViewSeemore = (TextView) view.findViewById(R.id.textViewSeemore);
        textViewSeeMoreBill = (TextView) view.findViewById(R.id.textViewSeeMoreBill);

        textViewSeemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragment to fragment
                Goal GoalFragment = new Goal();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, GoalFragment).addToBackStack( "tag" );;;
                transaction.commit();
            }
        });

        textViewSeeMoreBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Billing BillingFragment = new Billing();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, BillingFragment).addToBackStack( "tag" );;
                transaction.commit();
            }
        });

        progress = (RoundCornerProgressBar) view.findViewById(R.id.progress_1);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fregment to fregment
                Goal GoalFragment = new Goal();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, GoalFragment).addToBackStack( "tag" );;;
                transaction.commit();
            }
        });


        //nextbill
        relativeBill = (RelativeLayout) view.findViewById(R.id.relativeBill);
        relativeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Billing bill = new Billing();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, bill).addToBackStack( "tag" );;;
                transaction.commit();
            }

        });


        textViewBillDescription = (TextView) view.findViewById(R.id.textViewBillDescription);
        textViewBillDeadline = (TextView) view.findViewById(R.id.textViewBillDeadline);
        textViewBillPeriod = (TextView) view.findViewById(R.id.textViewBillPeriod);
        textViewBillStatus = (TextView) view.findViewById(R.id.textViewBillStatus);
        textViewBillNoData = (TextView) view.findViewById(R.id.textViewBillNoData);

        relativeBill = (RelativeLayout) view.findViewById(R.id.relativeBill);

        //settext in nextbill
        setNextBillData(period, description_bill, status_bill, deadline);

        Log.d(TAG, "end findviewbyid");
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.billing_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    //////////////////////////////////////////////////////////for month balance/////////////////////

    public String sumIncomeMonthToDB(String cust_id) {
        try {
            Log.d(TAG, "start sumIncomeMonth");
            httpIncomeMonth.run(BASE_URL + "/sumIncomeMonth.php?cust_id=" + cust_id);
            Log.d(TAG, "end sumIncomeMonth");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public String sumExpenseMonthToDB(String cust_id) {
        try {
            Log.d(TAG, "start sumExpenseMonth");
            httpExpenseMonth.run(BASE_URL + "/sumExpenseMonth.php?cust_id=" + cust_id);
            Log.d(TAG, "end sumExpenseMonth");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

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
                    Log.d(TAG, "onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String incomeMonth = response.body().string();
                                sumIncomeMonth = Double.parseDouble(incomeMonth.trim());
                                Log.d(TAG, "sumIncome = " + sumIncomeMonth);

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

    /////////////////////////////////////////////////////////for wallet balance/////////////////////

    public String sumIncomeToDB(String cust_id) {
        try {
            Log.d(TAG, "start transaction");
            httpIncome.run(BASE_URL + "/sumIncome.php?cust_id=" + cust_id);
            Log.d(TAG, "end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public String sumExpenseToDB(String cust_id) {
        try {
            Log.d(TAG, "start show");
            httpExpense.run(BASE_URL + "/sumExpense.php?cust_id=" + cust_id);
            Log.d(TAG, "end show");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

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
                    Log.d(TAG, "onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String expense = response.body().string();
                                sumExpense = Double.parseDouble(expense.trim());
                                Log.d(TAG, "sumExpense = " + sumExpense);

                                Log.d(TAG, "onResponse");
                                Log.d(TAG, "show");

                                if (sumExpense != 0 && sumIncome != 0) {
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
                    Log.d(TAG, "onFailure" + e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String income = response.body().string();
                                sumIncome = Double.parseDouble(income.trim());
                                Log.d(TAG, "sumIncome = " + sumIncome);

                                Log.d(TAG, "onResponse");
                                Log.d(TAG, "show");

                                if (sumExpense != 0 && sumIncome != 0) {
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

    //////////////////////////////////////////////////////////get date start////////////////////////
    public String dateStart(String cust_id) {
        try {
            Log.d(TAG, "start dateStart");
            httpDateStart.run(BASE_URL + "/dateStart.php?cust_id=" + cust_id);
            Log.d(TAG, "end dateStart");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public class getHttpDateStart {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpDateStart(Context context) {
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
                                date_start = response.body().string().trim();
                                sumAllBalance();
                                Log.d(TAG, "date_start = " + date_start);
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

    public void sumAllBalance() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        Log.d(TAG, "All start settext");
        monthBalance = sumIncomeMonth - sumExpenseMonth;
        Log.d(TAG, "Month balance = " + monthBalance);

        // follow UI
        ////////////////////////for wallet balance//////////////////////
        walletBalance = sumIncome - sumExpense;
        Log.d(TAG, "Wallet balance = " + walletBalance);

        //in a month
        setMonthBalance = "Month Balance : " + "<b>" + nf.format(monthBalance) + "</b>";
        textViewMonthBalance.setText((Html.fromHtml(setMonthBalance)));

        setIncomeMonth = "<b>" + nf.format(sumIncomeMonth) + "</b>";
        textViewMyIncome.setText((Html.fromHtml(setIncomeMonth)));
        Log.d(TAG, "sumIncomeMonth = " + sumIncomeMonth);

        setExpenseMonth = "<b>" + nf.format(sumExpenseMonth) + "</b>";
        textViewMyExpense.setText((Html.fromHtml(setExpenseMonth)));
        Log.d(TAG, "sumExpenseMonth = " + sumExpenseMonth);


        Log.d(TAG, "date_start" + date_start);

        //all of my life
        setWalletBalance = "[Since " + date_start + "] Wallet Balance : " + "<b>" + nf.format(walletBalance) + "</b>";
        textViewMyWallet.setText((Html.fromHtml(setWalletBalance)));
        Log.d(TAG, "end settext");

        incomePercent = (float) (monthBalance * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet incomePercent = " + incomePercent);

        expensePercent = (float) (sumExpenseMonth * (100 / sumIncomeMonth));
        Log.d(TAG, "Wallet expensePercent = " + expensePercent);

        //for pie chart
        initData();
    }

    /////////////////////////////////////////////////////////// for pie chart //////////////////////
    private void initData() {

        pieChart = (PieChart) mView.findViewById(R.id.PieChart);

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
        if (incomePercent > 0) yData.add(incomePercent);
        if (expensePercent > 0) yData.add(expensePercent);

        Log.d(TAG, "addDataSet started incomePercent" + incomePercent);
        Log.d(TAG, "addDataSet started expensePercent" + expensePercent);
        String[] xData = {"Income", "Expense"};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for (int i = 0; i < yData.size(); i++) {
            yEntrys.add(new PieEntry(yData.get(i), xData[i]));
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueFormatter(new PercentFormatter());


        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.buttonColorGreen));
        colors.add(getResources().getColor(R.color.buttonColorRed));
        pieDataSet.setColors(colors);

        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        int colorBlack = Color.parseColor("#000000");
        pieChart.setEntryLabelColor(colorBlack);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    ///////////////////////////////////////////////////////////////progress bar/////////////////////
    public void progressdata(String data) {
        Log.d(TAG, "data " + data);
        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));

        //empty result
        if (items.size() < 9) {
            //visible progress
            textViewProgressBarNoData.setVisibility(View.VISIBLE);

            progress.setVisibility(View.GONE);
            textViewGoalTitle.setVisibility(View.GONE);
            textViewPercentGoal.setVisibility(View.GONE);
            textViewStatusGoal.setVisibility(View.GONE);
            textViewMoneyLeftGoal.setVisibility(View.GONE);
            textViewDateLeft.setVisibility(View.GONE);
            textViewSeemore.setVisibility(View.GONE);
        } else {
            textViewProgressBarNoData.setVisibility(View.GONE);
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

            current_goalPercent = (current_goal / budget_goal) * 100;
            float_current_goalPercent = Float.parseFloat(String.format("%.2f", current_goalPercent));

         //   progressBar(float_current_goalPercent);
            setDataProcessBar(ending_date, description_goal, status_goal, budget_goal, current_goal);
        }
    }


    public void setDataProcessBar(String ending_date, String description_goal, String status_goal, double budget_goal, double current_goal) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        textViewGoalTitle.setText(description_goal);
        textViewPercentGoal.setText(float_current_goalPercent + "%");
        textViewStatusGoal.setText(status_goal);

        moneyleft = budget_goal - current_goal;
        Log.d(TAG, "moneyleft = " + moneyleft);

        //เก็บเกิน goal
        if (moneyleft < 0) { //เกินโกลไปแล้ว
            textViewMoneyLeftGoal.setText("0 Baht Left");
        } else {
            textViewMoneyLeftGoal.setText(nf.format(moneyleft) + " Left");
        }

        //update status
        if (current_goal >= budget_goal) {
            textViewStatusGoal.setText("Achieved");
            textViewStatusGoal.setTextColor(Color.parseColor("#088A4B")); //green
        } else {
            textViewStatusGoal.setTextColor(Color.parseColor("#e67e22")); //yellow
        }

        //days left
        Log.d(TAG, "ending_date = " + ending_date);
        List<String> items_ending_date = Arrays.asList(ending_date.split("\\s*-\\s*"));
        Log.d(TAG, "items_ending_date = " + items_ending_date);

        int d = Integer.parseInt(items_ending_date.get(0));
        int m = Integer.parseInt(items_ending_date.get(1));
        int y = Integer.parseInt(items_ending_date.get(2));

        Log.d(TAG, "d = " + d);
        Log.d(TAG, "m = " + m);
        Log.d(TAG, "y = " + y);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, d);
        thatDay.set(Calendar.MONTH, m-1); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, y);

        Calendar today = Calendar.getInstance();

        diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        days = diff / (24 * 60 * 60 * 1000);

        Log.d(TAG, "diff = " + diff);
        Log.d(TAG, "days = " + days);

        textViewDateLeft.setText(days + " days left");

        /////// get% progress bar ///////
        dayGoal = budget_goal / suggest_cost;
        Log.d(TAG, "dayGoal = " + dayGoal + savingplan);

        dayGoal25 = dayGoal*25/100;
        Log.d(TAG, "dayGoal25 = " + dayGoal25 + savingplan);

        dayGoal50 = dayGoal*50/100;
        Log.d(TAG, "dayGoal50 = " + dayGoal50 + savingplan);

        dayGoal75 = dayGoal*75/100;
        Log.d(TAG, "dayGoal75 = " + dayGoal75 + savingplan);

        //check money 25% that must achieve
        checkMoneyGoal25 = dayGoal25*suggest_cost;
        Log.d(TAG, "checkMoneyGoal25 = " + checkMoneyGoal25);

        //check money 50% that must achieve
        checkMoneyGoal50 = dayGoal50*suggest_cost;
        Log.d(TAG, "checkMoneyGoal50 = " + checkMoneyGoal50);

        //check money 75% that must achieve
        checkMoneyGoal75 = dayGoal75*suggest_cost;
        Log.d(TAG, "checkMoneyGoal75 = " + checkMoneyGoal75);


        if(savingplan.equals("Daily")){
            checkSavingplan = 1.00;
        }
        if(savingplan.equals("Weekly")){
            checkSavingplan = 7.00;
        }
        if(savingplan.equals("Monthly")){
            checkSavingplan = 30.00;
        }
        checkGoalHowLong = checkSavingplan * dayGoal;
        Log.d(TAG, "checkSavingplan = " + checkSavingplan);
        Log.d(TAG, "checkGoalHowLong = " + checkGoalHowLong);

        //check day 25% that must achieve / days
        checkDayGoal25 = checkSavingplan*dayGoal25;
        Log.d(TAG, "checkDayGoal25 = " + checkDayGoal25);

        //check day 50% that must achieve / days
        checkDayGoal50 = checkSavingplan*dayGoal50;
        Log.d(TAG, "checkDayGoal50 = " + checkDayGoal50);

        //check day 75% that must achieve / days
        checkDayGoal75 = checkSavingplan*dayGoal75;
        Log.d(TAG, "checkDayGoal75 = " + checkDayGoal75);


        //ภายใน checkDayGoal25 ต้องเก็บเงินได้ checkMoneyGoal25 จากเวลา checkGoalHowLong ทั้งหมด
        if((checkGoalHowLong - checkDayGoal25 >= days) &&(current_goal <checkMoneyGoal25)){
            textViewStatusGoal.setText("Unachieved On 25%");
            textViewStatusGoal.setTextColor(Color.parseColor("#Bf4b4b"));
            progress.setProgressColor(Color.parseColor("#Bf4b4b")); //red
        } else if((checkGoalHowLong - checkDayGoal50 >= days) &&(current_goal <checkMoneyGoal50)){
            textViewStatusGoal.setText("Unachieved On 50%");
            textViewStatusGoal.setTextColor(Color.parseColor("#Bf4b4b"));
            progress.setProgressColor(Color.parseColor("#Bf4b4b")); //red
        } else if((checkGoalHowLong - checkDayGoal75 >= days) &&(current_goal <checkMoneyGoal75)){
            textViewStatusGoal.setText("Unachieved On 75%");
            textViewStatusGoal.setTextColor(Color.parseColor("#Bf4b4b"));
            progress.setProgressColor(Color.parseColor("#Bf4b4b")); //red
        } else{
            progress.setProgressColor(Color.parseColor("#088A4B")); //green
        }

        progress.setProgressBackgroundColor(Color.parseColor("#FFFFFF"));
        progress.setMax(100);
        progress.setProgress(float_current_goalPercent);
        progress.setContentDescription(float_current_goalPercent + "");

        //see more
        see_more = "<u>" + "See more" + "</u>";
        textViewSeemore.setText((Html.fromHtml(see_more)));

        //dayleft <0
        if((current_goal < budget_goal) && (days < 0)){ //unchieved : current_goal >= budget_goal, dayleft<0
            int goal_id = Integer.parseInt(data_goal_id);
            updateStatus(cust_id, goal_id, 1);
        }
    }

    public void updateStatus(String cust_id, int goal_id, int flagSort){
        try {
            Log.d(TAG,"goal_id = " + goal_id);
            Log.d(TAG,"start select");
            httpUpdateStatus.run(BASE_URL + "/goalUpdateStatus.php?cust_id=" + cust_id + "&goal_id=" + goal_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public class getHttpUpdateStatus {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpUpdateStatus(Context context) {
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
                            Log.d(TAG,"update success");
                        }


                    });
                }
            });
        }
    }

    public String getProgressbar(String cust_id) {
        try {
            Log.d(TAG, "start progressbar");
            httpProgressBar.run(BASE_URL + "/progressbar.php?cust_id=" + cust_id);
            Log.d(TAG, "end progressbar");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

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
                    Log.d(TAG, "onFailure" + e.toString());
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

    //////////////////////////////////////////////////////////////////next bill/////////////////////
    public void nextBillData(String data) {
        Log.d(TAG, "data " + data);
        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));

        if (items.size() < 5) {
            //visible
            textViewBillNoData.setVisibility(View.VISIBLE);
            relativeBill.setVisibility(View.GONE);
        } else {
            textViewBillNoData.setVisibility(View.GONE);

            data_bill_id = items.get(0);
            period = items.get(1);
            description_bill = items.get(2);
            status_bill = items.get(3);
            deadline = items.get(4);

            Log.d(TAG, "data_bill_id " + data_bill_id);
            Log.d(TAG, "period " + period);
            Log.d(TAG, "description_bill " + description_bill);
            Log.d(TAG, "status_bill " + status_bill);
            Log.d(TAG, "deadline " + deadline);

            setNextBillData(period, description_bill, status_bill, deadline);
        }
    }

    public void setNextBillData(String period, String description_bill, String status_bill, String deadline) {
        textViewBillDescription.setText(description_bill);
        textViewBillDeadline.setText(deadline);
        textViewBillPeriod.setText(period);
        textViewBillStatus.setText(status_bill);
        textViewBillStatus.setTextColor(Color.parseColor("#e54649"));
        ;

        Log.d(TAG, "description_bill = " + description_bill);
        Log.d(TAG, "deadline = " + deadline);
        Log.d(TAG, "period = " + period);
        Log.d(TAG, "status_bill = " + status_bill);

        //see more
        see_more = "<u>" + "See more" + "</u>";
        textViewSeeMoreBill.setText((Html.fromHtml(see_more)));
    }

    public String getNextBill(String cust_id) {
        try {
            Log.d(TAG, "start nextbill");
            httpNextBill.run(BASE_URL + "/nextbill.php?cust_id=" + cust_id);
            Log.d(TAG, "end nextbill");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "error catch");
        }
        return response;
    }

    public class getHttpNextBill {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpNextBill(Context context) {
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
                                nextBillData(response.body().string().trim());
                            } catch (NumberFormatException e) {
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




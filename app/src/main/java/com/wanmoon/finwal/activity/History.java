package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link History.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History extends android.support.v4.app.Fragment  {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth firebaseAuth;

    private static View rootView;

    private View bView;

    private Typeface tf;

    private double sumIncomeAll;
    private double sumExpenseAll;
    //private double balanceAll;

    //private String setWalletAll;
    private String setIncomeAll;
    private String setExpenseAll;

    //public TextView textViewMyWallet;
    public TextView textViewMyIncomeAll;
    public TextView textViewMyExpenseAll;

    private View mView;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
//    getHttpIncome httpIncome = new getHttpIncome();
//    getHttpExpense httpExpense = new getHttpExpense();
    getHttpIncomeAll httpIncomeAll;
    getHttpExpenseAll httpExpenseAll;
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

        httpIncomeAll = new getHttpIncomeAll(getContext());
        httpExpenseAll = new getHttpExpenseAll(getContext());
        Log.d(TAG,"end onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        mView = rootView;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sumExpenseToDB(cust_id);
        sumIncomeToDB(cust_id);

        Log.d(TAG,"start findviewbyid");
        //textViewMyWallet = (TextView) view.findViewById(R.id.textViewMyWallet);
        textViewMyIncomeAll = (TextView) view.findViewById(R.id.textViewMyIncomeAll);
        textViewMyExpenseAll = (TextView) view.findViewById(R.id.textViewMyExpenseAll);
        Log.d(TAG,"end findviewbyid");

        textViewMyIncomeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllIncome.class);
                startActivity(i);
            }
        });

        textViewMyExpenseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllExpense.class);
                startActivity(i);
            }
        });
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

    // ** must have for connect DB
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

    // ** must have for connect DB
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
        //balanceAll = sumIncomeAll - sumExpenseAll;
        //Log.d(TAG, "balance = " + balanceAll);

        Log.d(TAG,"start settext");
        setIncomeAll = "Total Income : " + "<b>" + sumIncomeAll + " Baht</b>";
        Log.d(TAG,"Total Income = " + setIncomeAll);
        textViewMyIncomeAll.setText((Html.fromHtml(setIncomeAll)));

        setExpenseAll = "Total Expense : " + "<b>" + sumExpenseAll + " Baht</b>";
        textViewMyExpenseAll.setText((Html.fromHtml(setExpenseAll)));

        //setWalletAll = "My Wallet : " + "<b>" + balanceAll + " Baht</b>";
        //textViewMyWallet.setText((Html.fromHtml(setWalletAll)));
        Log.d(TAG,"end settext");
    }
}
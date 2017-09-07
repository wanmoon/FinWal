package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

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

    private double sumIncome;
    private double sumExpense;
    private double balance;

    private String setWallet;
    private String setIncome;
    private String setExpense;

    public TextView textViewMyWallet;
    public TextView textViewMyIncome;
    public TextView textViewMyExpense;



    private View mView;


    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    //    getHttpIncome httpIncome = new getHttpIncome();
//    getHttpExpense httpExpense = new getHttpExpense();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //for log
    private final String TAG = "History";



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
//        sumExpenseToDB(cust_id);
//        sumIncomeToDB(cust_id);


        Log.d(TAG,"start findviewbyid");
        textViewMyWallet = (TextView) view.findViewById(R.id.textViewMyWallet);
        textViewMyIncome = (TextView) view.findViewById(R.id.textViewMyIncome);
        textViewMyExpense = (TextView) view.findViewById(R.id.textViewMyExpense);
        Log.d(TAG,"end findviewbyid");

        textViewMyIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllIncome.class);
                startActivity(i);
            }
        });

        textViewMyExpense.setOnClickListener(new View.OnClickListener() {
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

//    public String sumIncomeToDB(String cust_id){
//        try {
//            Log.d(TAG,"start transaction");
//            httpIncome.run(BASE_URL + "/sumIncome.php?cust_id=" + cust_id);
//            Log.d(TAG,"end transaction");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG,"error catch");
//        }
//        return response;
//    }
//
//    public String sumExpenseToDB(String cust_id){
//        try {
//            Log.d(TAG,"start show");
//            httpExpense.run(BASE_URL + "/sumExpense.php?cust_id=" + cust_id);
//            Log.d(TAG,"end show");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG,"error catch");
//        }
//        return response;
//    }
//
//    // ** must have for connect DB
//
//    public class getHttpIncome {
//        OkHttpClient client = new OkHttpClient();
//
//        void run(String url) throws IOException {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.d(TAG,"onFailure" + e.toString());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    try {
//                        String income = response.body().string();
//                        sumIncome = Double.parseDouble(income.trim());
//                        Log.d(TAG,"sumIncome = " + sumIncome);
//
//                        Log.d(TAG,"onResponse");
//                        Log.d(TAG,"show");
//
//                        if(sumExpense != 0 && sumIncome != 0) {
//                            sumBalance();
//                        }
//                    } catch (NumberFormatException e){
//                        //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
//                        Log.d(TAG, "NumberFormatException");
//                    }
//                }
//            });
//        }
//    }
//
//    public class getHttpExpense {
//        OkHttpClient client = new OkHttpClient();
//
//        void run(String url) throws IOException {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.d(TAG,"onFailure" + e.toString());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    try {
//                        String expense = response.body().string();
//                        sumExpense = Double.parseDouble(expense.trim());
//                        Log.d(TAG,"sumExpense = " + sumExpense);
//
//                        Log.d(TAG,"onResponse");
//                        Log.d(TAG,"show");
//
//                        if(sumExpense != 0 && sumIncome != 0) {
//                            sumBalance();
//                        }
//                    } catch (NumberFormatException e){
//                        //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
//                        Log.d(TAG, "NumberFormatException");
//                    }
//                }
//            });
//        }
//    }
//
//
//    public void sumBalance(){
//        balance = sumIncome - sumExpense;
//        Log.d(TAG, "balance = " + balance);
//
//        Log.d(TAG,"start settext");
//        setIncome = "Total Income : " + "<b>" + sumIncome + " Baht</b>";
//        textViewMyIncome.setText((Html.fromHtml(setIncome)));
//
//        setExpense = "Total Expense : " + "<b>" + sumExpense + " Baht</b>";
//        textViewMyExpense.setText((Html.fromHtml(setExpense)));
//
//        setWallet = "My Wallet : " + "<b>" + balance + " Baht</b>";
//        textViewMyWallet.setText((Html.fromHtml(setWallet)));
//        Log.d(TAG,"end settext");
//
//
//    }
}
package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Billing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Billing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Billing extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Spinner spinnerSort;
    String defaultTextForSpinner = "text here";


    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    getHttp http = new getHttp();
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "Show All Billing";


    private View mView;

    public Billing() {
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
    public static Billing newInstance(String param1, String param2) {
        Billing fragment = new Billing();
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

        ((MainActivity)getActivity()).setTitle("Billing");

//        spinnerSort = (Spinner) mView.findViewById(R.id.spinnerSort);
//        String[] spinnerValue = new String[]{
//                "Time",
//                "Category",
//                "Category : A-Z",
//                "Category : Most popular",
//                "Price : Low-High",
//                "Price : High-Low"
//        };
//        final List<String> mspinnerSort = new ArrayList<>(Arrays.asList(spinnerValue));
//        ArrayAdapter<String> aSpinnerSort = new ArrayAdapter<String>(getActivity().getApplicationContext()
//                , android.R.layout.simple_spinner_item, mspinnerSort);
//        spinnerSort.setAdapter(aSpinnerSort);
//
//        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Toast.makeText(AllDetailTransaction.this, "Select : " + mspinnerSort.get(position), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        Log.d(TAG, "onCreate");

        getAllBilling(cust_id);


    }






    public void getAllBilling(String cust_id){
        try {
            Log.d(TAG,"start select");
            http.run(BASE_URL + "/showAllBilling.php?cust_id=" + cust_id);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showBillingToListView(String allBilling){
        //String allTransaction = response.body().string();
        Log.d(TAG, "allBilling " + allBilling);

        String[] transactionInfo;
        String period;
        String description_bill;
        String status_bill;
        String deadline;
        ArrayList<HashMap<String, String>> transactionList = null;

        transactionList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;


        Scanner scanner = new Scanner(allBilling);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            transactionInfo = data.split(",");

            period = transactionInfo[0];
            description_bill = transactionInfo[1];
            //status_bill = transactionInfo[2];
            deadline = transactionInfo[2];

            map = new HashMap<String, String>();
            map.put("period", period);
            map.put("description_bill", description_bill);
            //map.put("status_bill", status_bill);
            map.put("deadline", deadline);
            transactionList.add(map);
        }



        BillAdapter adapter = new BillAdapter(getContext(), transactionList);

        //listview for show alltransaction
        ListView billingListView = (ListView) getActivity().findViewById(R.id.listViewBilling);
        billingListView.setAdapter(adapter);
        billingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }

    // ** must have for connect DB
    public class getHttp {
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
                    showBillingToListView(response.body().string());
                    Log.d(TAG,"onResponse");
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_billing, container, false);

        return rootView;


    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.billing_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(getActivity(), NewBill.class);
            startActivity(intent);
        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}

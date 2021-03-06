package com.wanmoon.finwal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    //dont delete
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mView;

    ArrayList<HashMap<String, String>> billList;
    BillAdapter adapter;
    ListView billingListView;

    public Spinner spinnerSort;

    public Dialog dialogEditBill;
    public Dialog billCost;

    public TextView textViewDescription_bill;
    public TextView textViewStatus_bill;
    public TextView textViewPeriod;
    public TextView textViewDeadline;
    public TextView textViewPaid_date;
    public TextView TextViewEmptyResult;

    public Button buttonPaidBill;
    public Button buttonDeleteBill;
    public Button buttonReschedule;
    public Button buttonOK;

    public EditText editTextHowmuch;

    public String dialog_descriptionBill;
    public String dialog_statusBill;
    public String dialog_period;
    public String dialog_deadline;
    public String dialog_paid_date;
    public String newDeadline;
    public String get_bill_id;
    public String deadline;
    public String period;
    public String str_day;
    public String str_month;
    public String description_bill;
    public String getcost;

    public Double getBillcost;

    public int bill_id;
    public int day;
    public int month;
    public int year;

    String[] notidead;

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    String response = null;
    getHttp http;
    getHttpUpdateStatus httpUpdateStatus;
    getHttpToDB httpToDB;

    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "AllBillActivity";

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
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_billing, container, false);

        http = new getHttp(getContext());
        httpUpdateStatus = new getHttpUpdateStatus(getContext());
        httpToDB = new getHttpToDB(getContext());

        //listview
        billList = new ArrayList<HashMap<String, String>>();
        adapter = new BillAdapter(getContext(), billList);
        billingListView = (ListView) rootView.findViewById(R.id.listViewBilling);
        dialog_editbill();

        TextViewEmptyResult = (TextView) rootView.findViewById(R.id.TextViewEmptyResult);
        billingListView.setEmptyView(TextViewEmptyResult);
        billingListView.setAdapter(adapter);

        //spinner
        spinnerSort = (Spinner) rootView.findViewById(R.id.spinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected = " + selected);

                if (selected.equals("Paid")){ //0
                    Log.d(TAG, "selected = " + selected);
                    getAllBilling(cust_id, 0);
                } else if (selected.equals("Unpaid")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllBilling(cust_id, 1);
                } else if (selected.equals("All")) {
                    getAllBilling(cust_id, 2);
                } else if (selected.equals("Deleted")) {
                    getAllBilling(cust_id, 3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.d(TAG, "end get all billing");

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
        if (id == R.id.action_add) {
            Intent intent = new Intent(getActivity(), NewBill.class);
            startActivity(intent);
            getActivity().finish();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void dialog_editbill(){
        billingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                Log.d(TAG, "position " + position);
                HashMap<String, String> hashmap = (HashMap<String, String>) parent.getItemAtPosition(position);
                Log.d(TAG, hashmap.get("description_bill"));

                //make dialog
                dialogEditBill = new Dialog(view.getContext());
                dialogEditBill.getWindow();
                dialogEditBill.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogEditBill.setContentView(R.layout.dialog_editbill);
                dialogEditBill.setCancelable(true);
                dialogEditBill.show();

                ////////////////////////////////dialog's data

                //findviewbtid
                textViewDescription_bill = (TextView) dialogEditBill.findViewById(R.id.textViewDescription_bill);
                textViewStatus_bill = (TextView) dialogEditBill.findViewById(R.id.textViewStatus_bill);
                textViewPeriod = (TextView) dialogEditBill.findViewById(R.id.textViewPeriod);
                textViewDeadline = (TextView) dialogEditBill.findViewById(R.id.textViewDeadline);
                textViewPaid_date = (TextView) dialogEditBill.findViewById(R.id.textViewPaid_date);

                //setstring
                period = hashmap.get("period");
                deadline = hashmap.get("deadline");

                dialog_descriptionBill = hashmap.get("description_bill");
                dialog_statusBill = hashmap.get("status_bill");
                dialog_period = "Period : " + period;
                dialog_deadline = "Deadline : " + deadline;

                //bill_id = Integer.parseInt(hashmap.get("get_bill_id"));
                String str_bill_id = hashmap.get("bill_id") + "";
                bill_id = Integer.parseInt(str_bill_id);
                Log.d(TAG, "get_bill_id = " + get_bill_id);

                Log.d(TAG, "dialog_descriptionBill = " + dialog_descriptionBill);
                Log.d(TAG, "dialog_statusBill = " + dialog_statusBill);
                Log.d(TAG, "dialog_period = " + dialog_period);
                Log.d(TAG, "dialog_deadline = " + dialog_deadline);

                //settext
                textViewDescription_bill.setText(dialog_descriptionBill);
                textViewStatus_bill.setText(dialog_statusBill);
                textViewPeriod.setText(dialog_period);
                textViewDeadline.setText(dialog_deadline);

                //set paid_date text
                if (hashmap.get("paid_date").trim().equals("Unpaid")){
                    //null value = unpaid >> textViewPaid_date = gone
                    textViewPaid_date.setVisibility(View.GONE);
                } else {
                    dialog_paid_date = "Paid Date : " + hashmap.get("paid_date");
                    textViewPaid_date.setText(dialog_paid_date);
                }

                //set color
                if (dialog_statusBill.equals("Unpaid")){
                    textViewStatus_bill.setTextColor(Color.parseColor("#e54649")); //red
                } else {
                    textViewStatus_bill.setTextColor(Color.parseColor("#088A4B")); //green
                }

                Log.d(TAG, "Start button");
                ////////////////////////////////dialog's button
                //paid button
                buttonPaidBill = (Button) dialogEditBill.findViewById(R.id.buttonPaidBill);
                buttonPaidBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick Button Paid");
                        //flagSort = (?) > Paid
                        addTransaction();
                        billList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialogEditBill.cancel();
                    }
                });

                //delete button
                buttonDeleteBill = (Button) dialogEditBill.findViewById(R.id.buttonDeleteBill);
                buttonDeleteBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick Button Deleted");
                        //flagSort = (?) > Deleted
                        updateStatus(cust_id, bill_id, 1, ""); // 1= deleted
                        billList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialogEditBill.cancel();
                    }
                });

                //reschedule button
                buttonReschedule = (Button) dialogEditBill.findViewById(R.id.buttonReschedule);
                buttonReschedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick Button reschedule");
                        //flagSort = (?) > Paid
                        reschedule(dialog_period, deadline); //change dealine
                        updateStatus(cust_id, bill_id, 2, newDeadline); // other = unpaid
                        billList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialogEditBill.cancel();
                    }
                });
            }
        });
    }

    public void reschedule(String dialog_period, String deadline){
        List<String> items_deadline = Arrays.asList(deadline.split("\\s*-\\s*"));
        Log.d(TAG, "items_deadline = " + items_deadline);

        int get_day = Integer.parseInt(items_deadline.get(0));
        int get_month = Integer.parseInt(items_deadline.get(1));
        int get_year = Integer.parseInt(items_deadline.get(2));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, get_day);
        cal.set(Calendar.MONTH, get_month-1); // 0-11 so 1 less
        cal.set(Calendar.YEAR, get_year);

        Log.d(TAG, "cal.set(Calendar.DAY_OF_MONTH, get_day) = " + cal.get(Calendar.DAY_OF_MONTH));
        Log.d(TAG, "cal.set(Calendar.MONTH, get_month-1) = " + cal.get(Calendar.MONTH));
        Log.d(TAG, "cal.set(Calendar.YEAR, get_year); = " + cal.get(Calendar.YEAR));


        if (period.equals("Weekly")){ //week
            cal.add(Calendar.DAY_OF_MONTH, 7);

            Log.d(TAG, "period = " + period);
            Log.d(TAG, "Day = " + cal.get(Calendar.DAY_OF_MONTH));
        } if (period.equals("Monthly")){ //1month
            cal.add(Calendar.MONTH, 1);

            Log.d(TAG, "period = " + period);
            Log.d(TAG, "MONTH = " + cal.get(Calendar.MONTH));
        } if (period.equals("6 Monthly")){ //6month
            cal.add(Calendar.MONTH, 6);

            Log.d(TAG, "period = " + period);
            Log.d(TAG, "6 MONTH = " + cal.get(Calendar.MONTH));
        } else if (period.equals("Yearly")){ //yearly
            cal.add(Calendar.YEAR, 1);

            Log.d(TAG, "period = " + period);
            Log.d(TAG, "YEAR = " + cal.get(Calendar.YEAR));
        }

        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);

        //day '01'
        if (day<10){
            str_day = "0"+day;
        } else {
            str_day = day + "";
        }

        //month '01'
        if(month<10) {
            str_month = "0"+ month;
        } else {
            str_month = month + "";
        }

        newDeadline = str_day + "-"+ str_month + "-" + year;
        Log.d(TAG, "new_deadline = " + newDeadline);
    }

    /////////////////////////////////////////////////////////////////////////////addtransactiontodb
    public void addTransaction(){
        description_bill = dialog_descriptionBill + "";
        //dialog ask for bill's amoung
        billCost = new Dialog(getActivity());
        billCost.getWindow();
        billCost.requestWindowFeature(Window.FEATURE_NO_TITLE);
        billCost.setContentView(R.layout.billcost);
        billCost.setCancelable(true);
        billCost.show();

        //findview
        editTextHowmuch = (EditText) billCost.findViewById(R.id.editTextHowmuch);
        buttonOK = (Button) billCost.findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text
                getcost = editTextHowmuch.getText().toString().trim();

                if (getcost.isEmpty()){
                    Toast.makeText(billCost.getContext(), "How much?", Toast.LENGTH_LONG).show();
                } else {
                    getBillcost = Double.parseDouble(getcost);

                    Log.d(TAG, "cust_id = " + cust_id);
                    Log.d(TAG, "description_bill = " + description_bill);
                    Log.d(TAG, "getBillcost = " + getBillcost);

                    updateStatus(cust_id, bill_id, 0, ""); // 0 = paid
                    addTransactionToDB(cust_id, description_bill, getBillcost , "Expense", "Bill");
                    billCost.cancel();
                }
            }
        });
    }

    public void addTransactionToDB(String cust_id, String description, double cost, String transaction, String category){
        Log.d(TAG, cust_id + "cust_id");
        Log.d(TAG, description + "description");
        Log.d(TAG, cost + "cost");
        Log.d(TAG, transaction + "transaction");
        Log.d(TAG, category + "category");

        try {
            Log.d(TAG,"start transaction");
            httpToDB.run(BASE_URL + "/insertTransaction.php?cust_id=" + cust_id+"&description="+ description +"&cost=" + cost +"&transaction=" + transaction +"&category="+category);
            Log.d(TAG,"end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    // ** must have for connect DB
    public class getHttpToDB {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpToDB(Context context) {
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
                            Log.d(TAG,"insert success");
                        }
                    });
                }
            });
        }
    }

    ////////////////////////////////////////////////////////////////////////////////get billing data
    public String getAllBilling(String cust_id, int flagSort){
        try {
            Log.d(TAG,"flagSort = " + flagSort);
            Log.d(TAG,"start select");
            http.run(BASE_URL + "/showAllBilling.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    public void showBillingToListView(String allBilling){
        Log.d(TAG, "allBilling " + allBilling);

        String[] billInfo;
        String period;
        String description_bill;
        String status_bill;
        String deadline;
        String paid_date;

        billList.clear();

        HashMap<String, String> map;

        Scanner scanner = new Scanner(allBilling);

        for(int i = 0; scanner.hasNext(); i++){
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            billInfo = data.split(",");

            if(billInfo.length >= 5) {

                period = billInfo[0];
                description_bill = billInfo[1];
                status_bill = billInfo[2];
                deadline = billInfo[3];
                get_bill_id = billInfo[4];
                paid_date = billInfo[5];

                map = new HashMap<String, String>();
                map.put("period", period);
                map.put("description_bill", description_bill);
                map.put("status_bill", status_bill);
                map.put("deadline", deadline);
                map.put("bill_id", get_bill_id);
                map.put("paid_date", paid_date);

                billList.add(map);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public class getHttp {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttp(Context context) {
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
                                showBillingToListView(response.body().string());
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

    //////////////////////////////////////////////////////////////////////////////update bill status
    public void updateStatus(String cust_id, int bill_id, int flagSort, String newDeadline){
        try {
            Log.d(TAG,"bill_id = " + bill_id);
            Log.d(TAG,"start select");
            httpUpdateStatus.run(BASE_URL + "/billUpdateStatus.php?cust_id=" + cust_id + "&bill_id=" + bill_id + "&flagSort=" + flagSort  + "&newDeadline=" + newDeadline);
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

    public String[] getDeadline() {
        if(dialog_deadline != null) {
            notidead = dialog_deadline.split("-");

        }
        return this.notidead;
    }
}

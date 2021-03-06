package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
 * {@link Goal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Goal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Goal extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    // dont deleted
    private String mParam1;
    private String mParam2;

    public Spinner spinnerSort;

    //**get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //**connect DB
    getHttp http;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    //**for log
    private final String TAG = "AllGoalActivity";

    private View mView;

    ArrayList<HashMap<String, String>> goalList;
    GoalAdapter adapter;
    ListView goalListView;

    private OnFragmentInteractionListener mListener;

    public Goal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Goal.
     */
    // TODO: Rename and change types and number of parameters
    public static Goal newInstance(String param1, String param2) {
        Goal fragment = new Goal();
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

        ((MainActivity)getActivity()).setTitle("Goal");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_goal, container, false);

        goalList = new ArrayList<HashMap<String, String>>();
        adapter = new GoalAdapter(getContext(), goalList);
        goalListView = (ListView) rootView.findViewById(R.id.listViewGoal);

        TextView TextViewEmptyResult = (TextView) rootView.findViewById(R.id.TextViewEmptyResult);
        goalListView.setEmptyView(TextViewEmptyResult);

        goalListView.setAdapter(adapter);
        goalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d(TAG, "position " + position);

                HashMap<String, String> hashmap = (HashMap<String, String>) parent.getItemAtPosition(position);
                Log.d(TAG, hashmap.get("budget_goal"));

                //sent value
                Intent i = new Intent(getActivity(), EditGoal.class);
                i.putExtra("hashmap", hashmap);
                startActivity(i);
            }
        });

        http = new getHttp(getContext());

        //spinner
        spinnerSort = (Spinner) rootView.findViewById(R.id.spinnerSort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "selected = " + selected);

                if (selected.equals("All")){
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 4);
                } else if (selected.equals("On Process")) { //0
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 0);
                } else if (selected.equals("Achieved")) { //1
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 1);
                } else if (selected.equals("Price : Low-High")) { //2
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 2);
                } else if (selected.equals("Price : High-Low")) { //3
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 3);
                }  else if (selected.equals("Deleted")) {
                    getAllGoal(cust_id, 5);
                } else if (selected.equals("Unachieve")) { //6
                    Log.d(TAG, "selected = " + selected);
                    getAllGoal(cust_id, 6);
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
        inflater.inflate(R.menu.goal_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(getActivity(), NewGoal.class);
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

    public void getAllGoal(String cust_id, int flagSort){
        try {
            Log.d(TAG,"flagSort = " + flagSort);
            Log.d(TAG,"start select");
            http.run(BASE_URL + "/showAllGoal.php?cust_id=" + cust_id + "&flagSort=" + flagSort);
            Log.d(TAG,"end select");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
    }

    public void showGoalToListView(String allGoal){
        Log.d(TAG, "allGoal " + allGoal);

        String[] goalInfo;

        String goal_id;
        String ending_date;
        String description_goal;
        String status_goal;
        String budget_goal;
        String savingplan;
        String suggest_cost;
        String current_goal;

        goalList.clear();

        HashMap<String, String> map;

        Scanner scanner = new Scanner(allGoal);

        for(int i = 0; scanner.hasNext(); i++) {
            String data = scanner.nextLine();
            Log.d(TAG, "data has next " + data);

            goalInfo = data.split(",");

            if (goalInfo.length >= 8) {

                goal_id = goalInfo[0];  //
                ending_date = goalInfo[1];
                description_goal = goalInfo[2];
                status_goal = goalInfo[3];
                budget_goal = goalInfo[4];
                savingplan = goalInfo[5]; //
                suggest_cost = goalInfo[6];
                current_goal = goalInfo[7];

                map = new HashMap<String, String>();

                map.put("goal_id", goal_id);
                map.put("ending_date", ending_date);
                map.put("description_goal", description_goal);
                map.put("status_goal", status_goal);
                map.put("budget_goal", budget_goal);
                map.put("savingplan", savingplan);
                map.put("suggest_cost", suggest_cost);
                map.put("current_goal", current_goal);

                goalList.add(map);
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
                                showGoalToListView(response.body().string());
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
}

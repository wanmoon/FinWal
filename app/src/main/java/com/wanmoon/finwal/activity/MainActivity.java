package com.wanmoon.finwal.activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;
import com.wanmoon.finwal.R;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.onesignal.*;
//import com.onesignal.OneSignal.NotificationOpenedHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , Billing.OnFragmentInteractionListener
           , Goal.OnFragmentInteractionListener , Dashboard.OnFragmentInteractionListener
           , Home.OnFragmentInteractionListener , History.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private TextView textViewTitle;

    FloatingActionButton fab_plus, fab_speech, fab_scan, fab_typing;
    Animation fab_open, fab_close,  fab_backward, fab_forward;
    boolean isopen = false;

    private Handler handler;
    private Runnable runnable;


    private double sumIncomeMonth = -1;
    private double sumExpenseMonth = -1;
    private double monthBalance;
    private double sumIncomeYear = -1;
    private double sumExpenseYear = -1;
    private double YearBalance;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    //connect DB
    String response = null;
    getHttpIncomeMonth httpIncomeMonth;
    getHttpExpenseMonth httpExpenseMonth;
    getHttpIncomeYear httpIncomeYear;
    getHttpExpenseYear httpExpenseYear;


    //for log
    private final String TAG = "MainActivity";
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Home HomeFragment = new Home();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, HomeFragment);
        transaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        httpExpenseMonth = new getHttpExpenseMonth(getApplicationContext());
        httpIncomeMonth = new getHttpIncomeMonth(getApplicationContext());
        httpExpenseYear = new getHttpExpenseYear(getApplicationContext());
        httpIncomeYear = new getHttpIncomeYear(getApplicationContext());
        sumExpenseMonthToDB(cust_id);
        sumIncomeMonthToDB(cust_id);
        sumIncomeYearToDB(cust_id);
        sumExpenseYearToDB(cust_id);





        textViewTitle = (TextView) findViewById(R.id.toolbar_title);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_speech = (FloatingActionButton) findViewById(R.id.fab_speech);
        fab_scan = (FloatingActionButton) findViewById(R.id.fab_scan);
        fab_typing = (FloatingActionButton) findViewById(R.id.fab_typing);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopen) {
                    fab_speech.startAnimation(fab_close);
                    fab_scan.startAnimation(fab_close);
                    fab_typing.startAnimation(fab_close);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(false);
                    fab_scan.setClickable(false);
                    fab_typing.setClickable(true);
                    isopen = false;
                } else {
                    fab_speech.startAnimation(fab_open);
                    fab_scan.startAnimation(fab_open);
                    fab_typing.startAnimation(fab_open);
                    fab_plus.startAnimation(fab_backward);
                    fab_speech.setClickable(true);
                    fab_scan.setClickable(true);
                    fab_typing.setClickable(true);
                    isopen = true;
                }
            }
        });

        fab_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpeechToText.class);
                startActivity(i);
            }
        });
        fab_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTransaction.class);
                startActivity(i);
            }
        });
        fab_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Barcode.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        textViewUserEmail = (TextView)headerview.findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header_main);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
               // Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);

            }
        });

        //scan













    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
//            Home HomeFragment = new Home();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, HomeFragment);
//            transaction.commit();



        } else if (id == R.id.nav_billing) {
            Billing BillingFragment = new Billing();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, BillingFragment);
            transaction.commit();



        } else if (id == R.id.nav_goal){
            Goal GoalFragment = new Goal();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, GoalFragment);
            transaction.commit();



        } else if (id == R.id.nav_dashboard) {

            Bundle bundle = new Bundle();
            bundle.putDouble("sumIncomeMonth", sumIncomeMonth);
            bundle.putDouble("sumExpenseMonth", sumExpenseMonth);
            bundle.putDouble("sumIncomeYear", sumIncomeYear);
            bundle.putDouble("sumExpenseYear", sumExpenseYear);
            Log.d(TAG, "Main dash get sumIncomeMonth = " + sumIncomeMonth);

            Dashboard DashboardFragment = new Dashboard();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, DashboardFragment);
            transaction.commit();

            DashboardFragment.setArguments(bundle);


        } else if (id == R.id.nav_history){
            History HistoryFragment = new History();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, HistoryFragment);
            transaction.commit();


        } else if (id == R.id.nav_logout){
            firebaseAuth.signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // set name of ActionBar
    public void setTitle(String title) {
        textViewTitle.setText(title);
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



    // ** must have for connect DB
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

                                if(sumExpenseMonth != -1 && sumIncomeMonth != -1 ) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e){
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    });
                }
            });
        }
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

                                if (sumExpenseMonth != -1 && sumIncomeMonth != -1  ) {
                                    sumAllBalance();
                                }
                            } catch (NumberFormatException e) {
                                //Toast.makeText(Home.this,"", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "NumberFormatException");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    });
                }
            });
        }


    }




    //////////////////////for year balance/////////////////////

    public String sumIncomeYearToDB(String cust_id){
        try {
            Log.d(TAG,"start transaction");
            httpIncomeYear.run(BASE_URL + "/sumIncomeYear.php?cust_id=" + cust_id);
            Log.d(TAG,"end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpIncomeYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpIncomeYear(Context context) {
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
                public void onResponse(Call call, Response response) throws IOException {
                    try {

                        String income = response.body().string();
                        sumIncomeYear = Double.parseDouble(income.trim());
                        Log.d(TAG,"sumIncomeYear = " + sumIncomeYear);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseYear != -1 && sumIncomeYear != -1)   {
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


    public String sumExpenseYearToDB(String cust_id){
        try {
            Log.d(TAG,"start show");
            httpExpenseYear.run(BASE_URL + "/sumExpenseYear.php?cust_id=" + cust_id);
            Log.d(TAG,"end show");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
    public class getHttpExpenseYear {
        OkHttpClient client;
        Handler mainHandler;
        Context context;

        getHttpExpenseYear(Context context) {
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
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String expense = response.body().string();
                        sumExpenseYear = Double.parseDouble(expense.trim());
                        Log.d(TAG,"sumExpenseYear = " + sumExpenseYear);

                        Log.d(TAG,"onResponse");
                        Log.d(TAG,"show");

                        if(sumExpenseYear != -1 && sumIncomeYear != -1 ) {
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

        YearBalance = sumIncomeYear - sumExpenseYear;
        Log.d(TAG, "Year balance = " + YearBalance);


    }
//    private class ExampleNotificationOpenedHandler implements NotificationOpenedHandler {
//        /**
//         * Callback to implement in your app to handle when a notification is opened from the Android status bar or
//         * a new one comes in while the app is running.
//         * This method is located in this activity as an example, you may have any class you wish implement NotificationOpenedHandler and define this method.
//         *
//         * @param openedResult The message string the user seen/should see in the Android status bar.
//         */
//        @Override
//        public void notificationOpened(OSNotificationOpenResult openedResult) {
//            Log.e("OneSignalExample", "body: " + openedResult.notification.payload.body);
//            Log.e("OneSignalExample", "additional data: " + openedResult.notification.payload.additionalData);
//            //Log.e("OneSignalExample", "additionalData: " + additionalData.toString());
//        }
//    }
//public void onSubscribeClicked(View v) {
//    OneSignal.setSubscription(true);
//    OneSignal.promptLocation();
//
//    String ns = Context.NOTIFICATION_SERVICE;
//    NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//    nMgr.cancelAll();
//}
//
//    public void onUnsubscribeClicked(View v) {
//        OneSignal.setSubscription(false);
//    }

//   @Override
//   protected void onPause() {
//      super.onPause();
//      if (mHelper != null)
//         mHelper.dispose();
//   }
//
//   @Override
//   public void onDestroy() {
//      super.onDestroy();
//      if (mHelper != null) mHelper.dispose();
//      mHelper = null;
//   }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//            return true;
//
//        return super.onOptionsItemSelected(item);
//    }


}

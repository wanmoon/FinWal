package com.wanmoon.finwal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.wanmoon.finwal.R;

import java.io.IOException;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.CAMERA;


/**
 * Created by n.knum on 10/15/2017.
 */

public class Barcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public String name;
    public String description="";
    public String price;
    public String category;
    public String transaction = "Expense";
    public String get_cost;

    public double cost;

    public int currentApiVersion = Build.VERSION.SDK_INT;
    public int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    final String[] dbBarcode = {"4548718726783","4549738916659","8850250008811","8850999321004","4934761170206","8851952350161","8851959132364","8852001128502","8854641001740","8858891300158","8858860100260" };

    public  AlertDialog.Builder builder;

    //connect DB
    String response = null;
    getHttp http;
    public static final String BASE_URL = "http://finwal.sit.kmutt.ac.th/finwal";



    ArrayList<String> Userlist;

    //get current user
    public FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public final String cust_id = currentFirebaseUser.getUid();

    private final String TAG = "BarcodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);

        http = new getHttp(getApplicationContext());

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                } break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(Barcode.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference();

        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Detail of Product");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                description = Userlist.get(1);
                price = Userlist.get(2);
                category = Userlist.get(0);

                cost = (Double.parseDouble(price));
                get_cost = String.format("%.2f",cost);

                Log.d(TAG,"description = " + description);
                Log.d(TAG,"cost = " + cost);
                Log.d(TAG,"cate = " + category);

               addTransactionToDB(cust_id, description, cost, transaction, category);
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(Barcode.this);
            }
        });

        for(int j=0;j<dbBarcode.length;j++) {
            if (myResult.matches(dbBarcode[j]) == true) {
                DatabaseReference databaseReference2 = database.getReference();
                databaseReference2.child("Barcode").child("" + result.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Userlist = new ArrayList<String>();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Userlist.add(String.valueOf(dsp.getValue())); //add result into array list
                        }

                        builder.setMessage("Name : " + Userlist.get(1) + "\n" +
                                "Price : " + String.format("%.2f", Double.parseDouble(Userlist.get(2))) + "\n" +
                                "Category : " + Userlist.get(0));

                        AlertDialog alert1 = builder.create();
                        alert1.show();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });

            }
        }
        Toast.makeText(this, "This No. no longer in database", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), AddTransaction.class);
        startActivity(i);
    }

    public String addTransactionToDB(String cust_id, String description, double cost, String transaction, String category){

        Log.d(TAG, cust_id + "cust_id");
        Log.d(TAG, description + "description");
        Log.d(TAG, cost + "cost");
        Log.d(TAG, transaction + "transaction");
        Log.d(TAG, category + "category");
        try {
            Log.d(TAG,"start transaction");
            http.run(BASE_URL + "/insertTransaction.php?cust_id=" + cust_id+"&description="+ description +"&cost=" + cost +"&transaction=" + transaction +"&category="+category);
            Log.d(TAG,"end transaction");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"error catch");
        }
        return response;
    }

    // ** must have for connect DB
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
                            Log.d(TAG,"onResponse");
                            Log.d(TAG,"insert success");

                            Intent i = new Intent(getApplicationContext(), AllDetailTransaction.class);
                            startActivity(i);
                            finish();
                        }


                    });
                }
            });
        }
    }




    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
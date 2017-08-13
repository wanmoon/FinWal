package com.wanmoon.finwal.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanmoon.finwal.R;

/**
 * Created by pimpischaya on 8/8/2017 AD.
 */

public class Billing extends Fragment {

    View myView;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.billing, container, false);

        return myView;
    }


//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.billing);
//
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.billing_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_add:
//                Intent i=new Intent(getApplicationContext(),NewBill.class);
//                startActivity(i);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}

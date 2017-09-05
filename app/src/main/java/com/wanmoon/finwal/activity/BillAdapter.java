package com.wanmoon.finwal.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wanmoon.finwal.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pimpischaya on 9/5/2017 AD.
 */

public class BillAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> arrBill;


    public BillAdapter(Context context, ArrayList<HashMap<String, String>> arrBill) {
        this.context= context;
        this.arrBill = arrBill;

    }

    @Override
    public int getCount() {
        return arrBill.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
            convertView = mInflater.inflate(R.layout.listview_billing, parent, false);


        TextView textViewPeriod = (TextView) convertView.findViewById(R.id.textViewPeriod);
        textViewPeriod.setText(arrBill.get(position).get("period").toString());

        TextView textViewBillDescription = (TextView) convertView.findViewById(R.id.textViewBillDescription);
        textViewBillDescription.setText(arrBill.get(position).get("description_bill").toString());

//        TextView textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatus);
//        textViewStatus.setText(arrBill.get(position).get("status_bill").toString());

        TextView textViewDeadline = (TextView) convertView.findViewById(R.id.textViewDeadline);
        textViewDeadline.setText(arrBill.get(position).get("deadline").toString());



        ImageButton imageButtonEdit = (ImageButton) convertView.findViewById(R.id.imageButtonEdit);
        imageButtonEdit.setImageResource(R.mipmap.ic_mode_edit_white_24dp);



//        Spinner spinnerSort;
//        spinnerSort = (Spinner) convertView.findViewById(R.id.spinnerSort);
//        String[] spinnerValue = new String[]{
//                "Time",
//                "Category",
//                "Category : A-Z",
//                "Category : Most popular",
//                "Price : Low-High",
//                "Price : High-Low"
//        };
//        final List<String> mspinnerSort = new ArrayList<>(Arrays.asList(spinnerValue));
//        ArrayAdapter<String> aSpinnerSort = new ArrayAdapter<String>(context.getApplicationContext()
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


        return null;
    }
}

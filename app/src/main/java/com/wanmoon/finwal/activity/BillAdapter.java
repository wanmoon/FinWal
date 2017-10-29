package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.wanmoon.finwal.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pimpischaya on 9/5/2017 AD.
 */

public class BillAdapter extends BaseAdapter implements SpinnerAdapter{

    Context context;
    ArrayList<HashMap<String, String>> arrBill;


    public BillAdapter(Context context, ArrayList<HashMap<String, String>> arrBill) {
        this.context= context;
        this.arrBill = arrBill;

    }

    public int getCount() {
        //return arrBill.size();
        return (arrBill.isEmpty())? 0: arrBill.size();
    }

    public HashMap<String, String> getItem(int position) {
        return arrBill.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.listview_billing, parent, false);

        TextView textViewPeriod = (TextView) view.findViewById(R.id.textViewPeriod);
        textViewPeriod.setText(arrBill.get(position).get("period").toString());

        TextView textViewBillDescription = (TextView) view.findViewById(R.id.textViewBillDescription);
        textViewBillDescription.setText(arrBill.get(position).get("description_bill").toString());

        TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        textViewStatus.setText(arrBill.get(position).get("status_bill").toString());
        //if else to check transaction for set color of 'status_bill'
        if(arrBill.get(position).get("status_bill").toString().equals("Paid")){
            //income : green
            textViewStatus.setTextColor(Color.parseColor("#088A4B"));
        } else if (arrBill.get(position).get("status_bill").toString().equals("Unpaid") || arrBill.get(position).get("status_bill").toString().equals("Deleted")){
            //expense : red
            textViewStatus.setTextColor(Color.parseColor("#e54649"));
        }

        TextView textViewDeadline = (TextView) view.findViewById(R.id.textViewDeadline);
        textViewDeadline.setText(arrBill.get(position).get("deadline").toString());

        ImageView imageButtonEdit = (ImageView) view.findViewById(R.id.imageButtonEdit);
        imageButtonEdit.setImageResource(R.mipmap.ic_edit_black_24dp);

        return view;
    }

}

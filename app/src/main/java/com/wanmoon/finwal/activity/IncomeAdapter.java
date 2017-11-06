package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanmoon.finwal.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wanmoon on 9/6/2017 AD.
 */

public class IncomeAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrIncome;

    private Float cost;
    private String get_cost;


    public IncomeAdapter(Context context, ArrayList<HashMap<String, String>> arrIncome) {
        this.context= context;
        this.arrIncome = arrIncome;

    }

    public int getCount() {
        return (arrIncome.isEmpty())? 0: arrIncome.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        NumberFormat nf = NumberFormat.getNumberInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("฿");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_history, parent, false);

        TextView textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
        textViewDescription.setText(arrIncome.get(position).get("description").toString());

        TextView textViewDate = (TextView)view.findViewById(R.id.textViewDate);
        textViewDate.setText(arrIncome.get(position).get("timestamp").toString());

        get_cost = arrIncome.get(position).get("cost").toString();
        cost = Float.parseFloat( get_cost );
        TextView textViewCost = (TextView)view.findViewById(R.id.textViewCost);
        textViewCost.setText("฿" + nf.format(cost) );
        textViewCost.setTextColor(Color.parseColor("#088A4B"));

//        TextView textViewCost = (TextView)view.findViewById(R.id.textViewCost);
//        textViewCost.setText(arrIncome.get(position).get("cost").toString() + " Baht");
//        textViewCost.setTextColor(Color.parseColor("#088A4B"));


        ImageView imageViewCategoryIcon = (ImageView)view.findViewById(R.id.imageViewCategoryIcon);

        //switch case for check category to set the transaction icon
        switch(arrIncome.get(position).get("category").toString()){
            //case for income
            case "Salary"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_salary);
                break;
            case "Gift"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_gift);
                break;
            case "Loan"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_loan);
                break;
            case "Family and Personal"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_personalandfamily);
                break;
            case "Extra income"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_extraincome);
                break;
        }
        return view;
    }
}

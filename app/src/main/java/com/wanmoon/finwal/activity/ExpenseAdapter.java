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

public class ExpenseAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrExpense;

    private Float cost;
    private String get_cost;


    public ExpenseAdapter(Context context, ArrayList<HashMap<String, String>> arrExpense) {
        this.context= context;
        this.arrExpense = arrExpense;

    }

    public int getCount() {
        return (arrExpense.isEmpty())? 0: arrExpense.size();
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
        textViewDescription.setText(arrExpense.get(position).get("description").toString());

        TextView textViewDate = (TextView)view.findViewById(R.id.textViewDate);
        textViewDate.setText(arrExpense.get(position).get("timestamp").toString());

//        TextView textViewCost = (TextView)view.findViewById(R.id.textViewCost);
//        textViewCost.setText(arrExpense.get(position).get("cost").toString() + " Baht");

        get_cost = arrExpense.get(position).get("cost").toString();
        cost = Float.parseFloat( get_cost );
        TextView textViewCost = (TextView)view.findViewById(R.id.textViewCost);
        textViewCost.setText("฿" + nf.format(cost) );
        textViewCost.setTextColor(Color.parseColor("#088A4B"));


        //if else to check transaction for set color of 'textViewCost'
        if(arrExpense.get(position).get("transaction").toString().equals("Income")){
            //income : green
            textViewCost.setTextColor(Color.parseColor("#088A4B"));
        } else if (arrExpense.get(position).get("transaction").toString().equals("Expense")){
            //expense : red
            textViewCost.setTextColor(Color.parseColor("#e54649"));
        }

        ImageView imageViewCategoryIcon = (ImageView)view.findViewById(R.id.imageViewCategoryIcon);

        //switch case for check category to set the transaction icon
        switch(arrExpense.get(position).get("category").toString()){
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

            //case for expense
            case "Bill"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_bill);
                break;
            case "Education"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_education);
                break;
            case "Entertainment"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_entertainment);
                break;
            case "Food and Drink"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_foodanddrink);
                break;
            case "Shopping"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_shopping);
                break;
            case "Transport"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_transport);
                break;
            case "Travel"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_travel);
                break;
            case "Healthcare"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_health);
                break;
            case "Saving and Investment"
                    :     imageViewCategoryIcon.setImageResource(R.mipmap.ic_savingandinvestment);
                break;

        }
        return view;
    }
}

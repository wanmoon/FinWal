package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanmoon.finwal.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wanmoon on 9/6/2017 AD.
 */

public class IncomeAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrIncome;


    public IncomeAdapter(Context context, ArrayList<HashMap<String, String>> arrTransaction) {
        this.context= context;
        this.arrIncome = arrTransaction;

    }

    public int getCount() {
        return arrIncome.size();
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

        if(view == null)
            view = mInflater.inflate(R.layout.listview_allincome, parent, false);

        TextView textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
        textViewDescription.setText(arrIncome.get(position).get("description").toString());

        TextView textViewDate = (TextView)view.findViewById(R.id.textViewDate);
        textViewDate.setText(arrIncome.get(position).get("timestamp").toString());

        TextView textViewCost = (TextView)view.findViewById(R.id.textViewCost);
        textViewCost.setText(arrIncome.get(position).get("cost").toString() + " Baht");
        textViewCost.setTextColor(Color.parseColor("#39dd92"));

        ImageButton imageButtonEdit = (ImageButton)view.findViewById(R.id.imageButtonEdit);
        imageButtonEdit.setImageResource(R.mipmap.ic_mode_edit_white_24dp);

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

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

public class GoalAdapter extends BaseAdapter implements SpinnerAdapter {

    Context context;
    ArrayList<HashMap<String, String>> arrGoal;

    public GoalAdapter(Context context, ArrayList<HashMap<String, String>> arrGoal) {
        this.context= context;
        this.arrGoal = arrGoal;

    }

    public int getCount() {
        return (arrGoal.isEmpty())? 0: arrGoal.size();
    }

    public HashMap<String, String> getItem(int position) {
        return arrGoal.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.listview_goal, parent, false);

        TextView textViewCost = (TextView) view.findViewById(R.id.textViewCost);
        textViewCost.setText(arrGoal.get(position).get("budget_goal").toString() + " Baht");

        TextView textViewGoalDescription = (TextView) view.findViewById(R.id.textViewGoalDescription);
        textViewGoalDescription.setText(arrGoal.get(position).get("description_goal").toString());

        TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        textViewStatus.setText(arrGoal.get(position).get("status_goal").toString());
        if(arrGoal.get(position).get("status_goal").toString().equals("Achieved")){
            //income : green
            textViewStatus.setTextColor(Color.parseColor("#088A4B"));
        } else if (arrGoal.get(position).get("status_goal").toString().equals("Unachieve")){
            //expense : red
            textViewStatus.setTextColor(Color.parseColor("#e54649"));
        }

        TextView textViewDeadline = (TextView) view.findViewById(R.id.textViewDeadline);
        textViewDeadline.setText(arrGoal.get(position).get("ending_date").toString());

        ImageView imageButtonEdit = (ImageView) view.findViewById(R.id.imageButtonEdit);
        imageButtonEdit.setImageResource(R.mipmap.ic_mode_edit_white_24dp);

        return view;
    }
}


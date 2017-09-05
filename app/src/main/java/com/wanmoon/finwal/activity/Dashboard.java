package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.wanmoon.finwal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //for line chart

    private float[] yData = {10.0f, 90.0f};
    private String[] xData = {"Income", "Expense"};
    LineChart lineChart;


    private LineGraphSeries<DataPoint> series1, series2;

    private ImageView imageViewFrame;



    //for pie chart
    private final String TAG = "Dashboard";
    private float[] yDataIncome = {10.0f, 10.0f, 20.0f, 40.0f ,20.0f};
    private String[] xDataIncome = {"Salary", "Gift","Loan", "Family and Personal", "Extra income"};
    private float[] yDataExpense = {10.0f, 10.0f, 20.0f, 40.0f ,20.0f};
    private String[] xDataExpense = {"Bill", "Education","Entertainment", "Food and Drink", "Shopping"};
    PieChart pieChart;
    private View mView;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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

        ((MainActivity)getActivity()).setTitle("Dashboard");

        Log.d(TAG, "onCreate: Start to create chart ");





    }

    private void initData() {

        lineChart = (LineChart) mView.findViewById(R.id.lineChart);
        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x  = 0;
        int numDataPoints = 1000;
        for (int i = 0; i< numDataPoints ; i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            xAXES.add(i, String.valueOf(x));
        }

        String[] xaxes = new String[xAXES.size()];
        for (int i = 0; i< xAXES.size() ; i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.GREEN);

        LineDataSet lineDataSet2 = new LineDataSet(yAXEScos, "sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));
        lineChart.setVisibleXRangeMaximum(100f);

//
//        double x,y;
//        x=0;
//
//        GraphView graph = (GraphView)  mView.findViewById(R.id.graph);
//        series1 = new LineGraphSeries<>();
//        series2 = new LineGraphSeries<>();
//
//        int numDataPoints = 500;
//        for (int i = 0; i< numDataPoints ; i++){
//            x = x + 0.1;
//            y = Math.sin(x);
//            double y2 = Math.exp(x);
//            series1.appendData(new DataPoint(x,y), true, 60);
//            series1.appendData(new DataPoint(x+0.001, y2), true, 60);
//        }
//
//        series1.setColor(Color.GREEN);
//        series2.setColor(Color.RED);
//        graph.addSeries(series1);
//        graph.addSeries(series2);
    }

    private void initDataIncome() {

        pieChart = (PieChart) mView.findViewById(R.id.pieChartIncome);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Income");
        pieChart.setCenterTextSize(8);
        //pieChart.setDrawEntryLabels(true);

        addDataSetIncome();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }


    private void initDataExpense() {
        pieChart = (PieChart) mView.findViewById(R.id.pieChartExpense);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense");
        pieChart.setCenterTextSize(8);
        //pieChart.setDrawEntryLabels(true);

        addDataSetExpense();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetIncome() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i =0 ; i < yDataExpense.length ; i++){
            yEntrys.add(new PieEntry(yDataExpense[i], i));
        }
        for (int i =0 ; i < xDataExpense.length ; i++){
            xEntrys.add(xDataExpense[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "DD");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void addDataSetExpense() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i =0 ; i < yDataIncome.length ; i++){
            yEntrys.add(new PieEntry(yDataIncome[i], i));
        }
        for (int i =0 ; i < xDataIncome.length ; i++){
            xEntrys.add(xDataIncome[i]);
        }

        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "DD");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        // add color to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add Legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_dashboard, container, false);


        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mView = rootView;

        visible();


        initDataIncome();
        initDataExpense();
        initData();

        return rootView;
    }

    public void visible(){
        imageViewFrame = (ImageView) mView.findViewById(R.id.imageViewFrame);
        imageViewFrame.setVisibility(View.VISIBLE);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

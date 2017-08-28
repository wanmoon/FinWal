package com.wanmoon.finwal.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wanmoon.finwal.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Billing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Billing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth firebaseAuth;

    private static View rootView;
    private OnFragmentInteractionListener mListener;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    //private PieChart mPieChart;


    private View mView;
    private GraphicalView mGraphView;
    private Typeface tf;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Billing.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        setHasOptionsMenu(true);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mView = rootView;
        initData();

        return rootView;


    }

    private void initData() {

        String[] codename = {
                "Income", "Expense"
        };

        double[] values = {13.6, 86.4 };
        String[] colors = {
                "#66cc00", "#e60000",
        };


        CategorySeries series = new CategorySeries("Android Platform Version");
        int length = codename.length;
        for (int i = 0; i < length; i++) {
            series.add(codename[i], values[i]);
        }

        DefaultRenderer renderer = new DefaultRenderer();
        for (int i = 0; i <  codename.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(Color.parseColor(colors[i]));
            renderer.addSeriesRenderer(seriesRenderer);
        }

        renderer.setChartTitleTextSize(60);
        //renderer.setChartTitle("Android Platform Version");
        renderer.setLabelsTextSize(40);
        renderer.setLabelsColor(Color.GRAY);
        renderer.setLegendTextSize(30);

        drawChart(series, renderer);


    }

    private void drawChart(CategorySeries series,
                           DefaultRenderer renderer) {

        if (null == mGraphView) {
            mGraphView =
                    ChartFactory.getPieChartView(getActivity(), series, renderer);

            RelativeLayout container =
                    (RelativeLayout) mView.findViewById(R.id.graph_container);

            container.addView(mGraphView);
        } else {
            mGraphView.repaint();
        }
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.billing_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_add) {
//            Intent intent = new Intent(getActivity(), NewBill.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
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

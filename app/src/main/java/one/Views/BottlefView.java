package one.Views;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import redeploy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BottlefView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BottlefView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottlefView extends Fragment {

    HttpURLConnection urlIconConnect = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private HashMap<Integer, String[]> mParam4;
    private String mParam5;
    View fView;
    private OnFragmentInteractionListener mListener;

    public BottlefView() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @param param3 Parameter 3.
     * @return A new instance of fragment WeatherOut.
     */
    // TODO: Rename and change types and number of parameters
    public static BottlefView newInstance(HashMap<Integer, String[]> param4) {

        BottlefView fragment = new BottlefView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM4, param4.toString());
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam4 = (HashMap<Integer, String[]>) getArguments().get(ARG_PARAM4);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState)
    {
        fView = inflater.inflate(R.layout.fragment_weather_out, container, false);
//                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cloudy));
        return fView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(HashMap<Integer, String[]> mapfInst, double[][] inLatLong) {
        if (mListener != null) {
            mListener.onFragmentInteraction(mapfInst, inLatLong);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
//        void onFragmentInteraction(HashMap<Integer, String[]> mapf);
        void onFragmentInteraction(HashMap<Integer, String[]> mapf, double[][] LatLong);
    }
}

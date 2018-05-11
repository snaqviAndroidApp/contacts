package everyDayWeather.Views;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import everyDayWeather.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherfView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherfView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherfView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    TextView tempFragV, despFragV;
    ImageView imageV;
    View fView;
    Icon iconCloudy;
    private OnFragmentInteractionListener mListener;

    public WeatherfView() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment WeatherOut.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherfView newInstance(String param1, String param2, String param3, ArrayList<String> param4, ArrayList<String> param5) {
        WeatherfView fragment = new WeatherfView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4.toString());
        args.putString(ARG_PARAM5, param5.toString());
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState)
    {
        fView = inflater.inflate(R.layout.fragment_weather_out, container, false);
        tempFragV = fView.findViewById(R.id.tvfView);
        despFragV = fView.findViewById(R.id.tvfViewDesc);
        imageV = fView.findViewById(R.id.iW_icon);
        despFragV.setText(mParam1);
        tempFragV.setText(mParam2);
        switch (mParam3){                            // Image Display per Weather-description, few implemented
            case "04d":                                                      // Light rain or Cloudy
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cloudy));
            case "50d":                                                      // Light rain or Cloudy
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cloudy));
            case "10d":
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rainy));
                break;
            default:
                imageV.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.sunny));
                break;
        }
        return fView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str11, int temp22, String str33, ArrayList<String> strf44, ArrayList<String> strf55) {
        if (mListener != null) {
            mListener.onFragmentInteraction(str11, temp22, str33, strf44, strf55);
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
        void onFragmentInteraction(String str1 , int tempIn2, String str3, ArrayList<String> arrStr4, ArrayList<String> arrStrArr5);
    }
}

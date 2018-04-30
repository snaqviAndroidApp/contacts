package ghar.root.weather_Test.Facial;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherOut.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherOut#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherOut extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    TextView tempFragV, despFragV;
    ImageView imageV;
    View fView;
    Icon iconCloudy;


    private OnFragmentInteractionListener mListener;

    public WeatherOut() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherOut.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherOut newInstance(String param1, String param2, String param3) {
        WeatherOut fragment = new WeatherOut();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fView = inflater.inflate(R.layout.fragment_weather_out, container, false);
        tempFragV = fView.findViewById(R.id.tvfView);
        despFragV = fView.findViewById(R.id.tvfViewDesc);
        imageV = fView.findViewById(R.id.iW_icon);
        switch (mParam3){                            // Image Display per Weather-description, few implemented
            case "04d":                                                      // Light rain or Cloudy
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cloudy));
            case "50d":                                                      // Light rain or Cloudy
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cloudy));
            case "10d":
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rainy));
                break;
            default:
                imageV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sunny));
                break;
        }
        despFragV.setText(mParam1);
        tempFragV.setText(mParam2);

        return fView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str11, int temp22, String str33) {
        if (mListener != null) {
            mListener.onFragmentInteraction(str11, temp22, str33);
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
//        void onFragmentInteraction(Uri uri);
//        void onFragmentInteraction();
        void onFragmentInteraction(String str1 , int tempIn2, String str3);
    }
}

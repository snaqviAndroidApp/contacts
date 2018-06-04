package solstice.appsback;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.net.HttpURLConnection;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragView extends Fragment {

    HttpURLConnection urlIconConnect = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private HashMap<Integer, String[]> mParam4;
//    private String mParam5;
    View fView;
    private OnFragmentInteractionListener mListener;

    public ContactsFragView() {
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
    public static ContactsFragView newInstance(HashMap<Integer, String[]> param4) {
        ContactsFragView fragment = new ContactsFragView();
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
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public View onCreateView(LayoutInflater inflater
//            , ViewGroup container
//            , Bundle savedInstanceState)
//    {
//        //fView = inflater.inflate(R.layout.fragment_weather_out, container, false);
//        return fView;
//    }

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
        void onFragmentInteraction(HashMap<Integer, String[]> mapf, double[][] LatLong);
    }
}

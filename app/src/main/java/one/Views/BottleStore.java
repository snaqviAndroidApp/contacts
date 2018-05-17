package one.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import one.*;

import one.Model.BottlesD_Data;
import one.Model.RvAdapter;

import static java.lang.Thread.sleep;

public class BottleStore extends AppCompatActivity implements BottlefView.OnFragmentInteractionListener {

    public static final int INT_mIndex = 1;
    public static final int INT_mIndex1 = 2;
    public static final int INT_mIndex2 = 3;
    String[] compDataIndex = null;

    int countHTTP;
    JSONObject jsonObj = null;
    ImageView assetImg;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<BottlesD_Data> fetchedInfo = new ArrayList<>();
    HashMap<Integer, String[]> compBData = new HashMap<>();

    String inhVal[][] = null;
    String[] BServerData_addr;
    String[] BServerData_ph;
    double[][] Lat_Long;
    String[] BServerData_Logo;
    HttpURLConnection urlConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bottle);
            assetImg = findViewById(R.id.imgURL);
            myRecyclerView = findViewById(R.id.list_forcast);                   //Dynamic ListView: RecyclerView
            myRecyclerView.hasFixedSize();
            lLayoutMngr = new LinearLayoutManager(this);
            rAdapter = new RvAdapter(fetchedInfo, this);
            myRecyclerView.setLayoutManager(lLayoutMngr);
            try {
                sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new WHttpAsyncTask().execute("http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json");
        }
    }

    @Override
    public void onFragmentInteraction(HashMap<Integer, String[]> mapExt, double[][] rcvdLatLong) {
        PresentStoreData(mapExt, rcvdLatLong);
        myRecyclerView.setAdapter(rAdapter);
    }

    private void PresentStoreData(HashMap<Integer, String[]> exthMapSent, double[][] extRcvdLatLong) {
        fetchedInfo.clear();
        double[][] inhMapLatLong = extRcvdLatLong;
        inhVal = new String[exthMapSent.size()][];
        for (Map.Entry m : exthMapSent.entrySet()) {
            inhVal[Integer.valueOf(m.getKey().toString()) - 1] = (String[]) m.getValue();
        }
//        for (int i = 0; i < exthMapSent.get(INT_mIndex).length; i++) {               // No Key:Value Approach; ON need basis
//            fetchedInfo.add(new BottlesD_Data(
//                    exthMapSent.getOrDefault(INT_mIndex, compDataIndex)[i]
//                    ,exthMapSent.getOrDefault(INT_mIndex1, compDataIndex)[i]
//                    ,exthMapSent.getOrDefault(INT_mIndex2, compDataIndex)[i]
//                    , extRcvdLatLong[i]
//            ));
//        }
        for (int indexInd = 0; indexInd < inhVal[0].length; indexInd++) {           // Key:Value Approach; Recommended
            fetchedInfo.add
                    (new BottlesD_Data
                            (
                                    inhVal[0][indexInd]
                                    , inhVal[1][indexInd]
                                    , inhVal[2][indexInd]
                                    , inhMapLatLong[indexInd]
                            )
                    );
        }
        onDestroy(exthMapSent);
    }

    private void onDestroy(final HashMap<Integer, String[]> exthMapforClear) {
        exthMapforClear.clear();
    }

    private class WHttpAsyncTask extends AsyncTask<String, String, String> {
        public static final int longNumber = 2;
        public static final int longIndex = 1;
        public static final int latIndex = 0;

        @Override
        protected void onPreExecute() {
            countHTTP = 0;
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream inputStream;
            String result = "";
            StringBuffer resultBuff = null;
            try {
                URL url = new URL(urls[0]);   // New HTTP - Implementation:
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(7000);    //7.0Sec
                // New HTTP - Implementation--------  ENDS HERE -------------

                // New HTTP data fetching - Implementation--------
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                        Log.d("Debug", "Json coversion Response " + line);
                    }
                    inputStream.close();
                    publishProgress(result);
                } finally {
                    urlConnection.disconnect();              //end connection
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("InputStream", ex.getLocalizedMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... resultIn) {
            countHTTP++;
            try {
                jsonObj = new JSONObject(resultIn[0]);
                Log.d("httpBStore_", jsonObj.toString());
                JSONArray res1 = jsonObj.getJSONArray("stores");
                JSONObject add_compObj = res1.getJSONObject(0);
                BServerData_addr = new String[jsonObj.getJSONArray("stores").length()];
                BServerData_ph = new String[jsonObj.getJSONArray("stores").length()];
                BServerData_Logo = new String[jsonObj.getJSONArray("stores").length()];
                Lat_Long = new double
                        [jsonObj.getJSONArray("stores").length()]
                        [longNumber];
                for (int iterbottle = 0; iterbottle < jsonObj.getJSONArray("stores").length(); iterbottle++) {
                    JSONObject tempJObj = res1.getJSONObject(iterbottle);
                    BServerData_addr[iterbottle] = tempJObj.getString("address")
                            + " " + add_compObj.getString("city")
                            + ", " + add_compObj.getString("state")
                            + " " + add_compObj.getString("zipcode");
                    BServerData_ph[iterbottle] = tempJObj.getString("phone");
                    BServerData_Logo[iterbottle] = tempJObj.getString("storeLogoURL");
                    Lat_Long[iterbottle][latIndex] = tempJObj.getDouble("latitude");
                    Lat_Long[iterbottle][longIndex] = tempJObj.getDouble("longitude");
                }

                compBData.put(1, BServerData_addr);
                compBData.put(2, BServerData_ph);
                compBData.put(3, BServerData_Logo);

                if (BServerData_addr.length != 0) {
                    onFragmentInteraction(compBData, Lat_Long);                               //Fragment Instantiation Dynamic
                } else {
                    Snackbar.make(Objects.requireNonNull(getCurrentFocus())
                            , MessageFormat.format("An Error occured, please try again ", null)
                            , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            } catch (JSONException ej) {
                Log.e("JParse_e", ej.getMessage());
            }
        }

        protected void onPostExecute() {
        }
    }

}

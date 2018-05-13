package one.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

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
import static java.security.AccessController.getContext;

public class BottleStore extends AppCompatActivity implements BottlefView.OnFragmentInteractionListener {
    public static final int INT_mIndex = 1;
    public static final int INT_mIndex1 = 2;
    public static final int INT_mIndex2 = 3;
    int countHTTP, hMapCounter;
    JSONObject jsonObj = null;
    ImageView assetImg;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<BottlesD_Data> fetchedInfo = new ArrayList<>();
    HashMap<Integer, String[]> compBData = new HashMap<>();
    String[] BServerData_addr;
    String[] BServerData_ph;
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
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new WHttpAsyncTask().execute("http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json");
        }
    }
    @Override
    public void onFragmentInteraction(HashMap<Integer, String[]> mapExt) {
        PresentStoreData(mapExt);
        myRecyclerView.setAdapter(rAdapter);
    }
    private void PresentStoreData(HashMap<Integer, String[]> exthMapSent) {
        fetchedInfo.clear();
        for(Map.Entry m:exthMapSent.entrySet()){
            for (int i = 0; i < exthMapSent.get(INT_mIndex).length; i++) {
                fetchedInfo.add(new BottlesD_Data(
                        exthMapSent.get(INT_mIndex)[hMapCounter]
                        ,exthMapSent.get(INT_mIndex1)[hMapCounter]
                        ,exthMapSent.get(INT_mIndex2)[hMapCounter]
                ));
            }
            hMapCounter++;
        }
        onDestroy(exthMapSent);
    }

    private void onDestroy(final HashMap<Integer, String[]> exthMapforClear) {

        exthMapforClear.clear();
        Log.i("HashMap_mem","");
//        super.onDestroy();
    }

    private class WHttpAsyncTask extends AsyncTask<String, String, String> { // AysncTask Started
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
                }finally {
                    urlConnection.disconnect();              //end connection
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Log.e("InputStream", ex.getLocalizedMessage());
            }
            return null;
        }
        protected void onProgressUpdate(String... resultIn){
            countHTTP++;
            try {
                        jsonObj = new JSONObject(resultIn[0]);
                        Log.d("httpBStore_", jsonObj.toString());
                        JSONArray res1 = jsonObj.getJSONArray("stores");
                        JSONObject add_compObj = res1.getJSONObject(0);
                        BServerData_addr = new String[jsonObj.getJSONArray("stores").length()];
                        BServerData_ph = new String[jsonObj.getJSONArray("stores").length()];
                        BServerData_Logo = new String[jsonObj.getJSONArray("stores").length()];
                        for (int iterbottle = 0; iterbottle < jsonObj.getJSONArray("stores").length(); iterbottle++) {
                            JSONObject tempJObj  = res1.getJSONObject(iterbottle);
                            BServerData_addr[iterbottle] = tempJObj.getString("address")
                                    + " " + add_compObj.getString("city")
                                    + ", " + add_compObj.getString("state")
                                    + " " + add_compObj.getString("zipcode");
                            BServerData_ph[iterbottle]   = tempJObj.getString("phone");
                            BServerData_Logo[iterbottle] = tempJObj.getString("storeLogoURL");
                        }
                        compBData.put(1,BServerData_addr);
                        compBData.put(2,BServerData_ph);
                        compBData.put(3,BServerData_Logo);

//                if(w_code == 200 && w_codef == 200){
                if(BServerData_addr.length != 0){
                    onFragmentInteraction(compBData);               //Fragment Instantiation Dynamic
//                    assetImg.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloudy));
                }
                else {
                    Snackbar.make(Objects.requireNonNull(getCurrentFocus())
                            , MessageFormat.format("An Error occured, please try again ", null)
                            , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
            catch (JSONException ej){
                Log.e("JParse_e", ej.getMessage());
            }
        }
        protected void onPostExecute() {
        }
    }

}

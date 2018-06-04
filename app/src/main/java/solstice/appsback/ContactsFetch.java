
package solstice.appsback;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
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

import challenge.solstice.myapp.R;
import solstice.DAO.RvAdapter;
import solstice.model.Contacts_Mems;
import static java.lang.Thread.sleep;

public class ContactsFetch extends AppCompatActivity implements
        ContactsFragView.OnFragmentInteractionListener {

    public static final int INT_mIndex = 1;
    public static final int INT_mIndex1 = 2;
    public static final int INT_mIndex2 = 3;
    String[] compDataIndex = null;
    int countHTTP;
    HttpURLConnection urlConnection = null;

    ImageView assetImg;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<Contacts_Mems> fetchedInfo = new ArrayList<>();
    HashMap<Integer, String[]> compBData = new HashMap<>();
    String inhVal[][] = null;

//    JSON-data Extraction
    JSONObject jsonObj = null;

    JSONArray jArrayRcvd = null;
    String[][] jsonInData;
    String[] contacts_addr;
    String[][] contacts_ph;
    double[][] Lat_Long;
    String[] smallImageURL;
    String[] largeImageURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contlists);
            assetImg = findViewById(R.id.imgURL);
            myRecyclerView = findViewById(R.id._mainContacts);                   //Dynamic ListView: RecyclerView
            myRecyclerView.hasFixedSize();
            lLayoutMngr = new LinearLayoutManager(this);
            rAdapter = new RvAdapter(fetchedInfo, this);
            myRecyclerView.setLayoutManager(lLayoutMngr);
            try {
                sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            new WHttpAsyncTask().execute("http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json");
            new WHttpAsyncTask().execute("https://s3.amazonaws.com/technical-challenge/v3/contacts.json");
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
//        inhVal = new String[exthMapSent.size()][];
        for (String[] strings : inhVal = new String[exthMapSent.size()][]) {}
        for (Map.Entry m : exthMapSent.entrySet()) {
            inhVal[Integer.valueOf(m.getKey().toString()) - 1] = (String[]) m.getValue();
        }
        for (int indexInd = 0; indexInd < inhVal[0].length; indexInd++) {           // Key:Value Approach; Recommended
            fetchedInfo.add
                    (new Contacts_Mems
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
                        Log.d("allContacts", "Json-Data Rcvd" + line);
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

                jArrayRcvd = new JSONArray(resultIn[0]);
                Log.d("post_jArray", jArrayRcvd.toString());

//                JSONArray res1 = jsonObj.getJSONArray("stores");
//                JSONObject add_compObj = res1.getJSONObject(0);

                jsonInData = new String[jArrayRcvd.length()][];
                contacts_addr = new String[jsonInData.length];
                smallImageURL = new String[jsonInData.length];
                largeImageURL = new String[jsonInData.length];
                contacts_ph = new String[jArrayRcvd.length()][];

//                Lat_Long = new double[jsonObj.getJSONArray("stores").length()][longNumber];

                for (int iterbottle = 0; iterbottle < jsonInData.length; iterbottle++) {
                    JSONObject tempJObj = jArrayRcvd.getJSONObject(iterbottle);
                    contacts_addr[iterbottle] = tempJObj.getJSONObject("address").getString("street")
                            + " " + tempJObj.getJSONObject("address").getString("city")
                            + ", " + tempJObj.getJSONObject("address").getString("state")
                            + " " + tempJObj.getJSONObject("address").getString("zipCode")
                            + ", " + tempJObj.getJSONObject("address").getString("country");
                    smallImageURL[iterbottle] = tempJObj.getString("smallImageURL");
                    largeImageURL[iterbottle] = tempJObj.getString("largeImageURL");

                    contacts_ph[iterbottle][0] = tempJObj.getJSONObject("phone").getString("work");
                    contacts_ph[iterbottle][1] = tempJObj.getJSONObject("phone").getString("home");
                    contacts_ph[iterbottle][2] = tempJObj.getJSONObject("phone").getString("mobile");

//                    Lat_Long[iterbottle][latIndex] = tempJObj.getDouble("latitude");
//                    Lat_Long[iterbottle][longIndex] = tempJObj.getDouble("longitude");
                }

//                compBData.put(1, contacts_addr);
//                compBData.put(2, contacts_ph);
//                compBData.put(3, smallImageURL);

                if (contacts_addr.length != 0) {
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

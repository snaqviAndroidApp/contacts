package ghar.root.weather_Test.Facial;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
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
import java.util.List;
import java.util.Objects;

import ghar.root.weather_Test.bkEnd.ForecastData;
import ghar.root.weather_Test.bkEnd.RvAdapter;


public class WeatherAct extends AppCompatActivity implements WeatherOut.OnFragmentInteractionListener {

    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<ForecastData> forecastMembers = new ArrayList<>();
    //private Menu w_menuT;

    FragmentTransaction transaction;
    WeatherOut wFrag;
    EditText uInCity;
    TextView tempV, despV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_weather);
        }
        uInCity = findViewById(R.id.userIn);

        }

    @Override
    public void onFragmentInteraction(String descIn, int tempIn, String icon_in) {
        wFrag = WeatherOut.newInstance(descIn, Integer.toString(tempIn), icon_in);
        transaction = getSupportFragmentManager().beginTransaction();       // Fragment Replacement Approach
        transaction.replace(R.id.fragLayout, wFrag);
        transaction.commit();

        myRecyclerView = findViewById(R.id.list_forcast);  //ListView: RecyclerView a Dynamic View Structure
        myRecyclerView.hasFixedSize();
        lLayoutMngr = new LinearLayoutManager(this);
        rAdapter = new RvAdapter(forecastMembers, this); // create Adapter instance
        myRecyclerView.setLayoutManager(lLayoutMngr);
        useDynamicData();
    }

    public void onImageClick1(View view) {
        String userIn;
        userIn = uInCity.getText().toString();
        if(!userIn.isEmpty()){
            new HttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+userIn
                    +",US&units=imperial&APPID=7639f37bfc2d082876fa0816e7fad930");
            // http://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b6907d289e10d714a6e88b30761fae22    // FORECAST 05 -DAYS
        }else {
            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("Please enter a city", null)
                    , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }

    }

    private void useDynamicData() {
        forecastMembers.clear();
//        for (int i = 0; i < 3; i++) {
            String name = "Weather forecast";
            String songName = "Today";
//            forecastMembers.add(new ForecastData(songName,name, "Dummy"));
            forecastMembers.add(new ForecastData(songName,"Tue_con","Dum_con","Thu_con", "Fri-con"));
            myRecyclerView.setAdapter(rAdapter);
//        }
    }


    private class HttpAsyncTask extends AsyncTask<String, String, String> { // AysncTask Started
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... urls) {
            InputStream inputStream = null;
            String result = "";
            try {
                URL url = new URL(urls[0]);   // New HTTP - Implementation:
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(7000);    //7.5Sec
                // New HTTP - Implementation--------  ENDS HERE -------------

                // New HTTP data fetching - Implementation--------
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("Debug", "Json coversion Response " + line);
                        result += line;
                    }
                    inputStream.close();
                    publishProgress(result);
                }finally {
                    //end connection
                    urlConnection.disconnect();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Log.e("InputStream", ex.getLocalizedMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... result){
            try {
                JSONObject jsonObj = new JSONObject(result[0]);
                JSONArray res1 = jsonObj.getJSONArray("weather");
//                JSONArray res3 = jsonObj.getJSONArray("wind");

                JSONObject add_compObj = res1.getJSONObject(0);
                String w_desc = add_compObj.getString("description");
                String w_icon = add_compObj.getString("icon");
//                String w_temp = jsonObj.getJSONObject("main").getString("temp");    //to get the inner key:value
                int w_temp = jsonObj.getJSONObject("main").getInt("temp");    //to get the inner key:value

//                Log.d("addr_str", w_temp + w_desc);
                Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("PostAsync output: {0} and: {1}"
                        , w_temp, w_desc)
                        , Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                int w_code = (jsonObj.getInt("cod"));//.toString();//.getString("cod");
                Log.d("addr_str", Integer.toString(w_code));
                if(w_code == 200){
                    onFragmentInteraction(w_desc,w_temp, w_icon);         //Fragment Instantiation Dynamic
                }else {
                    Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("Please try again, Error code: {0 "
                            , w_code)
                            , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }catch (JSONException ej){
                Log.e("JParse_e", ej.getMessage());
            }
        }
        protected void onPostExecute(String... addr_fullStr) {

        }

    }


    //   ATTEMPT TO IMPLEMENT Search Activity
//    @Override
//    public boolean onCreateOptionsMenu(Menu menuW) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.wmenu, menuW);
//
//        // Associate searchable configuration with the SearchView
////        searchView.setSearchableInfo(
////                searchManager.getSearchableInfo(getComponentName()));
//
//        // Get the SearchView and set the searchable configuration
////        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
////        SearchView searchView = (SearchView) menuW.findItem(R.id.wMenuSearch).getActionView();
//        // Assumes current activity is the searchable activity
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
////        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//
//        return true;
//    }

////    Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getTitle().equals("WSearch")) {
//
//            Toast.makeText(getApplicationContext(), "Search = "+onSearchRequested(), Toast.LENGTH_LONG).show();
//            return onSearchRequested();
//        } else {
//            return false;
//        }
//    }


}

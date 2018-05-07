package Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import everyDayWeather.R;
import Model.ForecastData;
import Model.RvAdapter;
import Model.WeekDays;

import static java.lang.Thread.sleep;


public class WeatherAct extends AppCompatActivity implements WeatherfView.OnFragmentInteractionListener {

    int countHTTP;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<ForecastData> forecastMembers = new ArrayList<>();

    FragmentTransaction transaction;
    WeatherfView wFrag;
    EditText uInCity;
    HttpURLConnection urlConnection = null;
    WeekDays wDays;

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
        wFrag = WeatherfView.newInstance(descIn, Integer.toString(tempIn), icon_in);
        transaction = getSupportFragmentManager().beginTransaction();       // Fragment Replacement Approach
        transaction.replace(R.id.fragLayout, wFrag);
        transaction.commit();

        myRecyclerView = findViewById(R.id.list_forcast);  //ListView: RecyclerView a Dynamic View Structure
        myRecyclerView.hasFixedSize();
        lLayoutMngr = new LinearLayoutManager(this);
        rAdapter = new RvAdapter(forecastMembers, this); // create Adapter instance
        myRecyclerView.setLayoutManager(lLayoutMngr);
        useDummyData();
    }

    public void onImageClick1(View view) throws InterruptedException {
        String userIn = uInCity.getText().toString();
        if(!userIn.isEmpty()){
            new HttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+userIn           // Instant Weather HTTP Query API
                    +",US&units=imperial&APPID=7639f37bfc2d082876fa0816e7fad930");
            sleep(50);
            new HttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/forecast?q="+userIn          // Weather Forecast, 5-Day [3 hours update]
                    +",us&units=imperial&appid=7639f37bfc2d082876fa0816e7fad930");
        }else {
            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("Please enter a city", null)
                    , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }
    }

    private void useDummyData() {
        forecastMembers.clear();
            String name = "Weather forecast";
            String songName = "Today";
        forecastMembers.add(new ForecastData(songName,WeekDays.TUE.toString(),"Dum_con","Thu_con",WeekDays.FRI.toString()));
            myRecyclerView.setAdapter(rAdapter);
//        }
    }


    private class HttpAsyncTask extends AsyncTask<String, String, String> { // AysncTask Started
        @Override
        protected void onPreExecute() {
            countHTTP = 0;
        }
        @Override
        protected String doInBackground(String... urls) {
            InputStream inputStream = null;
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
//                        result.append(line);
                        Log.d("Debug", "Json coversion Response " + line);
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
            countHTTP++;
            JSONObject jsonObj = null;
            JSONObject jsonObjFore = null;
            try {
                switch (countHTTP){
                    case 1:
                        jsonObj = new JSONObject(result[0]);
                        JSONArray res1 = jsonObj.getJSONArray("weather");
                        JSONObject add_compObj = res1.getJSONObject(0);
                        String w_desc = add_compObj.getString("description");
                        String w_icon = add_compObj.getString("icon");
                        int w_temp = jsonObj.getJSONObject("main").getInt("temp");    //to get the inner key:value
                        Toast.makeText(getApplicationContext(), MessageFormat.format("PostAsync output: {0} and: {1}"
                                , w_temp, w_desc), Toast.LENGTH_SHORT).show();
                        int w_code = (jsonObj.getInt("cod"));
                        Log.d("addr_str", Integer.toString(w_code));
                        if(w_code == 200){
                            onFragmentInteraction(w_desc,w_temp, w_icon);         //Fragment Instantiation Dynamic
                        }else {
                            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("Current Weather_Err: {0} "
                                    , w_code)
                                    , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            break;
                        }
                    case 2:                                                     // Update forecast Data in reCyclerView
                        jsonObjFore = new JSONObject(result[0]);
                        Log.d("httpfore_", jsonObjFore.toString());
                        Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat
                                        .format("Forecast: {0} ", jsonObjFore.toString())
                                , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        break;
                }

            }catch (JSONException ej){
                Log.e("JParse_e", ej.getMessage());
            }
        }
        protected void onPostExecute() {
//            urlConnection.disconnect();
        }
    }

}

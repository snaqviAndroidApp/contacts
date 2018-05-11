package everyDayWeather.Views;

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
import everyDayWeather.Model.ForecastData;
import everyDayWeather.Model.RvAdapter;
import everyDayWeather.Model.WeekDays;

import static java.lang.Thread.sleep;

public class WeatherAct extends AppCompatActivity implements WeatherfView.OnFragmentInteractionListener {
    public static final int FORE_DATA_LIMIT = 30;
    public static final int INT_OFFSET = 7;
    int countHTTP;
    String w_desc, w_icon;
    int w_temp, w_code,w_codef, indexfore = 0;
    JSONObject jsonObj = null;
    JSONObject jsonObjFore = null;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    LinearLayoutManager lLayoutMngr;
    List<ForecastData> forecastMembers = new ArrayList<>();
    ArrayList<String> foreMonth = new ArrayList<>();
    ArrayList<String> foreDay = new ArrayList<>();
    HttpURLConnection urlConnection = null;
    String foreDay_L;

    FragmentTransaction transaction;
    WeatherfView wFrag;
    EditText uInCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_weather);
        }
        uInCity = findViewById(R.id.userIn);
        }

    @Override
    public void onFragmentInteraction(String descIn, int tempIn, String icon_in, ArrayList<String> fMon, ArrayList<String> fDay) {
        wFrag = WeatherfView.newInstance(descIn, Integer.toString(tempIn), icon_in, fMon, fDay);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragLayout, wFrag);
        transaction.commit();                                               // (View) Fragment Created
        myRecyclerView = findViewById(R.id.list_forcast);                   //Dynamic ListView: RecyclerView
        myRecyclerView.hasFixedSize();
        lLayoutMngr = new LinearLayoutManager(this);
        rAdapter = new RvAdapter(forecastMembers, this); // create Adapter instance
        myRecyclerView.setLayoutManager(lLayoutMngr);
        useDummyData(fMon, fDay);
    }

    public void onImageClick1(View view) throws InterruptedException {
        String userIn = uInCity.getText().toString();
        if(!userIn.isEmpty()){
            new WHttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+userIn           // Instant Weather HTTP Query API
                    +",US&units=imperial&APPID=7639f37bfc2d082876fa0816e7fad930");
            sleep(50);
            new WHttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/forecast?q="+userIn          // Weather Forecast, 5-Day [3 hours update]
                    +",us&units=imperial&appid=7639f37bfc2d082876fa0816e7fad930");
        }else {
            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), MessageFormat.format("Please enter a city","")
                    , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }
    }
    private void useDummyData(ArrayList<String> infMonth, ArrayList<String> infDay) {
        forecastMembers.clear();
        forecastMembers.add(new ForecastData(
                infMonth.get(0) + "/" + infDay.get(0)
                ,infMonth.get(1) + "/" + infDay.get(1)
                ,infMonth.get(2) + "/" + infDay.get(2)
                ,infMonth.get(3) + "/" + infDay.get(3)
                ,infMonth.get(4) + "/" + infDay.get(4))
        );
        myRecyclerView.setAdapter(rAdapter);
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
                switch (countHTTP){
                    case 1:
                        jsonObj = new JSONObject(resultIn[0]);
                        Log.d("httpI_", jsonObj.toString());
                        JSONArray res1 = jsonObj.getJSONArray("weather");
                        JSONObject add_compObj = res1.getJSONObject(0);
                        w_desc = add_compObj.getString("description");
                        w_icon = add_compObj.getString("icon");
                        w_temp = jsonObj.getJSONObject("main").getInt("temp");    //to get the inner key:value
                        w_code = (jsonObj.getInt("cod"));
                        break;
                    case 2:
                        jsonObjFore = new JSONObject(resultIn[0]);                      // Update forecast Data in recyclerView
                        w_codef = (jsonObjFore.getInt("cod"));
                        JSONArray resfore = jsonObjFore.getJSONArray("list");
                        String[] fDayformat0;
                        for (int i = 1; i < FORE_DATA_LIMIT;i += INT_OFFSET) {
                            if(i == 1) {
                                i = 0;
                                String iconf0 = resfore.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                                String date0 = resfore.getJSONObject(i).getString("dt_txt");
                                String[] forCh0 = null;
                                for (int i1=0;i1 < date0.length();i1++) { forCh0 = date0.split("-"); }
                                foreMonth.add(indexfore,forCh0[1]);
                                foreDay_L = forCh0[2].toString();
                                fDayformat0 = foreDay_L.split(" ");
                                foreDay.add(indexfore,fDayformat0[0]);
                                indexfore =+ 1;
                                i++;
                            }else {
                                String iconf0 = resfore.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                                String date0 = resfore.getJSONObject(i).getString("dt_txt");
                                String[] forCh0 = null;
                                for (int i1 = 0; i1 < date0.length(); i1++) {
                                    forCh0 = date0.split("-");
                                }
                                foreMonth.add(indexfore, forCh0[1]);
                                foreDay_L = forCh0[2].toString();
                                fDayformat0 = foreDay_L.split(" ");
                                foreDay.add(indexfore,fDayformat0[0]);
                                indexfore++;
                            }
                        }
                        break;
                }
                if(w_code == 200 && w_codef == 200){
                    onFragmentInteraction(w_desc,w_temp, w_icon, foreMonth, foreDay);               //Fragment Instantiation Dynamic
                }
                else {
                    Snackbar.make(Objects.requireNonNull(getCurrentFocus())
                            , MessageFormat.format("Current Weather_Success check: {0} ", w_code)
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

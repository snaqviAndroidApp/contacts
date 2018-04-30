package ghar.root.weather_Test.Facial;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Intent actWCheck;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

    }

    public void onWData(View view) {
        actWCheck = new Intent(MainActivity.this, WeatherAct.class);
        startActivity(actWCheck);
    }

//    private class HttpAsyncTask extends AsyncTask<String, String, String> {   // AysncTask Started
//        @Override
//        protected void onPreExecute() {
//
//        }
//        @Override
//        protected String doInBackground(String... urls) {
//            InputStream inputStream = null;
//            String result = "";
//            try {
//                URL url = new URL(urls[0]);   // New HTTP - Implementation:
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setConnectTimeout(7000);    //7.5Sec
//                // New HTTP - Implementation--------  ENDS HERE -------------
//
//                // New HTTP data fetching - Implementation--------
//                inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        Log.d("Debug", "Json coversion Response " + line);
//                        result += line;
//                    }
//                    inputStream.close();
//                    publishProgress(result);
//                }finally {
//                    //end connection
//                    urlConnection.disconnect();
//                }
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//                Log.e("InputStream", ex.getLocalizedMessage());
//            }
//            return null;
//        }
//
//        protected void onProgressUpdate(String... result){
//            try {
//                JSONObject jsonObj = new JSONObject(result[0]);
////                JSONArray res = jsonObj.getJSONArray("results");
//                JSONArray res1 = jsonObj.getJSONArray("weather");
//                JSONObject add_compObj = res1.getJSONObject(0);
//
////                JSONObject add_compObj = res.getJSONObject(0);
////                JSONArray addr_comp = add_compObj.getJSONArray("address_components");
////                String addr_comp1 = addr_comp.getString(0);
////                JSONObject addr_comp_long = addr_comp.getJSONObject(0);
////                Log.d("addr_jobject", addr_comp_long.toString());
////                String addr_str = addr_comp.getJSONObject(0).getString("long_name");    //to get the inner key:value
////                Log.d("addr_str", addr_str);
////                String addr_fullStr = add_compObj.getString("formatted_address");
////                String addr_fullStr = add_compObj.getString("main");
//                String addr_fullStr = add_compObj.getString("main");
//                Log.d("addr_str", addr_fullStr);
//                tvOutput.setText(addr_fullStr);
//            }catch (JSONException ej){
//                Log.e("JParse_e", ej.getMessage());
//            }
//        }
//        protected void onPostExecute(String str2) {
//
//        }
//
//    }
    // AysncTask completed --------

//@Override
//protected void onActivityResult(int requestCode, int resultCode, Intent data) {            // REF: startActivityForResult(intLogIn, codeReq);
//    if (resultCode == RESULT_OK && requestCode == codeReq) {
//        String bkUname = data.getExtras().getString("ret_uname");
////        String bkUPass = data.getExtras().getString("ret_upass");
//        Toast.makeText(this, MessageFormat.format("User credentials from LogIn Activity as User: {0}"
//                ,bkUname), Toast.LENGTH_LONG).show();
//        new HttpAsyncTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+bkUname
//                +",US&units=imperial&APPID=7639f37bfc2d082876fa0816e7fad930");
//
//    }
//}

}

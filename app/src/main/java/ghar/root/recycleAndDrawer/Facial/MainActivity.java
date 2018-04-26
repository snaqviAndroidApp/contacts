package ghar.root.recycleAndDrawer.Facial;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ghar.root.recycleAndDrawer.bkEnd.ContactsData;
import ghar.root.recycleAndDrawer.bkEnd.NewMessageNotification;
import ghar.root.recycleAndDrawer.bkEnd.RvAdapter;

public class MainActivity extends AppCompatActivity {

    TextView tvOutput;
    EditText etN;
    private Button bStart;                  //---- Background Services (audio file) using Thread
    private SeekBar sk;
    private String inLink;
    LinearLayoutManager lLayoutMngr;
    Context cntxtG;
    MediaPlayer mediaPlayer;
    String songName;
    myThread myTh;

    //--------------------------------------------------         // get Permissions
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;        // Location n Contacts
    final String CONTACTS_PERMISSION = "Contacts ";
    final String STORAGE_PERMISSION = "Storage ";

    private DrawerLayout mDrawerLayout;      //----Drawer
    private NavigationView navigationView;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter rAdapter;
    List<ContactsData> lContacts = new ArrayList<>();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        ////////////// View Section: recyclerView Instantiation
        bStart = findViewById(R.id.buStart);
        mediaPlayer = new MediaPlayer();
        sk = findViewById(R.id.skBarId);
        myRecyclerView = findViewById(R.id.recVew);  //ListView: RecyclerView a Dynamic View Structure
        myRecyclerView.hasFixedSize();
        lLayoutMngr = new LinearLayoutManager(this);
        rAdapter = new RvAdapter(lContacts, this); // create Adapter instance
        myRecyclerView.setLayoutManager(lLayoutMngr);
        ////////////// VIEW SECTION: recyclerView Instantiation ---- ENDS HERE ---------

        ////////////// PERMISSION ---------
        CheckUserPermissions();                        // Check PERMISSIONS for SDK VER >= 23
        ////////////// PERMISSION ---- ENDS HERE ---------

        ////////////// NATIVE SECTION                  // Example of a call to a native method
        etN = findViewById(R.id.etNative);
        etN.setText(sJNI("Hii Cplus"));
        ////////////// NATIVE SECTION  ---- ENDS HERE ---------

        ////////////// Drawer -----------                  // Auto Draw Actions
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        tvOutput = (TextView)findViewById(R.id.tvOutput);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navItem_01:                                 // First Menu Item selected
                                useDynamicData();                              // Implementation of backEnd-Calls including AsyncTasks
                                return true;
                            case R.id.navItem_02:
//                                new HttpAsyncTask().execute("https://maps.googleapis.com/maps/api/geocode/json?address" +          // googleMap api
//                                        "=2511+Windhaven+Pkwy,+Lewisville,+TX&key=AIzaSyBZYK6N7wEshYvcS5gZVt1EmJkU6XZfgYk");
                                new HttpAsyncTask().execute("https://maps.googleapis.com/maps/api/geocode/json?address="+etN.getText().toString()+"&key=" +
                                        "AIzaSyCi1huFfuiyCtB3ajN04dz51yKafRLuDEA");
//                                new HttpAsyncTask().execute("https://query.yahooapis.com/v1/public/yql?" +                         // yahoo weather api
//                                        "q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(" +
//                                        "select%20woeid%20from%20geo.places(1)%20where%20text%3D%22london%22)&format=" +
//                                        "json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
                                mDrawerLayout.closeDrawers();                    // 2nd Menu Item selected
                                return true;                                    // Can be Disabled to avoid exiting without .closeDrawer()
                        }
                        // set item as selected to persist highlight
//                        menuItem.setChecked(true);
                        Toast.makeText(getApplicationContext(), "Tapped Nav-View Outside Menu Items", Toast.LENGTH_LONG).show();
//                       mDrawerLayout.closeDrawers();                             // close drawer when item is tapped

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });
        ////////////// Drawer ENDS-----------
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void useDynamicData() {
        Cursor curContacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        lContacts.clear();
        int cnt = 0;
            //while (curContacts.moveToNext()) {
            while (cnt <=5) {
//                String name = curContacts.getString(curContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String name = "Oh ho";
//                mp3 songs web source-1: https://www.soundhelix.com/audio-examples
//                songName = "http://server6.mp3quran.net/aloosi/001.mp3";                   //link works

//                songName = "https://soundcloud.com/djactor/2013-1";                        //didn't work
                songName = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-13.mp3"; //works
                songName = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-16.mp3"; // Lighter//
                lContacts.add(new ContactsData(++cnt,songName,name));
                myRecyclerView.setAdapter(rAdapter);
            }
    }

    //----------- PERMISSIONS HANDLING ----------------------
    void CheckUserPermissions() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (
                    (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) &&
                            (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ) {
                requestPermissions(new String[]{
                        android.Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }

    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {        // DENIAL-cases pending implementation
                    // useDynamicData();
                    Toast.makeText(this, CONTACTS_PERMISSION + getResources().getString(R.string.permission_msg), Toast.LENGTH_SHORT)
                            .show();
                    Log.d("Media_cc", String.valueOf(""));
                }else if (grantResults[1] == PackageManager.PERMISSION_GRANTED){   // DENIAL-cases pending implementation
                    Toast.makeText(this, STORAGE_PERMISSION + getResources().getString(R.string.permission_msg), Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    // Permission Denied
                    Toast.makeText(this, getResources().getString(R.string.deniel_msg), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    //----------- PERMISSIONS HANDLING ENDS HERE--------------

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * @param s
     */
    public native String sJNI(String s);

    public void startMedia(View view) {                                 //'START'-OnClick() Call


        NewMessageNotification note = new NewMessageNotification();     // Notification Instance
//        NewMessageNotification.notify(this,"background-meida-service is running",1);
        note.notify(this,"background-meida-service is running",1);

        RvAdapter.ViewHolder vHdr;
        vHdr = new RvAdapter.ViewHolder(myRecyclerView, cntxtG, lContacts);   //
        if (lContacts.size() != 0) {
            if (
                    bStart.getText() == getString(R.string.btextStart)) {
                inLink = vHdr.tvName.getText().toString();   // take text as 'String' from 2nd Argument from View
                try {
                    mediaPlayer.setDataSource(inLink);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    sk.setMax(mediaPlayer.getDuration());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("media-start", inLink);       // Log-point
                Log.wtf("media-start", inLink);

                myTh = new myThread();                  // Create Thread-Instance
                myTh.start();
                NewMessageNotification.notify(this, "meida-service started", 1);  //Notification triggering
                bStart.setText(getString(R.string.btextPause));
            } else if(bStart.getText() == getString(R.string.btextPause)){
                mediaPlayer.pause();
                bStart.setText(getString(R.string.btextResume));
            }else if(bStart.getText() == getString(R.string.btextResume)){
                mediaPlayer.start();
                bStart.setText(getString(R.string.btextPause));
            }

        }else {
            Toast.makeText(getApplicationContext(), "List is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop(View view) {
        mediaPlayer.stop();
        mediaPlayer.reset();
//        sk.setMax(0);                  // Reset the seekBar Progress
        sk.setMax(mediaPlayer.getDuration());
        bStart.setText(R.string.btextStart);
    }

    class myThread extends Thread{
        public void run(){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mediaPlayer != null){
                            sk.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    }
                });
            }
        }
    }                                        // Thread Class
    private class HttpAsyncTask extends AsyncTask<String, String, String> {   // AysncTask Started
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
                JSONArray res = jsonObj.getJSONArray("results");
                JSONObject add_compObj = res.getJSONObject(0);
//                JSONArray addr_comp = add_compObj.getJSONArray("address_components");
//                String addr_comp1 = addr_comp.getString(0);
//                JSONObject addr_comp_long = addr_comp.getJSONObject(0);
//                Log.d("addr_jobject", addr_comp_long.toString());
//                String addr_str = addr_comp.getJSONObject(0).getString("long_name");    //to get the inner key:value
//                Log.d("addr_str", addr_str);
                String addr_fullStr = add_compObj.getString("formatted_address");
                Log.d("addr_str", addr_fullStr);
                tvOutput.setText(addr_fullStr);
            }catch (JSONException ej){
                Log.e("JParse_e", ej.getMessage());
            }
        }
        protected void onPostExecute(String str2) {

        }

    }
    // AysncTask completed --------

}

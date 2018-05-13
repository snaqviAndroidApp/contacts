package one.Model;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.List;
import java.util.Objects;

import one.R;
import one.Views.BottleStore;

import static java.lang.System.load;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private static final String TAG = "Item position";    // Logging to a file
    private List<BottlesD_Data> lforCast;                       //BackEnd Java DB Object list
    Context contextweatherFore;
    public RvAdapter(List<BottlesD_Data> forecastData, Context contextweatherFore) {
        this.lforCast = forecastData;
        this.contextweatherFore = contextweatherFore;
    }
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vH = LayoutInflater.from(parent.getContext())                          // Attaching backEnd Data to View
                .inflate(R.layout.forecast_list,parent,false);
        ViewHolder vCont = new ViewHolder(vH, contextweatherFore, lforCast);
        return vCont;
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, int position) {
        BottlesD_Data foreData = lforCast.get(position);
        holder.tvAddr.setText(foreData.getDb_Mon());
        holder.tvPh.setText(foreData.getDb_Tue());
        foreData.setDb_imgUrl(foreData.getDb_imgUrl(),holder.imgView);
    }

    @Override
    public int getItemCount() {
        return lforCast.size();            // return the size in the list
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView tvPh, tvAddr;
            ImageView imgView;
            List<one.Model.BottlesD_Data> lAdapterItems;
            Context cntextDisp;

    /**
     * @param itemView
     * @param cntxt
     * @param fItemData
     */

ViewHolder(View itemView, Context cntxt, List<BottlesD_Data> fItemData) {
            super(itemView);
            this.lAdapterItems = fItemData;
            this.cntextDisp = cntxt;
            itemView.setOnClickListener(this);      //Handle Clicks
            tvAddr = itemView.findViewById(R.id.tvAddr);
            tvPh = itemView.findViewById(R.id.tvPhone);
            imgView = itemView.findViewById(R.id.imgURL);
        }

/**
   recyclerView onClick() method provides all available Resources available in that recyclerView Adapter
   like in this case 03 parameters ID, Name, PhoneNumber

**/
    @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();                     //get Individual Item-position clicked
            BottlesD_Data lItems = lAdapterItems.get(pos);         //get data-Object from List
            String vLink = lItems.getDb_Mon();
            String vLinkT = lItems.getDb_Tue();
            String iImageRemote = lItems.getDb_imgUrl();


        Log.d(TAG,String.valueOf(pos));              // Log point for recyView--onClick()-event
        Log.d("rViewAddr",vLink);                // Log point for recyView--onClick()-event
        Log.d("rImgURL",iImageRemote);                // Log point for recyView--onClick()-event

//            new ImgHATask().execute(iImageRemote);
//        Picasso.with().load(iImageRemote).into(imgView);
    }

    }

}

class ImgHATask extends AsyncTask<String, String, String> { // AysncTask Started

    //Image Fetch
    ImageView imgView;

    public void setImgView(final ImageView imgView) {
        this.imgView = imgView;
    }

    int countHImg;
    JSONObject ImgjsonObj = null, ImgjsonObj1 = null, ImgjsonObj2 = null;
    HttpURLConnection ImgurlConn = null;

    @Override
    protected void onPreExecute() {
        countHImg++;
    }
    @Override
    protected String doInBackground(String... urls) {
        InputStream inputStream;
        String result = "";
        StringBuffer resultBuff = null;
        try {
            URL url = new URL(urls[0]);   // New HTTP - Implementation:
            ImgurlConn = (HttpURLConnection) url.openConnection();
            ImgurlConn.setConnectTimeout(7000);    //7.0Sec
            // New HTTP - Implementation--------  ENDS HERE -------------

            // New HTTP data fetching - Implementation--------
            inputStream = new BufferedInputStream(ImgurlConn.getInputStream());
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                        Log.d("ImgURL_Proc", "Json coversion Response " + line);
                }
                inputStream.close();
                publishProgress(result);
            }finally {
                ImgurlConn.disconnect();              //end connection
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Log.e("InputStream", ex.getLocalizedMessage());
        }
        return null;
    }
    protected void onProgressUpdate(String... resultIn){
        try {
            ImgjsonObj = null;
            ImgjsonObj1 = new JSONObject(resultIn[0]);
//            ImageView imgV = new JSONObject(ImgjsonObj1.);
            Log.d("postImgFetch","");

//                if(w_code == 200 && w_codef == 200) {
////                    assetImg.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloudy));
//                }else {
//                    Snackbar.make(Objects.requireNonNull(getCurrentFocus())
//                            , MessageFormat.format("An Error occured, please try again ", null)
//                            , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                }
        }
        catch (JSONException ej){
            Log.e("JParse_e", ej.getMessage());
        }
    }
    protected void onPostExecute() {
    }
}

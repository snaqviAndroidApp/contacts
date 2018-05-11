package everyDayWeather.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import everyDayWeather.R;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private static final String TAG = "Item position";    // Logging to a file
    private List<ForecastData> lforCast;        //BackEnd Java DB Object list
    private Context contextweatherFore;
    public RvAdapter(List<ForecastData> forecastData, Context contextweatherFore) {
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
        ForecastData foreData = lforCast.get(position);
        holder.tvMon.setText(foreData.getDb_Mon());
        holder.tvTue.setText(foreData.getDb_Tue());
        holder.tvWed.setText(foreData.getDb_Wed());
        holder.tvThu.setText(foreData.getDb_Thu());
        holder.tvFri.setText(foreData.getDb_Fri());
    }

    @Override
    public int getItemCount() {
        return lforCast.size();            // return the size in the list
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView tvMon, tvTue, tvWed, tvThu, tvFri;
            List<ForecastData> lAdapterItems;
            Context cntxtDisp;

    /**
     * @param itemView
     * @param cntxt
     * @param fItemData
     */
//        public ViewHolder(View itemView, Context cntxt, List<ForecastData> fItemData) {
ViewHolder(View itemView, Context cntxt, List<ForecastData> fItemData) {
            super(itemView);
            this.lAdapterItems = fItemData;
            this.cntxtDisp = cntxt;
            itemView.setOnClickListener(this);      //Handle Clicks
            tvMon = itemView.findViewById(R.id.tvMon);
            tvTue = itemView.findViewById(R.id.tvTue);
            tvWed = itemView.findViewById(R.id.tvWed);
            tvThu = itemView.findViewById(R.id.tvThur);
            tvFri = itemView.findViewById(R.id.tvFri);
        }

/**
   recyclerView onClick() method provides all available Resources available in that recyclerView Adapter
   like in this case 03 parameters ID, Name, PhoneNumber

**/
    @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();                     //get Individual Item-position clicked
            ForecastData lItems = lAdapterItems.get(pos);         //get data-Object from List
            String vLink = lItems.getDb_Mon();
            String vLinkT = lItems.getDb_Tue();
            String vLinkW = lItems.getDb_Wed();
            String vLinkTh = lItems.getDb_Thu();
            String vLinkFr = lItems.getDb_Fri();

//        Log.d(TAG,String.valueOf(pos));              // Log point for recyView--onClick()-event
        Log.d(TAG,String.valueOf(pos));              // Log point for recyView--onClick()-event
        Log.d("rViewLink",vLink);                // Log point for recyView--onClick()-event

    }
}

}

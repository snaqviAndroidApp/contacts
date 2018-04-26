package ghar.root.recycleAndDrawer.bkEnd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ghar.root.recycleAndDrawer.Facial.R;

/**
 * Created by root on 2/7/18.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private static final String TAG = "Item position";    // Logging to a file

    private List<ContactsData> lContact;        //BackEnd Java DB Object list
    private Context contextContacts;
    public RvAdapter(List<ContactsData> lContacts, Context contextContacts) {
        this.lContact = lContacts;
        this.contextContacts = contextContacts;
    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vH = LayoutInflater.from(parent.getContext())                          // Attaching backEnd Data to View
                .inflate(R.layout.contacts_bkend_list,parent,false);
        ViewHolder vCont = new ViewHolder(vH,contextContacts, lContact);
        return vCont;
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, int position) {

        ContactsData contData = lContact.get(position);                     //data binding to a specified 'position' in DB (ContactsData) class
        holder.tvID.setText(String.valueOf(contData.getId()));              // setting TextViews (defined below in ViewHolder Class to the DB-members
        holder.tvName.setText(contData.getContName());
        holder.tvPhoneNum.setText(contData.getPhNum());
    }

    @Override
    public int getItemCount() {
        return lContact.size();            // return the size in the list
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView tvID;                  // Create data members as of contacts_bkend_list.xml
            public TextView tvName;
            public TextView tvPhoneNum;
            List<ContactsData> lContsItems;
            Context cntxtDisp;

        public ViewHolder(View itemView, Context cntxt, List<ContactsData> contsData) {
            super(itemView);
            this.lContsItems = contsData;
            this.cntxtDisp = cntxt;
            itemView.setOnClickListener(this);      //Handle Clicks

            tvID = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhoneNum = itemView.findViewById(R.id.tvPhNum);
        }

/**
   recyclerView onClick() method provides all available Resources available in that recyclerView Adapter
   like in this case 03 parameters ID, Name, PhoneNumber

**/
    @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();                     //get Individual Item-position clicked
            ContactsData lItems = lContsItems.get(pos);         //get data-Object from List
            String vLink = lItems.getContName();

        Log.d(TAG,String.valueOf(pos));              // Log point for recyView--onClick()-event
        Log.d("rViewLink",vLink);              // Log point for recyView--onClick()-event

    }
}

}

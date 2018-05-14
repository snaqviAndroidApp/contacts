package one.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.List;

import one.Model.BottlesD_Data;
import one.R;

public class StoreDetails extends AppCompatActivity {

    private List<BottlesD_Data> detailDataIn;                       //BackEnd Java DB Object list

    Intent iDetailRcv = null;
    String addrDIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        iDetailRcv = getIntent();

//        iDetailRcv.getStringExtra("adb");
        TextView tvAdd = findViewById(R.id.tVDetailAddr);
        TextView tvPh = findViewById(R.id.tVDetailPh);
        ImageView imgDLogoIn = findViewById(R.id.imgDetailLog);

//
//        String imgDUrl = iDetailRcv.getStringExtra("imgStrr");
        tvAdd.setText(iDetailRcv.getStringExtra("addrStrr"));
        tvPh.setText(iDetailRcv.getStringExtra("phStrr").split(":")[1]);
        Picasso.get().load(iDetailRcv.
                getStringExtra("imgStrr"))
                .into(imgDLogoIn
                );

    }
}

package one.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;

import one.Model.BottlesD_Data;
import one.R;

public class StoreDetails extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;        // Location n Contacts
    Intent iDetailRcv = null;
    String addrDIn = null;
    TextView tvAdd, tvPh;
    ImageView imgDLogoIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        iDetailRcv = getIntent();
        tvAdd = findViewById(R.id.tVDetailAddr);
        tvPh = findViewById(R.id.tVDetailPh);
        imgDLogoIn = findViewById(R.id.imgDetailLog);

        CheckUserPermissions();                        // Check PERMISSIONS for SDK VER >= 23

        tvAdd.setText(iDetailRcv.getStringExtra("addrStrr"));
        tvPh.setText(iDetailRcv.getStringExtra("phStrr").split(":")[1]);
        Picasso.get().load(iDetailRcv.
                getStringExtra("imgStrr"))
                .into(imgDLogoIn
                );
    }

    public void onPhone(View view) {
        Intent intDial = new Intent(Intent.ACTION_DIAL);
        intDial.setData(Uri.parse("tel:" + tvPh.getText().toString().trim()));
        if (intDial.resolveActivity(getPackageManager()) != null) {
            startActivity(intDial);
        } else {
            Log.e("phCall", "Can't resolve app for ACTION_DIAL Intent.");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (
                    (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    ) {
                requestPermissions(new String[]{
                                Manifest.permission.CALL_PHONE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }
}

package one.Views;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import one.R;

public class StoreDetails extends AppCompatActivity  implements OnMapReadyCallback {

    Intent iDetailRcv = null;
    TextView tvAdd, mtvLat, mtvLong, tvPh, detalHeading;
    ImageView imgDLogoIn;
    GoogleMap gMap=null;
    double[] pProcLoc_= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        iDetailRcv = getIntent();
        tvAdd = findViewById(R.id.tVDetailAddr);
        tvPh = findViewById(R.id.tVDetailPh);
        detalHeading = findViewById(R.id.detailsheading);
        detalHeading.setPaintFlags(detalHeading.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        imgDLogoIn = findViewById(R.id.imgDetailLog);
        tvAdd.setText(iDetailRcv.getStringExtra("addrStrr"));
        tvPh.setText(iDetailRcv.getStringExtra("phStrr").split(":")[1]);
        pProcLoc_ = iDetailRcv.getDoubleArrayExtra("dArrayLat_");
        Picasso.get().load(iDetailRcv.
                getStringExtra("imgStrr"))
                .into(imgDLogoIn
                );
        GoogleMapOptions sMapOpt = new GoogleMapOptions();          // GoogleMap Options
        sMapOpt.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .getCamera()
        ;
        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapCandidate, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
        //////////////////////////////////////////////////
    }

    public void onPhone(View view) {
        Intent intDial = new Intent(Intent.ACTION_DIAL);
        intDial.setData(Uri.parse("tel:" + tvPh.getText().toString().trim()));
        if (intDial.resolveActivity(getPackageManager()) != null) {
            startActivity(intDial);
        } else {
            Log.e("phCall", "Can't resolve app for ACTION_DIAL Intent.");
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final LatLng LOC_RCVD = new LatLng(pProcLoc_[0],pProcLoc_[1]);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LOC_RCVD)           // Sets the center of the map to processed Location
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(
                        new LatLng(pProcLoc_[0],pProcLoc_[1]))
                .title("Marker"));
    }
}

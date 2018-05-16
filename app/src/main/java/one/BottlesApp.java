package one;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import one.Views.BottleStore;


public class BottlesApp extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;        // Location n Contacts
    Intent actWCheck;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            super.onCreate(savedInstanceState);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            CheckUserPermissions();                        // Check PERMISSIONS for SDK VER >= 23
            setContentView(R.layout.activity_main);
    }

    public void onWData(View view) {
        actWCheck = new Intent(BottlesApp.this, BottleStore.class);
        startActivity(actWCheck);
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

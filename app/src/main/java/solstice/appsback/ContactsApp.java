package solstice.appsback;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import challenge.solstice.myapp.R;

public class ContactsApp extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;        // Call
    final String CALL_PERMISSION = "Calls ";
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
        actWCheck = new Intent(ContactsApp.this, ContactsFetch.class);
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {        // DENIAL-cases pending implementation
                    Toast.makeText(this, CALL_PERMISSION
                            + getResources().getString(R.string.permission_msg), Toast.LENGTH_SHORT)
                            .show();
                    Log.d("Media_cc", String.valueOf(""));
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




}

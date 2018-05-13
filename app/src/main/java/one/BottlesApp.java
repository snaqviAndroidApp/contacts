package one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import one.Views.BottleStore;


public class BottlesApp extends AppCompatActivity {

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
            setContentView(R.layout.activity_main);
    }

    public void onWData(View view) {
        actWCheck = new Intent(BottlesApp.this, BottleStore.class);
        startActivity(actWCheck);
    }

}

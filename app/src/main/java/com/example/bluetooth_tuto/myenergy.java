package com.example.bluetooth_tuto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class myenergy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myenergy);
    }
    public void gotoUrl (View view) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.translatorscafe.com/unit-converter/fr-FR/calculator/led-resistor/"));
        if(i.resolveActivity(getPackageManager())!=null){
            startActivity(i);

        }
}
}

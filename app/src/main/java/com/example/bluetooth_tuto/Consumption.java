package com.example.bluetooth_tuto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Consumption extends AppCompatActivity {
   private Button bt;
   private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        bt = (Button) findViewById(R.id.btn);
        bt2 = (Button)findViewById(R.id.btn2);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficher(v);

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);

            }
        });


        }

    private void calculate(View v) {
        Intent intent = new Intent(this,myenergy.class);
        startActivity(intent);

    }
    private void afficher(View v) {
        Intent intent = new Intent(this,Mygraphs.class);
        startActivity(intent);

    }

    }




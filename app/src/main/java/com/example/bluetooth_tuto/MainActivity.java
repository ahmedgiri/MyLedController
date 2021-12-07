package com.example.bluetooth_tuto;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private TextView tv_status;
    private CheckBox ckb_led1;
    private CheckBox ckb_led2;
    private CheckBox ckb_led3;
    private ListView lv_devlist;
    SeekBar brightness;
    TextView lumn;

    private BluetoothAdapter my_bt_adapter;
    private MyBluetoothClass mybluetooth;
    private BluetoothSocket my_bt_soket = null;
    private OutputStream my_bt_out_stream = null;
    private OutputStream my_bt_out_stream2 = null;
    private InputStream my_bt_inp_stream = null;
    private String dev_address;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Handler my_handler;
    private final static int STATUS = 1;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_status = (TextView) findViewById(R.id.TV_STATUS);
        lv_devlist = (ListView) findViewById(R.id.LV_DEVLIST);
        ckb_led1 = (CheckBox) findViewById(R.id.CKB_LED);
        ckb_led2 = (CheckBox) findViewById(R.id.CKB_LED2);
        ckb_led3 = (CheckBox) findViewById(R.id.CKB_LED3);

        brightness = (SeekBar)findViewById(R.id.seekBar);
        lumn = (TextView)findViewById(R.id.lumn);


        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser==true)
                {
                    lumn.setText(String.valueOf(progress));
                    try
                    {
                        my_bt_soket.getOutputStream().write(String.valueOf(progress).getBytes());

                    }
                    catch (IOException e)
                    {
                        my_handler.obtainMessage(STATUS, -1, -1,"Erreur dans writebyte").sendToTarget();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //=== Définir le handler  ==================================================================
        my_handler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case STATUS:
                        tv_status.setText((String)(msg.obj));
                        break;
                }
            }
        };

        // ===========  affecter un identificateur au module bluetooth ============================
        my_bt_adapter = BluetoothAdapter.getDefaultAdapter();
        if (my_bt_adapter == null) {
            tv_status.setText("Pas d'interface Bluetooth");
        }

        // ============== démarrer le bluetooth s'il ne l'est pas =============================
        if (!my_bt_adapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
        }
        while (!my_bt_adapter.isEnabled()) ; // attendre que le démarrage soit effectif

        // =============== afficher la liste des équipements associé dans la liste =================
        Set<BluetoothDevice> pairedDevices = my_bt_adapter.getBondedDevices();
        if (pairedDevices.isEmpty()) tv_status.setText("Liste Vide");
        ArrayList pairedlist = new ArrayList();
        for (BluetoothDevice bt : pairedDevices)
            pairedlist.add(bt.getName() + "\n" + bt.getAddress());
        ArrayAdapter my_list_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedlist);
        lv_devlist.setAdapter(my_list_adapter);
        tv_status.setText("Choose your bluetooth device ");
        lv_devlist.setOnItemClickListener(devlist_listener);
    }
    //======= Listner de la liste =====================================================
    private AdapterView.OnItemClickListener devlist_listener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            String devchoisi = ((TextView) v).getText().toString();
            dev_address = devchoisi.substring(devchoisi.length() - 17);
            //dev_name = devchoisi.substring(0, devchoisi.length() - 17);
            tv_status.setText("CONNEXION EN COURS");
            // démarrer le Thread qui gère la connexion
            mybluetooth = new MyBluetoothClass();
            mybluetooth.start();
        }
    };


    // définition de la classe BluetoothClass pour (connexion , lecture , écriture, déconnexion)
    private class MyBluetoothClass extends Thread {
        public void run() {
            boolean SOCKET_OK, CONX_OK, OUTS_OK, INPS_OK;

            // créer un objet bluetooth pour notre HC05
            BluetoothDevice HC05 = my_bt_adapter.getRemoteDevice(dev_address);

            //Créer un soket (pipeline) pour communiquer avec notre HC05
            SOCKET_OK = true;
            try {
                my_bt_soket = HC05.createInsecureRfcommSocketToServiceRecord(myUUID);
            } catch (IOException e) {
                SOCKET_OK = false;
            }
            if (SOCKET_OK) {
                // connecter le soket
                CONX_OK = true;
                try {
                    my_bt_soket.connect();
                } catch (IOException e) {
                    CONX_OK = false;
                }
                if (CONX_OK) {
                    OUTS_OK = true;
                    try {
                        my_bt_out_stream = my_bt_soket.getOutputStream();
                    } catch (IOException e) {
                        my_handler.obtainMessage(STATUS, -1, -1, "Echec création OUTPUT stream").sendToTarget();
                        OUTS_OK = false;
                    }
                    INPS_OK = true;
                    try {
                        my_bt_inp_stream = my_bt_soket.getInputStream();
                    } catch (IOException e) {
                        my_handler.obtainMessage(STATUS, -1, -1, "Echec création INPUT STREAM").sendToTarget();
                        INPS_OK = false;
                    }
                    if (OUTS_OK && INPS_OK)
                        my_handler.obtainMessage(STATUS, -1, -1, "Connécté").sendToTarget();
                } else {
                    my_handler.obtainMessage(STATUS, -1, -1, "Echec Connexion").sendToTarget();
                }
            } else {
                my_handler.obtainMessage(STATUS, -1, -1, "Echec création Soket COMM").sendToTarget();
            }
        }

        void writebyte(byte b) {
            try {
                my_bt_out_stream.write(b);
            } catch (IOException e) {
                my_handler.obtainMessage(STATUS, -1, -1,"Erreur dans writebyte").sendToTarget();
            }
        }




        void disconnect() {
            try {
                my_bt_soket.close();
            } catch (IOException e) {
                my_handler.obtainMessage(STATUS, -1, -1, "Echec Déconnexion").sendToTarget();
            }
        }
    }


    public void ledcommand(View VW) {
        byte ledcomm = '0';
        if (ckb_led1.isChecked()) {
            ledcomm = 'A';
        } else {
            ledcomm = 'B';
        }
        mybluetooth.writebyte(ledcomm);
    }
    public void ledcommand2(View VW) {
        byte ledcomm2 = '0';
        if (ckb_led2.isChecked()) {
            ledcomm2 = 'C';
        } else {
            ledcomm2 = 'D';
        }
        mybluetooth.writebyte(ledcomm2);
    }
    public void ledcommand3(View VW) {
        byte ledcomm3 = '0';
        if (ckb_led3.isChecked()) {
            ledcomm3 = 'E';
        } else {
            ledcomm3 = 'F';
        }
        mybluetooth.writebyte(ledcomm3);
    }

    public void deconnecter(View view) {
        mybluetooth.disconnect();
        tv_status.setText("Déconnecté");
    }
    public void consum(View view) {
        Intent intent = new Intent(this,Consumption.class);
        startActivity(intent);

    }

}
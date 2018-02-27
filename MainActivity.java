package com.example.jonathan.bluetoothcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button onButton, offButton, visibleButton, showDeviceButton;
    private BluetoothAdapter ba;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = (Button) findViewById(R.id.onButton);
        offButton = (Button) findViewById(R.id.offButton);
        visibleButton = (Button) findViewById(R.id.visibleButton);
        showDeviceButton = (Button) findViewById(R.id.showDeviceButton);

        ba = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView) findViewById(R.id.lv);

        onButton.setOnClickListener(on);
        offButton.setOnClickListener(off);
        visibleButton.setOnClickListener(showVisible);
        showDeviceButton.setOnClickListener(showDevice);
    }

    View.OnClickListener on = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!ba.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 0);
                Toast.makeText(MainActivity.this, "Turned On", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Already On", Toast.LENGTH_LONG).show();
            }
        }
    };

    View.OnClickListener off = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ba.disable();
            Toast.makeText(MainActivity.this, "Turned off", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener showVisible = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(getVisible, 0);
        }
    };

    View.OnClickListener showDevice = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pairedDevices = ba.getBondedDevices();

            ArrayList list = new ArrayList();
            for(BluetoothDevice bt: pairedDevices) list.add(bt.getName());
            Toast.makeText(MainActivity.this, "Show Devices", Toast.LENGTH_SHORT).show();
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
        }
    };


}

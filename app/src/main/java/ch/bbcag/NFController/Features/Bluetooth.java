package ch.bbcag.NFController.Features;

import android.bluetooth.BluetoothAdapter;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.MapActivities.SelectGeoFencingFeatureActivity;

public class Bluetooth {

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public void toggleBluetooth(String switcher) {

            if (switcher.equals("1")) {
                bluetoothAdapter.enable();
            } else if (switcher.equals("0")) {
                bluetoothAdapter.disable();
            }
        }
    }



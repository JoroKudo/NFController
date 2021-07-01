package ch.bbcag.NFController.Features;

import android.bluetooth.BluetoothAdapter;

public class Bluetooth {

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public void toggleBluetooth(String switcher) {

        switch (switcher) {
            case "1":
                bluetoothAdapter.enable();
                break;
            case "0":
                bluetoothAdapter.disable();
                break;
            case "2":
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                } else {
                    bluetoothAdapter.enable();
                }
                break;
        }

    }
}



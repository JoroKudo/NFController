package ch.bbcag.NFController.Features;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.Toast;

import ch.bbcag.NFController.R;

@SuppressWarnings("ALL")
public class Wifi {

    private final WifiManager wifi;
    private final Context context;

    public Wifi(Context context, WifiManager wifi) {
        this.wifi = wifi;
        this.context = context;
    }

    public void toggleWifi(String switcher) {
        if (Build.VERSION.SDK_INT <= 29) {
            if (switcher.equals("1")) {
                wifi.setWifiEnabled(true);
            } else if (switcher.equals("0")) {
                wifi.setWifiEnabled(false);
            }
        } else {
            Toast.makeText(context, (context.getResources().getString(R.string.version_too_high_info)), Toast.LENGTH_SHORT).show();

        }
    }
}



package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.Toast;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.MapActivities.SelectGeoFencingFeatureActivity;

public class Wifi {

    private final WifiManager wifi;

    private final Activity activity;

    public Wifi(WifiManager wifi, Activity activity) {
        this.wifi = wifi;
        this.activity = activity;
    }

    public void toggleWifi(String switcher) {

            if (Build.VERSION.SDK_INT <= 29) {
                if (switcher.equals("1")) {
                    wifi.setWifiEnabled(true);
                } else if (switcher.equals("0")) {
                    wifi.setWifiEnabled(false);
                }
            } else {
                Toast.makeText(activity, ("This function is not working on the newest version of android"), Toast.LENGTH_SHORT).show();

            }
        }
    }



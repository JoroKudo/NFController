package ch.bbcag.NFController;


import android.app.NotificationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

import javax.inject.Inject;
import javax.inject.Singleton;
@Singleton
public class AppDataManager {

    private String[] splitted;

    @Inject
    public AppDataManager() {
    }

    public String[] getSplitted() {
        return splitted;
    }

    public void setSplitted(String[] splitted) {
        this.splitted = splitted;
    }
}

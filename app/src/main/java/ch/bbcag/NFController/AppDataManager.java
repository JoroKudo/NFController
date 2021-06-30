package ch.bbcag.NFController;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class AppDataManager {

    private String[] splitted;
    private WifiManager wifiManager;
    private AudioManager audioManager;
    private NotificationManager notificationManager;

    @Inject
    public AppDataManager(){}

    public String[] getSplitted() {
        return splitted;
    }

    public void setSplitted(String[] splitted) {
        this.splitted = splitted;
    }
}

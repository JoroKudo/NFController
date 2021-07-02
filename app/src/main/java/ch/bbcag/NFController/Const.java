package ch.bbcag.NFController;

import android.os.Build;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;


public class Const {

    public static final String[] fulltask = {"", "", "", "", "", "", "", "", "", ""};
    public static final ArrayList<String[]> taskcontainer = new ArrayList<>();
    public static final String SPACER = "⠀";



    public static final String[] tasknames = {"BlueTooth", "WiFi", "TONE", "MUTE",
            "VIBRATE", "VOL", "TTS", "OpenApp", "set Alarm", "set Timer", "Location", "FLASHLIGHT", "SendWhatsapp", "OpenWebsite", "Geofencing"};
    public static final List<String> TASKS = Arrays.asList("blue", "wifi", "tone", "mute", "vibrate", "vol", "tts", "open", "alarm", "timer", "location", "flash", "send", "web", "geofencing");
    public static final String[] GEOTASKS = {"blue", "blue", "wifi", "wifi", "tone", "mute", "vibrate", "vol", "open", "flash", "web"};

    public static void fragmentLauncher(Fragment fragment, int layout, AppCompatActivity apac) {
        FragmentManager fm = apac.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        assert fragment != null;
        ft.replace(layout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
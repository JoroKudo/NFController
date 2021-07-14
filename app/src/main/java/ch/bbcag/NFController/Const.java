package ch.bbcag.NFController;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.NFController.Database.RequestHandler;

public class Const {
    public static final List<String> procedures = new RequestHandler().getprocedureList();
    public static final String SPACER = "∣";
    public static final String[] tasknames = {"BlueTooth", "WiFi", "TONE", "MUTE",
            "VIBRATE", "VOL", "TTS", "OpenApp", "set Alarm", "set Timer", "Location", "FLASHLIGHT", "SendWhatsapp", "OpenWebsite", "Geofencing", "Custom procedure"};
    public static final String[] TASKS = {"blue", "wifi", "tone", "mute", "vibrate", "vol", "tts", "open", "alarm", "timer", "location", "flash", "send", "web", "geofencing"};
    public static final String[] GEOTASKS = {"blue", "blue", "wifi", "wifi", "tone", "mute", "vibrate", "vol", "open", "flash", "web"};
    public static ArrayList<String[]> taskcontainer = new ArrayList<>();
}
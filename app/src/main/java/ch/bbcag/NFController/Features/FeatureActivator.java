package ch.bbcag.NFController.Features;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Util;

@Singleton
public class FeatureActivator {
    private final AppDataManager appDataManager;

    @Inject
    public FeatureActivator(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    public void activateFeature(Context context, int subFeaturePosition, String[] splitted) {
        switch (splitted[subFeaturePosition]) {
            case "blue":
                Bluetooth bluetooth = new Bluetooth();
                bluetooth.toggleBluetooth(splitted[1]);
                break;
            case "wifi":
                Wifi wifiClass = new Wifi(context, Util.getWifiManager(context));
                wifiClass.toggleWifi(splitted[subFeaturePosition + 1]);
                break;
            case "tone":
                getVolume(context).setToTone();
                break;
            case "mute":
                getVolume(context).setTomute();
                break;
            case "vibrate":
                getVolume(context).setToVibrate();
                break;
            case "vol":
                getVolume(context).changeVolume(splitted[subFeaturePosition + 1]);
                break;
            case "tts":
                TextToSpeechConverter textToSpeechConverter = new TextToSpeechConverter(context);
                textToSpeechConverter.TextToSpeech(splitted[1]);
                break;
            case "open":
                ApplicationOpener applicationOpener = new ApplicationOpener(context);
                applicationOpener.openApp(splitted[subFeaturePosition + 1]);
                break;
            case "alarm":
                Clock alarmClock = new Clock(context);
                alarmClock.setAlarm(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), splitted[3]);
                break;
            case "timer":
                Clock timerClock = new Clock(context);
                timerClock.setTimer(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), splitted[4]);
                break;
            case "flash":
                Flashlight flashlight = new Flashlight(context);
                flashlight.flash(splitted[subFeaturePosition + 1]);
                break;
            case "send":
                WhatsappTexter whatsappTexter = new WhatsappTexter(context);
                whatsappTexter.sendWhatsapp(splitted[1], splitted[2]);
                break;
            case "web":
                Website website = new Website(context);
                website.opensite(splitted[subFeaturePosition + 1]);
                break;

        }
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    private Volume getVolume(Context context) {
        return new Volume(Util.getAudioManager(context));
    }
}
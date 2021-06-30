package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

public class Volume {

    private final AudioManager audioManager;

    public Volume(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public void setTomute() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void setToTone() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public void setToVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }


    public void changeVolume(String adjuster) {
        if (adjuster.equals("+")) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

        } else if (adjuster.equals("-")) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }
    }



}

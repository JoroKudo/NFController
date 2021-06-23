package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

public class Volume {

    private final Activity activity;
    private final AudioManager audioManager;
    private final NotificationManager notificationManager;

    public Volume(Activity activity, AudioManager audioManager, NotificationManager notificationManager) {
        this.activity = activity;
        this.audioManager = audioManager;
        this.notificationManager = notificationManager;
    }

    public void setTomute() {
        checkIfNotificationPermissionIsGranted();
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void setToTone() {
        checkIfNotificationPermissionIsGranted();
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public void setToVibrate() {
        checkIfNotificationPermissionIsGranted();
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }


    public void changeVolume(String adjuster) {
        if (adjuster.equals("+")) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

        } else if (adjuster.equals("-")) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }
    }


    public void checkIfNotificationPermissionIsGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                activity.startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
            } else {
                Toast.makeText(activity, ("This function is not working on this version of android"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}

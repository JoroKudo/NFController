package ch.bbcag.nfcontroller;


import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;


import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;


public class Tasks {

    private final Activity activity;
    private final NotificationManager notificationManager;
    private final WifiManager wifi;
    private final AudioManager audioManager;


    private TextToSpeech tts;
    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();


    public Tasks(Activity activity, NotificationManager notificationManager, WifiManager wifi, AudioManager audioManager) {


        this.activity = activity;

        this.notificationManager = notificationManager;
        this.wifi = wifi;
        this.audioManager = audioManager;
    }

    public void bluetooth(String switcher) {
        if (switcher.equals("1")) {
            bAdapter.enable();
        } else if (switcher.equals("0")) {
            bAdapter.disable();
        }
    }

    public void wifi(String switcher) {
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

    public void checkIfNotificationPermissionIsGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                activity.startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
            } else {
                Toast.makeText(activity, ("This function is not working on this version of android"), Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void opensite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;}
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);

    }


    public void changeVolume(String adjuster) {
        if (adjuster.equals("+")) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

        } else if (adjuster.equals("-")) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }
    }


    public void TextToSpeech(String speech) {
        tts = new TextToSpeech(activity, status -> {
            if (status == TextToSpeech.SUCCESS) {

                tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
            }
        });
        tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
    }

    public void openApp(String packageName) {


        Intent launch = activity.getPackageManager().getLaunchIntentForPackage(packageName);

        if (launch == null) {
            try {
                // if play store installed, open play store, else open browser
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            } catch (Exception e) {
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            }
        }
        activity.startActivity(launch);
    }

    public void setAlarm(int hour, int minute) {


        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        alarm.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarm.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        alarm.putExtra(AlarmClock.EXTRA_MESSAGE, "Good Morning");
        activity.startActivity(alarm);
    }

    public void setTimer(int seconds) {

        Intent timer = new Intent(AlarmClock.ACTION_SET_TIMER);
        timer.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        timer.putExtra(AlarmClock.EXTRA_LENGTH, seconds);
        timer.putExtra(AlarmClock.EXTRA_MESSAGE, "Good Morning");
        activity.startActivity(timer);
    }


    public void flash(String switcher) {

        CameraManager camManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        String cameraId;
        try {
            cameraId = camManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (switcher.equals("1")) {
                    camManager.setTorchMode(cameraId, true);
                } else if (switcher.equals("0")) {
                    camManager.setTorchMode(cameraId, false);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void sendWhatsapp(String nr, String message) {
        Intent In_Whats = new Intent(Intent.ACTION_VIEW);
        In_Whats.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + nr + "&&text=" + message));
        activity.startActivity(In_Whats);
    }


}


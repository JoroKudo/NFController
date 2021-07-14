package ch.bbcag.NFController.Features;

import android.media.AudioManager;

public class Volume {

    private final AudioManager audioManager;

    public Volume(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public void setToMute() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void setToTone() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public void setToVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public void changeVolume(String adjuster) {
        int attribute = Integer.parseInt(adjuster.substring(1));
        switch (adjuster.substring(0, 1)) {
            case "+":
                adjusting(attribute);
                break;
            case "-":
                adjusting(-attribute);
                break;
            case "=":
                adjusting(audioManager.getStreamVolume(attribute - AudioManager.STREAM_MUSIC));
                break;
        }
    }

    private void adjusting(int attribute) {
        boolean isNeg = attribute < 0;
        int abs = Math.abs(attribute);
        int adjust;
        if (isNeg) {
            adjust = AudioManager.ADJUST_LOWER;
        } else {
            adjust = AudioManager.ADJUST_RAISE;
        }
        for (int i = 0; i < abs; i++) {
            audioManager.adjustVolume(adjust, AudioManager.FLAG_PLAY_SOUND);
        }
    }
}

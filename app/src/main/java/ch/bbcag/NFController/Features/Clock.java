package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;

public class Clock {

    private final Activity activity;

    public Clock(Activity activity) {
        this.activity = activity;

    }

    public void setAlarm(int hour, int minute, String message) {


        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        alarm.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarm.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        alarm.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        activity.startActivity(alarm);
    }

    public void setTimer(int h, int m, int s, String message) {
        int timerLength = 60 * (h * 60 + m) + s;
        Intent timer = new Intent(AlarmClock.ACTION_SET_TIMER);
        timer.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        timer.putExtra(AlarmClock.EXTRA_LENGTH, timerLength);
        timer.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        activity.startActivity(timer);
    }
}

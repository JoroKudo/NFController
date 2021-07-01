package ch.bbcag.NFController;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import javax.inject.Inject;

public class SecurityManager {

    @Inject
    SecurityManager() {
    }


    public void checkIfNotificationPermissionIsGranted(Activity activity, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                activity.startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
            } else {
                Toast.makeText(activity, ("This function is not working on this version of android"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}

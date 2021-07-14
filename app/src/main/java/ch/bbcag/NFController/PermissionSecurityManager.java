package ch.bbcag.NFController;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class PermissionSecurityManager {

    @SuppressLint("InlinedApi")
    private static final String ACCESS_BACKGROUND_LOCATION = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 1;
    private static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static boolean hasThePermissionAlreadyBeenDenied = false;
    public final int BACKGROUND_PERMISSION_REQUEST = 2;
    private final String[] permissions = new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

    @Inject
    public PermissionSecurityManager() {
    }

    public void checkIfNotificationPermissionIsGranted(Activity activity, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                activity.startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
            }
        } else {
            Toast.makeText(activity, (activity.getApplicationContext().getResources().getString(R.string.version_not_compatible_info)), Toast.LENGTH_SHORT).show();
        }
    }

    public void requestMultiplePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_COARSE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION)) {
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            activity.getApplicationContext().getResources().getString(R.string.please_grant_location_permissions_message),
                            Snackbar.LENGTH_INDEFINITE).setAction(activity.getApplicationContext().getResources().getString(R.string.request_multiple_permissions_button),
                            v -> activity.requestPermissions(
                                    permissions, PERMISSIONS_MULTIPLE_REQUEST)).show();
                }
            }
        }
    }

    public void requestBackgroundLocation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_BACKGROUND_LOCATION)) {
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            activity.getApplicationContext().getResources().getString(R.string.please_change_location_settings_message),
                            Snackbar.LENGTH_INDEFINITE).setAction(activity.getApplicationContext().getResources().getString(R.string.request_multiple_permissions_button),
                            v -> activity.requestPermissions(new String[]{ACCESS_BACKGROUND_LOCATION}, BACKGROUND_PERMISSION_REQUEST)).show();
                }
            }
        }
    }

    public boolean areMultiplePermissionsGranted(Activity activity) {
        return (ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_COARSE_LOCATION))
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasThePermissionAlreadyBeenDenied() {
        return hasThePermissionAlreadyBeenDenied;
    }

    public void setIfThePermissionHasAlreadyBeenDenied(boolean hasThePermissionAlreadyBeenDenied) {
        PermissionSecurityManager.hasThePermissionAlreadyBeenDenied = hasThePermissionAlreadyBeenDenied;
    }
}

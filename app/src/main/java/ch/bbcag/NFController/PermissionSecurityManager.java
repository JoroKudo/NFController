package ch.bbcag.NFController;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;
import javax.inject.Singleton;

public class PermissionSecurityManager {

    private static final String ACCESS_BACKGROUND_LOCATION = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    private static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 1;
    public final int BACKGROUND_PERMISSION_REQUEST = 2;
    private static boolean hasThePermissionAlreadyBeenDenied = false;

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
            Toast.makeText(activity, ("This function is not working on this version of android"), Toast.LENGTH_SHORT).show();
        }
    }

    public void requestMultiplePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //LUEGT EB D PERMISSIONS SCHO ERTEILT WORDE SIND
            if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_COARSE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {
                //LUEGT EB D PERMISSION EN REQUEST IM UI BRUUCHT
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION)) {
                    //ZEIGT S UI FÜR DE REQUEST AH
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            "Please grant the required permissions to use the geofencing feature",
                            Snackbar.LENGTH_INDEFINITE).setAction("Continue",
                            v -> activity.requestPermissions(
                                    permissions, PERMISSIONS_MULTIPLE_REQUEST)).show();
                }
            }
        }
    }

    public void requestBackgroundLocation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //LUEGT EB D PERMISSIONS SCHO ERTEILT WORDE SIND
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //LUEGT EB D PERMISSION EN REQUEST IM UI BRUUCHT
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_BACKGROUND_LOCATION)) {
                    //ZEIGT S UI FÜR DE REQUEST AH
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            "Please change your Location settings to ALLOW ALL THE TIME to use the geofencing feature.",
                            Snackbar.LENGTH_INDEFINITE).setAction("Continue",
                            v -> activity.requestPermissions(new String[]{ACCESS_BACKGROUND_LOCATION}, BACKGROUND_PERMISSION_REQUEST)).show();

                }
            }
        }
    }

    public boolean areMultiplePermissionsGranted(Activity activity) {
        if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_COARSE_LOCATION))
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    public boolean isBackgroundPermissionGranted(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    public boolean hasThePermissionAlreadyBeenDenied() {
        return hasThePermissionAlreadyBeenDenied;
    }

    public void setIfThePermissionHasAlreadyBeenDenied(boolean hasThePermissionAlreadyBeenDenied) {
        PermissionSecurityManager.hasThePermissionAlreadyBeenDenied = hasThePermissionAlreadyBeenDenied;
    }

    public int getPERMISSIONS_MULTIPLE_REQUEST() {
        return PERMISSIONS_MULTIPLE_REQUEST;
    }

    public int getBACKGROUND_PERMISSION_REQUEST() {
        return BACKGROUND_PERMISSION_REQUEST;
    }
}

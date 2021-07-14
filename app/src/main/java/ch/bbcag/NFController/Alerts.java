package ch.bbcag.NFController;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import javax.inject.Inject;

public class Alerts {

    @Inject
    public Alerts() {
    }

    public void displayPermissionAlert(Context context) {

        AlertDialog permissionAlert = new AlertDialog.Builder(context)
                .setTitle(R.string.permission_alert_title)
                .setMessage(R.string.permission_alert_message)
                .setPositiveButton(R.string.change_location_permissions, (dialog, which) -> context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(R.string.close, (dialog, which) -> context.startActivity(new Intent(context, TaskAdder.class)))
                .show();
        permissionAlert.setCanceledOnTouchOutside(false);
    }

    public void diplayNoGeofenceActiveAlert(Context context) {
        AlertDialog noGeofenceActiveAlert = new AlertDialog.Builder(context).setTitle("No Geofences active")
                .setMessage("There are currently no geofences active. " +
                        "You can add Geofences in ADD TASKS or you can get information about a Geofence through scanning a NFC Tag. ")
                .setPositiveButton("got it", (dialog, which) -> dialog.cancel()).setNegativeButton("cancel", (dialog, which) -> context.startActivity(new Intent(context, NfcHome.class)))
                .show();
        noGeofenceActiveAlert.setCanceledOnTouchOutside(false);
    }
}

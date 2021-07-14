package ch.bbcag.NFController;

import android.app.Activity;
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

    public void deletedialog(int position, Activity act) {
        new AlertDialog.Builder(act)
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")
                .setPositiveButton("delete", (dialog, which) -> {
                    Const.taskcontainer.remove(position);
                    act.startActivity(new Intent(act, TaskAdder.class));
                    act.finish();
                })

                .setNegativeButton("dont delete", (dialog, which) -> dialog.cancel())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    
    public void displayNoGeofenceActiveAlert(Context context) {
        AlertDialog noGeofenceActiveAlert = new AlertDialog.Builder(context).setTitle(context.getResources().getString(R.string.no_geofence_active_alert_title))
                .setMessage(context.getResources().getString(R.string.no_geofence_active_alert_message))
                .setPositiveButton(context.getResources().getString(R.string.no_geofence_active_alert_positive_button), (dialog, which) -> dialog.cancel())
                .setNegativeButton(context.getResources().getString(R.string.no_geofence_active_alert_negative_button), (dialog, which) -> context.startActivity(new Intent(context, NfcHome.class)))
                .show();
        noGeofenceActiveAlert.setCanceledOnTouchOutside(false);
    }
}

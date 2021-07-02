package ch.bbcag.NFController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import javax.inject.Inject;

public class Alerts {

    private AlertDialog permissionAlert;

    @Inject
    public Alerts() {
    }

    public void DisplayPermissionAlert(Context context) {
        permissionAlert = new AlertDialog.Builder(context)
                .setTitle(R.string.permission_alert_title)
                .setMessage(R.string.permission_alert_message)
                .setPositiveButton(R.string.change_location_permissions, (dialog, which) -> context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(R.string.close, (dialog, which) -> context.startActivity(new Intent(context, TaskAdder.class)))
                .show();
    }
}

package ch.bbcag.NFController.MapActivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import javax.inject.Inject;

import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.Features.FeatureActivator;
import ch.bbcag.NFController.NfcHome;
import ch.bbcag.NFController.R;

import static android.content.ContentValues.TAG;

public class GeofencingBroadcastReceiver extends BroadcastReceiver {

    @Inject
    FeatureActivator featureActivator;

    @Override
    public void onReceive(Context context, Intent intent) {

        ((NFControllerApplication) context.getApplicationContext()).appComponent.inject(this);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                int subFeaturePosition = 7;
                featureActivator.activateFeature(context, subFeaturePosition, featureActivator.getAppDataManager().getSplitted());
            }
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            String geofenceTransitionDetails = this.toString() + geofenceTransition + triggeringGeofences;

            Log.i(TAG, geofenceTransitionDetails);
            Toast.makeText(context.getApplicationContext(), context.getResources().getString(R.string.geofencing_transition_occurred), Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(context.getApplicationContext(), NfcHome.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(homeIntent);
        } else {
            Toast.makeText(context.getApplicationContext(), TAG + (geofenceTransition), Toast.LENGTH_SHORT).show();
        }
    }
}


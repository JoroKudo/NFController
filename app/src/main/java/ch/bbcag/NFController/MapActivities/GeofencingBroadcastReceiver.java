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

import static android.content.ContentValues.TAG;

public class GeofencingBroadcastReceiver extends BroadcastReceiver {

    private final int subFeaturePosition = 7;

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

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                featureActivator.activateFeature(context, subFeaturePosition, featureActivator.getAppDataManager().getSplitted());
            }
            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = this.toString() + geofenceTransition + triggeringGeofences;

            // Send notification and log the transition details.
            Log.i(TAG, geofenceTransitionDetails);
            Toast.makeText(context.getApplicationContext(), "Youre in", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(context.getApplicationContext(), NfcHome.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(homeIntent);
        } else {
            // Log the error.
            Toast.makeText(context.getApplicationContext(), TAG + (geofenceTransition), Toast.LENGTH_SHORT).show();

        }
    }

    private String getGeofenceTransitionDetails(GeofencingBroadcastReceiver geofencingBroadcastReceiver, int geofenceTransition, List<Geofence> triggeringGeofences) {
        return getGeofenceTransitionDetails(geofencingBroadcastReceiver, geofenceTransition, triggeringGeofences);
    }
}


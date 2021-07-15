package ch.bbcag.NFController.Dagger2;

import javax.inject.Singleton;

import ch.bbcag.NFController.Features.FeatureActivator;
import ch.bbcag.NFController.Features.Flashlight;
import ch.bbcag.NFController.MapActivities.FinalGeoFencingViewActivity;
import ch.bbcag.NFController.MapActivities.GeofencingActivity;
import ch.bbcag.NFController.MapActivities.GeofencingBroadcastReceiver;
import ch.bbcag.NFController.MapActivities.GeofencingFeatureSelector;
import ch.bbcag.NFController.MapActivities.MapsActivity;
import ch.bbcag.NFController.MapActivities.SecurityFragmentActivity;
import ch.bbcag.NFController.MapActivities.SelectGeofencingExpirationTimeActivity;
import ch.bbcag.NFController.MapActivities.SelectGeofencingRadiusActivity;
import ch.bbcag.NFController.NFCRead;
import ch.bbcag.NFController.NfcHome;
import dagger.Component;

@Singleton
@Component
public interface ApplicationComponent {
    void inject(GeofencingBroadcastReceiver geofencingBroadcastReceiver);

    void inject(GeofencingActivity geofencingActivity);

    void inject(NFCRead nfcRead);

    void inject(FeatureActivator featureActivator);

    void inject(FinalGeoFencingViewActivity finalGeoFencingViewActivity);

    void inject(MapsActivity mapsActivity);

    void inject(SecurityFragmentActivity securityFragmentActivity);

    void inject(NfcHome nfcHome);

    void inject(SelectGeofencingRadiusActivity selectGeofencingRadiusActivity);

    void inject(SelectGeofencingExpirationTimeActivity selectGeofencingExpirationTimeActivity);

    void inject(GeofencingFeatureSelector geofencingFeatureSelector);
}

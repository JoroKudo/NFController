package ch.bbcag.NFController.Dagger2;

import javax.inject.Singleton;

import ch.bbcag.NFController.Features.FeatureActivator;
import ch.bbcag.NFController.MapActivities.GeofencingActivity;
import ch.bbcag.NFController.MapActivities.GeofencingBroadcastReceiver;
import ch.bbcag.NFController.NFCRead;
import dagger.Component;

@Singleton
@Component
public interface ApplicationComponent {
    void inject(GeofencingBroadcastReceiver geofencingBroadcastReceiver);

    void inject(GeofencingActivity geofencingActivity);

    void inject(NFCRead nfcRead);

    void inject(FeatureActivator featureActivator);

}

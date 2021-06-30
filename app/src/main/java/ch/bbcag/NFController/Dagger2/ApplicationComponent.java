package ch.bbcag.NFController.Dagger2;

import ch.bbcag.NFController.MapActivities.GeofencingBroadcastReceiver;
import dagger.Component;

@Component
public interface ApplicationComponent {
    void inject(GeofencingBroadcastReceiver geofencingBroadcastReceiver);
}

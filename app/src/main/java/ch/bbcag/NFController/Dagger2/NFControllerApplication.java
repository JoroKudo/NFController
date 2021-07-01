package ch.bbcag.NFController.Dagger2;

import android.app.Application;

public class NFControllerApplication extends Application {
    public final ApplicationComponent appComponent = DaggerApplicationComponent.create();
}

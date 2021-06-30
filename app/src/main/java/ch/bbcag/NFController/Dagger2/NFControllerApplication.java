package ch.bbcag.NFController.Dagger2;

import android.app.Application;

public class NFControllerApplication extends Application {
    ApplicationComponent appComponent= DaggerApplicationComponent.create();
}

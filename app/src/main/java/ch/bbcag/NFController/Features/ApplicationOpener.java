package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class ApplicationOpener {

    private final Activity activity;

    public ApplicationOpener(Activity activity) {
        this.activity = activity;
    }

    public void openApp(String packageName) {


        Intent launch = activity.getPackageManager().getLaunchIntentForPackage(packageName);

        if (launch == null) {
            try {
                // if play store installed, open play store, else open browser
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            } catch (Exception e) {
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            }
        }
        activity.startActivity(launch);
    }
}

package ch.bbcag.NFController.Features;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import ch.bbcag.NFController.R;

public class ApplicationOpener {

    private final Context context;

    public ApplicationOpener(Context context) {
        this.context = context;
    }

    public void openApp(String packageName) {

        Intent launch = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (launch == null) {
            try {
                // if play store installed, open play store, else open browser
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getResources().getString(R.string.app_store_redirect_URL) + packageName));
            } catch (Exception e) {
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getResources().getString(R.string.app_store_web_application_redirect_URL) + packageName));
            }
        }
        context.startActivity(launch);
    }
}

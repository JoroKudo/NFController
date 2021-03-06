package ch.bbcag.NFController.Features;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Website {

    private final Context context;

    public Website(Context context) {
        this.context = context;
    }

    public void openSite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

}

package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class WhatsappTexter {

    private final Activity activity;

    public WhatsappTexter(Activity activity) {
        this.activity = activity;
    }


    public void sendWhatsapp(String nr, String message) {
        Intent In_Whats = new Intent(Intent.ACTION_VIEW);
        In_Whats.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + nr + "&&text=" + message));
        activity.startActivity(In_Whats);
    }
}

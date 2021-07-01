package ch.bbcag.NFController.Features;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WhatsappTexter {

    private final Context context;

    public WhatsappTexter(Context context) {
        this.context = context;
    }


    public void sendWhatsapp(String nr, String message) {
        Intent In_Whats = new Intent(Intent.ACTION_VIEW);
        In_Whats.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + nr + "&&text=" + message));
        context.startActivity(In_Whats);
    }
}

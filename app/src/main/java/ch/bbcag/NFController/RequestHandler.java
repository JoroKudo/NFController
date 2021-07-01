package ch.bbcag.NFController;


import android.annotation.SuppressLint;
import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;
import android.os.Parcelable;

import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class RequestHandler {
    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");


    public void SaveTagdata(Ndef ndef, Parcelable[] messages, NdefRecord[] records) {

        String e = ndef.toString();
        e = e.replaceAll("\\.", "_");

        e = e.split("_")[3];

        String path = "Tag/Data/" + e + " " + now() + "/";


        database.getReference(path + "ndef").push().setValue(ndef.toString());
        for (Parcelable message : messages) {
            database.getReference(path + "messages").push().setValue(message.toString());
        }

        for (NdefRecord record : records) {
            database.getReference(path + "Type").push().setValue(record.toMimeType());
            database.getReference(path + "TagData").push().setValue(new String(record.getPayload()));
        }


    }

    public String now() {
        TimeZone tz = TimeZone.getTimeZone("UTC");

        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(" dd-MM-yyyy HH:mm");

        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
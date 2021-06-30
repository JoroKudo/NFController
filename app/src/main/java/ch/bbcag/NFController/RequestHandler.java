package ch.bbcag.NFController;


import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class RequestHandler {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");
    public String post;
    private long entrycount;

    public void writeit() {

        DatabaseReference mDbRef = database.getReference("Donor/Name");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue().toString();

            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDbRef.addValueEventListener(postListener);
        mDbRef.setValue(post + " lol");

    }

    public void SaveTagdata(Ndef ndef, Parcelable[] messages, NdefRecord records[]) {

        String e = ndef.toString();
        e = e.replaceAll("\\.", "_");

        e = e.split("_")[3];

        String path = "Tag/Data/" + e + " " + now() + "/";


        database.getReference(path + "ndef").push().setValue(ndef.toString());
        for (int i = 0; i < messages.length; i++) {
            database.getReference(path + "messages").push().setValue(messages[i].toString());
        }

        for (int j = 0; j < records.length; j++) {
            database.getReference(path + "Type").push().setValue(records[j].toMimeType());
            database.getReference(path + "TagData").push().setValue(new String(records[j].getPayload()));
        }


    }

    public String now() {
        TimeZone tz = TimeZone.getTimeZone("UTC");

        DateFormat df = new SimpleDateFormat(" dd-MM-yyyy HH:mm");

        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
package ch.bbcag.NFController;


import android.annotation.SuppressLint;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;


public class RequestHandler {
    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");
    private int entries;

    public static ArrayList<String[]> ayy = new ArrayList<>();


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

    public ArrayList<String[]> insane(String procName) {


            String path = "Procedures/" + procName ;

            DatabaseReference mDbRef = database.getReference(path).getRef();


            mDbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    entries = (int) dataSnapshot.getChildrenCount();

                    for (int i = 0; i < entries; i++) {
                        RequestHandler.ayy.add(i,Objects.requireNonNull(dataSnapshot.child(String.valueOf(i)).getValue(String.class)).split(","));



                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }


            });



        return ayy;
    }

    public void tksop(String ProcedureName) {


        for (int i = 0; i < Const.taskcontainer.size(); i++) {

                if (!Const.taskcontainer.get(i)[0].isEmpty()) {
                    database.getReference("Procedures/" + ProcedureName + "/" + i )
                            .setValue(Const.taskcontainer.get(i).toString());
                }

        }


    }

    public String now() {
        TimeZone tz = TimeZone.getTimeZone("UTC");

        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(" dd-MM-yyyy HH:mm");

        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
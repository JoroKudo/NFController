package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.bbcag.NFController.MapActivities.SelectGeofencingRadiusActivity;


public class HomeTab1 extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_tab_1, container,
                false);
        RelativeLayout rlRead = view.findViewById(R.id.rlReadNFCTAG);
        RelativeLayout rlWrite = view.findViewById(R.id.rlWriteWithNFC);
        RelativeLayout rlWipe = view.findViewById(R.id.rlWipeWithNFC);
        RelativeLayout rlTest = view.findViewById(R.id.rlTestWithNFC);
        rlRead.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlWipe.setOnClickListener(this);
        rlTest.setOnClickListener(this);
        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rlReadNFCTAG:
                intent = new Intent(getContext(), NFCRead.class);
                startActivity(intent);
                break;

            case R.id.rlWriteWithNFC:
                writeit();
                intent = new Intent(getContext(), NFCWrite.class);
                startActivity(intent);
                break;
            case R.id.rlWipeWithNFC:
                intent = new Intent(getContext(), NFCWipe.class);
                startActivity(intent);
                break;
            case R.id.rlTestWithNFC:
                intent = new Intent(getContext(), NFCTest.class);
                startActivity(intent);
                break;


        }
    }
    public void writeit() {
        FirebaseDatabase databaset = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference mDbRef = databaset.getReference("Donor/Name");
        mDbRef.setValue("Parinitha Krishna");
    }

}
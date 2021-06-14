package ch.bbcag.NFController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FirstFragment extends Fragment implements View.OnClickListener{
    private final Activity activity;
    private RelativeLayout rlRead;
    private RelativeLayout rlWrite;
    private RelativeLayout rlWipe;
    private RelativeLayout rlTest;

    public FirstFragment(Activity activity){
        this.activity=activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlRead.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlWipe.setOnClickListener(this);
        rlTest.setOnClickListener(this);

    }
    private void initViews() {

        rlRead = activity.findViewById(R.id.rlReadNFCTAG);
        rlWrite = activity.findViewById(R.id.rlWriteWithNFC);
        rlWipe = activity.findViewById(R.id.rlWipeWithNFC);
        rlTest = activity.findViewById(R.id.rlTestWithNFC);

    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rlReadNFCTAG:
                intent = new Intent(activity, NFCRead.class);
                this.startActivity(intent);
                break;

            case R.id.rlWriteWithNFC:
                intent = new Intent(activity, NFCWrite.class);
                this.startActivity(intent);
                break;
            case R.id.rlWipeWithNFC:
                intent = new Intent(activity, NFCWipe.class);
                this.startActivity(intent);
                break;
            case R.id.rlTestWithNFC:
                intent = new Intent(activity, NFCTest.class);
                this.startActivity(intent);
                break;




        }
    }
 
}
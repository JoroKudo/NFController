package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class NfcHome extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlRead;
    private RelativeLayout rlWrite;
    private RelativeLayout rlWipe;
    private RelativeLayout rlTest;
    private TabLayout tabLayout;
    private ViewPager simpleViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_home);
        initViews();

        rlRead.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlWipe.setOnClickListener(this);
        rlTest.setOnClickListener(this);

        simpleViewPager =  findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("First");
        firstTab.setIcon(R.drawable.ic_launcher);
        tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Second");
        secondTab.setIcon(R.drawable.ic_launcher);
        tabLayout.addTab(secondTab);


        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FirstFragment(getParent());
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;

                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews() {

        rlRead = findViewById(R.id.rlReadNFCTAG);
        rlWrite = findViewById(R.id.rlWriteWithNFC);
        rlWipe = findViewById(R.id.rlWipeWithNFC);
        rlTest = findViewById(R.id.rlTestWithNFC);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rlReadNFCTAG:
                intent = new Intent(this, NFCRead.class);
                this.startActivity(intent);
                break;

            case R.id.rlWriteWithNFC:
                intent = new Intent(this, NFCWrite.class);
                this.startActivity(intent);
                break;
            case R.id.rlWipeWithNFC:
                intent = new Intent(this, NFCWipe.class);
                this.startActivity(intent);
                break;
            case R.id.rlTestWithNFC:
                intent = new Intent(this, NFCTest.class);
                this.startActivity(intent);
                break;




        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
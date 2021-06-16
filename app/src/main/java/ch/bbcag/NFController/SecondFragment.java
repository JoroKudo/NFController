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



public class SecondFragment extends Fragment implements View.OnClickListener{
    private final Activity activity;

    public SecondFragment(Activity activity){
        this.activity=activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_second, container,
                false);
        RelativeLayout rlcommand = view.findViewById(R.id.rlNFCommands);

        rlcommand.setOnClickListener(this);

        return view;

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.rlNFCommands) {
            intent = new Intent(activity, NFCommands.class);
            activity.startActivity(intent);
        }
    }

}
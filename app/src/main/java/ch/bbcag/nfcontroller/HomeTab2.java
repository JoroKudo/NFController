package ch.bbcag.nfcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;



public class HomeTab2 extends Fragment{
    private final NFCBase activity;

    public HomeTab2(NFCBase activity){
        this.activity=activity;
    }



    // Array of strings...
    String[] mobileArray = {"BlueTooth", "WiFi", "TONE", "MUTE",
            "VIBRATE", "VOL", "FLASHLIGHT", "TTS"};

    String[] taskArray = {"blue", "wifi", "tone", "mute",
            "vibrate", "vol", "flash", "tts"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.task_list, container,
                false);
        ListView listView = view.findViewById(R.id.mobile_list);
        ArrayAdapter adapter = new ArrayAdapter<>(activity,
                R.layout.task_list_item, mobileArray);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, viewv, position, id) -> {
            Const.task = taskArray[position];
            Intent intent = new Intent(activity, TaskWriter.class);
            activity.startActivity(intent);
        });

        return view;

    }



}


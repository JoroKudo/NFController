package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import ch.bbcag.NFController.MapActivities.MapsActivity;

public class TaskAdderList extends Fragment {


    private String[] taskListNames = new String[Const.taskcontainer.size()];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = requireContext().getPackageManager().queryIntentActivities(mainIntent, 0);


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container,
                false);

        ListView listView = view.findViewById(R.id.mobile_list);


        updatelist();
        ArrayAdapter<?> adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, taskListNames);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, viewv, position, id) -> {
            showNfcSettingsDialog(position);

        });

        return view;

    }

    public void showNfcSettingsDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")

                .setPositiveButton("delete", (dialog, which) -> {


                    Const.taskcontainer.remove(position);
                    Intent intent = new Intent(getContext(), taskadder.class);

                    startActivity(intent);
                    getActivity().finish();
                })

                .setNegativeButton("edit", (dialog, which) -> this.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void updatelist() {
        for (int i = 0; i < Const.taskcontainer.size(); i++) {
            taskListNames[i] = Const.tasknames[Const.TASKS.indexOf(Const.taskcontainer.get(i)[0])] + " " + Const.taskcontainer.get(i)[1];
        }
    }
}


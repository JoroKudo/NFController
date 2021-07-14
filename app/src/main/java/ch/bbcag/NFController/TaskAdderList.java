package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.Arrays;

import ch.bbcag.NFController.CustomLists.CustomList;

public class TaskAdderList extends Fragment {

    private final String[] taskListNames = new String[Const.taskcontainer.size()];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container,
                false);
        ListView listView = view.findViewById(R.id.mobile_list);
        updatelist();
        @SuppressLint("UseCompatLoadingForDrawables")
        CustomList appList = new CustomList(getActivity(), taskListNames, new Drawable[]{getResources().getDrawable(R.drawable.trash)}, R.layout.added_task_list_item);
        listView.setAdapter(appList);
        return view;
    }

    private void updatelist() {
        for (int i = 0; i < Const.taskcontainer.size(); i++) {
            taskListNames[i] = Const.tasknames[Arrays.asList(Const.TASKS).indexOf(Const.taskcontainer.get(i)[0])] + " " + Const.taskcontainer.get(i)[1];
        }
    }
}


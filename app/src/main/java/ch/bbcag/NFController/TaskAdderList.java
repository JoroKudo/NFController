package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;


public class TaskAdderList extends Fragment {


    private final String[] taskListNames = new String[Const.taskcontainer.size()];


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
        @SuppressLint("UseCompatLoadingForDrawables")
        CustomLists.MyCustomAdapter appList = new CustomLists.MyCustomAdapter(getActivity(), taskListNames, getResources().getDrawable(R.drawable.trash), R.layout.added_task_list_item);
        listView.setAdapter(appList);
        //ArrayAdapter<?> adapter = new ArrayAdapter<>(getContext(), R.layout.task_list_item, taskListNames);


        //listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemView, position, id) -> {


        });

        return view;

    }


    private void updatelist() {
        for (int i = 0; i < Const.taskcontainer.size(); i++) {
            taskListNames[i] = Const.tasknames[Const.TASKS.indexOf(Const.taskcontainer.get(i)[0])] + " " + Const.taskcontainer.get(i)[1];
        }
    }
}


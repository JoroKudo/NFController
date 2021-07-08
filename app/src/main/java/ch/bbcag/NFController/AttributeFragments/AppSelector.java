package ch.bbcag.NFController.AttributeFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import ch.bbcag.NFController.CustomList;
import ch.bbcag.NFController.R;

public  class AppSelector extends Fragment {

    private String[] pkgNames;
    private String[] appNames;
    private Drawable[] appIcons;
    String app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = requireContext().getPackageManager().queryIntentActivities(mainIntent, 0);
        appNames = new String[pkgAppsList.size()];
        pkgNames = new String[pkgAppsList.size()];
        appIcons = new Drawable[pkgAppsList.size()];
        for (int i = 0; i < pkgAppsList.size(); i++) {
            pkgNames[i] = pkgAppsList.get(i).toString().split(" ")[1].split("/")[0];
            PackageManager packageManager = requireActivity().getApplicationContext().getPackageManager();

            try {
                appIcons[i] = packageManager.getApplicationIcon(pkgNames[i]);
                appNames[i] = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(pkgNames[i], PackageManager.GET_META_DATA));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list, container,
                false);

        ListView listView = view.findViewById(R.id.mobile_list);


        // For populating list data
        CustomList appList = new CustomList(getActivity(), appNames, appIcons);
        listView.setAdapter(appList);


        listView.setOnItemClickListener((parent, clickView, position, id) -> {

            app = pkgNames[position];
            for (int i = 0; i < parent.getChildCount(); i++) {
                parent.getChildAt(i).setBackgroundColor(android.R.color.transparent);
            }
            clickView.setBackgroundColor(Color.BLUE);

        });
        return view;
    }

    public String getapp() {
        return app;

    }


}
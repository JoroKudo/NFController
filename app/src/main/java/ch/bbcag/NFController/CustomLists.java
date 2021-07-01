package ch.bbcag.NFController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomLists {


    @SuppressWarnings("rawtypes")
    public static class MyCustomAdapter extends ArrayAdapter {
        private final String[] listEntries;
        private final Activity context;
        private final Drawable icon;
        private final int layout;

        @SuppressWarnings("unchecked")
        public MyCustomAdapter(Activity context, String[] listEntries, Drawable icon, int layout) {
            super(context, layout, listEntries);
            this.context = context;
            this.listEntries = listEntries;
            this.icon = icon;
            this.layout = layout;

        }

        @Override
        public int getCount() {
            return listEntries.length;
        }

        @Override
        public Object getItem(int pos) {
            return listEntries[pos];
        }

        @Override
        public long getItemId(int pos) {
            return 0;
            //just return 0 if your list items do not have an Id variable.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(layout, null);
            }

            //Handle TextView and display string from your list
            TextView tvContact = view.findViewById(R.id.TV_ListEntry);


            //Handle buttons and add onClickListeners
            ImageView imageFlag = (ImageView) view.findViewById(R.id.ImgV_icon);
            tvContact.setText(listEntries[position]);

            imageFlag.setImageDrawable(icon);

            imageFlag.setOnClickListener(v -> deletedialog(position));


            return view;
        }

        public void deletedialog(int position) {
            new AlertDialog.Builder(getContext())
                    .setTitle("NFC is disabled")
                    .setMessage("You must enable NFC to use this app.")

                    .setPositiveButton("delete", (dialog, which) -> {


                        Const.taskcontainer.remove(position);
                        Intent intent = new Intent(getContext(), taskadder.class);

                        context.startActivity(intent);
                        context.finish();
                    })

                    .setNegativeButton("edit", (dialog, which) -> context.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}

package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class CustomTrainingAdapter extends ArrayAdapter<String>{
    CustomTrainingAdapter(Context context, String[] titulos) {
        super(context, R.layout.layout_training, titulos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        final View customView = buckysInflater.inflate(R.layout.layout_training, parent,false);

        String singleTitleItem = getItem(position);
        final TextView title = (TextView) customView.findViewById(R.id.item_title);
        final ImageButton options = (ImageButton) customView.findViewById(R.id.item_options);

        title.setText(singleTitleItem);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(customView);
            }
        });
        return customView;
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(view.findViewById(R.id.item_options).getContext(), view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });// to implement on click event on items of menu
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_training_elements, popup.getMenu());
        popup.show();
    }
}

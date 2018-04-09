package com.rigobertosl.nevergiveapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomFoodAdapter extends RecyclerView.Adapter<CustomFoodAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<FoodTable> foodTables;
    private String filterDay;
    private DataBaseContract db;
    private RecyclerView recyclerView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageButton itemOptions;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            itemOptions = (ImageButton) view.findViewById(R.id.item_options);
        }
    }

    public CustomFoodAdapter(Context mContext, ArrayList<FoodTable> foodTables, String filterDay) {
        this.mContext = mContext;
        this.foodTables = foodTables;
        this.filterDay = filterDay;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_foods, parent, false);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recylcer_foods);
        recyclerView.setHasFixedSize(true);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(foodTables.get(position).getName());
        holder.itemOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.itemOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_foods_elements);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        db = new DataBaseContract(mContext);
                        db.open();
                        ArrayList<FoodTable> foodTable = db.getAllFoodsFilterByType(filterDay);
                        db.close();
                        switch (item.getItemId()) {
                            case R.id.menu_foods_elements_edit:
                                Toast.makeText(mContext,
                                        "Edit pulsado", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_foods_elements_delete:
                                db.open();
                                db.deleteFood(foodTable.get(holder.getAdapterPosition()));
                                db.close();
                                Toast.makeText(mContext,
                                        "Delete pulsado", Toast.LENGTH_LONG).show();

                                foodTables.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), foodTables.size());
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodTables.size();
    }
}

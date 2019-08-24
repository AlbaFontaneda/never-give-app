package com.rigobertosl.nevergiveapp.food.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.database.DataBaseContract;
import com.rigobertosl.nevergiveapp.food.activity.FoodResumeActivity;
import com.rigobertosl.nevergiveapp.food.activity.FoodsActivity;
import com.rigobertosl.nevergiveapp.food.activity.FoodsApi;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.FoodTable;

import java.util.ArrayList;


public class CustomFoodAdapter extends RecyclerView.Adapter<CustomFoodAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<FoodTable> foodTables;
    private String filterDay;
    private boolean isType;
    private DataBaseContract db;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView days;
        public TextView kcal;
        public ImageView image;
        public ImageButton itemOptions;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            days = (TextView) view.findViewById(R.id.item_days);
            kcal = (TextView) view.findViewById(R.id.num_kcal);
            image = (ImageView) view.findViewById(R.id.item_image);
            itemOptions = (ImageButton) view.findViewById(R.id.item_options);
        }
    }

    public CustomFoodAdapter(Context mContext, ArrayList<FoodTable> foodTables, String filterDay, boolean isType) {
        this.mContext = mContext;
        this.foodTables = foodTables;
        this.filterDay = filterDay;
        this.isType = isType;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_foods, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(foodTables.get(position).getName());
        holder.days.setText(foodTables.get(position).getDays());
        if(foodTables.get(position).getKcal() == null) {
            holder.kcal.setText("Parece que no guardaste el plato correctamente.\nPuedes editarlo para añadir las Kcal.");
            holder.kcal.setTextColor(R.color.bpblack);
            holder.kcal.setTextSize(10);
            holder.kcal.setAllCaps(false);
            holder.kcal.setTypeface(holder.kcal.getTypeface(), Typeface.ITALIC);
        } else {
            holder.kcal.setText(foodTables.get(position).getKcal());
        }
        // ToDo: revisar porqué la imagen es null cuando se crea una comida.
        byte[] b = foodTables.get(position).getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        final Drawable d = new BitmapDrawable(mContext.getResources(), bmp);
        holder.image.setImageDrawable(d);

        holder.itemOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.itemOptions);
                //inflating menu from xml resource
                if(mContext.getClass() == FoodsActivity.class) {
                    popup.inflate(R.menu.menu_foods_elements);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            db = new DataBaseContract(mContext);
                            db.open();
                            ArrayList<FoodTable> foodTable;
                            if(isType) {
                                foodTable = db.getAllFoodsFilterByType(filterDay);
                            } else {
                                foodTable = db.getAllFoodsFilterByDay(filterDay);
                            }

                            db.close();
                            switch (item.getItemId()) {
                                case R.id.menu_foods_elements_edit:
                                    if(FoodResumeActivity.listKcal.size() == 0) {
                                        new FoodsApi().execute();
                                    }
                                    Intent intent = new Intent(mContext, FoodResumeActivity.class);
                                    intent.putExtra("fromFoods", true);
                                    intent.putExtra("foodId", foodTable.get(holder.getAdapterPosition()).getId());
                                    intent.putExtra("fromResume", true);
                                    mContext.startActivity(intent);
                                    break;
                                case R.id.menu_foods_elements_delete:
                                    db.open();
                                    db.deleteFood(foodTable.get(holder.getAdapterPosition()));
                                    db.close();
                                    Toast.makeText(mContext,
                                            "Comida eliminada", Toast.LENGTH_LONG).show();

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
                } else {
                    popup.inflate(R.menu.menu_foods_elements_main);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            db = new DataBaseContract(mContext);
                            db.open();
                            ArrayList<FoodTable> foodTable;
                            if(isType) {
                                foodTable = db.getAllFoodsFilterByType(filterDay);
                            } else {
                                foodTable = db.getAllFoodsFilterByDay(filterDay);
                            }

                            db.close();
                            switch (item.getItemId()) {
                                case R.id.menu_foods_elements_edit:
                                    if(FoodResumeActivity.listKcal.size() == 0) {
                                        new FoodsApi().execute();
                                    }
                                    Intent intent = new Intent(mContext, FoodResumeActivity.class);
                                    intent.putExtra("fromFoods", false);
                                    intent.putExtra("foodId", foodTable.get(holder.getAdapterPosition()).getId());
                                    intent.putExtra("fromResume", true);
                                    mContext.startActivity(intent);
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return foodTables.size();
    }
}

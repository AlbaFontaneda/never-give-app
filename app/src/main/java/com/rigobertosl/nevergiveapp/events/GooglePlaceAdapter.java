package com.rigobertosl.nevergiveapp.events;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.GooglePlace;

import java.util.ArrayList;

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.MyViewHolder> {

    private ArrayList<GooglePlace> googlePlacesList;
    private static ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, ratingNumber, userRatingTotal, distance, openText;
        private RatingBar ratingBar;
        //private Button selected;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            ratingNumber = (TextView)itemView.findViewById(R.id.rating_number);
            ratingBar = (RatingBar)itemView.findViewById(R.id.rating_stars);
            userRatingTotal = (TextView)itemView.findViewById(R.id.user_rating_total);
            distance = (TextView)itemView.findViewById(R.id.distance);
            openText = (TextView)itemView.findViewById(R.id.openText);
            //selected = (Button) itemView.findViewById(R.id.select_location);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public GooglePlaceAdapter(ArrayList<GooglePlace> googlePlacesList) {
        this.googlePlacesList = googlePlacesList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GooglePlace myPlace = this.googlePlacesList.get(position);

        holder.title.setText(myPlace.getName());
        if(myPlace.getRating() != -1.0){
            holder.ratingNumber.setText(String.valueOf(myPlace.getRating()));
            holder.ratingBar.setRating((float) myPlace.getRating());
            holder.userRatingTotal.setText("("+String.valueOf(myPlace.getUserRatings())+")");
        }
        if(myPlace.isOpened()){
            holder.openText.setText("Abierto");
        }else{
            holder.openText.setText("Cerrado");
            holder.openText.setTextColor(Color.RED);
        }
    }


    @Override
    public int getItemCount() {
        return googlePlacesList.size();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        GooglePlaceAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }


}

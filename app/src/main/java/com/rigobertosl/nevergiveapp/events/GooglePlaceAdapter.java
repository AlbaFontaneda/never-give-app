package com.rigobertosl.nevergiveapp.events;

import android.content.Context;
import android.content.res.Resources;
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

    private Resources resources;
    private ArrayList<GooglePlace> googlePlacesList;
    private ArrayList<String> distanceList;
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

    public GooglePlaceAdapter(ArrayList<GooglePlace> googlePlacesList, ArrayList<String> distanceList) {
        this.googlePlacesList = googlePlacesList;
        this.distanceList = distanceList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_card, parent, false);
        resources = parent.getContext().getResources();
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
        }else{
            holder.ratingNumber.setText("0");
            holder.ratingBar.setRating(0);
            holder.userRatingTotal.setText("(0)");
        }
        if(myPlace.isOpened()){
            holder.openText.setText("Abierto");
            holder.openText.setTextColor(resources.getColor(R.color.colorOpen));
        }else{
            holder.openText.setText("Cerrado");
            holder.openText.setTextColor(resources.getColor(R.color.colorClose));
        }
        if(distanceList != null){
            holder.distance.setText(distanceList.get(position)+" km");
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

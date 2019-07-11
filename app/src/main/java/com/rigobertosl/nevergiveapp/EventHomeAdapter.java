package com.rigobertosl.nevergiveapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventHomeAdapter extends RecyclerView.Adapter<EventHomeAdapter.MyViewHolder> {

    private ArrayList<Event> eventList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, hour;
        private ImageView image;

        public MyViewHolder (View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            hour = (TextView) view.findViewById(R.id.hour);
            //image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public EventHomeAdapter(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(eventList.get(position).getSport());
        holder.hour.setText(String.valueOf(eventList.get(position).getHour()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

package com.rigobertosl.nevergiveapp.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.Event;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {

    /*********************************** Variables of Adapter *************************************/
    private static final int UNSELECTED = -1;

    private Context context;
    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    private ArrayList<Event> eventList;


    /************************************ Class MyViewHolder **************************************/
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

        private TextView titleText, dateText, timeText, locationText, hostText, notesText;
        private Switch switchSigned;
        private ExpandableLayout expandableLayout;
        private ListView memerList;
        private ConstraintLayout detailsLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.item_title);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
            timeText = (TextView) itemView.findViewById(R.id.time_text);
            locationText = (TextView) itemView.findViewById(R.id.location_text);
            hostText = (TextView) itemView.findViewById(R.id.host_text);
            notesText = (TextView) itemView.findViewById(R.id.notes_text);
            memerList = (ListView) itemView.findViewById(R.id.members_list);
            switchSigned = (Switch) itemView.findViewById(R.id.signed);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
            detailsLayout = (ConstraintLayout) itemView.findViewById(R.id.layout_details);

            expandableLayout.setInterpolator(new OvershootInterpolator());
            expandableLayout.setOnExpansionUpdateListener(this);

            detailsLayout.setOnClickListener(this);
        }

        public void bind() {
            int position = getAdapterPosition();
            boolean isSelected = position == selectedItem;

            detailsLayout.setSelected(isSelected);
            expandableLayout.setExpanded(isSelected, false);
        }

        @Override
        public void onClick(View view) {
            MyViewHolder holder = (MyViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.detailsLayout.setSelected(false);
                holder.expandableLayout.collapse();
            }

            int position = getAdapterPosition();
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                detailsLayout.setSelected(true);
                expandableLayout.expand();
                selectedItem = position;
            }
        }

        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            if (state == ExpandableLayout.State.EXPANDING) {
                memerList.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }

    /********************************** Constructor of Adapter ************************************/
    public EventListAdapter(RecyclerView recyclerView, ArrayList<Event> eventList) {
        this.recyclerView = recyclerView;
        this.eventList = eventList;
    }

    /************************************ Methods of Adapter **************************************/
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.event_card_rectangle, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        holder.titleText.setText(currentEvent.getSport());
        holder.dateText.setText(currentEvent.getDate().getDayMonthYear());
        holder.timeText.setText(currentEvent.getDate().getTime());
        holder.locationText.setText(currentEvent.getPlace().getName());
        holder.hostText.setText(currentEvent.getHost().getName());
        holder.notesText.setText(currentEvent.getNotes());

        holder.bind();
        //holder.memerList.setAdapter(new ArrayAdapter<String>(context, eventList.get(position).getAssistants()));
    }

    @Override
    public int getItemCount() {
        if(eventList != null){
            return eventList.size();
        }else{
            return 0;
        }
    }
}

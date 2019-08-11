package com.rigobertosl.nevergiveapp.events;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.Event;

import org.w3c.dom.Text;

import java.util.List;

public class EventList extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> eventList;
    private TextView title, hour, people;

    public EventList(Activity context, List<Event> eventList) {
        super(context, R.layout.event_card, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.event_card, null , true);

        title = (TextView) listViewItem.findViewById(R.id.title);
        hour = (TextView) listViewItem.findViewById(R.id.hour);
        people = (TextView) listViewItem.findViewById(R.id.people);

        Event event = eventList.get(position);
        title.setText(event.getSport());
        hour.setText(event.getHour().concat(":").concat(String.valueOf(eventList.get(position).getMinutes())));
        people.setText(event.getPeople());

        return listViewItem;
    }
}

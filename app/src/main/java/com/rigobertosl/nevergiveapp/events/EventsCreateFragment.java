package com.rigobertosl.nevergiveapp.events;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Date;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.GooglePlace;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;
import java.util.Locale;

public class EventsCreateFragment extends FragmentFiredatabase implements DatePickerDialog.OnDateSetListener {

    private Event evento = new Event();
    private Date eventDate = new Date();

    private LinearLayout calendarLayout, time_layout, peopleLayout, location_Layout, notes_Layout;
    private EditText titleEditText, notesEditText, peopleText;
    private TextView dateText, timeText, locationText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_event, container, false);

        calendarLayout = (LinearLayout)view.findViewById(R.id.calendar_layout);
        time_layout = (LinearLayout)view.findViewById(R.id.time_layout);
        peopleLayout = (LinearLayout)view.findViewById(R.id.people_layout);
        location_Layout = (LinearLayout)view.findViewById(R.id.location_layout);
        notes_Layout = (LinearLayout)view.findViewById(R.id.notes_layout);

        titleEditText = (EditText)view.findViewById(R.id.title_event);
        peopleText = (EditText) view.findViewById(R.id.people_text);
        notesEditText = (EditText)view.findViewById(R.id.notes_text);

        dateText = (TextView)view.findViewById(R.id.date_text);
        timeText = (TextView)view.findViewById(R.id.time_text);
        locationText = (TextView)view.findViewById(R.id.location_text);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        calendarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarPicker();
            }
        });

        time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v, timeText);
            }
        });

        peopleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleText.requestFocus();
            }
        });

        //ToDo: cambiar esto
        location_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationText.requestFocus();
            }
        });

        notes_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesEditText.requestFocus();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!titleEditText.getText().toString().equals("") && !peopleText.getText().toString().equals("")
                        && !dateText.getText().toString().equals("") && !timeText.getText().toString().equals("")){

                    evento.setSport(titleEditText.getText().toString());
                    evento.setAssistants(Integer.parseInt(peopleText.getText().toString()));
                    // ToDo: localización a mano.
                    evento.setPlace(new GooglePlace(null, 40.316817,  -3.706154));
                    evento.setDate(eventDate);
                    addDataToFirebase(eventsKey, evento);
                    Toast.makeText(getContext(), "Evento creado", Toast.LENGTH_LONG);

                    // Cambiamos a la pantalla principal
                    ((EventsMain)getActivity()).changeFragment(1);
                }else{
                    Toast.makeText(getContext(), "Por favor, rellene todos los campos.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    public void openCalendarPicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String inputDate = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year);
        dateText.setText(inputDate);
        eventDate.setDayOfMonth(day);
        eventDate.setMonth(month);
        eventDate.setYear(year);
    }

    public interface OnFragmentInteractionListener {
    }

    public void openDatePicker(View view, final TextView editTextTime){
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogLayout = LayoutInflater.from(view.getContext())
                .inflate(R.layout.popup_hours_minutes, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final int[] selectedMinute = {0};
        final int[] selectedHour = {0};

        NumberPicker minutosPikcer = (NumberPicker) dialogLayout.findViewById(R.id.minutos_picker);
        NumberPicker horasPicker = (NumberPicker) dialogLayout.findViewById(R.id.horas_picker);

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);

        minutosPikcer.setValue(0);
        minutosPikcer.setMinValue(0);
        minutosPikcer.setMaxValue(59);
        minutosPikcer.setWrapSelectorWheel(true);

        minutosPikcer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                selectedMinute[0] = newVal;
            }
        });

        horasPicker.setValue(0);
        horasPicker.setMinValue(0);
        horasPicker.setMaxValue(23);
        horasPicker.setWrapSelectorWheel(true);

        horasPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                selectedHour[0] = newVal;
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", selectedHour[0], selectedMinute[0]);
                editTextTime.setText(timeLeftFormatted);
                eventDate.setHour(selectedHour[0]);
                eventDate.setMinutes(selectedMinute[0]);
                dialog.cancel();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
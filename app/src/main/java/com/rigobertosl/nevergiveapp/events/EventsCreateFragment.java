package com.rigobertosl.nevergiveapp.events;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.objects.Date;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.FragmentFiredatabase;
import com.rigobertosl.nevergiveapp.objects.GooglePlace;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;
import java.util.Locale;

public class EventsCreateFragment extends FragmentFiredatabase implements DatePickerDialog.OnDateSetListener {

    private EditText mySport, myAssistants, myPlace, myDay, myTime;
    private Event evento = new Event();
    private Date eventDate = new Date();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_events_create, container, false);

        mySport = (EditText)view.findViewById(R.id.sport);
        myAssistants = (EditText)view.findViewById(R.id.num_series);
        myPlace = (EditText)view.findViewById(R.id.location);
        myDay = (EditText)view.findViewById(R.id.day);
        myTime = (EditText)view.findViewById(R.id.hour);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mySport.getText().toString().equals("") && !myAssistants.getText().toString().equals("")
                        && !myDay.getText().toString().equals("") && !myTime.getText().toString().equals("")){

                    evento.setSport(mySport.getText().toString());
                    evento.setAssistants(Integer.parseInt(myAssistants.getText().toString()));
                    // ToDo: localización a mano.
                    evento.setPlace(new GooglePlace(null, 40.316817,  -3.706154));
                    evento.setDate(eventDate);
                    //Snackbar.make(view, evento.creacionDeEvento(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    addDataToFirebase(eventsKey, evento);
                    //Snackbar.make(view, "Evento creado.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    Toast.makeText(getContext(), "Por favor, rellene todos los campos.", Toast.LENGTH_LONG).show();
                }

            }
        });

        myTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v, myTime);
            }
        });

        myDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarPicker();
            }
        });

        return view;
    }

    public void openCalendarPicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        myDay.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", day, month, year));
        eventDate.setDay(day);
        eventDate.setMonth(month);
        eventDate.setYear(year);
    }

    public interface OnFragmentInteractionListener {
    }

    public void openDatePicker(View view, final EditText editTextTime){
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
                String timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d", selectedHour[0], selectedMinute[0]);
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
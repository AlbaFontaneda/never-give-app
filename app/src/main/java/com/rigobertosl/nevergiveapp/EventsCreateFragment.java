package com.rigobertosl.nevergiveapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;
import java.util.Locale;

public class EventsCreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private EditText mySport, myPeople, myLocation, myDay, myTime;
    private Event evento = new Event();

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
        myPeople = (EditText)view.findViewById(R.id.num_series);
        myLocation = (EditText)view.findViewById(R.id.location);
        myDay = (EditText)view.findViewById(R.id.day);
        myTime = (EditText)view.findViewById(R.id.hour);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.setSport(mySport.getText().toString());
                evento.setPeople(myPeople.getText().toString());
                Snackbar.make(view, evento.creacionDeEvento(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Snackbar.make(view, "Evento creado.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

        evento.setDay(String.valueOf(day));
        evento.setMonth(String.valueOf(month));
        evento.setYear(String.valueOf(year));
    }

    public interface OnFragmentInteractionListener {
    }

    public void openDatePicker(View view, final EditText EditTextTime){
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
                EditTextTime.setText(timeLeftFormatted);
                evento.setHour(String.valueOf(selectedHour[0]));
                if(selectedMinute[0] < 10){
                    evento.setMinutes("0" + String.valueOf(selectedMinute[0]));
                } else{
                    evento.setMinutes(String.valueOf(selectedMinute[0]));
                }
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
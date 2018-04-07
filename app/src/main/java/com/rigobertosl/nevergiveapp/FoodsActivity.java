package com.rigobertosl.nevergiveapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;

import java.util.ArrayList;

public class FoodsActivity extends MainActivity {

    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;
    public FloatingActionButton fab;
    private DataBaseContract db;
    public static FoodTable foodTable;
    public String weekDays = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        db = new DataBaseContract(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<7; i++){
            Fragment f = new FoodsFragment();
            fragments.add(f);
        }

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foods, menu);
        return true;
    }

    //Función para dar funcionalidades a cada item del menu del app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_foods_visual | id == R.id.menu_foods_settings | id == R.id.menu_foods_edit) {
            Toast.makeText(getApplicationContext(),
                    item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_new_food, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();


        final Button continuar = (Button) dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button) dialogLayout.findViewById(R.id.button_cancel);

        final EditText foodNameEditText = (EditText)dialogLayout.findViewById(R.id.name_food);
        final EditText foodDaysEditText = (EditText)dialogLayout.findViewById(R.id.food_days);
        final Spinner dropdown = dialogLayout.findViewById(R.id.food_spinner);

        String[] items = new String[]{"Selecciona una comida...", "Desayuno", "Almuerzo", "Comida", "Merienda", "Cena"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FoodsActivity.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);



        foodDaysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putBoolean(RecurrencePickerDialogFragment.BUNDLE_HIDE_SWITCH_BUTTON, true);
                RecurrencePickerDialogFragment rpd = new RecurrencePickerDialogFragment();
                rpd.setArguments(bundle);
                rpd.setOnRecurrenceSetListener(new RecurrencePickerDialogFragment.OnRecurrenceSetListener(){
                    @Override
                    public void onRecurrenceSet(String rrule) {
                        if (rrule != null && rrule.length() > 0) {
                            weekDays = rrule.substring(rrule.lastIndexOf("=") + 1);
                            char[] myDaysChars = weekDays.toCharArray();
                            ArrayList<String> myDays = new ArrayList<>();
                            for(int i = 0; i < myDaysChars.length; i++) {
                                if(myDaysChars[i] == 'S' && myDaysChars[i+1] == 'U'){
                                    myDays.add("DO");
                                }
                                else if(myDaysChars[i] == 'M'){
                                    myDays.add("LU");
                                }
                                else if(myDaysChars[i] == 'T' && myDaysChars[i+1] == 'U'){
                                    myDays.add("M");
                                }
                                else if(myDaysChars[i] == 'W'){
                                    myDays.add("X");
                                }
                                else if(myDaysChars[i] == 'T' && myDaysChars[i+1] == 'H'){
                                    myDays.add("JU");
                                }
                                else if(myDaysChars[i] == 'F'){
                                    myDays.add("VI");
                                }
                                else if(myDaysChars[i] == 'S' && myDaysChars[i+1] == 'A'){
                                    myDays.add("SA");
                                }
                            }
                            if(myDays.get(0).equals("DO")) {
                                myDays.remove(0);
                                myDays.add("DO");
                            }

                            weekDays = myDays.toString();
                            foodDaysEditText.setText(weekDays.substring(1, weekDays.length()-1));
                        }
                    }
                });
                rpd.show(fm, "recurrencePickerDialogFragment");
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = foodNameEditText.getText().toString();
                String foodDays = "";
                String foodType = dropdown.getSelectedItem().toString();
                if(!weekDays.equals("")){
                    foodDays = weekDays.substring(1, weekDays.length()-1);
                }
                if (foodName.matches("") || foodDays.matches("") || foodType.equals("Selecciona una comida...")) {
                    Toast.makeText(FoodsActivity.this,
                            "Necesitas rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    foodTable = db.createTableFoods(foodName, foodDays, foodType);
                    db.close();
                    dialog.cancel();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    /**
     * Transiciones entre tabs y fragmentos
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            FoodsFragment fragment = (FoodsFragment) fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            return 5;
        }
    }

    /*********** FUNCIONES DE LA PANTALLA DE COMIDAS ******************/


}

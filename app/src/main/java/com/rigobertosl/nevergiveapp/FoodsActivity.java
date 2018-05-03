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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;

import java.util.ArrayList;

public class FoodsActivity extends AppCompatActivity {

    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;
    public FloatingActionButton fab;
    private DataBaseContract db;
    public static FoodTable foodTable;
    public String weekDays = "";
    public static long foodTableId;

    EditText foodDaysEditText;

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
        getMenuInflater().inflate(R.menu.activity_menu_food, menu);
        return true;
    }

    //Función para dar funcionalidades a cada item del menu del app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();
            TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
            textoAviso.setText(R.string.avisoFood);
            final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
            final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

            quedarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.open();
                    db.resetFoods();
                    db.close();
                    finish();
                    startActivity(getIntent());
                    dialog.cancel();
                }
            });
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
        foodDaysEditText = (EditText)dialogLayout.findViewById(R.id.food_days);
        final Spinner dropdown = dialogLayout.findViewById(R.id.food_spinner);

        String[] items = new String[]{"Selecciona una comida...", "Desayuno", "Almuerzo", "Comida", "Merienda", "Cena"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FoodsActivity.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        foodDaysEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDays();
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
                    foodTable = db.createTableFoods(foodName, foodDays, foodType, null, null);
                    finish();
                    //startActivity(new Intent(FoodsActivity.this, FoodResumeActivity.class));
                    long id = foodTable.getId();
                    foodTableId = id;
                    Intent intent = new Intent(FoodsActivity.this, FoodResumeActivity.class);
                    intent.putExtra("foodId", foodTableId);
                    intent.putExtra("fromResume", false);
                    startActivity(intent);
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

    public void selectDays() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FoodsActivity.this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_custom_weekpicker, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final ToggleButton lunes = (ToggleButton)dialogLayout.findViewById(R.id.button_lunes);
        final ToggleButton martes = (ToggleButton)dialogLayout.findViewById(R.id.button_martes);
        final ToggleButton miercoles = (ToggleButton)dialogLayout.findViewById(R.id.button_miercoles);
        final ToggleButton jueves = (ToggleButton)dialogLayout.findViewById(R.id.button_jueves);
        final ToggleButton viernes = (ToggleButton)dialogLayout.findViewById(R.id.button_viernes);
        final ToggleButton sabado = (ToggleButton)dialogLayout.findViewById(R.id.button_sabado);
        final ToggleButton domingo = (ToggleButton)dialogLayout.findViewById(R.id.button_domingo);

        final CheckBox checkAll = (CheckBox) dialogLayout.findViewById(R.id.check_all);

        final ArrayList<String> myDays = new ArrayList<>();

        final boolean[] lunesChecked = new boolean[1];
        final boolean[] martesChecked = new boolean[1];
        final boolean[] miercolesChecked = new boolean[1];
        final boolean[] juevesChecked = new boolean[1];
        final boolean[] viernesChecked = new boolean[1];
        final boolean[] sabadoChecked = new boolean[1];
        final boolean[] domingoChecked = new boolean[1];

        final boolean[] checks = new boolean[7];

        lunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lunesChecked[0] = true;
                } else {
                    lunesChecked[0] = false;
                }
                checks[0] = lunesChecked[0];
            }
        });

        martes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    martesChecked[0] = true;
                } else {
                    martesChecked[0] = false;
                }
                checks[1] = martesChecked[0];
            }
        });

        miercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    miercolesChecked[0] = true;
                } else {
                    miercolesChecked[0] = false;
                }
                checks[2] = miercolesChecked[0];
            }
        });

        jueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    juevesChecked[0] = true;
                } else {
                    juevesChecked[0] = false;
                }
                checks[3] = juevesChecked[0];
            }
        });

        viernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viernesChecked[0] = true;
                } else {
                    viernesChecked[0] = false;
                }
                checks[4] = viernesChecked[0];
            }
        });

        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sabadoChecked[0] = true;
                } else {
                    sabadoChecked[0] = false;
                }
                checks[5] = sabadoChecked[0];
            }
        });

        domingo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    domingoChecked[0] = true;
                } else {
                    domingoChecked[0] = false;
                }
                checks[6] = domingoChecked[0];
            }
        });

        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lunes.setChecked(true);
                    martes.setChecked(true);
                    miercoles.setChecked(true);
                    jueves.setChecked(true);
                    viernes.setChecked(true);
                    sabado.setChecked(true);
                    domingo.setChecked(true);
                } else {
                    lunes.setChecked(false);
                    martes.setChecked(false);
                    miercoles.setChecked(false);
                    jueves.setChecked(false);
                    viernes.setChecked(false);
                    sabado.setChecked(false);
                    domingo.setChecked(false);
                }
            }
        });

        final Button continuar = (Button)dialogLayout.findViewById(R.id.button_continue);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checks[0] == true){
                    myDays.add("LU");
                }
                if (checks[1] == true) {
                    myDays.add("M");
                }
                if (checks[2] == true) {
                    myDays.add("X");
                }
                if (checks[3] == true) {
                    myDays.add("JU");
                }
                if (checks[4] == true) {
                    myDays.add("VI");
                }
                if (checks[5] == true) {
                    myDays.add("SA");
                }
                if (checks[6] == true) {
                    myDays.add("DO");
                }

                weekDays = myDays.toString();
                foodDaysEditText.setText(weekDays.substring(1, weekDays.length()-1));
                dialog.cancel();

            }
        });

        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);
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


    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     **/
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(FoodsActivity.this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }

    /*********** FUNCIONES DE LA PANTALLA DE COMIDAS ******************/


}

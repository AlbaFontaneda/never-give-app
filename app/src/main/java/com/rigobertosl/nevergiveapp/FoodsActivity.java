package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.Toast;


public class FoodsActivity extends MainActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        //Finds ID
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Layout donde aparece el nombre de cada activity y las acciones
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs); //Layout donde ponemos los tabs
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); //Boton flotante de la actividad

        setSupportActionBar(toolbar);

        //Función para volver a la pantalla anterior
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FoodsActivity.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });

        //Tabs de las comidas
        tabs.addTab(tabs.newTab().setText(R.string.lunes));
        tabs.addTab(tabs.newTab().setText(R.string.martes));
        tabs.addTab(tabs.newTab().setText(R.string.miercoles));
        tabs.addTab(tabs.newTab().setText(R.string.jueves));
        tabs.addTab(tabs.newTab().setText(R.string.viernes));
        tabs.addTab(tabs.newTab().setText(R.string.sabado));
        tabs.addTab(tabs.newTab().setText(R.string.domingo));

        //Función para dar funcionalidad al boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Menues de la pantalla de inicio para cada elemento
        final ImageButton desayunoOptions = (ImageButton) findViewById(R.id.desayuno_options);
        registerForContextMenu(desayunoOptions);
        desayunoOptions.setOnClickListener(this);

        final ImageButton tentempieOptions = (ImageButton) findViewById(R.id.tentempie_options);
        registerForContextMenu(tentempieOptions);
        tentempieOptions.setOnClickListener(this);

        final ImageButton comidaOptions = (ImageButton) findViewById(R.id.comida_options);
        registerForContextMenu(comidaOptions);
        comidaOptions.setOnClickListener(this);

        final ImageButton meriendaOptions = (ImageButton) findViewById(R.id.merienda_options);
        registerForContextMenu(meriendaOptions);
        meriendaOptions.setOnClickListener(this);

        final ImageButton cenaOptions = (ImageButton) findViewById(R.id.cena_options);
        registerForContextMenu(cenaOptions);
        cenaOptions.setOnClickListener(this);

    }
    /*********** FUNCIONES DE LA PANTALLA DE COMIDAS ******************/
    //Función para abrir el menu de opciones del app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foods, menu);
        return true;
    }
    //Función para dar funcionalidades a cada item del menu del app bar
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.foods_visual) {
            Toast.makeText(FoodsActivity.this,
                    "Settings pulsado", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(FoodsActivity.this,
                "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.desayuno_options: {
                showMenu(v);
                break;
            }
            case R.id.tentempie_options: {
                showMenu(v);
                break;
            }
            case R.id.comida_options: {
                showMenu(v);
                break;
            }
            case R.id.merienda_options: {
                showMenu(v);
                break;
            }
            case R.id.cena_options: {
                showMenu(v);
                break;
            }
            default: {
                break;
            }
        }
    }
}

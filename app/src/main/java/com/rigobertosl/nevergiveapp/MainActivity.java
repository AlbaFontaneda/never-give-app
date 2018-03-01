package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.PopupMenu;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Finds ID
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Layout para contener en el inicio el appbar y el menu desplegable
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //Layout del menu lateral desplegable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_achievements); //Layout donde aparece el nombre de cada activity y las acciones
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs); //Layout donde ponemos los tabs

        setSupportActionBar(toolbar);

        //Funciones para el menu lateral desplegable
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //Menues de la pantalla de inicio para cada elemento
        final ImageButton foodsOptions = (ImageButton) findViewById(R.id.foods_options);
        registerForContextMenu(foodsOptions);
        foodsOptions.setOnClickListener(this);

        final ImageButton trainOptions = (ImageButton) findViewById(R.id.train_options);
        registerForContextMenu(trainOptions);
        trainOptions.setOnClickListener(this);

        final ImageButton eventsOptions = (ImageButton) findViewById(R.id.events_options);
        registerForContextMenu(eventsOptions);
        eventsOptions.setOnClickListener(this);

        final ImageButton achievementsOptions = (ImageButton) findViewById(R.id.achievements_options);
        registerForContextMenu(achievementsOptions);
        achievementsOptions.setOnClickListener(this);

    }
    /*********** FUNCIONES DE LA PANTALLA DE INICIO ******************/
    //Función para mostrar los menus desplegables
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(),
                        item.getTitle(), Toast.LENGTH_SHORT).show();
                //CON ESTO METEMOS UNA FUNCION A CADA COSO DEL MENU DEPENDIENDO DE LA ID
                /*if(item.getItemId()==R.id.open_foods) {
                    Intent intent = new Intent(MainActivity.this, FoodsActivity.class);
                    startActivity(intent);
                }*/
                return true;
            }
        });// to implement on click event on items of menu
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_init_elements, popup.getMenu());
        popup.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //Función para abrir el menu de opciones del app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Función para dar funcionalidades a cada item del menu del app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_init_settings) {
            // Se debería poner de esta manera:
            /*Toast.makeText(getApplicationContext(),
                    item.getTitle(), Toast.LENGTH_SHORT).show();
            */
            Toast.makeText(MainActivity.this,
                    "Settings pulsado", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Función para dar funcionalidad a cada elemento del menu desplegable de la pantalla de inicio
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
        } else if (id == R.id.nav_entrenamiento) {
            Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_calendario) {
            Intent intent = new Intent(MainActivity.this, FoodsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_eventos) {
            //HACER ACTIVIDAD EVENTOS
        } else if (id == R.id.nav_logros) {
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_compartir) {

        } else if (id == R.id.nav_report) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this,
                "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.foods_options: {
                showMenu(v);
                break;
            }
            case R.id.train_options: {
                showMenu(v);
                break;
            }
            case R.id.events_options: {
                showMenu(v);
                break;
            }
            case R.id.achievements_options: {
                showMenu(v);
                break;
            }
            default: {
                break;
            }
        }
    }
}

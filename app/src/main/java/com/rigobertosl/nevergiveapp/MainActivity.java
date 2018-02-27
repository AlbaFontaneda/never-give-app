package com.rigobertosl.nevergiveapp;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        //Menues de la pantalla de inicio para cada elemento
        final ImageButton foodsOptions = (ImageButton) findViewById(R.id.foods_options);
        registerForContextMenu(foodsOptions);
        foodsOptions.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                if(v==foodsOptions) {
                    showMenu(v);
                }
            }
        });

        //Tabs de la ventana de inicio
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("LUNES"));
        tabs.addTab(tabs.newTab().setText("MARTES"));
        tabs.addTab(tabs.newTab().setText("MIERCOLES"));
        tabs.addTab(tabs.newTab().setText("JUEVES"));
        tabs.addTab(tabs.newTab().setText("VIERNES"));
        tabs.addTab(tabs.newTab().setText("SABADO"));
        tabs.addTab(tabs.newTab().setText("DOMINGO"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //MenuItem openFoods = (MenuItem) findViewById(R.id.open_foods);
                Toast.makeText(getApplicationContext(),
                        item.getTitle(), Toast.LENGTH_SHORT).show();
                if(item.getItemId()==R.id.open_foods) {
                    Intent intent = new Intent(MainActivity.this, FoodsActivity.class);
                    startActivity(intent);
                }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this,
                    "Settings pulsado", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_entrenamiento) {
            // Handle the camera action
        } else if (id == R.id.nav_calendario) {

        } else if (id == R.id.nav_eventos) {

        } else if (id == R.id.nav_logros) {

        } else if (id == R.id.nav_compartir) {

        } else if (id == R.id.nav_report) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

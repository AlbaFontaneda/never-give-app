package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Finds ID
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Layout para contener en el inicio el appbar y el menu desplegable
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //Layout del menu lateral desplegable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Layout donde aparece el nombre de cada activity y las acciones
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs); //Layout donde ponemos los tabs

        setSupportActionBar(toolbar);

        //Funciones para el menu lateral desplegable
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
    /*********** FUNCIONES DE LA PANTALLA DE INICIO ******************/
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //Función para dar funcionalidades a cada item del menu del app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
            implements View.OnClickListener{
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //Menues de la pantalla de inicio para cada elemento
            final ImageButton foodsOptions = (ImageButton) rootView.findViewById(R.id.foods_options);
            registerForContextMenu(foodsOptions);
            foodsOptions.setOnClickListener(this);

            final ImageButton trainOptions = (ImageButton) rootView.findViewById(R.id.train_options);
            registerForContextMenu(trainOptions);
            trainOptions.setOnClickListener(this);

            final ImageButton eventsOptions = (ImageButton) rootView.findViewById(R.id.events_options);
            registerForContextMenu(eventsOptions);
            eventsOptions.setOnClickListener(this);

            final ImageButton achievementsOptions = (ImageButton) rootView.findViewById(R.id.achievements_options);
            registerForContextMenu(achievementsOptions);
            achievementsOptions.setOnClickListener(this);
            return rootView;
        }
        //Función para mostrar los menus desplegables
        public void showMenu(View v) {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //CON ESTO METEMOS UNA FUNCION A CADA COSO DEL MENU DEPENDIENDO DE LA ID
                if(item.getItemId()==R.id.menu_init_element_settings) {
                    Toast.makeText(getActivity(),item.getTitle(), Toast.LENGTH_SHORT).show();
                }
                    return true;
                }
            });// to implement on click event on items of menu
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_init_elements, popup.getMenu());
            popup.show();
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }
    }
}

//Todo: Condicionar en el Dialog la entrada a la siguiente pantalla y activar el botón Cancel
//Todo: HACER PANTALLA DE FRAGMENTS
//Todo: cambiar el tamaño del tabLayout cuando se gira el movil, puesto que el toolbar cambia de tamaño
//Todo: arreglar el transito entre pantallas. Cerrar fragments
//Todo: Arreglar posicionamiento de tabs al girar pantalla
//Todo: HACER ACTIVIDAD EVENTOS
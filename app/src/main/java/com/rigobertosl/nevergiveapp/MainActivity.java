package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppFiredatabase
        implements NavigationView.OnNavigationItemSelectedListener {

    private DataBaseContract db;
    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;

    private static final String DATABASE_NAME = "dbNeverGiveApp.db";
    private static final String PRELOADED_DATABASE_NAME = "preloaded.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        ////////////////////////////////// FIREDATABASE

        Profile profile1 = new Profile("Javi");
        Profile profile2 = new Profile("01", "javi@javi.com", "Javiivu", "24", "1.85", "86", "imageURL", 666666669, true);
        addDataToFirebase(usersKey, profile1);
        addDataToFirebase(usersKey, profile2);
        addDataToFirebase(usersKey, "user1");

        ///////////////////////////////////////// -FIREDATABASE
        */
        if(FoodResumeActivity.listKcal == null || FoodResumeActivity.listKcal.size() == 0) {
            new FoodsApi().execute();
        }

        setContentView(R.layout.activity_main);
        db = new DataBaseContract(this);
        db.open();
        if(!db.checkifTrainTableisEmpty()) {
            ArrayList<TrainingTable> allTrainTables = db.getAllTables();
            for (TrainingTable trainTable : allTrainTables) {
                ArrayList<Exercise> tableExercises = db.getAllExercisesFromTable(trainTable);
                if (tableExercises.size() == 0) db.deleteTable(trainTable.getId());
            }
        }

        SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        Boolean firstTime = mPreferences.getBoolean("firstTime", true);
        if (firstTime) {
            try {
                String destPath = "/data/data/" + getPackageName() + "/databases/" + DATABASE_NAME;

                System.out.println("Traza: no existe el fichero");
                InputStream in = getAssets().open(PRELOADED_DATABASE_NAME);
                OutputStream out = new FileOutputStream(destPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
                out.close();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        db.close();

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
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<7; i++){
            Fragment f = new MainFragment();
            fragments.add(f);
        }

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);

        // Set up the ViewPager with the sections adapter.
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));
    }
    /*********** FUNCIONES DE LA PANTALLA DE INICIO ******************/


    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            signInAnonymously();
        } else  {
            Toast.makeText(this, "Hola, " + user.getUid(), Toast.LENGTH_LONG).show();
        }


    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("USER", "signInAnonymously:success " + user.getUid());
                            Toast.makeText(MainActivity.this, "CREANDO A " + user.getUid(), Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("USER", "signInAnonymously:failure", task.getException());

                        }


                    }
                });
    }

    */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        } else if (id == R.id.nav_localizacion) {
            Intent intent = new Intent(MainActivity.this, Location.class);
            startActivity(intent);
        } else if (id == R.id.nav_eventos) {
            Intent intent = new Intent(MainActivity.this, EventsMain.class);
            startActivity(intent);
        } else if (id == R.id.nav_logros) {
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_reinicio) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();
            TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
            textoAviso.setText(R.string.avisoMain);
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
                    db.resetDataBase();
                    db.resetAchievements();
                    db.close();
                    finish();
                    startActivity(getIntent());
                    dialog.cancel();
                }
            });
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
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
            MainFragment fragment = (MainFragment) fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            return 7;
        }
    }

}
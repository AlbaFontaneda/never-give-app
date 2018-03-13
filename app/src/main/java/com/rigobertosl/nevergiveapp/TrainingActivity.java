package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TrainingActivity extends MainActivity {
    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Función para volver a la pantalla anterior
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager());
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        TabLayout trainTabs = (TabLayout) findViewById(R.id.tabs);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(trainTabs));
        trainTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(TrainingActivity.this,
                    "Settings pulsado", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return TrainingFragment.newInstance(position);
                }
                case 1: {
                    return TrainingFragmentDefault.newInstance(position);
                }
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}



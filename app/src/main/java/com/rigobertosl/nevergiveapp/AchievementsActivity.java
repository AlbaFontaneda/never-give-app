package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AchievementsActivity extends MainActivity {

    private SectionsPagerAdapter seleccionPagina;
    private ViewPager vistaPagina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Función para volver a la pantalla anterior
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AchievementsActivity.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });

        seleccionPagina = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        vistaPagina = (ViewPager) findViewById(R.id.container);
        vistaPagina.setAdapter(seleccionPagina);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        vistaPagina.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vistaPagina));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_achievements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
                    return AchievementsFragmentPoints.newInstance(position);
                }
                case 1: {
                    return AchievementsFragment.newInstance(position);
                }
                case 2: {
                    return AchievementsFragment.newInstance(position);
                }
                case 3: {
                    return AchievementsFragment.newInstance(position);
                }
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
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
        Intent setIntent = new Intent(AchievementsActivity.this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}
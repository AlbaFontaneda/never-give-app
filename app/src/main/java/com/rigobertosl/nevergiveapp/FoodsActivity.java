package com.rigobertosl.nevergiveapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class FoodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("LUNES"));
        tabs.addTab(tabs.newTab().setText("MARTES"));
        tabs.addTab(tabs.newTab().setText("MIERCOLES"));
        tabs.addTab(tabs.newTab().setText("JUEVES"));
        tabs.addTab(tabs.newTab().setText("VIERNES"));
        tabs.addTab(tabs.newTab().setText("SABADO"));
        tabs.addTab(tabs.newTab().setText("DOMINGO"));
    }
}

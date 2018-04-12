package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FoodResumeActivity extends FoodsActivity {

    private Context mContext;
    private DataBaseContract db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        final FoodTable foodTable = db.getFoodById(FoodsActivity.foodTableId);
        db.close();
        toolbar.setTitle(foodTable.getName());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final AlertDialog.Builder builder = new AlertDialog.Builder(FoodResumeActivity.this);
                final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
                final AlertDialog dialog = builder.create();
                dialog.setView(dialogLayout);
                dialog.show();

                final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
                final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

                volver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db = new DataBaseContract(FoodResumeActivity.this);
                        db.open();
                        db.deleteFood(foodTable);
                        db.close();
                        finish();
                        startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                    }
                });
                quedarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                Toast.makeText(mContext,
                        "Comida guardada con exito", Toast.LENGTH_LONG).show();
            }
        });

        TextView foodType = (TextView) findViewById(R.id.food_type);
        foodType.setText(foodTable.getType());

        TextView foodDays = (TextView) findViewById(R.id.food_days);
        foodDays.setText(foodTable.getDays());
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

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FoodResumeActivity.this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.show();

        final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
        final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DataBaseContract(FoodResumeActivity.this);
                db.open();
                db.deleteFood(foodTable);
                db.close();
                finish();
                startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
            }
        });
        quedarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}

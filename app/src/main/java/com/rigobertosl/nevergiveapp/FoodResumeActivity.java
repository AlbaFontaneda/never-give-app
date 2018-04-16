package com.rigobertosl.nevergiveapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FoodResumeActivity extends FoodsActivity {

    private Context mContext;
    private DataBaseContract db;
    private long foodId;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView imageView;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        foodId = (long) getIntent().getSerializableExtra("foodId");
        final FoodTable foodTable = db.getFoodById(foodId);
        db.close();
        toolbar.setTitle(foodTable.getName());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                Toast.makeText(mContext,
                        foodTable.getType() + " guardado con exito", Toast.LENGTH_LONG).show();
            }
        });

        TextView foodType = (TextView) findViewById(R.id.food_type);
        foodType.setText(foodTable.getType());

        TextView foodDays = (TextView) findViewById(R.id.food_days);
        foodDays.setText(foodTable.getDays());

        imageView = (ImageView) findViewById(R.id.image_view);
        imageButton = (ImageButton) findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        if(foodTable.getType().equals("Desayuno")) {
            Bitmap bitmap = ((BitmapDrawable)getDrawable(R.drawable.desayuno_default)).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            db.open();
            db.addImageFood(foodTable, bitmapdata);
            db.close();
            imageButton.setBackground(getDrawable(R.drawable.desayuno_default));
        } else if(foodTable.getType().equals("Almuerzo")) {
            Bitmap bitmap = ((BitmapDrawable)getDrawable(R.drawable.almuerzo_default)).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            db.open();
            db.addImageFood(foodTable, bitmapdata);
            db.close();
            imageButton.setBackground(getDrawable(R.drawable.almuerzo_default));
        } else if(foodTable.getType().equals("Comida")) {
            Bitmap bitmap = ((BitmapDrawable)getDrawable(R.drawable.comida_default)).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            db.open();
            db.addImageFood(foodTable, bitmapdata);
            db.close();
            imageButton.setBackground(getDrawable(R.drawable.comida_default));
        } else if(foodTable.getType().equals("Merienda")) {
            Bitmap bitmap = ((BitmapDrawable)getDrawable(R.drawable.merienda_default)).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            db.open();
            db.addImageFood(foodTable, bitmapdata);
            db.close();
            imageButton.setBackground(getDrawable(R.drawable.merienda_default));
        } else if(foodTable.getType().equals("Cena")) {
            Bitmap bitmap = ((BitmapDrawable)getDrawable(R.drawable.cena_default)).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            db.open();
            db.addImageFood(foodTable, bitmapdata);
            db.close();
            imageButton.setBackground(getDrawable(R.drawable.cena_default));
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Camara", "Galeria", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(FoodResumeActivity.this);
        builder.setTitle("Añade una imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camara")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                }else if(items[i].equals("Galeria")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Seleciona un archivo"), SELECT_FILE);


                }else if(items[i].equals("Cancelar")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap) bundle.get("data");
                bmp = Bitmap.createScaledBitmap(bmp, 120, 140, true);
                imageView.setImageBitmap(bmp);
                final BitmapDrawable bmpd = (BitmapDrawable) imageView.getDrawable();
                imageButton.setAdjustViewBounds(true);
                imageButton.setBackground(bmpd);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();
                db.open();
                db.addImageFood(foodTable, bitmapdata);
                db.close();

            } else if(requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bmp = Bitmap.createScaledBitmap(bmp, 120, 140, true);
                imageView.setImageBitmap(bmp);
                final BitmapDrawable bmpd = (BitmapDrawable) imageView.getDrawable();
                imageButton.setAdjustViewBounds(true);
                imageButton.setBackground(bmpd);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();
                db.open();
                db.addImageFood(foodTable, bitmapdata);
                db.close();
            }
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

    @Override
    public void onBackPressed() {
        if((boolean) getIntent().getSerializableExtra("fromResume")){
            finish();
            if((boolean) getIntent().getSerializableExtra("fromFoods")){
                startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
            }else{
                startActivity(new Intent(FoodResumeActivity.this, MainActivity.class));
            }
        }else{
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
        //setIntent.addCategory(Intent.CATEGORY_HOME);
        //setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


    }
}

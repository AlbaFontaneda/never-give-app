package com.rigobertosl.nevergiveapp.food.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rigobertosl.nevergiveapp.database.DataBaseContract;
import com.rigobertosl.nevergiveapp.objects.KcalTable;
import com.rigobertosl.nevergiveapp.main.activity.MainActivity;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.FoodTable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class FoodResumeActivity extends AppCompatActivity {

    private Context mContext;
    private DataBaseContract db;
    private long foodId;

    public static TextView kcal;

    public static List<Integer> listKcal;

    boolean isPastaCheck;
    boolean isHuevosCheck;
    boolean isLecheCheck;
    boolean isCarneCheck;
    boolean isPescadoCheck;
    boolean isVerduraCheck;
    boolean isBolleriaCheck;
    boolean isCerealesCheck;
    boolean isLegumbreCheck;
    boolean isEmbutidoCheck;
    boolean isQuesoCheck;
    boolean isYogurtCheck;

    boolean[] checks;

    byte[] bitmapdataCamera;
    byte[] bitmapdataGalery;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView imageView;
    ImageButton imageButton;

    KcalTable kcalTable;
    FoodTable foodTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        foodId = (long) getIntent().getSerializableExtra("foodId");
        foodTable = db.getFoodById(foodId);
        db.close();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               onBackPressed();
            }
        });

        if((boolean) getIntent().getSerializableExtra("fromResume")) {
            kcalTable = db.getKcalTableByFood(foodId);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                if(bitmapdataCamera != null){
                    db.addImageFood(foodTable, bitmapdataCamera);
                } else if(bitmapdataGalery != null) {
                    db.addImageFood(foodTable, bitmapdataGalery);
                }
                db.updateKcal(foodTable, kcal.getText().toString());
                finish();
                if((boolean) getIntent().getSerializableExtra("fromResume")) {
                    db.updateTableKcal(kcalTable, checks);
                    if((boolean) getIntent().getSerializableExtra("fromFoods")){
                        startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                    }else{
                        startActivity(new Intent(FoodResumeActivity.this, MainActivity.class));
                    }
                } else {
                    db.createTableKcal(foodId, checks);
                    startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                }
                db.close();
                Toast.makeText(mContext,
                        foodTable.getType() + " guardado con exito", Toast.LENGTH_LONG).show();
            }
        });

        if(listKcal.size() == 0) {
            LinearLayout apiAviso = findViewById(R.id.apiAviso);
            LinearLayout layoutKcal = findViewById(R.id.listKcal);
            apiAviso.setVisibility(View.VISIBLE);
            layoutKcal.setVisibility(View.GONE);
        }

        checks = new boolean[12];

        CheckBox checkPasta = (CheckBox) findViewById(R.id.pasta_button);
        CheckBox checkHuevos = (CheckBox) findViewById(R.id.huevos_button);
        CheckBox checkLeche = (CheckBox) findViewById(R.id.leche_button);
        CheckBox checkCarne = (CheckBox) findViewById(R.id.carne_button);
        CheckBox checkPescado = (CheckBox) findViewById(R.id.pescado_button);
        CheckBox checkVerdura = (CheckBox) findViewById(R.id.verdura_button);
        CheckBox checkBolleria = (CheckBox) findViewById(R.id.bolleria_button);
        CheckBox checkCereales = (CheckBox) findViewById(R.id.cereales_button);
        CheckBox checkLegumbre = (CheckBox) findViewById(R.id.legumbre_button);
        CheckBox checkEmbutido = (CheckBox) findViewById(R.id.embutido_button);
        CheckBox checkQueso = (CheckBox) findViewById(R.id.queso_button);
        CheckBox checkYogurt = (CheckBox) findViewById(R.id.yogur_button);

        kcal = (TextView) findViewById(R.id.num_kcal);

        if(kcalTable != null) {
            if(kcalTable.isPasta()) {
                checkPasta.setChecked(true);
                checks[0] = true;
            }
            if(kcalTable.isHuevos()) {
                checkHuevos.setChecked(true);
                checks[1] = true;
            }
            if(kcalTable.isLeche()) {
                checkLeche.setChecked(true);
                checks[2] = true;
            }
            if(kcalTable.isCarne()) {
                checkCarne.setChecked(true);
                checks[3] = true;
            }
            if(kcalTable.isPescado()) {
                checkPescado.setChecked(true);
                checks[4] = true;
            }
            if(kcalTable.isVerdura()) {
                checkVerdura.setChecked(true);
                checks[5] = true;
            }
            if(kcalTable.isBolleria()) {
                checkBolleria.setChecked(true);
                checks[6] = true;
            }
            if(kcalTable.isCereales()) {
                checkCereales.setChecked(true);
                checks[7] = true;
            }
            if(kcalTable.isLegumbre()) {
                checkLegumbre.setChecked(true);
                checks[8] = true;
            }
            if(kcalTable.isEmbutido()) {
                checkEmbutido.setChecked(true);
                checks[9] = true;
            }
            if(kcalTable.isQueso()) {
                checkQueso.setChecked(true);
                checks[10] = true;
            }
            if(kcalTable.isYogurt()) {
                checkYogurt.setChecked(true);
                checks[11] = true;
            }

            kcal.setText(foodTable.getKcal().toString());
            //kcal.setText(foodTable.getKcal());
        }

        if(listKcal != null){
            checkPasta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(0);
                        kcal.setText(res.toString());
                        isPastaCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(0);
                        kcal.setText(res.toString());
                        isPastaCheck = false;
                    }
                    checks[0] = isPastaCheck;
                }
            });

            checkHuevos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(1);
                        kcal.setText(res.toString());
                        isHuevosCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(1);
                        kcal.setText(res.toString());
                        isHuevosCheck = false;
                    }
                    checks[1] = isHuevosCheck;
                }
            });

            checkLeche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(2);
                        kcal.setText(res.toString());
                        isLecheCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(2);
                        kcal.setText(res.toString());
                        isLecheCheck = false;
                    }
                    checks[2] = isLecheCheck;
                }
            });

            checkCarne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(3);
                        kcal.setText(res.toString());
                        isCarneCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(3);
                        kcal.setText(res.toString());
                        isCarneCheck = false;
                    }
                    checks[3] = isCarneCheck;
                }
            });

            checkPescado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(4);
                        kcal.setText(res.toString());
                        isPescadoCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(4);
                        kcal.setText(res.toString());
                        isPescadoCheck = false;
                    }
                    checks[4] = isPescadoCheck;
                }
            });

            checkVerdura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(5);
                        kcal.setText(res.toString());
                        isVerduraCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(5);
                        kcal.setText(res.toString());
                        isVerduraCheck = false;
                    }
                    checks[5] = isVerduraCheck;
                }
            });

            checkBolleria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(6);
                        kcal.setText(res.toString());
                        isBolleriaCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(6);
                        kcal.setText(res.toString());
                        isBolleriaCheck = false;
                    }
                    checks[6] = isBolleriaCheck;
                }
            });

            checkCereales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(7);
                        kcal.setText(res.toString());
                        isCerealesCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(7);
                        kcal.setText(res.toString());
                        isCerealesCheck = false;
                    }
                    checks[7] = isCerealesCheck;
                }
            });

            checkLegumbre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(8);
                        kcal.setText(res.toString());
                        isLegumbreCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(8);
                        kcal.setText(res.toString());
                        isLegumbreCheck = false;
                    }
                    checks[8] = isLegumbreCheck;
                }
            });

            checkEmbutido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(9);
                        kcal.setText(res.toString());
                        isEmbutidoCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(9);
                        kcal.setText(res.toString());
                        isEmbutidoCheck = false;
                    }
                    checks[9] = isEmbutidoCheck;
                }
            });

            checkQueso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(10);
                        kcal.setText(res.toString());
                        isQuesoCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(10);
                        kcal.setText(res.toString());
                        isQuesoCheck = false;
                    }
                    checks[10] = isQuesoCheck;
                }
            });

            checkYogurt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue + listKcal.get(11);
                        kcal.setText(res.toString());
                        isYogurtCheck = true;
                    } else {
                        Integer currentValue = Integer.valueOf(kcal.getText().toString());
                        Integer res = currentValue - listKcal.get(11);
                        kcal.setText(res.toString());
                        isYogurtCheck = false;
                    }
                    checks[11] = isYogurtCheck;
                }
            });
        }
        Log.e("PR:", "CHECKS "+checks);
        TextView foodType = (TextView) findViewById(R.id.food_type);
        foodType.setText(foodTable.getType());

        TextView foodDays = (TextView) findViewById(R.id.food_days);
        foodDays.setText(foodTable.getDays());

        kcal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                db.open();
                db.close();
            }
        });

        imageView = (ImageView) findViewById(R.id.image_view);
        imageButton = (ImageButton) findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });



        if(foodTable.getImage() != null ) {
            byte[] byteArray = foodTable.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
            bmp = Bitmap.createScaledBitmap(bmp, 120, 140, true);
            imageView.setImageBitmap(bmp);
            final BitmapDrawable bmpd = (BitmapDrawable) imageView.getDrawable();
            imageButton.setAdjustViewBounds(true);
            imageButton.setBackground(bmpd);
        } else {

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
                bitmapdataCamera = stream.toByteArray();
                db.open();
                //db.addImageFood(foodTable, bitmapdataCamera);
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
                bitmapdataGalery = stream.toByteArray();
                db.open();
                //db.addImageFood(foodTable, bitmapdataGalery);
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(FoodResumeActivity.this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();
            TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
            textoAviso.setText(R.string.avisoVolver);
            final Button volver = (Button)dialogLayout.findViewById(R.id.button_volver);
            final Button quedarse = (Button)dialogLayout.findViewById(R.id.button_quedarse);

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    if((boolean) getIntent().getSerializableExtra("fromFoods")){
                        startActivity(new Intent(FoodResumeActivity.this, FoodsActivity.class));
                    }else{
                        startActivity(new Intent(FoodResumeActivity.this, MainActivity.class));
                    }
                    dialog.cancel();
                }
            });
            quedarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(FoodResumeActivity.this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_alert, null);
            final AlertDialog dialog = builder.create();
            dialog.setView(dialogLayout);
            dialog.show();
            TextView textoAviso = dialogLayout.findViewById(R.id.textoAviso);
            textoAviso.setText(R.string.avisoVolver);
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
}

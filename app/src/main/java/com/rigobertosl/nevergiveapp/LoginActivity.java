package com.rigobertosl.nevergiveapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.rigobertosl.nevergiveapp.events.EventsMain;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;
import com.rigobertosl.nevergiveapp.objects.Profile;

public class LoginActivity extends AppFiredatabase {

    private static final String TAG = "LoginActivity";

    private EditText editEmail, editPassword;
    private Button buttonSignIn, buttonSignUp;
    private ProgressDialog progressDialog;

    private Profile currentProfile;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        buttonSignIn = (Button) findViewById(R.id.sign_in);
        buttonSignUp = (Button) findViewById(R.id.sign_up);

        progressDialog = new ProgressDialog(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    signUp();
                }
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    progressDialog.setMessage("Iniciando sesión...");
                    progressDialog.show();
                    signIn();
                }
            }
        });
    }

    private void signUp(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //El usuario se registra de manera exitosa
                    FirebaseUser user = mAuth.getCurrentUser();
                    toastMessage("Registro completado.", Toast.LENGTH_SHORT);
                    //updateUI(user);
                } else {
                    //Si el usuario ya existe
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        toastMessage("El usuario introducido no existe.", Toast.LENGTH_LONG);
                        editEmail.setText("");
                        editPassword.setText("");
                    }else{
                        toastMessage("Algo ha fallado, inténtelo de nuevo.", Toast.LENGTH_LONG);
                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    private void signIn(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, EventsMain.class);
                    startActivity(intent);
                    //updateUI(user);
                } else {
                    if(!(task.getException() instanceof FirebaseAuthUserCollisionException)){
                        toastMessage("El usuario introducido no existe.", Toast.LENGTH_LONG);
                    }else{
                        toastMessage("Algo ha fallado, inténtelo de nuevo.", Toast.LENGTH_LONG);
                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = editEmail.getText().toString();
        if (email.equals("")) {
            editEmail.setError("Necesario");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        String password = editPassword.getText().toString();
        if (password.equals("")) {
            editPassword.setError("Necesario");
            valid = false;
        } else {
            editPassword.setError(null);
        }
        return valid;
    }

    private void toastMessage(String message, int length){
        Toast.makeText(this, message, length).show();
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
        Intent setIntent = new Intent(LoginActivity.this, MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}

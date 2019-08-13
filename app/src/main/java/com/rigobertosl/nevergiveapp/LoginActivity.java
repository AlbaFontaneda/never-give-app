package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

public class LoginActivity extends AppFiredatabase {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    private EditText editEmail, editPassword;
    private Button buttonSignIn, buttonSignOut;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        buttonSignIn = (Button) findViewById(R.id.sign_in_button);
        buttonSignOut = (Button) findViewById(R.id.sign_out_button);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = editEmail.getText().toString();
                String passwordText = editPassword.getText().toString();
                if(!emailText.equals("") && !passwordText.equals("")){
                    mAuth.signInWithEmailAndPassword(emailText, passwordText);
                }else{
                    toastMessage("Por favor, rellene los campos.");
                }
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Desconectado.");
            }
        });

    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

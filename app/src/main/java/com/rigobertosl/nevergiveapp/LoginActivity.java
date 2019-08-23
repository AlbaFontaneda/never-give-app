package com.rigobertosl.nevergiveapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.rigobertosl.nevergiveapp.events.EventsMain;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;
import com.rigobertosl.nevergiveapp.objects.LoginController;
import com.rigobertosl.nevergiveapp.objects.Profile;

import net.cachapa.expandablelayout.ExpandableLayout;

public class LoginActivity extends AppFiredatabase {

    private static final String TAG = "LoginActivity";

    private Context context = this;
    private EditText editEmail, editPassword;
    private Button singUp, singIn, registerButton, notAccountButton;
    private ProgressDialog progressDialog;
    private TextView loginButton;
    private ExpandableLayout expandableLayout;
    private ConstraintLayout background;
    private LoginController isLogin = LoginController.REGISTER;
    private TextView textView;

    private Profile currentProfile;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        background = (ConstraintLayout) findViewById(R.id.background);
        registerButton = (Button) findViewById(R.id.registerbutton);
        loginButton = (TextView) findViewById(R.id.loginbutton);
        expandableLayout = (ExpandableLayout) findViewById(R.id.expandablelayout);
        singUp = (Button) findViewById(R.id.sing_up);
        singIn = (Button) findViewById(R.id.sing_in);
        notAccountButton = (Button) findViewById(R.id.notaccount);
        textView = (TextView) findViewById(R.id.text);

        progressDialog = new ProgressDialog(this);


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim().toLowerCase();
                String password = editPassword.getText().toString().trim();
                if(validateForm()){
                    signUp(context, email, password, progressDialog);
                    //editEmail.setText("");
                    //editPassword.setText("");
                }
            }
        });

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim().toLowerCase();
                String password = editPassword.getText().toString().trim();
                if(validateForm()){
                    signIn(context, email, password, progressDialog);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = LoginController.REGISTER;
                updateView();
                expandableLayout.expand();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = LoginController.LOGIN;
                updateView();
                expandableLayout.expand();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    isLogin = LoginController.REGISTER;
                    expandableLayout.collapse();
                }
            }
        });

        notAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }

    private void updateView() {
        if (isLogin.equals(LoginController.LOGIN)) {
            singUp.setVisibility(View.GONE);
            singIn.setVisibility(View.VISIBLE);
            textView.setText("Introduce tu usuario y contraseña y... ¡sigue entrenando!");
        } else {
            singUp.setVisibility(View.VISIBLE);
            singIn.setVisibility(View.GONE);
            textView.setText("¡Un último paso y habremos terminado!");
        }
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

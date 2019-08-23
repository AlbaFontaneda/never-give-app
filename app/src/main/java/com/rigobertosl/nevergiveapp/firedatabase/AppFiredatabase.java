package com.rigobertosl.nevergiveapp.firedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rigobertosl.nevergiveapp.LoginActivity;
import com.rigobertosl.nevergiveapp.MainActivity;
import com.rigobertosl.nevergiveapp.events.EventsMain;
import com.rigobertosl.nevergiveapp.objects.Event;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;
import java.util.UUID;

public class AppFiredatabase extends AppCompatActivity implements FiredatabaseInterface {

    protected DatabaseReference mydbRef;
    protected ArrayList<Event> allEvents = new ArrayList<>();

    /************************************  Realtime Database  *************************************/
    @Override
    public void addDataToFirebase(String key, Object data) {
        mydbRef = database.getReference(key);
        mydbRef.child(UUID.randomUUID().toString()).setValue(data);
    }

    public void updateProfileToFirebase(String ID, String data, String newValue){
        mydbRef = database.getReference(usersKey);
        mydbRef.child(ID).child(data).setValue(newValue);
    }

    private void addUserToFirebase(String email, String password){
        this.addDataToFirebase(usersKey, new Profile(mAuth.getCurrentUser().getUid(),
                email, password));
    }

    /**************************************  Authenticator  ***************************************/
    public boolean autoLogin(){
        return (mAuth.getCurrentUser() != null);
    }

    public void signIn(final Context context, final String email, final String password, final ProgressDialog progressDialog){

        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startNewActivity(context, MainActivity.class);
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

    public void signUp(final Context context, final String email, final String password, final ProgressDialog progressDialog){

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //El usuario se registra de manera exitosa
                            addUserToFirebase(email, password);
                            toastMessage("Registro completado.", Toast.LENGTH_SHORT);
                            startNewActivity(context, MainActivity.class);
                            //updateUI(user);
                        } else {
                            //Si el usuario ya existe
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                toastMessage("El usuario introducido ya existe.", Toast.LENGTH_LONG);
                            }else{
                                toastMessage("Algo ha fallado, inténtelo de nuevo.", Toast.LENGTH_LONG);
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void signOut(){
        mAuth.signOut();
    }

    public void updateNameProfile(final String newName){

        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(mAuth.getCurrentUser().getUid(), "name", newName);
                        toastMessage("Nombre actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    public void updateEmailProfile(final String newEmail){
        FirebaseUser user = mAuth.getCurrentUser();

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(mAuth.getCurrentUser().getUid(), "email", newEmail);
                        toastMessage("Email actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    public void updatePasswordProfile(final String newPassword){
        FirebaseUser user = mAuth.getCurrentUser();

        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(mAuth.getCurrentUser().getUid(), "password", newPassword);
                        toastMessage("Password actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    public void updateAllProfile(String newName, String newEmail, String newPassword){
        updateNameProfile(newName);
        updateEmailProfile(newEmail);
        updatePasswordProfile(newPassword);
    }

    /******************************************  Tools  *******************************************/
    public void startNewActivity(Context context, Class<?> newActivity){
        Intent intent = new Intent(context, newActivity);
        startActivity(intent);
    }

    public void toastMessage(String message, int length){
        Toast.makeText(this, message, length).show();
    }
}
package com.rigobertosl.nevergiveapp.firedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rigobertosl.nevergiveapp.main.activity.MainActivity;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AppFiredatabase extends AppCompatActivity implements FiredatabaseInterface {

    /************************************* Variables **********************************************/
    protected DatabaseReference mydbRef;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected FirebaseStorage storage = FirebaseStorage.getInstance();
    protected StorageReference storageRef = storage.getReference();


    /************************************  Realtime Database  *************************************/
    public void updateProfileToFirebase(String UID, String data, String newValue){
        mydbRef = database.getReference(usersKey);
        mydbRef.child(UID).child(data).setValue(newValue);
    }

    private void addUserToFirebase(String email, String password, String username, String profileImage) {
        mydbRef = database.getReference(usersKey);
        String UID = getUid();
        mydbRef.child(UID).setValue(new Profile(UID, email, password, username, profileImage));
    }

    /**************************************  Authenticator  ***************************************/
    public boolean autoLogin() {
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
                            toastMessage("Registro completado.", Toast.LENGTH_SHORT);
                            startNewActivity(context, MainActivity.class);
                            updateUserOnRegister(email, password);
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

    private String getRandomImg() {
        String[] profiles = {"profile_amy.png", "profile_bender.png", "profile_fry.png", "profile_hermes.png", "profile_leela.png", "profile_mom.png",
                "profile_mordisquitos.png", "profile_profesor.png", "profile_zapp.png", "profile_zoiberg.png"};
        int rnd = new Random().nextInt(profiles.length);

        return profiles[rnd];
    }

    private void updateUserOnRegister(final String email, final String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String username = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        final Uri[] imageUrl = {null};

        storageRef.child("profilephotos/"+getRandomImg()).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        UserProfileChangeRequest profileUpdates;
                        if(task.isSuccessful()) {
                            imageUrl[0] = task.getResult();
                            profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .setPhotoUri(imageUrl[0])
                                    .build();
                        } else {
                            profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .setPhotoUri(imageUrl[0])
                                    .build();
                        }

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("FIREBASE", "User profile updated.");
                                            addUserToFirebase(email, password, username, imageUrl[0].toString());
                                        } else {
                                            addUserToFirebase(email, password, "Anonimo",  imageUrl[0].toString());
                                        }
                                    }
                                });
                    }
                });
    }

    public void updateNameProfile(final String newName){
        FirebaseUser user = mAuth.getCurrentUser();

        if (newName.isEmpty()) {
            createErrorAlert(this, "Alerta", "Este campo no puede estar vacio");
        } else {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(getUid(), "name", newName);
                        toastMessage("Username actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
        }


    }

    public void updateEmailProfile(final String newEmail){
        FirebaseUser user = mAuth.getCurrentUser();

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(getUid(), "email", newEmail);
                        toastMessage("Email actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    public void updatePasswordProfile(final String newPassword){
        FirebaseUser user = mAuth.getCurrentUser();

        if (newPassword.isEmpty()) {
            createErrorAlert(this, "Alerta", "Este campo no puede estar vacio");
        } else {
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateProfileToFirebase(getUid(), "password", newPassword);
                        toastMessage("Password actualizado", Toast.LENGTH_SHORT);
                    }else{
                        toastMessage("Algo ha salido mal, inténtelo de nuevo.", Toast.LENGTH_SHORT);
                    }
                }
            });
        }


    }

    public void updateAllProfile(String newName, String newEmail, String newPassword){
        FirebaseUser user = mAuth.getCurrentUser();
        if (!newName.equals(user.getDisplayName()) || !newName.equals("")) {
            updateNameProfile(newName);
        }
        if (!newEmail.equals(user.getEmail()) || !newEmail.equals("")) {
            updateEmailProfile(newEmail);
        }
    }

    /******************************************  Tools  *******************************************/
    public void startNewActivity(Context context, Class<?> newActivity){
        Intent intent = new Intent(context, newActivity);
        startActivity(intent);
    }

    public void toastMessage(String message, int length){
        Toast.makeText(this, message, length).show();
    }


    public SweetAlertDialog createWarningSweetAlert(Context ctx, String title, String message, String confirmButtonm, String cancelButton) {
        SweetAlertDialog alert = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(confirmButtonm)
                .setCancelText(cancelButton);

        return alert;
    }

    public SweetAlertDialog createErrorAlert(Context ctx, String title, String message) {
        SweetAlertDialog alert = new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message);

        return alert;
    }

    @Override
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    public String getUsername() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

    }

    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public Uri getProfilePhoto() {
        return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

    }

}
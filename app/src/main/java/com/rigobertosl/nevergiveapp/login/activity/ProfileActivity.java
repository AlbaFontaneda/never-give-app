package com.rigobertosl.nevergiveapp.login.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.polyak.iconswitch.IconSwitch;
import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.firedatabase.AppFiredatabase;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ProfileActivity extends AppFiredatabase {

    private ExpandableLayout expandableLayout;
    private TextView username, password, email, waitText;
    private EditText newUserName, newPassword;//, newEmail;
    private Button cancel, save;
    private ProgressBar spinner;
    private IconSwitch iconSwitch;
    private LinearLayout blurUser, blurPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = (TextView) findViewById(R.id.user);
        username.setText(getUsername());
        email = (TextView) findViewById(R.id.email);
        email.setText(getEmail());

        newUserName = (EditText) findViewById(R.id.new_user);
        //newUserName.setText(getUsername());
        newPassword = (EditText) findViewById(R.id.new_pass);
        //newEmail = (EditText) findViewById(R.id.new_email);
        //newEmail.setText(getEmail());

        blurUser = (LinearLayout) findViewById(R.id.edit_user);
        blurPass = (LinearLayout) findViewById(R.id.edit_pass);

        cancel = (Button) findViewById(R.id.cancel_button);
        save = (Button) findViewById(R.id.save_button);
        iconSwitch = (IconSwitch) findViewById(R.id.icon_switch);

        waitText = (TextView) findViewById(R.id.wait_message);
        spinner = (ProgressBar) findViewById(R.id.progress_loader);

        expandableLayout = (ExpandableLayout) findViewById(R.id.expandablelayout);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                } else {
                    expandableLayout.expand();
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });


        iconSwitch.setChecked(IconSwitch.Checked.LEFT);

        iconSwitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                if (current.equals(IconSwitch.Checked.LEFT)) {
                    blurPass.setVisibility(View.INVISIBLE);
                    blurUser.setVisibility(View.VISIBLE);
                    newUserName.setEnabled(true);
                    newPassword.setEnabled(false);
                    newPassword.setText("");
                } else {
                    blurUser.setVisibility(View.INVISIBLE);
                    blurPass.setVisibility(View.VISIBLE);
                    newUserName.setEnabled(false);
                    newPassword.setEnabled(true);
                    newUserName.setText("");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //updateAllProfile(newUserName.getText().toString(), newEmail.getText().toString(), newPassword.getText().toString());
                //updateAllProfile(newUserName.getText().toString(), mAuth.getCurrentUser().getEmail(), newPassword.getText().toString());

                if (iconSwitch.getChecked().equals(IconSwitch.Checked.LEFT)) {
                    updateNameProfile(newUserName.getText().toString());
                    newUserName.setEnabled(false);
                } else if (iconSwitch.getChecked().equals(IconSwitch.Checked.RIGHT)) {
                    updatePasswordProfile(newPassword.getText().toString());
                    newPassword.setEnabled(false);
                }

                waitText.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                save.setEnabled(false);
                cancel.setEnabled(false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 2s = 2000ms
                        expandableLayout.collapse();
                        fab.setVisibility(View.VISIBLE);
                        waitText.setVisibility(View.INVISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                        newUserName.setEnabled(true);
                        newPassword.setEnabled(true);
                        save.setEnabled(true);
                        cancel.setEnabled(true);
                    }
                }, 5000);
            }


        });
    }
}

package com.rigobertosl.nevergiveapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.database.DataBaseContract;
import com.rigobertosl.nevergiveapp.objects.Exercise;
import com.rigobertosl.nevergiveapp.objects.TrainingTable;
import com.rigobertosl.nevergiveapp.training.fragment.ExerciseResumeFragment;
import com.rigobertosl.nevergiveapp.training.activity.TrainingActivity;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class DefaultResume extends AppCompatActivity {

    private Context mContext;
    private DataBaseContract db;
    private long tableID;
    private int numPaginas;
    private TrainingTable trainingTable;
    private ExercisePageAdapter mExercisePageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_resume);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        tableID = (long) getIntent().getSerializableExtra("tablaID");
        trainingTable = db.getDefaultTableByID(tableID);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numPaginas = (int)db.getAllDefaultExercisesFromTable(trainingTable).size();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<numPaginas; i++){
            Fragment f = new ExerciseResumeFragment();
            fragments.add(f);
        }

        mExercisePageAdapter = new ExercisePageAdapter(getSupportFragmentManager(), fragments);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mExercisePageAdapter);

    }

    /** ADAPTADOR DEL VIEWPAGER **/
    public class ExercisePageAdapter extends FragmentPagerAdapter {

        private  ArrayList<Fragment> fragments;

        public ExercisePageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            ExerciseResumeFragment fragment = (ExerciseResumeFragment) fragments.get(position);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return numPaginas;
        }
    }


    /** ADAPTADOR DEL RECYCLERVIEW **/
    public static class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.MyViewHolder> {

        private Exercise exercise;

        public CheckboxAdapter(Exercise exercise){
            this.exercise = exercise;
        }

        @Override
        public CheckboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox, parent, false);
            return new CheckboxAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CheckboxAdapter.MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return valueOf(exercise.getSeries());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /** Sobrescripción del botón de atrás del propio móvil
     * Código extraido de: Ekawas.
     * Answered Jun 29 '10 at 16:00.
     * Edited by Arvindh Mani.
     * Edited Aug 10 '16 at 1:23.
     * Visitado a día 11/04/2018
     * Enlace: https://stackoverflow.com/questions/3141996/android-how-to-override-the-back-button-so-it-doesnt-finish-my-activity?rq=1
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
        Intent setIntent;
        setIntent = new Intent(DefaultResume.this, TrainingActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(setIntent);
    }
}

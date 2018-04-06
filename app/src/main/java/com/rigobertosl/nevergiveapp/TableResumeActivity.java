package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class TableResumeActivity extends TrainingActivity {

    private Context mContext;
    private DataBaseContract db;
    private ArrayList<TrainingTable> trainingTable;
    private int position;
    private RecyclerView recyclerView;
    private ExerciseResumeAdapter exerciseResumeAdapter;
    private ExercisePageAdapter mExercisePageAdapter;
    private ViewPager mViewPager;
    public int numPaginas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_resume);

        mContext = getApplicationContext();
        db = new DataBaseContract(mContext);
        db.open();
        trainingTable = db.getAllTables();
        position = (int) getIntent().getSerializableExtra("position");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(trainingTable.get(position).getName());
        setSupportActionBar(toolbar);

        numPaginas = (int)db.getAllExercisesFromTable(trainingTable.get(position)).size();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i<numPaginas; i++){
            Fragment f = new ExerciseResumeFragment();
            fragments.add(f);
        }

        mExercisePageAdapter = new ExercisePageAdapter(getSupportFragmentManager(), fragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mExercisePageAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view, trainingTable.get(position));
            }
        });
    }

    public void openDialog(View view, TrainingTable table) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_edit_table, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.setTitle(table.getName());
        dialog.show();

        final EditText tableName = (EditText) dialogLayout.findViewById(R.id.table_name);
        EditText tableDays = (EditText) dialogLayout.findViewById(R.id.table_days);
        final Button editar = (Button)dialogLayout.findViewById(R.id.button_edit);
        final Button cancelar = (Button)dialogLayout.findViewById(R.id.button_cancel);
        tableName.setText(table.getName());
        tableDays.setText(table.getDays());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView = (RecyclerView)dialogLayout.findViewById(R.id.recylcer_exercises);
        recyclerView.setHasFixedSize(true);
        ArrayList<Exercise> exerciseList = db.getAllExercisesFromTable(table);
        exerciseResumeAdapter = new ExerciseResumeAdapter(exerciseList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseResumeAdapter);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                db.editTable(trainingTable.get(position), tableName.getText().toString(), (ArrayList<Exercise>) exerciseResumeAdapter.getExercisesEdited());
                dialog.cancel();
                finish();
                startActivity(getIntent());
            }
        });
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
}

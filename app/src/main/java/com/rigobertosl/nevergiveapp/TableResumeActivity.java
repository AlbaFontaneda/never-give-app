package com.rigobertosl.nevergiveapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class TableResumeActivity extends TrainingActivity {

    private Context mContext;
    private DataBaseContract db;
    private ArrayList<TrainingTable> trainingTable;
    private int position;
    private RecyclerView recyclerView;
    private ExerciseResumeAdapter exerciseResumeAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


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

    public static class ExerciseResume extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private int position;
        private DataBaseContract db;
        private Exercise ejercicio;

        public ExerciseResume() {
        }

        public static ExerciseResume newInstance(int sectionNumber) {
            ExerciseResume fragment = new ExerciseResume();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_table_resume, container, false);
            position = (int) getActivity().getIntent().getSerializableExtra("position");

            TextView exerciseTitle = (TextView)rootView.findViewById(R.id.titleExercise);
            TextView exerciseSeries = (TextView)rootView.findViewById(R.id.series);
            TextView exerciseRep = (TextView)rootView.findViewById(R.id.repeticiones);
            TextView exerciseDescanso = (TextView)rootView.findViewById(R.id.descanso);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerCheckBoxView);

            db = new DataBaseContract(getActivity());
            db.open();

            ArrayList<TrainingTable> trainingTable = db.getAllTables();
            ejercicio = db.getAllExercisesFromTable(trainingTable.get(position)).get(position);

            exerciseTitle.setText((String) ejercicio.getNombre());
            exerciseSeries.setText((String) ejercicio.getSeries());
            exerciseRep.setText((String) ejercicio.getRepeticiones());
            exerciseDescanso.setText((String) ejercicio.getDescanso());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new CheckboxAdapter(ejercicio);
            recyclerView.setAdapter(adapter);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ExerciseResume (defined as a static inner class below).
            return ExerciseResume.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return db.getAllExercisesFromTable(trainingTable.get(position)).size();
        }
    }

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

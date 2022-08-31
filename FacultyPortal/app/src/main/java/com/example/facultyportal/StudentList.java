package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.facultyportal.Adapter.StudentAdapter;

import com.example.facultyportal.Model.StudentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter adapter;
    List<StudentModel> userList;
    DatabaseReference reference;
    Toolbar toolbar;
    String CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        SharedPreferences sh= StudentList.this.getSharedPreferences("code",MODE_PRIVATE);
        CODE=sh.getString("code","");

        Log.d("Bitch", CODE);

        recyclerView= findViewById(R.id.selectedrecycler);
        toolbar= findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        userList= new ArrayList<>();


        adapter= new StudentAdapter(userList, StudentList.this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        recyclerView.setLayoutManager(lm);

        populateRecyclerView();




    }

    /**
     * TODO Ensure only students within the registered course are displayed
     */

    private void populateRecyclerView() {

        reference= FirebaseDatabase.getInstance().getReference("Students");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){

                    StudentModel rs= ds.getValue(StudentModel.class);

                    userList.add(rs);

                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.studentsportal.Adapter.DownloadAdapter;
import com.example.studentsportal.Model.DownloadModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DownloadNotes extends AppCompatActivity {

    String CODE;
    Toolbar toolbar;
    RecyclerView recyclerView;
    DownloadAdapter adapter;
    List<DownloadModel> userList;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_notes);

        Intent intent= getIntent();
        CODE=intent.getStringExtra("code");
        Log.d("Banana",CODE);

        toolbar=findViewById(R.id.selectedtoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView=findViewById(R.id.recyclerview);

        userList=new ArrayList<>();
        adapter= new DownloadAdapter(this,userList);



        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        populateData();


    }

    private void populateData() {

        reference= FirebaseDatabase.getInstance().getReference("notes");
        Query v=reference.orderByChild("code").equalTo(CODE);
        v.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    DownloadModel dm= ds.getValue(DownloadModel.class);
                    userList.add(dm);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
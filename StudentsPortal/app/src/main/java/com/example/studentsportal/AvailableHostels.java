package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.studentsportal.Adapter.RoomAdapter;
import com.example.studentsportal.Model.RoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailableHostels extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RoomAdapter adapter;
    List<RoomModel> userList;

    DatabaseReference ref;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hostels);


        toolbar=findViewById(R.id.selectedtoolbar);
        recyclerView=findViewById(R.id.recyclerview);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AVAILABLE HOSTELS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        addData();
        dialog= new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Loading");

        userList= new ArrayList<>();

        adapter=new RoomAdapter(this, userList);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);








    }

    private void addData() {



        ref= FirebaseDatabase.getInstance().getReference().child("accommodation");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){

                    RoomModel rm= ds.getValue(RoomModel.class);


                    userList.add(rm);
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
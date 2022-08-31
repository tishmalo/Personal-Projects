package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.studentsportal.Adapter.ResultsAdapter;
import com.example.studentsportal.Model.ResultsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference reference;
    Toolbar toolbar;
    ResultsAdapter adapter;
    List<ResultsModel> userList;
    String REG, TOTAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        recyclerView= findViewById(R.id.recyclerview);

        toolbar= findViewById(R.id.toolBar);


        showResults();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(TOTAL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userList=new ArrayList<>();

        adapter=new ResultsAdapter(this, userList);



        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);




    }

    private void showResults() {

        reference= FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                REG= snapshot.child("Reg").getValue().toString();

                DatabaseReference ref;
                ref=FirebaseDatabase.getInstance().getReference("results").child(REG);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userList.clear();
                        for (DataSnapshot ds1:snapshot.getChildren()){
                            ResultsModel rm=ds1.getValue(ResultsModel.class);
                            userList.add(rm);
                        }
                        adapter.notifyDataSetChanged();

                        Double SUMS=0.0;

                        for(DataSnapshot ds2: snapshot.getChildren()){
                            String RESULT= ds2.child("result").getValue().toString();

                            Double CHANGE= Double.valueOf(RESULT);

                           SUMS +=CHANGE;
                           TOTAL=String.valueOf(SUMS);

                            Log.d("WIN", TOTAL);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
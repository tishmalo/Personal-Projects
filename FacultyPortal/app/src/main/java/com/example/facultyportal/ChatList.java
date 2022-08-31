package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.facultyportal.Adapter.chatAdapter;
import com.example.facultyportal.Model.UnitsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView rv;

    chatAdapter adapter;
    List<UnitsModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        toolbar=findViewById(R.id.toolBar);
        rv=findViewById(R.id.selectedrecycler);

        userList=new ArrayList<>();
        adapter=new chatAdapter(this, userList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TITLES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        populateData();

    }

    private void populateData() {

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Units").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              userList.clear();
              for(DataSnapshot ds1:snapshot.getChildren()){

                  UnitsModel um=ds1.getValue(UnitsModel.class);

                  userList.add(um);
              }

              adapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });


    }
}
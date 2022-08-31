package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.studentsportal.Adapter.PostAdapter;
import com.example.studentsportal.Model.PostModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout layout;
    NavigationView navigationView;
    Toolbar toolbar;

    Intent intent;
    RecyclerView recyclerView;

    List<PostModel> userList;
    PostAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layout=findViewById(R.id.drawerLayout1);
        navigationView=findViewById(R.id.navigationBar1);
        toolbar=findViewById(R.id.toolBar2);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("STUDENTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userList=new ArrayList<>();
        adapter=new PostAdapter(this, userList);

        recyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        populateData();



        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(MainActivity.this, layout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        layout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void populateData() {

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("notification");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    PostModel pm= ds.getValue(PostModel.class);
                    userList.add(pm);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       switch(item.getItemId()){

           case R.id.results:
               intent=new Intent(getApplicationContext(),Results.class);
               startActivity(intent);
               break;

           case R.id.notes:
               intent= new Intent(getApplicationContext(), Notes.class);
               startActivity(intent);
               break;


           case R.id.accommodation:
               intent= new Intent(getApplicationContext(), AvailableHostels.class);
               startActivity(intent);
               break;

           case R.id.fees:
               intent= new Intent(getApplicationContext(), Fees.class);
               startActivity(intent);
               break;

           case R.id.courses:
               intent= new Intent(getApplicationContext(), CourseRegistration.class);
               startActivity(intent);
               break;

           case R.id.message:
               intent= new Intent(getApplicationContext(), ChatList.class);
               startActivity(intent);
               break;


       }



        return false;
    }
}
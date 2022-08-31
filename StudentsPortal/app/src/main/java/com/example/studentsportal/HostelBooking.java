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
import android.widget.Toast;

import com.example.studentsportal.Adapter.BookingAdapter;
import com.example.studentsportal.Model.BookingModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HostelBooking extends AppCompatActivity {

    FloatingActionButton button;
    RecyclerView recyclerView;
    DatabaseReference reference, ref;
    Toolbar toolbar;
    Intent intent;
    String ROOM;

    BookingAdapter adapter;
    List<BookingModel> userList;
    String REG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_booking);


        button=findViewById(R.id.enter);
        recyclerView= findViewById(R.id.recyclerview);
        toolbar= findViewById(R.id.selectedtoolbar);


        userList=new ArrayList<>();
        adapter= new BookingAdapter(this, userList);


        intent=getIntent();
        ROOM= intent.getStringExtra("Room");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ROOM" + " " + ROOM);

        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        /**
         * TODO...Ensure that the student can only book a hostel once;
         */


        bookHostel();

        showRoommates();


    }



    private void bookHostel() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference= FirebaseDatabase.getInstance().getReference().child("accommodation").child(ROOM);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        final String SPACE= snapshot.child("space").getValue().toString();




                        Integer updatedSpace= Integer.valueOf(SPACE);


                        Integer finalSpace= updatedSpace-1;


                                final String TISH = String.valueOf(finalSpace);



                                Log.d("SPACE", TISH);
                                HashMap map = new HashMap();
                                map.put("space", TISH);
                                map.put("room", ROOM);

                                Log.d("Tish", SPACE);


                                reference.updateChildren(map);


                                DatabaseReference databaseReference;
                                databaseReference=FirebaseDatabase.getInstance().getReference("Bookings");

                                BookingModel bm= new BookingModel(REG,ROOM);

                                databaseReference.child(ROOM).setValue(bm);


                                Toast.makeText(HostelBooking.this, "Hostel Booked", Toast.LENGTH_SHORT).show();


                        Intent intent30= new Intent(HostelBooking.this, MainActivity.class);
                        startActivity(intent30);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    private void showRoommates() {


        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Bookings").child(ROOM);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
               if(snapshot.exists()){

                   BookingModel bm= snapshot.getValue(BookingModel.class);



                   userList.add(bm);





               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    REG= snapshot.child("Reg").getValue().toString();
                    Log.i("REGY", REG);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
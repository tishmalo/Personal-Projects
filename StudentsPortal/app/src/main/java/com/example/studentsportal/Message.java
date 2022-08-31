package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.studentsportal.Adapter.MessageAdapter;
import com.example.studentsportal.Adapter.chatAdapter;
import com.example.studentsportal.Model.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageButton button;
    EditText message;
    DatabaseReference reference;
    String CODE;

    List<ChatModel> userList;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        SharedPreferences sh=Message.this.getSharedPreferences("code",MODE_PRIVATE);
        CODE=sh.getString("code","");

        toolbar=findViewById(R.id.toolBar);
        recyclerView=findViewById(R.id.recyclerview);
        message=findViewById(R.id.text_send);
        button=findViewById(R.id.btn_send);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userList=new ArrayList<>();
        adapter=new MessageAdapter(Message.this, userList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(false);
        recyclerView.setLayoutManager(lm);


        ViewMessages();
        SendMessages();

    }

    private void SendMessages() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference=FirebaseDatabase.getInstance().getReference("Chats");

                final String SENDER=FirebaseAuth.getInstance().getCurrentUser().getUid();
                final String MESSAGE=message.getText().toString();
                final String EMAIL=FirebaseAuth.getInstance().getCurrentUser().getEmail();



                ChatModel cm=new ChatModel(SENDER,CODE,MESSAGE,EMAIL);
                reference.push().setValue(cm);
                adapter.notifyDataSetChanged();

                message.setText("");
            }
        });




    }

    private void ViewMessages() {

        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                Query v=reference.orderByChild("receiver").equalTo(CODE);
                v.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot ds1: snapshot.getChildren()){

                            ChatModel cm= ds1.getValue(ChatModel.class);

                            userList.add(cm);

                        }

                        adapter.notifyDataSetChanged();





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
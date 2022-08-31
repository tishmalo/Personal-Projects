package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostel.Model.HostelList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

  EditText space,room;
  Button submit;

  ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        space= findViewById(R.id.space);
        room=findViewById(R.id.room);
        submit=findViewById(R.id.submit);

        dialog= new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Adding..");

        addtoDatabase();



    }

    private void addtoDatabase() {

        /**
         * To do
         * Check if hostel number already exists, then update it.
         */

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.show();

                DatabaseReference ref;
                ref= FirebaseDatabase.getInstance().getReference("accommodation");

                String space1=space.getText().toString();
                String room1= room.getText().toString();



                HostelList hl=new HostelList(room1,space1);

                ref.child(room1).setValue(hl);

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hostel added", Toast.LENGTH_SHORT).show();

                space.setText("");
                room.setText("");

            }
        });



    }
}
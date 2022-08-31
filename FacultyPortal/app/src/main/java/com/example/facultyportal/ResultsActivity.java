package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facultyportal.Model.ResultsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {


      EditText cat, exams, assignment;
      Button button;
      DatabaseReference reference;
      Toolbar toolbar;
    String Reg;
    ProgressDialog dialog;


     String FINALCODE,Code1;
     TextView CourseCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        toolbar= findViewById(R.id.toolBar);

        Intent intent= getIntent();

        Reg= intent.getStringExtra("Reg");
        Code1=intent.getStringExtra("Code");




        CourseCode= findViewById(R.id.courseCode);







        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Reg);

       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });



        cat=findViewById(R.id.CAT);
        exams=findViewById(R.id.EXAMS);
        assignment= findViewById(R.id.Assino);
        button= findViewById(R.id.submit);


        cat.setText("0");
        exams.setText("0");
        assignment.setText("0");
        CourseCode.setText(Code1);

        dialog= new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("uploading");

        retrieveresults();
        uploadResults();


    }



    private void uploadResults() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final String CAT= cat.getText().toString();
                final String EXAMS=exams.getText().toString();
                final String ASSIGNMENTS= assignment.getText().toString();

                
                Double E1= Double.valueOf(CAT);
                Double E2= Double.valueOf(EXAMS);
                Double E3= Double.valueOf(ASSIGNMENTS);

                Double F1= (E1/30)*30;
                Double F2= (E2/100)*70;
                Double result= F1+F2;

                String RESULT= String.valueOf(result);


                reference=FirebaseDatabase.getInstance().getReference("results");


                ResultsModel rm= new ResultsModel(Reg,CAT,EXAMS,ASSIGNMENTS,RESULT,Code1);

                reference.child(Reg).child(Code1).setValue(rm);
                Toast.makeText(getApplicationContext(), "uploaded successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), FacultyPage.class);
                startActivity(intent);

               finish();








            }

        });





    }

    /**
     * TODO remove the spinner
     */

    private void retrieveresults() {

        reference= FirebaseDatabase.getInstance().getReference("results").child(Reg).child(Code1);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    final String CAT = snapshot.child("cat").getValue().toString();
                    final String EXAMS = snapshot.child("exams").getValue().toString();
                    final String ASSIGNMENT = snapshot.child("assignment").getValue().toString();
                    FINALCODE= snapshot.child("code").getValue().toString();


                    Log.d("REGI", CAT);

                    cat.setText(CAT);
                    exams.setText(EXAMS);
                    assignment.setText(ASSIGNMENT);
                    CourseCode.setText(Code1);

                }else{
                    Toast.makeText(ResultsActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                        cat.setText("0");
                        exams.setText("0");
                        assignment.setText("0");
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
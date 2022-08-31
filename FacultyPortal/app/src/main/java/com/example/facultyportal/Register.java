package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {


    EditText email, password, cpassword;
    TextView already;
    Button therapist;
    FirebaseAuth mAuth;

    Intent intent;
    DatabaseReference ref;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        already=findViewById(R.id.already);
        therapist=findViewById(R.id.therapist);


        dialog= new ProgressDialog(this);


        RegisterUser();


        dialog.setCancelable(false);
        dialog.setMessage("Registering");

    }

    /***
     * TODO Ensure Firebase Database paths must not contain '.', '#', '$', '[', or ']'
     */


    private void RegisterUser() {

        therapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String EMAIL= email.getText().toString();
                final String PASSWORD= password.getText().toString();
                final String CPASSWORD=cpassword.getText().toString();



                if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Enter Valid Email");
                    return;

                }if(!PASSWORD.equals(CPASSWORD)){
                    cpassword.setError("Passwords don't match");
                    return;
                }if(PASSWORD.length()<6){
                    password.setError("Use a longer password");
                }else{

                    dialog.show();

                    mAuth=FirebaseAuth.getInstance();

                    ref= FirebaseDatabase.getInstance().getReference("Faculty");


                    mAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }else{
                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                HashMap map= new HashMap();
                                map.put("email", EMAIL);

                                ref.push().setValue(map);

                                dialog.dismiss();

                                intent= new Intent(getApplicationContext(), FacultyPage.class);
                                startActivity(intent);


                                finish();

                            }

                        }
                    });

                }


            }
        });







    }


}
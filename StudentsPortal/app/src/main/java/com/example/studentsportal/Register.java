package com.example.studentsportal;

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
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText  username, email, password, cpassword;
    TextView already;
    Button therapist;
    FirebaseAuth mAuth;
    Spinner spinner;
    Intent intent;
    DatabaseReference ref;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username= findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        already=findViewById(R.id.already);
        therapist=findViewById(R.id.therapist);
        spinner= findViewById(R.id.spinner);

        dialog= new ProgressDialog(this);


        RegisterUser();


        dialog.setCancelable(false);
        dialog.setMessage("Registering");


    }

    private void RegisterUser() {

        therapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String EMAIL= email.getText().toString();
                final String PASSWORD= password.getText().toString();
                final String USERNAME=username.getText().toString();
                final String CPASSWORD=cpassword.getText().toString();
                final String COURSE= spinner.getSelectedItem().toString();


                if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Enter Valid Email");
                    return;

                }if(TextUtils.isEmpty(USERNAME)){
                    username.setError("Enter Registration number");
                    return;
                }if(COURSE=="SELECT COURSE"){
                    Toast.makeText(Register.this, "Select Course", Toast.LENGTH_SHORT).show();
                    return;
                }if(!PASSWORD.equals(CPASSWORD)){
                    cpassword.setError("Passwords don't match");
                    return;
                }if(PASSWORD.length()<6){
                    password.setError("Use a longer password");
                }else{

                    dialog.show();

                    mAuth=FirebaseAuth.getInstance();

                    ref= FirebaseDatabase.getInstance().getReference("Students");


                    mAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }else{
                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                HashMap map= new HashMap();
                                map.put("Reg", USERNAME);
                                map.put("Email", EMAIL);
                                map.put("Course",COURSE);
                                map.put("UserId", FirebaseAuth.getInstance().getCurrentUser().getUid());

                                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);

                                dialog.dismiss();

                                intent= new Intent(getApplicationContext(), MainActivity.class);
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
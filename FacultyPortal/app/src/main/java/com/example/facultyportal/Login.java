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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    EditText email, password;
    Button button;
    TextView already;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        button=findViewById(R.id.login);

        mAuth=FirebaseAuth.getInstance();
        already= findViewById(R.id.already);







        goToRegister();



        dialog= new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Logging in");

        LoginUser();
        ListenforRegistereduser();

    }

    private void goToRegister() {

        Intent intent30= new Intent(Login.this, Register.class);
        startActivity(intent30);
    }


    private void ListenforRegistereduser() {

        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user;
                user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){

                    Intent intent2= new Intent(Login.this, FacultyPage.class);
                    startActivity(intent2);

                    finish();


                }

            }
        };


    }

    private void LoginUser() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String EMAIL=email.getText().toString();
                final String PASSWORD=password.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){

                    email.setError("Enter valid email");
                    return;
                }if(TextUtils.isEmpty(PASSWORD)){

                    password.setError("Enter password");
                    return;
                }else{
                    dialog.show();

                    mAuth.signInWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }else{

                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                                Intent intent= new Intent(Login.this, FacultyPage.class);
                                startActivity(intent);

                                finish();

                            }


                        }
                    });


                }

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);


    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(authStateListener);
    }


}
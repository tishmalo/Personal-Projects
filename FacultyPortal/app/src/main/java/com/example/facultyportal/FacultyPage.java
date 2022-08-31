package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView email;
    CircleImageView circleImageView;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar=findViewById(R.id.toolBar2);
        navigationView=findViewById(R.id.navigationBar1);
        drawerLayout=findViewById(R.id.drawerLayout1);
        email=findViewById(R.id.email);
        circleImageView=findViewById(R.id.profilepic);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Faculty");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(FacultyPage.this, drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.courses:
                Intent intent= new Intent(getApplicationContext(), UploadCourse.class);
                startActivity(intent);
                break;

            case R.id.home:
                Intent intent1= new Intent(getApplicationContext(), FacultyPage.class);
                startActivity(intent1);
                break;

            case R.id.results:
                Intent intent4= new Intent(getApplicationContext(), UnitList.class);
                startActivity(intent4);
                break;


            case R.id.notes:
                Intent intent6= new Intent(getApplicationContext(), PdfPrefix.class);
                startActivity(intent6);
                break;

            case R.id.message:
                Intent intent7= new Intent(getApplicationContext(), ChatList.class);
                startActivity(intent7);
                break;


            case R.id.logout:
                finish();
                break;


        }

        return false;
    }
}
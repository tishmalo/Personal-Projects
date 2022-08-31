package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.facultyportal.Adapter.NotesAdapter;
import com.example.facultyportal.Model.NotesModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadsNotes extends AppCompatActivity {
    
    
    private Button nameLabel, add;
    TextView  name;
    EditText pdf;
    de.hdodenhof.circleimageview.CircleImageView patientpicture;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    Uri imageUri=null;

    List<NotesModel> userList;
    NotesAdapter adapter;

    String CODE,NAME;

    Toolbar toolbar;
    String PDF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads_notes);

        SharedPreferences sharedPreferences= UploadsNotes.this.getSharedPreferences("code",MODE_PRIVATE);
        CODE=sharedPreferences.getString("code","");

        nameLabel=findViewById(R.id.nameLabel);
        add=findViewById(R.id.add);
        patientpicture=findViewById(R.id.patientpicture);
        pdf=findViewById(R.id.NAME);

        name=findViewById(R.id.pdf);

        recyclerView=findViewById(R.id.selectedrecycler);
        toolbar=findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        userList= new ArrayList<>();
        adapter= new NotesAdapter(UploadsNotes.this, userList);

        showNotes();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(adapter);

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading");



        getLocalFile();
        addtoFirebase();
    }

    private void showNotes() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("notes");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Query v= ref.orderByChild("code").equalTo(CODE);
                v.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot ds1: snapshot.getChildren()){
                            NotesModel nm= ds1.getValue(NotesModel.class);

                            userList.add(nm);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addtoFirebase() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String TimeStamp= ""+System.currentTimeMillis();
                StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                final String TimeId= TimeStamp;

                final StorageReference file= storageReference.child(TimeId+ "."+"pdf");
                dialog.show();

                file.putFile(imageUri).continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return file.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // After uploading is done it progress
                            // dialog box will be dismissed
                            dialog.dismiss();
                            Uri uri = task.getResult();
                            String myurl;
                            myurl = uri.toString();
                            String codes;
                            codes="";
                            NAME=pdf.getText().toString();


                            DatabaseReference ref;
                            ref= FirebaseDatabase.getInstance().getReference("notes");

                            NotesModel nm = new NotesModel(myurl,CODE,NAME);

                            ref.push().setValue(nm);

                            name.setText("Upload pdf");
                            pdf.setText("");



                            Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "UploadedFailed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void getLocalFile() {
        nameLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent= new Intent();

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                galleryIntent.setType("application/pdf");

                startActivityForResult(galleryIntent,1);




            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode==RESULT_OK){




            imageUri=data.getData();

            PDF= imageUri.toString();



            name.setText(PDF);






        }
    }
}
package com.example.studentsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentsportal.Adapter.CourseAdapter;
import com.example.studentsportal.Model.CourseCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseRegistration extends AppCompatActivity {


    Spinner spinner;
    List<String> userList;
    ArrayAdapter<String> adapter;

    List<CourseCode> list;
    CourseAdapter courseAdapter;

    DatabaseReference reference;
    String unitCode;

    Handler handler;
    String code;
    String Email;
    ImageButton button;

    Toolbar toolbar;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);

        userList=new ArrayList<String>();

        handler=new Handler(Looper.getMainLooper());

        spinner= findViewById(R.id.courses);
        button=findViewById(R.id.add);


        list= new ArrayList<>();
        courseAdapter= new CourseAdapter(this, list);

        toolbar= findViewById(R.id.toolBar);
        recyclerView=findViewById(R.id.selectedrecycler);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(courseAdapter);

        addCourse();

        populate();

        new connection().execute();

    }

    private void populate() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Units").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    CourseCode cc= ds.getValue(CourseCode.class);
                    list.add(cc);
                }
                courseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void addCourse() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference= FirebaseDatabase.getInstance().getReference("Units");

                unitCode= spinner.getSelectedItem().toString();

                CourseCode ct=new CourseCode(unitCode, Email, FirebaseAuth.getInstance().getCurrentUser().getUid());


                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(unitCode).setValue(ct);

                Toast.makeText(CourseRegistration.this, "Units added", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public class connection extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            try  {


                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://192.168.100.146:8080/find")
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {


                        String Error=e.toString();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                Log.e("Tag",Error);

                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String Content= response.body().string();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println(Content);
                                Log.d("TAG",Content);
                                Toast.makeText(getApplicationContext(),Content,Toast.LENGTH_SHORT).show();
                                try {
                                    JSONArray array= new JSONArray(Content);

                                    for(int i=0; i< array.length();i++){

                                        JSONObject object= array.getJSONObject(i);



                                       code = object.getString("code");
                                       Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                                        // cm.setcode(code);

                                        userList.add(code);


                                        adapter= new ArrayAdapter(CourseRegistration.this, android.R.layout.simple_list_item_1, userList);

                                        spinner.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();




                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
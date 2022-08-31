package com.example.facultyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facultyportal.Adapter.UnitsAdapter;
import com.example.facultyportal.Model.CourseModel;
import com.example.facultyportal.Model.UnitsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UploadCourse extends AppCompatActivity {


       private HttpURLConnection connection;
       Spinner spinner;
       List<String> userList;
       ArrayAdapter<String> adapter;

       List<UnitsModel> list;
       UnitsAdapter unitsAdapter;

       ImageButton fab;

       RecyclerView recyclerView;

       String UnitCodes;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_course);

        spinner= findViewById(R.id.courses);

        fab=findViewById(R.id.add);




        addDatatoFirebase();

        new connection().execute();

        list= new ArrayList<>();
        unitsAdapter= new UnitsAdapter(this, list);



        populateDate();



        userList=new ArrayList<String>();

        handler=new Handler(Looper.getMainLooper());



        recyclerView= findViewById(R.id.selectedrecycler);

        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(unitsAdapter);



    }

    private void populateDate() {


        DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference("Units").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot ds: snapshot.getChildren()){

                    UnitsModel um=ds.getValue(UnitsModel.class);

                    list.add(um);

                }

                unitsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void addDatatoFirebase() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference;
                UnitCodes= spinner.getSelectedItem().toString();
                reference= FirebaseDatabase.getInstance().getReference("Units");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        final String Email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        UnitsModel um= new UnitsModel(UnitCodes, Email, FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(UnitCodes).setValue(um);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



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



                                        String code= object.getString("code");
                                       // cm.setcode(code);

                                        userList.add(code);


                                        adapter= new ArrayAdapter(UploadCourse.this, android.R.layout.simple_list_item_1, userList);

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



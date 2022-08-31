package com.example.facultyportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.facultyportal.Model.StudentModel;
import com.example.facultyportal.R;
import com.example.facultyportal.ResultsActivity;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<StudentModel> userList;

    Context context;

    public StudentAdapter(List<StudentModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        View v= LayoutInflater.from(context).inflate(R.layout.show_student,parent,false);





        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {

        StudentModel r= userList.get(position);
        holder.email.setText(r.getReg());

        SharedPreferences pref= context.getSharedPreferences("code", Context.MODE_PRIVATE);

        String CODE= pref.getString("code","");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ResultsActivity.class);
                intent.putExtra("Reg", r.getReg());

                intent.putExtra("Code",CODE);


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email=itemView.findViewById(R.id.reg);




        }


    }
}

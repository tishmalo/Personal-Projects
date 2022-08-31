package com.example.studentsportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.DownloadNotes;
import com.example.studentsportal.Model.CourseCode;
import com.example.studentsportal.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    Context context;
    List<CourseCode> userList;

    public CourseAdapter(Context context, List<CourseCode> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.course,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {


        CourseCode cc= userList.get(position);

        holder.Code.setText(cc.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, DownloadNotes.class);
                intent.putExtra("code", cc.getcode());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        TextView Code;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            Code=itemView.findViewById(R.id.code);
            
        }
    }
}

package com.example.facultyportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyportal.Model.CourseModel;
import com.example.facultyportal.Model.StudentModel;
import com.example.facultyportal.R;
import com.example.facultyportal.UpdateAttendance;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    List<CourseModel> userList;

    Context context;

    public AttendanceAdapter(List<CourseModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_unit_codes,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {


        CourseModel sm=userList.get(position);

        holder.Code.setText(sm.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, UpdateAttendance.class);
                intent.putExtra("code",sm.getcode());
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

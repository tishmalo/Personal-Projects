package com.example.facultyportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyportal.Model.StudentModel;
import com.example.facultyportal.R;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {

    Context context;
    List<StudentModel> userList;

    public UpdateAdapter(Context context, List<StudentModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UpdateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_student,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAdapter.ViewHolder holder, int position) {

        StudentModel sm=userList.get(position);

        holder.Reg.setText(sm.getReg());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Reg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Reg=itemView.findViewById(R.id.reg);

        }
    }
}

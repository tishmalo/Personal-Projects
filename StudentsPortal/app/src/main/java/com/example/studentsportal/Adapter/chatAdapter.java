package com.example.studentsportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.Message;
import com.example.studentsportal.Model.CourseCode;
import com.example.studentsportal.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {

    Context context;
    List<CourseCode> userList;

    public chatAdapter(Context context, List<CourseCode> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public chatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.course,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.ViewHolder holder, int position) {

        CourseCode cc= userList.get(position);
        holder.code.setText(cc.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh= context.getSharedPreferences("code",Context.MODE_PRIVATE);

                SharedPreferences.Editor edit= sh.edit();

                edit.putString("code",cc.getcode());

                edit.apply();

                Intent intent= new Intent(context, Message.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.code);
        }
    }
}

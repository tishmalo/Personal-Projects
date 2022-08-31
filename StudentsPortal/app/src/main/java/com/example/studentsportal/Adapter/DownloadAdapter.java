package com.example.studentsportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.Model.DownloadModel;
import com.example.studentsportal.R;
import com.example.studentsportal.StartDownload;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    Context context;
    List<DownloadModel> userList;

    public DownloadAdapter(Context context, List<DownloadModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_units,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {

        DownloadModel dm= userList.get(position);
        holder.name.setText(dm.getname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ph= context.getSharedPreferences("tish",Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=ph.edit();

                editor.putString("name", dm.getname());
                editor.putString("code",dm.getcode());
                editor.putString("document",dm.getdocument());

                editor.apply();

                Intent intent= new Intent(context, StartDownload.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name=itemView.findViewById(R.id.name);



        }
    }
}

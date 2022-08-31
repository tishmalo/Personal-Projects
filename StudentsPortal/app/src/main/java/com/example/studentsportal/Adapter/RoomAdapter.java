package com.example.studentsportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.HostelBooking;
import com.example.studentsportal.Model.RoomModel;
import com.example.studentsportal.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    Context context;
    List<RoomModel> userList;

    public RoomAdapter(Context context, List<RoomModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_hostels,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {

        RoomModel user= userList.get(position);

        holder.room.setText("ROOM" + " " + user.getroom());
        holder.space.setText("SPACE" +" " + user.getspace());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, HostelBooking.class);
                intent.putExtra("Room",user.getroom());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView room,space;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            room= itemView.findViewById(R.id.room);
            space=itemView.findViewById(R.id.email);
        }
    }
}

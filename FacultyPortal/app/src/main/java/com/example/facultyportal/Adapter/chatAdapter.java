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

import com.example.facultyportal.Message;
import com.example.facultyportal.Model.UnitsModel;
import com.example.facultyportal.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.viewHolder>{

    Context context;
    List<UnitsModel> userList;

    public chatAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public chatAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_unit_codes,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.viewHolder holder, int position) {


        UnitsModel um=userList.get(position);

        holder.Code.setText(um.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh= context.getSharedPreferences("ice",Context.MODE_PRIVATE);

                SharedPreferences.Editor edit=sh.edit();

                edit.putString("code",um.getcode());

                edit.apply();

                Intent intent=new Intent(context, Message.class);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView Code;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            Code=itemView.findViewById(R.id.code);

        }
    }
}

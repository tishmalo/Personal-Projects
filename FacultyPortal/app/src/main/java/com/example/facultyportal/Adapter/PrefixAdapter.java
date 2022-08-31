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

import com.example.facultyportal.Model.UnitsModel;
import com.example.facultyportal.R;
import com.example.facultyportal.UploadsNotes;

import java.util.List;

public class PrefixAdapter extends RecyclerView.Adapter<PrefixAdapter.ViewHolder> {


    Context context;
    List<UnitsModel> userList;

    public PrefixAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public PrefixAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_unit_codes,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PrefixAdapter.ViewHolder holder, int position) {

        UnitsModel um= userList.get(position);
        holder.code.setText(um.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences ph= context.getSharedPreferences("code", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=ph.edit();

                editor.putString("code",um.getcode());

                editor.apply();

                Intent intent= new Intent(context, UploadsNotes.class);
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

package com.example.facultyportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyportal.Model.UnitsModel;
import com.example.facultyportal.R;

import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.ViewHolder>{

    Context context;
    List<UnitsModel> userList;

    public UnitsAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UnitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_units, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitsAdapter.ViewHolder holder, int position) {


        UnitsModel um= userList.get(position);

        holder.Reg.setText(um.getcode());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Reg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Reg= itemView.findViewById(R.id.reg);


        }
    }
}

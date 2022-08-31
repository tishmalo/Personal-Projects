package com.example.facultyportal.Adapter;

import static android.content.SharedPreferences.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyportal.Model.ResultsModel;
import com.example.facultyportal.Model.UnitsModel;
import com.example.facultyportal.R;
import com.example.facultyportal.ResultsActivity;
import com.example.facultyportal.StudentList;

import java.util.List;

public class UnitCodesAdapter extends RecyclerView.Adapter<UnitCodesAdapter.ViewHolder> {
    Context context;
    List<UnitsModel> userList;

    public UnitCodesAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UnitCodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_unit_codes, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitCodesAdapter.ViewHolder holder, int position) {

        UnitsModel rm= userList.get(position);

        holder.Code.setText(rm.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh= context.getSharedPreferences("code",Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=sh.edit();

                editor.putString("code",rm.getcode());

                editor.commit();

                Intent intent= new Intent(context, StudentList.class);
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

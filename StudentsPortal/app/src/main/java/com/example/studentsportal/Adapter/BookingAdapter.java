package com.example.studentsportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.Model.BookingModel;
import com.example.studentsportal.R;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {


    Context context;
    List<BookingModel> userList;

    public BookingAdapter(Context context, List<BookingModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.book_hostels, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {


        BookingModel bm= userList.get(position);

        holder.reg.setText(bm.getreg());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reg=itemView.findViewById(R.id.reg);

        }
    }
}

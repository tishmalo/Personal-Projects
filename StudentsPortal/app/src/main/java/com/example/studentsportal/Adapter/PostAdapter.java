package com.example.studentsportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentsportal.Model.PostModel;
import com.example.studentsportal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<PostModel> userList;

    public PostAdapter(Context context, List<PostModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    /**
     *
     * TODO Ensure that the button is disabled when one books a hostel
     */

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_posts,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {

        PostModel pm=userList.get(position);

        holder.description.setText(pm.getdescription());
        holder.title.setText(pm.gettitle());

        Glide.with(context).load(pm.getphoto()).into(holder.photo);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView photo;
        TextView title,description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photo=itemView.findViewById(R.id.photo);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);

        }
    }
}

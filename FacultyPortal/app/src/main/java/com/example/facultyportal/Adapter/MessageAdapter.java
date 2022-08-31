package com.example.facultyportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyportal.Model.MessageModel;
import com.example.facultyportal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    List<MessageModel> userList;
    private final static int SHOW_SENDER=0;
    private final static int SHOW_RECEIVER=1;

    public MessageAdapter(Context context, List<MessageModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SHOW_SENDER){

            View a= LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(a);
        }else{

            View b=LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(b);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        MessageModel mm=userList.get(position);

        holder.message.setText(mm.getmessage());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message=itemView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(userList.get(position).getsender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

            return SHOW_SENDER;
        }else{
            return SHOW_RECEIVER;
        }
    }
}

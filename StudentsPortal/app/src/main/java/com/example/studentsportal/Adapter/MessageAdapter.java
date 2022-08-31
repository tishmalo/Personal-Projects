package com.example.studentsportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsportal.Model.ChatModel;
import com.example.studentsportal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    List<ChatModel> userList;

    private static int CHAT_SENDER=0;
    private static int CHAT_RECEIVER=1;

    public MessageAdapter(Context context, List<ChatModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==CHAT_SENDER) {

            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(v);

        }else{

            View b=LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(b);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        ChatModel cm=userList.get(position);
        holder.Message.setText(cm.getmessage());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Message=itemView.findViewById(R.id.show_message);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if(userList.get(position).getsender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

            return CHAT_SENDER;
        }else{
            return CHAT_RECEIVER;
        }



    }
}

package com.release.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageController extends RecyclerView.Adapter {
    private List<Message> messageList;
    private RecyclerView recyclerView;

    private static final int TYPE_INCOMING = 0;
    private static final int TYPE_OUTGOING = 1;
    private static final int MAX_MESSAGES = 1000;

    private int messageTextId;
    private int messageTimeId;
    private int userNameId;
    private int outgoingLayout;
    private int incomingLayout;


    public static class Message {
        String text;
        Date date;
        String userName;
        Boolean isOutgoing;

        public Message(String text, String userName, Boolean isOutgoing) {
            this.text = text;
            this.userName = userName;
            this.date = new Date();
            this.isOutgoing = isOutgoing;
        }
    }
    public class MessageView extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageTime;
        TextView userName;


        MessageView(@NonNull View itemView, int messageTextId, int messageTimeId, int userNameId) {
            super(itemView);
            messageText = itemView.findViewById(messageTextId);
            messageTime = itemView.findViewById(messageTimeId);
            userName = itemView.findViewById(userNameId);
        }

        void bind(Message message) {
            DateFormat fmt = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
            messageText.setText(message.text);
            messageTime.setText(fmt.format(message.date));
            userName.setText(message.userName);
        }
    }


    public MessageController setMessageTextId(int messageTextId) {
        this.messageTextId = messageTextId;
        return this;
    }

    public MessageController setMessageTimeId(int messageTimeId) {
        this.messageTimeId = messageTimeId;
        return this;
    }

    public MessageController setUserNameId(int userNameId) {
        this.userNameId = userNameId;
        return this;
    }

    public MessageController setOutgoingLayout(int outgoingLayout) {
        this.outgoingLayout = outgoingLayout;
        return this;
    }

    public MessageController setIncomingLayout(int incomingLayout) {
        this.incomingLayout = incomingLayout;
        return this;
    }

    public MessageController() {
        this.messageList = new ArrayList<>();

    }
    public void appendTo(RecyclerView recyclerView, Context parent) {
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        this.recyclerView.setAdapter(this);
    }
    public void addMessage(Message m) {
        messageList.add(m);
        if (messageList.size() > MAX_MESSAGES) {
            messageList = messageList.subList(messageList.size() - MAX_MESSAGES, messageList.size());
        }
        this.notifyDataSetChanged();
        this.recyclerView.scrollToPosition(messageList.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.isOutgoing ? TYPE_OUTGOING : TYPE_INCOMING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int view_type) {
        View view;
        if (view_type == TYPE_OUTGOING) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(outgoingLayout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(incomingLayout, viewGroup, false);
        }
        return new MessageView(view, messageTextId, messageTimeId, userNameId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message message = messageList.get(i);
        ((MessageView) viewHolder).bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

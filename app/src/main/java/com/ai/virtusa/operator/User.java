package com.ai.virtusa.operator;

import android.content.Context;

import com.ai.virtusa.operator.UI.Message;
import com.ai.virtusa.operator.UI.MessageAdapter;

import java.util.ArrayList;

/**
 * Created by ahashim on 2/17/2016.
 */
public class User {
    String Topic;
    String name;
    public ArrayList<Message> messages;
    public MessageAdapter adapter;
    public User(String Topic, String name,Context con){
        this.Topic = Topic;
        this.name = name;
        messages= new ArrayList<Message>();
        adapter = new MessageAdapter(con,this);
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void addMessage(Message m){
        messages.add(m);
    }



}

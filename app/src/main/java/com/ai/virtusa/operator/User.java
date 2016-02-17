package com.ai.virtusa.operator;

import com.ai.virtusa.operator.UI.Message;

import java.util.ArrayList;

/**
 * Created by ahashim on 2/17/2016.
 */
public class User {
    String Topic;
    String name;
    public ArrayList<Message> messages;

    public User(String Topic, String name){
        this.Topic = Topic;
        this.name = name;
        messages= new ArrayList<Message>();
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void addMessage(Message m){
        messages.add(m);
    }



}

package com.ai.virtusa.operator;

import com.ai.virtusa.operator.UI.Message;
import com.ai.virtusa.operator.UI.MessageAdapter;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ahashim on 2/11/2016.
 */
public class Utility{
        public static String topic = "chat/aqeel";
        public static String url = "172.22.228.25";
        public static String urlFromat = "tcp://" + url + ":1883";

        public static MessageAdapter adapter;
        public static ArrayList<String> topics = new ArrayList<String>();
        public static Queue<MqttMessage> displayMessage = new LinkedList<MqttMessage>();
}

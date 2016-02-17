package com.ai.virtusa.operator;

import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * Created by ahashim on 2/17/2016.
 */
public class ActionListener implements IMqttActionListener {

    Context con;

    public void setCon(Context con) {
        this.con = con;
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        new Toast(con).makeText(con,"Connected successfully",Toast.LENGTH_LONG);
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        new Toast(con).makeText(con,"Connection Failed",Toast.LENGTH_LONG);
    }
}

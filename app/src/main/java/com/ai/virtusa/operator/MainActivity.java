package com.ai.virtusa.operator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ai.virtusa.operator.UI.MessageAdapter;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity implements MqttCallback{

    public static MqttClient client;

    private ListView mDrawerList;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Toast(getApplicationContext()).makeText(getApplicationContext(), "Proceeding with connection", Toast.LENGTH_LONG).show();
        connect();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        Utility.adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Utility.topics);
        mDrawerList.setAdapter(Utility.adapter2);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.d("", "onDrawerClosed: ");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d("", "onDrawerOpened: ");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //getActionBar().setHomeButtonEnabled(true);
    }

    public boolean connect(){
        MemoryPersistence persistance = new MemoryPersistence();

        try {
            client = new MqttClient("tcp://172.22.228.25:1883","AQEELOP",persistance);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        new Toast(getApplicationContext()).makeText(getApplicationContext(),"Connecting",Toast.LENGTH_LONG).show();
        try {
            client.setCallback(this);
            client.connect();
            client.subscribe("chat/+");
            new Toast(getApplicationContext()).makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client.isConnected();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(final String topic, MqttMessage mqttMessage) throws Exception {
        final String result[] = JSONFormatController.readJSONmessage(mqttMessage.toString());
        if(!Utility.topics.contains(result[1])) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utility.topics.add(result[1]);
                    Utility.adapter2.notifyDataSetChanged();
                    mDrawerList.setSelection(Utility.topics.size() - 1);
                    for(int i=0;i<Utility.topics.size();i++)
                        System.out.println(Utility.topics.get(i));
                }
            });

        }

        if(!Utility.topicUserList.containsKey(topic))
            Utility.topicUserList.put(topic, new User(topic,result[1],getApplicationContext()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        new Toast(getApplicationContext()).makeText(getApplicationContext(),position,Toast.LENGTH_LONG);

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        CharSequence mTitle = title;
        Log.d("", "setTitle: ");
    }

}

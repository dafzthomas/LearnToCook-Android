package com.apps.dafz.learntocook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.dafz.learntocook.models.Tip;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class KitchenManagement extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(2).setChecked(true);

        getKitchenManagement();

    }

    public void setAdaptor(JSONArray response) {
        ArrayList<Tip> arrayOfManagement = new ArrayList<Tip>();

        for (int i = 0; i < response.length(); i++) {

            try {
                Tip tempTip = new Tip(response.getJSONObject(i));
                arrayOfManagement.add(tempTip);

            } catch (JSONException e) {

            }
        }

        // Create the adapter to convert the array to views
        KitchenManagement.ManagementAdaptor adapter = new KitchenManagement.ManagementAdaptor(this, arrayOfManagement);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvManagement);
        listView.setAdapter(adapter);
    }

    public void getKitchenManagement() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.myjson.com/bins/ve92p", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"
                setAdaptor(response);

                Log.d("JSON", response.toString());

                ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                if (retryNo < 5) {
                    getKitchenManagement();
                }
            }
        });
    }

    private class ManagementAdaptor extends ArrayAdapter<Tip> {
        private ManagementAdaptor(Context context, ArrayList<Tip> tips) {
            super(context, 0, tips);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Tip tip = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_tip, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.Title);
            TextView text = (TextView) convertView.findViewById(R.id.Text);
            ImageView image = (ImageView) convertView.findViewById(R.id.Image);

            title.setText(tip.Title);
            text.setText(tip.Text);
            Glide.with(getContext()).load(tip.Image).into(image);

            // Return the completed view to render on screen
            return convertView;
        }
    }


}

package com.apps.dafz.learntocook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.dafz.learntocook.models.Recipe;
import com.apps.dafz.learntocook.models.Tip;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EssentialTips extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(4).setChecked(true);

        getTips();
    }

    public void setAdaptor(JSONArray response) {
        ArrayList<Tip> arrayOfTips = new ArrayList<Tip>();

        for (int i = 0; i < response.length(); i++) {

            try {
                Tip tempTip = new Tip(response.getJSONObject(i));
                arrayOfTips.add(tempTip);

            } catch (JSONException e) {

            }
        }

        // Create the adapter to convert the array to views
        EssentialTips.TipsAdaptor adapter = new EssentialTips.TipsAdaptor(this, arrayOfTips);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvTips);
        listView.setAdapter(adapter);
    }

    public void getTips() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.myjson.com/bins/175e0j", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"
                setAdaptor(response);

                ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                if (retryNo < 5) {
                    getTips();
                }
            }
        });
    }

    private class TipsAdaptor extends ArrayAdapter<Tip> {
        private TipsAdaptor(Context context, ArrayList<Tip> tips) {
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

package com.apps.dafz.learntocook;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.apps.dafz.learntocook.models.Tip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class KitchenEquipment extends MainActivity implements MediaPlayer.OnPreparedListener {

    private VideoView video;
    private MediaController mc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_equipment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(1).setChecked(true);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.kitchen_equipment;

        video = (VideoView) findViewById(R.id.videoView);
        mc = new MediaController(this);
        video.setVideoPath(path);
        video.setOnPreparedListener(this);
        video.setMediaController(mc);
        video.requestFocus();

        getKitchenEquipment();
    }

    @Override
    public void onStop()
    {
        // really important - does most of the actual work
        super.onStop();
        // if the video is playing, stops it
        stopVideo();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        // starts the video playing
        video.start();
        // attaches the controller, now we know how
        // big the display area needs to be
        mc.setAnchorView(video);
        // briefly shows the controls UI
        mc.show();
    }

    private void stopVideo()
    {
        if (video.isPlaying())
        {
            // stops the video
            video.stopPlayback();

            // Sets the player back to an uninitialised state.
            // Stops the widget from loading more of the old video
            // and from generating events for the old video stream.
            video.setVideoURI(null);
        }
    }


    public void setAdaptor(JSONArray response) {
        ArrayList<Tip> arrayOfEquipment = new ArrayList<Tip>();

        for (int i = 0; i < response.length(); i++) {

            try {
                Tip tempTip = new Tip(response.getJSONObject(i));
                arrayOfEquipment.add(tempTip);

            } catch (JSONException e) {

            }
        }

        // Create the adapter to convert the array to views
        KitchenEquipment.TipsAdaptor adapter = new KitchenEquipment.TipsAdaptor(this, arrayOfEquipment);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvEquipment);
        listView.setAdapter(adapter);
    }

    public void getKitchenEquipment() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.myjson.com/bins/126ldd", new JsonHttpResponseHandler() {

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
                    getKitchenEquipment();
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

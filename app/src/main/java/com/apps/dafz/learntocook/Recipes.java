package com.apps.dafz.learntocook;

import android.content.Context;
import android.media.Image;
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

import com.apps.dafz.learntocook.models.Recipe;
import com.bumptech.glide.Glide;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Recipes extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(5).setChecked(true);

        getRecipes();
    }

    public void setAdaptor(JSONArray response) {
        ArrayList<Recipe> arrayOfRecipes = new ArrayList<Recipe>();

        for (int i = 0; i < response.length(); i++) {

            try {
                Recipe tempRecipe = new Recipe(response.getJSONObject(i));
                arrayOfRecipes.add(tempRecipe);

            } catch (JSONException e) {

            }
        }

        // Create the adapter to convert the array to views
        RecipesAdaptor adapter = new RecipesAdaptor(this, arrayOfRecipes);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvRecipes);
        listView.setAdapter(adapter);
    }

    public void getRecipes() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.myjson.com/bins/n75j7", new JsonHttpResponseHandler() {

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
                    getRecipes();
                }
            }
        });
    }

    private class RecipesAdaptor extends ArrayAdapter<Recipe> {
        private RecipesAdaptor(Context context, ArrayList<Recipe> recipes) {
            super(context, 0, recipes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Recipe recipe = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_recipe, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.Title);
            ImageView image = (ImageView) convertView.findViewById(R.id.Image);


            // Populate the data into the template view using the data object
            title.setText(recipe.Title);
            Glide.with(getContext()).load(recipe.Image).into(image);

            // Return the completed view to render on screen
            return convertView;
        }
    }

}

package com.apps.dafz.learntocook;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Recipes extends MainActivity {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private DatabaseReference mRecipesReference;

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

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mRecipesReference = FirebaseDatabase.getInstance().getReference()
                .child("Recipes");



        // Read from the database
        mRecipesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Recipe> array = new ArrayList<Recipe>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Recipe recipe = postSnapshot.getValue(Recipe.class);

                    array.add(recipe);
                }

                setAdaptor(array);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Failed to read value.", error.toException().toString());
            }
        });
    }

    public void setAdaptor(ArrayList<Recipe> arrayOfRecipes) {
        // Create the adapter to convert the array to views
        Recipes.RecipesAdaptor adapter = new Recipes.RecipesAdaptor(this, arrayOfRecipes);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvRecipes);
        listView.setAdapter(adapter);

        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
    }

    private class RecipesAdaptor extends ArrayAdapter<Recipe> {
        private RecipesAdaptor(Context context, ArrayList<Recipe> recipes) {
            super(context, 0, recipes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Recipe recipe = getItem(position);

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

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("STRING CLICK: ", view.toString());

                    Intent intent = new Intent(Recipes.this, SingleRecipeFull.class);
                    intent.putExtra("Image", recipe.Image);
                    intent.putExtra("Title", recipe.Title);
                    intent.putExtra("Text", recipe.Text);
                    intent.putExtra("Serves", recipe.Serves);
                    intent.putExtra("PrepTime", recipe.PrepTime);
                    intent.putExtra("CookingTime", recipe.CookingTime);
                    intent.putExtra("Ingredients", recipe.Ingredients);
                    intent.putExtra("Method", recipe.Method);

                    startActivity(intent);
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }

}

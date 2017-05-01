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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.dafz.learntocook.models.Recipe;
import com.apps.dafz.learntocook.models.Term;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CookeryTerms extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookery_terms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(3).setChecked(true);

        getTerms();
    }

    public void setAdaptor(JSONArray response) {
        ArrayList<Term> arrayOfTerms = new ArrayList<Term>();

        for (int i = 0; i < response.length(); i++) {

            try {
                Term tempTerm = new Term(response.getJSONObject(i));
                arrayOfTerms.add(tempTerm);

            } catch (JSONException e) {

            }
        }

        // Create the adapter to convert the array to views
        CookeryTerms.TermsAdaptor adapter = new CookeryTerms.TermsAdaptor(this, arrayOfTerms);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvTerms);
        listView.setAdapter(adapter);
    }

    public void getTerms() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.myjson.com/bins/za943", new JsonHttpResponseHandler() {

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
                    getTerms();
                }

                Log.d("JSON: ", "Problem");
            }
        });
    }

    private class TermsAdaptor extends ArrayAdapter<Term> {
        private TermsAdaptor(Context context, ArrayList<Term> terms) {
            super(context, 0, terms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Term term = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_term, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.Term);
            TextView text = (TextView) convertView.findViewById(R.id.Explanation);
            // Populate the data into the template view using the data object

            title.setText(term.Term);
            text.setText(term.Explanation);

            // Return the completed view to render on screen
            return convertView;
        }
    }

}

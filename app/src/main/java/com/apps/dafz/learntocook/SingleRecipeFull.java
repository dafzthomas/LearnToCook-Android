package com.apps.dafz.learntocook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.apps.dafz.learntocook.models.Recipe;

public class SingleRecipeFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String Title = (String) getIntent().getExtras().getSerializable("Title");

        TextView txtView = (TextView) findViewById(R.id.Title);

        txtView.setText(Title);
    }

}

package com.apps.dafz.learntocook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.dafz.learntocook.models.Recipe;
import com.bumptech.glide.Glide;

public class SingleRecipeFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String Image = (String) getIntent().getExtras().getSerializable("Image");
        String Title = (String) getIntent().getExtras().getSerializable("Title");
        String Text = (String) getIntent().getExtras().getSerializable("Text");
        String Serves = (String) getIntent().getExtras().getSerializable("Serves");
        String PrepTime = (String) getIntent().getExtras().getSerializable("PrepTime");
        String CookingTime = (String) getIntent().getExtras().getSerializable("CookingTime");
        String Ingredients = (String) getIntent().getExtras().getSerializable("Ingredients");
        String Method = (String) getIntent().getExtras().getSerializable("Method");

        setTitle(Title);

        ImageView imgView = (ImageView) findViewById(R.id.Image);
        Glide.with(this).load(Image).into(imgView);

        TextView textView = (TextView) findViewById(R.id.Text);
        textView.setText(Text);

        TextView servesView = (TextView) findViewById(R.id.Serves);
        servesView.setText(Serves);

        TextView prepTimeView = (TextView) findViewById(R.id.PrepTime);
        prepTimeView.setText(PrepTime);

        TextView cookingTimeView = (TextView) findViewById(R.id.CookingTime);
        cookingTimeView.setText(CookingTime);

        TextView ingredientsView = (TextView) findViewById(R.id.Ingredients);
        ingredientsView.setText(Ingredients);

        TextView methodView = (TextView) findViewById(R.id.Method);
        methodView.setText(Method);

    }

}

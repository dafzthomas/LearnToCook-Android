package com.apps.dafz.learntocook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.apps.dafz.learntocook.helpers.Contract;
import com.apps.dafz.learntocook.helpers.DBHelper;
import com.apps.dafz.learntocook.models.Recipe;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyRecipes extends MainActivity implements AdapterView.OnItemClickListener {

    private EditText mName;
    private EditText mText;

    private ListView mList;
    private DBHelper mHelper;

    private SQLiteDatabase mDB;

    private Cursor mCursor;

    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(6).setChecked(true);

        // grabs the edit text fields in the UI
        mName = (EditText) findViewById(R.id.edit_name);
        mText = (EditText) findViewById(R.id.edit_height);

        // sets up the list view with an item-click event handler
        mList = (ListView) findViewById(R.id.lvMyRecipes);
        mList.setOnItemClickListener(this);

        // initialises the DB if it doesn't exist
        mHelper = new DBHelper(this);


    }

    @Override
    public void onResume()
    {
        super.onResume();

        // gets a connection to the DB
        mDB = mHelper.getWritableDatabase();

        // cals the convenience method getAll() which grabs the whole table
        mCursor = mHelper.getAll(mDB);

        // connects the list to the data - these are the two fields to view
        String[] headers = new String[]{Contract.Example.COLNAME_NAME,
                Contract.Example.COLNAME_TEXT};

        // creates an adapter from a really useful stock Android
        // built-in layout. Note the use of the built-in
        // "android.R.id...", not this app's "R.id..."
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.single_recipe,
                mCursor, headers, new int[]{R.id.Title,
                R.id.Text}, 0);

        // links the adapter to the list viewer
        mList.setAdapter(mAdapter);
    }

    public void onClick(View v)
    {
        // gets the name, height data from the UI
        String name = mName.getText().toString();
        String text = mText.getText().toString();

        mName.setText("");
        mText.setText("");

        // adds the record to the DB
        ContentValues cv = new ContentValues(2);
        cv.put(Contract.Example.COLNAME_NAME, name);
        cv.put(Contract.Example.COLNAME_TEXT, text);
        mDB.insert(Contract.Example.TABLE_NAME, null, cv);

        // the list has changed, so we query the DB and
        // give the adapter an updated Cursor of data
        mCursor = mHelper.getAll(mDB);
        mAdapter.changeCursor(mCursor);

        // forces the updated list to refresh the display
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mDB.close();
        mCursor.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
    {
        // moves to the position in the DB given by the listview click
        mCursor.moveToPosition(position);

        // gets the name, height data from the UI
        final String name = mCursor.getString(mCursor.getColumnIndex("name"));
        final String text = mCursor.getString(mCursor.getColumnIndex("text"));;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("STRING CLICK: ", view.toString());

                Intent intent = new Intent(MyRecipes.this, SingleRecipeFull.class);
                intent.putExtra("Title", name);
                intent.putExtra("Text", text);

                startActivity(intent);
            }
        });
    }



}

package com.apps.dafz.learntocook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class UnitConverter extends MainActivity {

    private EditText mFromField;
    private TextView mToField;

    private Spinner mDropDown1;
    private Spinner mDropDown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);
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

        mFromField = (EditText) findViewById(R.id.unit1);
        mToField = (TextView) findViewById(R.id.unit2);

        mDropDown1 = (Spinner) findViewById(R.id.dropDownList1);
        mDropDown2 = (Spinner) findViewById(R.id.dropDownList2);

        String[] items = new String[]{"g", "oz"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mDropDown1.setAdapter(adapter);
        mDropDown2.setAdapter(adapter);

        mFromField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String selectedUnit = mDropDown1.getSelectedItem().toString();
                String selectedUnit2 = mDropDown2.getSelectedItem().toString();

                if (!Objects.equals(mFromField.getText().toString(), "")) {
                    double newValue = Double.parseDouble(mFromField.getText().toString());


                    if (!Objects.equals(selectedUnit, selectedUnit2)) {
                        if (selectedUnit == "g" && selectedUnit2 == "oz") {
                            mToField.setText(String.valueOf(gramsToOunces(newValue)));
                        }

                        if (selectedUnit == "oz" && selectedUnit2 == "g") {
                            mToField.setText(String.valueOf(ouncesToGrams(newValue)));
                        }
                    }
                }
            }
        });
    }


    public double gramsToOunces(double grams) {
        return grams / 28.35;
    }

    public double ouncesToGrams(double ounces) {
        return ounces * 28.35;
    }

}

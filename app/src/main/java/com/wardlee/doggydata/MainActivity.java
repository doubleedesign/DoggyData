package com.wardlee.doggydata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    // Tag for debugging
    private static final String TAG = "MainActivity";

    // Declare variables for the interface fields, for later use
    private Spinner speciesSpinner;
    private LinearLayout activityLayout;

    // onCreate method, let's get this show on the road
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a connection check object
        ConnectionCheck connectionCheck = new ConnectionCheck(this);

        // Check for internet connection before proceeding
        if(!connectionCheck.isInternetAvailable(this)) {
            Log.e(TAG, "Problem with internet connection");
        }

        // Set up an adapter to grab the species spinner option values from the XML file
        // @see res/values/strings.xml
        ArrayAdapter<CharSequence> speciesListAdapter = ArrayAdapter.createFromResource(this, R.array.species, android.R.layout.simple_spinner_item);
        speciesListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Find the species spinner and set it to use the unit list adapter
        speciesSpinner = findViewById(R.id.spinner_species);
        speciesSpinner.setAdapter(speciesListAdapter);

        // Assign the activityLayout element
        activityLayout = findViewById(R.id.layout_mainActivity);
    }


    // Method to run the search
    // TODO: Search function. For now this just loads all breeds, as a development step.
    public void runSearch(View view) {
        // Get the species selection from the spinner
        String species = (String) speciesSpinner.getSelectedItem();

        // Create the loader
        BreedlistFragmentLoader loader = new BreedlistFragmentLoader(this, species);

        // Load the fragment to show the breed list
        FragmentManager BreedlistManager = this.getSupportFragmentManager();
        loader.loadFragment(BreedlistManager);
    }
}

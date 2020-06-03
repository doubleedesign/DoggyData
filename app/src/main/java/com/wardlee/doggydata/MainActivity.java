package com.wardlee.doggydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    // onCreate method, let's get this show on the road
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up an adapter to grab the species spinner option values from the XML file
        // @see res/values/strings.xml
        ArrayAdapter<CharSequence> speciesListAdapter = ArrayAdapter.createFromResource(this, R.array.species, android.R.layout.simple_spinner_item);
        speciesListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Find the species spinner and set it to use the unit list adapter
        // Declare variables for the interface fields
        Spinner speciesSpinner = findViewById(R.id.spinner_species);
        speciesSpinner.setAdapter(speciesListAdapter);
    }


    // Method to run the search
    public void runSearch() {
        // TODO: Search function
    }
}

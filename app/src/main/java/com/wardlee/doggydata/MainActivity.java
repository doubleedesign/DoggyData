package com.wardlee.doggydata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    // Tag for debugging
    private static final String TAG = "MainActivity";

    // Declare variables for the interface fields, for later use
    private Spinner speciesSpinner;
    private LinearLayout activityLayout;
    private EditText weightInput;
    private EditText heightInput;

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

        // Assign the activity layout
        activityLayout = findViewById(R.id.layout_mainActivity);

        // Assign the input fields
        weightInput = findViewById(R.id.editText_weight);
        heightInput = findViewById(R.id.editText_height);
    }


    // Method to run the search
    // TODO: Search function. For now this just loads all breeds, as a development step.
    public void runSearch(View view) {

        // Close the keyboard for a smoother user experience
        // @ref https://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
        /*
        InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } */

        // Get the species selection from the spinner
        String species = (String) speciesSpinner.getSelectedItem();

        // Get the search criteria and put them in a hashmap
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put("weight", weightInput.getText().toString());
        searchCriteria.put("height", heightInput.getText().toString());

        // Create the loader
        BreedlistFragmentLoader loader = new BreedlistFragmentLoader(this, species, searchCriteria);

        // Load the fragment to show the breed list
        FragmentManager BreedlistManager = this.getSupportFragmentManager();
        loader.loadFragment(BreedlistManager);
    }
}

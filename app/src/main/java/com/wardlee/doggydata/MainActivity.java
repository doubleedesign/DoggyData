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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.vision.text.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private LinearLayout heightWrapper;
    private CheckBox checkboxLoyal;
    private CheckBox checkboxIndependent;
    private CheckBox checkboxPlayful;
    private CheckBox checkboxActive;
    private CheckBox checkboxIntelligent;
    private CheckBox checkboxAffectionate;

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

        // Assign layout item wrappers (used for showing/hiding layout elements)
        heightWrapper = findViewById(R.id.layout_heightwrapper);

        // Assign the input fields (used for getting data)
        weightInput = findViewById(R.id.editText_weight);
        heightInput = findViewById(R.id.editText_height);
        checkboxLoyal = findViewById(R.id.temperament_loyal);
        checkboxIndependent = findViewById(R.id.temperament_independent);
        checkboxPlayful = findViewById(R.id.temperament_playful);
        checkboxActive = findViewById(R.id.temperament_active);
        checkboxIntelligent = findViewById(R.id.temperament_intelligent);
        checkboxAffectionate = findViewById(R.id.temperament_affectionate);

        // Add a listener for spinner change to show/hide layout elements according to breed
        speciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedSpecies = parent.getItemAtPosition(position).toString();
                if(selectedSpecies.equals("Doggy")) {
                    heightWrapper.setVisibility(View.VISIBLE);
                }
                else if(selectedSpecies.equals("Kitty")) {
                    heightWrapper.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    // Method to run the search
    public void runSearch(View view) {

        // Get the species selection from the spinner
        String species = (String) speciesSpinner.getSelectedItem();

        // Hashmap to store the search criteria
        Map<String, Object> searchCriteria = new HashMap<>();

        // Add height and weight to the search criteria map
        searchCriteria.put("weight", weightInput.getText().toString());
        searchCriteria.put("height", heightInput.getText().toString());

        // Build an ArrayList of checked temperament terms, add it to the search criteria
        ArrayList<String> temperament = new ArrayList<String>();
        if(checkboxLoyal.isChecked()) {
            temperament.add("Loyal");
        }
        if(checkboxIndependent.isChecked()) {
            temperament.add("Independent");
        }
        if(checkboxPlayful.isChecked()) {
            temperament.add("Playful");
        }
        if(checkboxActive.isChecked()) {
            temperament.add("Active");
        }
        if(checkboxIntelligent.isChecked()) {
            temperament.add("Intelligent");
        }
        if(checkboxAffectionate.isChecked()) {
            temperament.add("Affectionate");
        }
        searchCriteria.put("temperament", temperament);

        // Create the loader
        BreedlistFragmentLoader loader = new BreedlistFragmentLoader(this, species, searchCriteria);

        // Load the fragment to show the breed list
        FragmentManager BreedlistManager = this.getSupportFragmentManager();
        loader.loadFragment(BreedlistManager);
    }
}

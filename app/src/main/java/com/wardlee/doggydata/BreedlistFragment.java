package com.wardlee.doggydata;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class BreedlistFragment extends Fragment {
    // Tag for debugging
    private static final String TAG = "BreedListFragment";

    // List to store pets from the API
    private ArrayList<Pet> petList = new ArrayList<Pet>();

    // Variables to be passed in from the activity or fragment calling this
    private Context thisContext;
    private String SelectedSpecies;
    private Map SearchCriteria;

    // Other variable initialisations
    private RecyclerView thisRecyclerView;
    private LinearLayout NoResultsMessage;
    private FragmentManager BreedFragmentManager;
    private FragmentActivity thisFragmentActivity;


    /**
     * Constructor
     */
    public BreedlistFragment(Context context, String species, Map search) {
        thisContext = context;
        SelectedSpecies = species;
        SearchCriteria = search;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Find the fragment placeholder and bring it to the front
        FrameLayout thisFragment = getActivity().findViewById(R.id.fragment_listPlaceholder);
        thisFragment.bringToFront();

        // Define the layout file for the fragment
        return inflater.inflate(R.layout.fragment_breedlist, parent, false);
    }

    @Override
    public void onAttach(Activity activity) {
        thisFragmentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Find the no results message (initially hidden)
        NoResultsMessage = view.findViewById(R.id.layout_noResultsMessage);

        // Find the RecyclerView
        thisRecyclerView = view.findViewById(R.id.rv_breeds);

        // Initially hide it (to show the loading screen)
        view.findViewById(R.id.rv_breeds).setVisibility(View.GONE);

        // Load breeds into the RecyclerView and show it after a short delay
        loadPets(thisRecyclerView, SelectedSpecies);

        new Handler().postDelayed(() -> {
            view.findViewById(R.id.rv_breeds).setVisibility(View.VISIBLE);
        }, 1000);

        // Hide the loading screen (after a short delay because I want to show it for the purposes of this assignment)
        new Handler().postDelayed(() -> {
            view.findViewById(R.id.layout_loadingPanel).setVisibility(View.GONE);
        }, 2000);
    }


    /**
     * Method to load breeds from TheDogAPI.com or TheCatAPI.com using Volley
     * Based on tutorial:
     * @author Belal Khan
     * @link https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
     */
    protected void loadPets(RecyclerView thisRecyclerView, String species) {

        // Set API request URL according to the selected species
        String JSON_URL = "";
        if(species.equals("Doggy")) {
            JSON_URL = "https://api.thedogapi.com/v1/breeds?api_key=ea7d3e22-d28a-427f-a71b-5d29db2bc67d";
        }
        else if(species.equals("Kitty")) {
            JSON_URL = "https://api.thecatapi.com/v1/breeds?api_key=ea7d3e22-d28a-427f-a71b-5d29db2bc67d";
        }
        
        if(!JSON_URL.isEmpty()) {

            // Create the request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                        // Get the JSON from the response
                        JSONArray pets = new JSONArray(response);

                        // Set how many to get (all of them)
                        int qty = pets.length();

                        // Loop through the array
                        for (int i = 0; i < qty; i++) {

                            // Initialise fields with default values
                            String breedName = "";
                            String origin = "";
                            String id = "";
                            String[] weightValues;
                            int MinWeight = 0;
                            int MaxWeight = 0;
                            String[] lifespanValues;
                            int LifeSpanMin = 0;
                            int LifeSpanMax = 0;
                            ArrayList<String> TemperamentTerms = new ArrayList<String>();

                            // Get the JSON object of this dog/cat
                            JSONObject petObject = pets.getJSONObject(i);

                            // Get the straightforward data from the object
                            if (petObject.has("name")) {
                                breedName = petObject.getString("name");
                            }
                            if (petObject.has("origin")) {
                                origin = petObject.getString("origin");
                            }
                            if (petObject.has("id")) {
                                id = petObject.getString("id");
                            }

                            // Get the weight from the object,
                            // explode the string into an array
                            // and use that to set the min and max weight variables
                            // Also, account for only one value being set by assigning that value to both
                            // and account for values being "NaN" in the dataset
                            if (petObject.has("weight")) {
                                JSONObject weightsObject = petObject.getJSONObject("weight");
                                String weightsString = weightsObject.getString("metric");
                                weightsString = weightsString.replace("–", "-"); // account for different dashes
                                weightValues = weightsString.split("-");
                                String value1 = weightValues[0].trim();

                                if(!value1.equals("NaN")) {
                                    MinWeight = Integer.parseInt(weightValues[0].trim());
                                }
                                if (weightValues.length > 1) {
                                    String value2 = weightValues[1].trim();
                                    if(!value2.equals("NaN")) {
                                        MaxWeight = Integer.parseInt(weightValues[1].trim());
                                    }
                                    else {
                                        MaxWeight = MinWeight;
                                    }
                                } else {
                                    MaxWeight = MinWeight;
                                }
                            }

                            // Get the lifespan from the object,
                            // remove the " years" suffix,
                            // explode the remaining "x - y" string into an array
                            // and use that to set the min and max lifespan variables
                            if (petObject.has("life_span")) {
                                String lifeSpanString = petObject.getString("life_span");
                                lifeSpanString = lifeSpanString.replace(" Years years","").trim();
                                lifeSpanString = lifeSpanString.replace(" years","").trim();
                                lifeSpanString = lifeSpanString.replace("–", "-"); // account for different dashes
                                lifespanValues = lifeSpanString.split("-");
                                String value1 = lifespanValues[0].trim();

                                if(!value1.equals("NaN")) {
                                    LifeSpanMin = Integer.parseInt(lifespanValues[0].trim());
                                }
                                if (lifespanValues.length > 1) {
                                    String value2 = lifespanValues[1].trim();
                                    if(!value2.equals("NaN")) {
                                        LifeSpanMax = Integer.parseInt(lifespanValues[1].trim());
                                    }
                                    else {
                                        LifeSpanMax = LifeSpanMin;
                                    }
                                } else {
                                    LifeSpanMax = LifeSpanMin;
                                }
                            }

                            // Get the temperament terms and explode the string into an ArrayList
                            if (petObject.has("temperament")) {
                                String TemperamentTermsString = petObject.getString("temperament");
                                //TemperamentTerms = (ArrayList<String>) Arrays.asList(TemperamentTermsString.split(","));
                            }

                            // Check against search criteria
                            Boolean weightMatch = false;
                            int searchWeight = Integer.parseInt(SearchCriteria.get("weight").toString());
                            if(searchWeight >= MinWeight && searchWeight <= MaxWeight) {
                                weightMatch = true;
                            }

                            if(species.equals("Doggy")) {
                                //Boolean heightMatch = false;
                                //int searchheight = (int) SearchCriteria.get("height");
                            }

                            // Create a Pet object (our dog/cat class object, not the JSON object) if search criteria are met
                            // Then add this dog to the list that the RecyclerView will use
                            if(weightMatch) {
                                if(species.equals("Doggy")) {
                                    Dog thisDog = new Dog(breedName, id, MinWeight, MaxWeight, LifeSpanMin, LifeSpanMax, origin, TemperamentTerms);
                                    petList.add(thisDog);
                                }
                                else if(species.equals("Kitty")) {
                                    Cat thisCat = new Cat(breedName, id, MinWeight, MaxWeight, LifeSpanMin, LifeSpanMax, origin, TemperamentTerms);
                                    petList.add(thisCat);
                                }
                            }
                        }

                        // If no results matched, then the pet list will be empty; show the no results message
                        if(petList.isEmpty()) {
                            NoResultsMessage.setVisibility(View.VISIBLE);
                        }
                        // If results matched:
                        else {
                            // Create adapter, passing in the pet list
                            BreedListItemAdapter petAdapter = new BreedListItemAdapter(petList, thisContext, thisFragmentActivity);

                            // Attach the adapter to the recyclerview to populate items
                            thisRecyclerView.setAdapter(petAdapter);

                            // Set layout manager to position the items
                            thisRecyclerView.setLayoutManager(new LinearLayoutManager(thisContext));

                            // Add a basic divider between the items
                            thisRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            error -> {
                // Display error in toast
                Toast.makeText(thisContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            });

            // Create the request queue
            RequestQueue requestQueue = Volley.newRequestQueue(thisContext);

            // Add the string request to request queue
            requestQueue.add(stringRequest);
        }
    }
}

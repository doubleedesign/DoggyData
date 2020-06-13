package com.wardlee.doggydata;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

public class BreedlistFragment extends Fragment {
    // Tag for debugging
    private static final String TAG = "BreedListFragment";

    // List to store pets from the API
    private ArrayList<Pet> petList = new ArrayList<Pet>();

    // Variables to be passed in from the activity or fragment calling this
    private Context thisContext;

    // Other variable initialisations
    private RecyclerView thisRecyclerView;


    /**
     * Constructor
     */
    public BreedlistFragment(Context context) {
        thisContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Find the fragment placeholder and bring it to the front
        FrameLayout thisFragment = getActivity().findViewById(R.id.fragment_placeholder);
        thisFragment.bringToFront();

        // Define the layout file for the fragment
        return inflater.inflate(R.layout.fragment_breedlist, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Find the RecyclerView
        thisRecyclerView = view.findViewById(R.id.rv_breeds);

        // Load dogs into the RecyclerView
        loadDogs(thisRecyclerView);
    }


    /**
     * Method to load dog breeds from TheDogAPI.com using Volley
     * Based on tutorial:
     * @author Belal Khan
     * @link https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
     */
    protected void loadDogs(RecyclerView thisRecyclerView) {
        String JSON_URL = "https://api.thedogapi.com/v1/breeds?api_key=ea7d3e22-d28a-427f-a71b-5d29db2bc67d";

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // Get the JSON from the response
                    JSONArray dogs = new JSONArray(response);

                    // Set how many to show (all of them)
                    int qty = dogs.length();

                    // Loop through the array
                    for (int i = 0; i < qty; i++) {

                        // Get the JSON object of this dog
                        JSONObject dogObject = dogs.getJSONObject(i);

                        // Get the data we want from the object
                        String breedName = dogObject.getString("name");

                        // Create a Dog object (our dog class object, not the JSON object)
                        Dog thisDog = new Dog(breedName);

                        // Add this dog to the list that the RecyclerView will use
                        petList.add(thisDog);
                    }

                    // Create adapter, passing in the pet list
                    BreedListItemAdapter petAdapter = new BreedListItemAdapter(petList, thisContext);

                    // Attach the adapter to the recyclerview to populate items
                    thisRecyclerView.setAdapter(petAdapter);

                    // Set layout manager to position the items
                    thisRecyclerView.setLayoutManager(new LinearLayoutManager(thisContext));

                    // Add a basic divider between the items
                    thisRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


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

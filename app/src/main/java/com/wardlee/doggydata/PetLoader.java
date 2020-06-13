package com.wardlee.doggydata;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

//import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URI;
import java.util.ArrayList;

public class PetLoader {
    // Tag for debugging
    private static final String TAG = "PetLoader";

    // List to store pets from the API
    private ArrayList<Pet> petList = new ArrayList<Pet>();

    // Variables to be passed in from the activity or fragment calling the PetLoader
    private Context thisContext;
    //private RecyclerView thisRecyclerView;

    /**
     * Constructor
     */
    public PetLoader(Context context, RecyclerView recyclerView) {
        this.thisContext = context;
        thisRecyclerView = recyclerView;
    }


    /**
     * Method to load dog breeds from TheDogAPI.com using Volley
     * Based on tutorial:
     * @author Belal Khan
     * @link https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
     */
    protected void loadDogs() {
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

                        //Log.d(TAG, dogObject.toString());

                    }

                    // Lookup the recyclerview in the activity layout
                    //RecyclerView rvArticles = thisRecyclerView;

                    // Create adapter passing in the outlets and colours
                    //ArticleAdapter articleAdapter = new ArticleAdapter(articleList, thisContext, backgroundColor, textColor);

                    // Attach the adapter to the recyclerview to populate items
                    //rvArticles.setAdapter(articleAdapter);

                    // Set layout manager to position the items
                    //rvArticles.setLayoutManager(new LinearLayoutManager(thisContext));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            // Display error in toast
           Toast.makeText(thisContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Create the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);

        // Add the string request to request queue
        requestQueue.add(stringRequest);
    }

}
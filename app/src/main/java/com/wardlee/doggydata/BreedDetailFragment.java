package com.wardlee.doggydata;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BreedDetailFragment extends Fragment {
    // Tag for debugging
    private static final String TAG = "BreedDetailFragment";

    Pet petObject;
    Context thisContext;
    String Image_JSON_URL;

    public BreedDetailFragment(Pet pet) {
        petObject = pet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Define the layout file for the fragment
        return inflater.inflate(R.layout.fragment_breeddetail, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        thisContext = view.getContext();

        // The layout elements
        ImageView ImageField = view.findViewById(R.id.imageView_breedImage);
        TextView LabelField = view.findViewById(R.id.textView_breedDetailTitle);
        LinearLayout OriginWrapper = view.findViewById(R.id.LinearLayout_breedOriginWrapper);
        TextView OriginField = view.findViewById(R.id.textView_breedOrigin);
        TextView WeightField = view.findViewById(R.id.textView_breedWeight);
        TextView LifespanField = view.findViewById(R.id.textView_breedLifespan);
        TextView Credit = view.findViewById(R.id.textView_credit);

        // Populate the credit field and set the image request URL according to species
        if(petObject instanceof Dog) {
            Credit.setText("Powered by TheDogAPI.com");
            Image_JSON_URL = "https://api.thedogapi.com/v1/images/search?breed_id=" + petObject.getApi_Id() + "&api_key=ea7d3e22-d28a-427f-a71b-5d29db2bc67d";
        }
        else if(petObject instanceof Cat) {
            Credit.setText("Powered by TheCatAPI.com");
            Image_JSON_URL = "https://api.thecatapi.com/v1/images/search?breed_id=" + petObject.getApi_Id() + "&api_key=ea7d3e22-d28a-427f-a71b-5d29db2bc67d";
        }

        // Populate the text fields with the pet object values
        LabelField.setText(petObject.getName());
        WeightField.setText(petObject.getMinWeight() + "-" + petObject.getMaxWeight() + "kg");
        LifespanField.setText(petObject.getMinLifespan() + "-" + petObject.getMaxLifespan() + " years");

        // Check if origin field is set, if it isn't then hide the field and label
        String origin = petObject.getOrigin();
        if(!origin.isEmpty()) {
            OriginField.setText(petObject.getOrigin());
        }
        else {
            OriginWrapper.setVisibility(view.GONE);
        }

        // Initially hide the image field (so the loading icon will show)
        ImageField.setVisibility(View.VISIBLE);

        // Get an image from the API and populate the image field
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Image_JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // Get the JSON from the response
                    JSONArray imageDetail = new JSONArray(response);
                    JSONObject imageDetailResult = imageDetail.getJSONObject(0);

                    // Get the image URL
                    String imageURL = imageDetailResult.getString("url");

                    // Load the image if one is returned
                    if(!imageURL.isEmpty()) {
                        Picasso.get()
                                .load(imageURL)
                                .fit()
                                .centerCrop()
                                .into(ImageField);

                        // Show the image field after a short delay
                        new Handler().postDelayed(() -> {
                            ImageField.setVisibility(View.VISIBLE);
                            ImageField.getLayoutParams().height = 500;
                        }, 1000);

                        // Hide the loading screen (after a short delay because I want to show it for the purposes of this assignment)
                        new Handler().postDelayed(() -> {
                            view.findViewById(R.id.layout_imageLoadingPanel).setVisibility(View.GONE);
                        }, 2000);
                    }
                    else {
                        // Hide the loading icon and change the text if no image is found
                        ProgressBar loadingIcon = view.findViewById(R.id.layout_imageLoadingPanel_icon);
                        TextView loadingText = view.findViewById(R.id.layout_imageLoadingPanel_text);
                        loadingIcon.setVisibility(View.GONE);
                        loadingText.setText("No image found");
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

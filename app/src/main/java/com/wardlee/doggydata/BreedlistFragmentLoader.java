package com.wardlee.doggydata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class BreedlistFragmentLoader {
    // Tag for debugging
    private static final String TAG = "BreedListFragmentLoader";

    // Variables to be passed in from the activity or fragment calling this
    private Context thisContext;


    /**
     * Constructor
     */
    public BreedlistFragmentLoader(Context context) {
        this.thisContext = context;
    }


    /**
     * Method to swap the fragment placeholder with a new list fragment
     * @param fragmentManager
     */
    protected BreedlistFragment loadFragment(FragmentManager fragmentManager) {

        // Create a breed list fragment
        BreedlistFragment listfragment = new BreedlistFragment(thisContext);

        // Create a fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the contents of the placeholder with this fragment
        transaction.replace(R.id.fragment_placeholder, listfragment);

        // Add an animation setting so the fragment nicely fades in/out of view
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Make the back button work (i.e. hide the fragment and go back to the main menu)
        transaction.addToBackStack(null);

        // Done with the fragment transaction
        transaction.commit();

        return listfragment;
    }
}
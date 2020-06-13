package com.wardlee.doggydata;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BreedDetailFragment extends Fragment {
    // Tag for debugging
    private static final String TAG = "BreedDetailFragment";

    Pet petObject;

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

        // The layout elements
        ImageView ImageField = view.findViewById(R.id.imageView_breedImage);
        TextView LabelField = view.findViewById(R.id.textView_breedDetailTitle);
        LinearLayout OriginWrapper = view.findViewById(R.id.LinearLayout_breedOriginWrapper);
        TextView OriginField = view.findViewById(R.id.textView_breedOrigin);
        TextView WeightField = view.findViewById(R.id.textView_breedWeight);
        TextView LifespanField = view.findViewById(R.id.textView_breedLifespan);

        // TODO: Get an image from the API and populate the image field

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

    }
}

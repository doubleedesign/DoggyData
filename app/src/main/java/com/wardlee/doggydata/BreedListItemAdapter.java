package com.wardlee.doggydata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BreedListItemAdapter extends RecyclerView.Adapter<BreedListItemAdapter.ViewHolder> {
    // Tag for debugging
    private static final String TAG = "PetAdapter";

    // Member variables for the breed list and fragment manager
    private ArrayList<Pet> BreedList;
    private FragmentActivity thisFragmentActivity;

    /**
     * Constructor
     */
    public BreedListItemAdapter(ArrayList<Pet> breeds, Context context, FragmentActivity activity) {
        BreedList = breeds;
        thisFragmentActivity = activity;
    }


    /**
     * Inner class for the view holder of each item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout PetWrapper;
        TextView BreedName;

        // Tag for debugging
        private static final String TAG = "BreedlistItemAdapter";

        // Variables for the data about each item
        Pet thisPet;

        // The constructor for the viewholder
        public ViewHolder(final View itemView) {
            super(itemView);

            // The interface elements
            LinearLayout BreedItemWrapper = itemView.findViewById(R.id.layout_BreedListItem);
            BreedName = itemView.findViewById(R.id.textView_breedName);

            // TODO: Set interface styling


            //  Add click listener to each item to open a detail fragment
            BreedItemWrapper.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    FragmentManager BreedFragmentManager = thisFragmentActivity.getSupportFragmentManager();

                    // Create a new instance of a breed detail fragment,
                    // passing in the relevant Pet object
                    BreedDetailFragment detailFragment = new BreedDetailFragment(thisPet);

                    // Find the fragment placeholder and bring it to the front
                    FrameLayout fragmentPlaceholder = thisFragmentActivity.findViewById(R.id.fragment_detailPlaceholder);
                    fragmentPlaceholder.bringToFront();

                    // Create a fragment transaction
                    FragmentTransaction transaction = BreedFragmentManager.beginTransaction();

                    // Replace the contents of the placeholder with this fragment
                    transaction.replace(R.id.fragment_detailPlaceholder, detailFragment);

                    // Add an animation setting so the fragment nicely fades in/out of view
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                    // Make the back button work (i.e. hide the fragment and go back to the main menu)
                    transaction.addToBackStack(null);

                    // Done
                    transaction.commit();
                }
            });
        }

        // Method to set the pet object after the view has been created,
        // so the whole object can be passed to the fragment
        public void setObject(Pet pet) {
            thisPet = pet;
        }
    }

    /**
     * Adapter methods
     */
    @Override
    public int getItemCount() {
        return BreedList.size();
    }

    @Override
    public BreedListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View BreedView = inflater.inflate(R.layout.listitem_breed, parent, false);

        // Return view holder instance
        ViewHolder viewholder = new ViewHolder(BreedView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(BreedListItemAdapter.ViewHolder viewHolder, int position) {
        View view = viewHolder.itemView;

        // Get the data for this item based on position in the list
        Pet pet = BreedList.get(position);

        // Set the viewholder's object to that pet object
        viewHolder.setObject(pet);

        // Get layout elements for this item
        TextView name = view.findViewById(R.id.textView_breedName);

        // Set the name text
        name.setText(pet.getName());
    }
}

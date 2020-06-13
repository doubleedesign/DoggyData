package com.wardlee.doggydata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BreedListItemAdapter extends RecyclerView.Adapter<BreedListItemAdapter.ViewHolder> {
    // Tag for debugging
    private static final String TAG = "PetAdapter";

    // Member variable for the breed list
    private ArrayList<Pet> BreedList;

    /**
     * Constructor
     */
    public BreedListItemAdapter(ArrayList<Pet> breeds, Context context) {
        BreedList = breeds;
    }

    /**
     * Inner class for the view holder of each item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout PetWrapper;
        TextView BreedName;

        // The constructor for the viewholder
        public ViewHolder(final View itemView) {
            super(itemView);

            // The interface elements
            BreedName = itemView.findViewById(R.id.textView_breedName);

            // TODO: Set interface styling

            // TODO: Add click listener to each item to open a detail fragment
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

        // Get layout elements for this item
        TextView name = view.findViewById(R.id.textView_breedName);

        // Set the name text
        name.setText(pet.getName());
    }
}

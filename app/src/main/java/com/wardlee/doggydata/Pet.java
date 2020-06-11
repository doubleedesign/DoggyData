package com.wardlee.doggydata;

import java.util.ArrayList;

public class Pet {
    private String Name;
    private int MinWeight;
    private int MaxWeight;
    private int MinHeight;
    private int MaxHeight;
    private int LifeSpanMin;
    private int LifeSpanMax;
    private String BreedGroup; // TODO: Can this be an ID to keep it consistent across instances?
    private String Origin;
    private String Purpose;
    private ArrayList<String> TemperamentTerms;

    /**
     * Constructor
     *
     * @param name
     * @param minWeight
     * @param maxWeight
     * @param minHeight
     * @param maxHeight
     * @param lifeSpanMin
     * @param lifeSpanMax
     * @param breedGroup
     * @param origin
     * @param bredFor;
     * @param temperament
     */
    public Pet(String name, int minWeight, int maxWeight, int minHeight, int maxHeight, int lifeSpanMin, int lifeSpanMax, String breedGroup, String origin, String bredFor, ArrayList<String> temperament) {
        Name = name;
        MinWeight = minWeight;
        MaxWeight = maxWeight;
        MinHeight = minHeight;
        MaxHeight = maxHeight;
        LifeSpanMin = lifeSpanMin;
        LifeSpanMax = lifeSpanMax;
        BreedGroup = breedGroup;
        Origin = origin;
        Purpose = bredFor;
        TemperamentTerms = temperament;
    }

    /**
     * Getter methods
     */
    public String getName() {
        return Name;
    }

    public int getMinWeight() {
        return MinWeight;
    }

    public int getMaxWeight() {
        return MaxWeight;
    }

    public int getMinHeight() {
        return MinHeight;
    }

    public int getMaxHeight() {
        return MaxHeight;
    }

    public int getMinLifespan() {
        return LifeSpanMin;
    }

    public int getMaxLifespan() {
        return LifeSpanMax;
    }

    public String getBreedGroup() {
        return BreedGroup;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getPurpose() {
        return Purpose;
    }

    public ArrayList<String> getTemperamentTerms() {
        return TemperamentTerms;
    }
}
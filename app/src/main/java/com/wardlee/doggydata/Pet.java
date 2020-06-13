package com.wardlee.doggydata;

import java.util.ArrayList;

public class Pet {
    private String Name;
    private int Api_Id;
    private int MinWeight;
    private int MaxWeight;
    private int LifeSpanMin;
    private int LifeSpanMax;
    private String Origin;
    private ArrayList<String> TemperamentTerms;


    /**
     * Constructor with base properties for all kinds of pets
     *
     * @param name
     * @param minWeight
     * @param maxWeight
     * @param lifeSpanMin
     * @param lifeSpanMax
     * @param origin
     * @param temperament
     */
    public Pet(String name, int id, int minWeight, int maxWeight, int lifeSpanMin, int lifeSpanMax, String origin, ArrayList<String> temperament) {
        Name = name;
        Api_Id = id;
        MinWeight = minWeight;
        MaxWeight = maxWeight;
        LifeSpanMin = lifeSpanMin;
        LifeSpanMax = lifeSpanMax;
        Origin = origin;
        TemperamentTerms = temperament;
    }

    /**
     * Getter methods
     */
    public String getName() {
        return Name;
    }

    public int getApi_Id() {
        return Api_Id;
    }

    public int getMinWeight() {
        return MinWeight;
    }

    public int getMaxWeight() {
        return MaxWeight;
    }

    public int getMinLifespan() {
        return LifeSpanMin;
    }

    public int getMaxLifespan() {
        return LifeSpanMax;
    }

    public String getOrigin() {
        return Origin;
    }

    public ArrayList<String> getTemperamentTerms() {
        return TemperamentTerms;
    }
}
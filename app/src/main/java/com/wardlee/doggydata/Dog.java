package com.wardlee.doggydata;

import java.util.ArrayList;

public class Dog extends Pet {
    private int MinHeight;
    private int MaxHeight;
    private String BreedGroup; // TODO: Can this be an ID to keep it consistent across instances?
    private String Purpose;

    /**
     * Constructor with only the name, for dev purposes
     * @param name
     */
    public Dog(String name) {
        super(name);
    }

    /**
     * Constructor with only base Pet properties
     *
     * @param name
     * @param minWeight
     * @param maxWeight
     * @param lifeSpanMin
     * @param lifeSpanMax
     * @param origin
     * @param temperament
     */
    public Dog(String name, int minWeight, int maxWeight, int lifeSpanMin, int lifeSpanMax, String origin, ArrayList<String> temperament) {
        super(name, minWeight, maxWeight, lifeSpanMin, lifeSpanMax, origin, temperament);
    }

    /**
     * Constructor with all Dog properties
     *
     * @param name
     * @param minWeight
     * @param maxWeight
     * @param minHeight
     * @param maxHeight
     * @param lifeSpanMin
     * @param lifeSpanMax
     * @param origin
     * @param temperament
     * @param breedGroup
     * @param bredFor
     */
    public Dog(String name, int minWeight, int maxWeight, int minHeight, int maxHeight, int lifeSpanMin, int lifeSpanMax, String origin, ArrayList<String> temperament, String breedGroup, String bredFor) {
        super(name, minWeight, maxWeight, lifeSpanMin, lifeSpanMax, origin, temperament);
        MinHeight = minHeight;
        MaxHeight = maxHeight;
        BreedGroup = breedGroup;
        Purpose = bredFor;
    }


    /**
     * Getter methods
     */
    public int getMinHeight() {
        return MinHeight;
    }

    public int getMaxHeight() {
        return MaxHeight;
    }

    public String getBreedGroup() {
        return BreedGroup;
    }

    public String getPurpose() {
        return Purpose;
    }

}

package com.wardlee.doggydata;

import java.util.ArrayList;

public class Cat extends Pet {
    // TODO: Add more cat properties

    /**
     * Constructor with only base Pet properties
     *
     * @param name
     * @param id
     * @param minWeight
     * @param maxWeight
     * @param lifeSpanMin
     * @param lifeSpanMax
     * @param origin
     * @param temperament
     */
    public Cat(String name, int id, int minWeight, int maxWeight, int lifeSpanMin, int lifeSpanMax, String origin, ArrayList<String> temperament) {
        super(name, id, minWeight, maxWeight, lifeSpanMin, lifeSpanMax, origin, temperament);
    }
}

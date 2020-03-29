package com.jonathan.cocktailapi.entity;

import java.util.ArrayList;

/**
 * @author 1772004 - Jonathan Bernad
 */
public class DrinkResponse {

    private ArrayList<Drink> drinks;

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }
}

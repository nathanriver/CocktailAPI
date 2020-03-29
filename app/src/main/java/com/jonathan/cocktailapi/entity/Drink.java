package com.jonathan.cocktailapi.entity;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Drink {

    @SerializedName("idDrink")
    private String id;

    @SerializedName("strDrink")
    private String name;

    @SerializedName("strDrinkThumb")
    private String thumbnail;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strInstructions")
    private String instructions;

    public Drink() {
    }

    public Drink(String id, String name, String thumbnail, String category, String instructions) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.category = category;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public String toString() {
        return category;
    }
}



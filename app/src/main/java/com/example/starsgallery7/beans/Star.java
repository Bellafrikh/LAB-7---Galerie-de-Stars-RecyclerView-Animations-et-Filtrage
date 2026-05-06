package com.example.starsgallery7.beans;

public class Star {
    private int id;
    private String name;
    private int image; // Changé de String à int
    private float rating;

    public Star(int id, String name, int image, float rating) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.rating = rating;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getImage() { return image; } // Getter retournant un int
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}
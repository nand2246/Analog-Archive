package model;

import org.json.JSONObject;

/*
 * Represents a Camera with given characteristics
 *
 * name: represents the name of the camera
 * filmType: represents the type of film the camera uses (polaroid, 35mm, medium format)
 * manufacturer: represents the manufacturer of the camera
 */
public class Camera {

    private String name;
    private String filmType;
    private String manufacturer;

    // EFFECTS: constructs new camera with given name, filmType, and manufacturer
    public Camera(String name, String filmType, String manufacturer) {
        this.name = name;
        this.filmType = filmType;
        this.manufacturer = manufacturer;
    }

    // EFFECTS: return the name of the camera
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of the camera to given string
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: return the film type of the camera
    public String getFilmType() {
        return filmType;
    }

    // MODIFIES: this
    // EFFECTS: sets the film type of the camera
    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    // EFFECTS: return the manufacturer of the camera
    public String getManufacturer() {
        return manufacturer;
    }

    // MODIFIES: this
    // EFFECTS: set the manufacturer of the camera to the given string
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    // EFFECTS: returns all properties of this camera as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("film type", filmType);
        json.put("manufacturer", manufacturer);
        return json;
    }

    // EFFECTS: returns the camera as a string including the manufacturer and name
    @Override
    public String toString() {
        return manufacturer + " " + name;
    }
}

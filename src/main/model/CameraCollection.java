package model;

import java.util.ArrayList;

/*
 * Represents a collection of cameras, containing multiple cameras
 * <p>
 * collection: represents the list of cameras in the collection
 */
public class CameraCollection {
    private final ArrayList<Camera> collection;

    // EFFECTS: constructs new camera collection with an empty list of film
    public CameraCollection() {
        collection = new ArrayList<>();
    }

    // EFFECTS: returns the size of the collection
    public int getSize() {
        return collection.size();
    }

    // MODIFIES: this
    // EFFECTS: add a camera to the collection
    public boolean addCamera(Camera camera) {
        if (collection.contains(camera)) {
            return false;
        } else {
            collection.add(camera);
            return true;
        }
    }

    // REQUIRES: given camera must be in the collection
    // MODIFIES: this
    // EFFECTS: removes given camera from the collection
    public void removeCamera(Camera camera) {
        collection.remove(camera);
    }

    // REQUIRES: index must be smaller than the size of the collection
    // MODIFIES: this
    // EFFECTS: returns camera at given index in the collection
    public Camera getCamera(int index) {
        return collection.get(index);
    }

    //EFFECTS: returns a list of cameras in the collection
    public ArrayList<Camera> getCollection() {
        return collection;
    }

    // EFFECTS: returns a list of Cameras with given name
    public ArrayList<Camera> filterByName(String name) {
        ArrayList<Camera> filtered = new ArrayList<>();

        for (Camera c : collection) {
            if (c.getName().equals(name)) {
                filtered.add(c);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of cameras with given film type
    public ArrayList<Camera> filterByFilmType(String filmType) {
        ArrayList<Camera> filtered = new ArrayList<>();

        for (Camera c : collection) {
            if (c.getFilmType().equals(filmType)) {
                filtered.add(c);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of cameras with given manufacturer
    public ArrayList<Camera> filterByManufacturer(String manufacturer) {
        ArrayList<Camera> filtered = new ArrayList<>();

        for (Camera c : collection) {
            if (c.getManufacturer().equals(manufacturer)) {
                filtered.add(c);
            }
        }

        return filtered;
    }
}

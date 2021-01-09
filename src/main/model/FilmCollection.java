package model;

import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Represents a collection of film, containing multiple rolls/packs of film
 * <p>
 * collection: represents the list of film rolls/packs in the collection
 */
public class FilmCollection {
    private final ArrayList<Film> collection;

    // EFFECTS: constructs a new film collection with an empty list of cameras
    public FilmCollection() {
        collection = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a roll/pack of film to the collection,
    // if it is not already in the collection.
    public boolean addFilm(Film film) {
        if (collection.contains(film)) {
            return false;
        } else {
            collection.add(film);
            return true;
        }
    }

    // REQUIRES: film must be in this collection
    // MODIFIES: this
    // EFFECTS: removes given roll/pack of film from collection
    public void removeFilm(Film film) {
        collection.remove(film);
    }

    // REQUIRES: index must be smaller than the size of the collection
    // EFFECTS: return the roll/pack of film at the given index in the collection
    public Film getRoll(int index) {
        return collection.get(index);
    }

    // EFFECTS: returns a list containing all of the film rolls/packs in the collection
    public ArrayList<Film> getCollection() {
        return collection;
    }

    // EFFECTS: returns the size of the collection
    public int getSize() {
        return collection.size();
    }

    // EFFECTS: returns a list of film rolls/packs that were taken using the given camera
    public ArrayList<Film> filterByCamera(Camera camera) {
        ArrayList<Film> filtered = new ArrayList<>();
        String cameraName = camera.getName();

        for (Film f : collection) {
            if (f.getCamera().getName().equals(cameraName)) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with given name
    public ArrayList<Film> filterByName(String name) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getName().equals(name)) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with given ISO
    public ArrayList<Film> filterByIso(int iso) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getIso() == iso) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with given type
    public ArrayList<Film> filterByType(String type) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getType().equals(type)) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with given brand
    public ArrayList<Film> filterByBrand(String brand) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getBrand().equals(brand)) {
                filtered.add(f);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with the given expiry date
    public ArrayList<Film> filterByExpiry(LocalDate expiry) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getExpiry() != null) {
                if (f.getExpiry().equals(expiry)) {
                    filtered.add(f);
                }
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with the given developing date
    public ArrayList<Film> filterByDevelopDate(LocalDate developDate) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getDevelopDate() != null) {
                if (f.getDevelopDate().equals(developDate)) {
                    filtered.add(f);
                }
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with the given developing location
    public ArrayList<Film> filterByDevelopLocation(String developLocation) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getDevelopLocation() != null) {
                if (f.getDevelopLocation().equals(developLocation)) {
                    filtered.add(f);
                }
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of film rolls/packs with a images path
    public ArrayList<Film> filterByPhotosPathExist() {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film f : collection) {
            if (f.getDirectory() != null) {
                filtered.add(f);
            }
        }
        return filtered;
    }
}
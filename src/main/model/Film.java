package model;

import org.json.JSONObject;

import java.time.LocalDate;

/*
 * This class represents one roll/pack of film that the user has inputted into the file.
 * <p>
 * name: represents the name of the roll of film, given by the user.
 * iso: represents the ISO of the film, inputted by the user.
 * type: represents the type of film represented. (polaroid film, 35mm film, medium format).
 * camera: represents the camera used to capture the photos.
 * brand: represents the manufacturer of the film.
 * expiry: represents the expiry date of the film
 * developDate: represents the date that the film was developed.
 * developLocation: represents the development lab that the film was developed
 * directory: represents the folder where the photos are stored on the computer
 */
public class Film {

    private String name;
    private int iso;
    private String type;
    private Camera camera;
    private String brand;
    private LocalDate expiry;
    private LocalDate developDate;
    private String developLocation;
    private String directory;

    //EFFECTS: creates a new roll/pack of film with given name, iso, type, camera, and brand
    public Film(String name, int iso, String type, Camera camera, String brand) {
        this.name = name;
        this.iso = iso;
        this.type = type;
        this.camera = camera;
        this.brand = brand;
    }

    // REQUIRES: Film object with both an expiry and a develop date
    // EFFECTS: returns true if the film was expired when it was developed
    public boolean isExpired() {
        return expiry.isBefore(developDate);
    }

    // REQUIRES: Film object with and expiry date
    // EFFECTS: return the expiry date of this
    public LocalDate getExpiry() {
        return expiry;
    }

    // REQUIRES: year with four digits, month between 1 and 12, and day that belongs in the specified month
    // MODIFIES: this
    // EFFECTS: sets expiry date of the given roll/pack of film
    public void setExpiry(int year, int month, int day) {
        expiry = LocalDate.of(year, month, day);
    }

    // REQUIRES: Film object with a developing date
    // EFFECTS: return the developing date of the film
    public LocalDate getDevelopDate() {
        return developDate;
    }

    // REQUIRES: year with four digits, month between 1 and 12, and day that belongs in the specified month
    // MODIFIES: this
    // EFFECTS: sets the developing date of the film
    public void setDevelopDate(int year, int month, int day) {
        developDate = LocalDate.of(year, month, day);
    }

    // EFFECTS: return the name given to the film
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of the film to given string
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: return the ISO of the film
    public int getIso() {
        return iso;
    }

    // REQUIRES: positive integer to represent ISO
    // MODIFIES: this
    // EFFECTS: sets the ISO of the film to given integer
    public void setIso(int iso) {
        this.iso = iso;
    }

    // EFFECTS: return the type of the film
    public String getType() {
        return type;
    }

    // MODIFIES: this
    // EFFECTS: sets the type of film used
    public void setType(String type) {
        this.type = type;
    }

    // EFFECTS: returns the camera used to capture photos on the film
    public Camera getCamera() {
        return camera;
    }

    // MODIFIES: this
    // EFFECTS: sets the camera used to capture the photos on the film,
    // if the camera and the film are of the same type
    public boolean setCamera(Camera camera) {
        if (camera.getFilmType().equals(type)) {
            this.camera = camera;
            return true;
        }
        return false;
    }

    // EFFECTS: return the brand of the film
    public String getBrand() {
        return brand;
    }

    // MODIFIES: this
    // EFFECTS: sets the brand of the film to given string
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // REQUIRES: film with a developing location
    // EFFECTS: return the developing location
    public String getDevelopLocation() {
        return developLocation;
    }

    // MODIFIES: this
    // EFFECTS: sets the developing location of the film to given string
    public void setDevelopLocation(String developLocation) {
        this.developLocation = developLocation;
    }

    // REQUIRES: film with an image path
    // EFFECTS: return the image path of the film photos
    public String getDirectory() {
        return directory;
    }

    // REQUIRES: imagePath points to valid path on computer
    // MODIFIES: this
    // EFFECTS: set the image path of the film
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    // EFFECTS: returns the properties of this film as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("iso", iso);
        json.put("type", type);
        json.put("camera", camera.toJson());
        json.put("brand", brand);
        json.put("expiry", dateToJson(expiry));
        json.put("develop date", dateToJson(developDate));
        json.put("develop location", developLocation);
        json.put("images path", directory);
        return json;
    }

    // EFFECTS: returns the given date as a JSONObject
    public JSONObject dateToJson(LocalDate date) {
        JSONObject json = new JSONObject();
        if (date != null) {
            String year = Integer.toString(date.getYear());
            String month = Integer.toString(date.getMonthValue());
            String day = Integer.toString(date.getDayOfMonth());
            json.put("year", year);
            json.put("month", month);
            json.put("day", day);
        }
        return json;
    }

    // EFFECTS returns the film as a string, with the name, brand, and type
    @Override
    public String toString() {
        return name + ", " + brand + ", " + type;
    }
}
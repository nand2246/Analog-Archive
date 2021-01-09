package model;

import org.json.JSONArray;
import org.json.JSONObject;
/*
 * Represents an Archive with given collections
 *
 * cameraCollection: represents a collection of cameras in the archive
 * filmCollection: represents a collection of film in the archive
 */

public class Archive {
    public final CameraCollection cameraCollection;
    public final FilmCollection filmCollection;

    //EFFECTS: constructs new archive with given film collection and camera collection
    public Archive(CameraCollection cc, FilmCollection fc) {
        cameraCollection = cc;
        filmCollection = fc;
    }

    //EFFECTS: returns collections in the archive as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Cameras", camerasToJson());
        json.put("Film", filmToJson());
        return json;
    }

    //EFFECTS: returns cameras in the archive as a JSONObject
    private JSONArray camerasToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Camera c : cameraCollection.getCollection()) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns film in the archive as a JSONObject
    private JSONArray filmToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Film f : filmCollection.getCollection()) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}

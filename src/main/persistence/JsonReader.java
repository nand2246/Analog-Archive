package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

/*
 * Is used to read information about an archive from a .json file, and create an archive from use in the application
 * <p>
 * source: represents the location fo the file that is being read from.
 */
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads archive from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Archive read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseArchive(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses archive from JSON object and returns it
    private Archive parseArchive(JSONObject jsonObject) {
        Archive a = new Archive(new CameraCollection(), new FilmCollection());
        addCameraCollection(a, jsonObject);
        addFilmCollection(a, jsonObject);
        return a;
    }

    // MODIFIES: a
    // EFFECTS: parses camera collection from JSON object and adds it to archive
    private void addCameraCollection(Archive a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Cameras");
        for (Object json : jsonArray) {
            JSONObject nextCamera = (JSONObject) json;
            addCamera(a, nextCamera);
        }
    }

    // MODIFIES: a
    // EFFECTS: parses camera from JSON object and adds it to archive
    private void addCamera(Archive a, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String filmType = jsonObject.getString("film type");
        String manufacturer = jsonObject.getString("manufacturer");
        Camera camera = new Camera(name, filmType, manufacturer);
        a.cameraCollection.addCamera(camera);
    }

    // MODIFIES: a
    // EFFECTS: parses film collection from JSON object and adds it to archive
    private void addFilmCollection(Archive a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Film");
        for (Object json : jsonArray) {
            JSONObject nextFilm = (JSONObject) json;
            addFilm(a, nextFilm);
        }
    }

    // MODIFIES: a
    // EFFECTS: parses film from JSON object and adds it to archive
    private void addFilm(Archive a, JSONObject jsonObject) {

        Camera camera = addCameraToFilm(jsonObject.getJSONObject("camera"));

        Film film = new Film(jsonObject.getString("name"),
                jsonObject.getInt("iso"),
                jsonObject.getString("type"),
                camera,
                jsonObject.getString("brand"));

        LocalDate expiry = addDateToFilm(jsonObject.getJSONObject("expiry"));
        LocalDate developDate = addDateToFilm(jsonObject.getJSONObject("develop date"));
        if (expiry != null) {
            film.setExpiry(expiry.getYear(), expiry.getMonthValue(), expiry.getDayOfMonth());
        }
        if (developDate != null) {
            film.setDevelopDate(developDate.getYear(), developDate.getMonthValue(), developDate.getDayOfMonth());
        }
        if (jsonObject.has("develop location")) {
            String developLocation = jsonObject.getString("develop location");
            film.setDevelopLocation(developLocation);

        }
        if (jsonObject.has("images path")) {
            String imagesPath = jsonObject.getString("images path");
            film.setDirectory(imagesPath);
        }
        a.filmCollection.addFilm(film);
    }

    // EFFECTS: parses date from JSON object and returns it
    private LocalDate addDateToFilm(JSONObject jsonObject) {
        LocalDate date;
        if (!jsonObject.isEmpty()) {
            int year = jsonObject.getInt("year");
            int month = jsonObject.getInt("month");
            int day = jsonObject.getInt("day");
            date = LocalDate.of(year, month, day);
        } else {
            date = null;
        }
        return date;
    }

    // EFFECTS: parses camera from JSON object and returns it
    private Camera addCameraToFilm(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String filmType = jsonObject.getString("film type");
        String manufacturer = jsonObject.getString("manufacturer");
        return new Camera(name, filmType, manufacturer);
    }
}

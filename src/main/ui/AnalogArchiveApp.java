package ui;

import model.Archive;
import model.CameraCollection;
import model.Film;
import model.FilmCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

// Analog Archive application
public class AnalogArchiveApp {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private FilmCollection filmCollection;
    private CameraCollection cameraCollection;
    private Film activeFilm;

    // EFFECTS: runs the teller application
    public AnalogArchiveApp() {
        jsonWriter = new JsonWriter("./data/saveFile.json");
        jsonReader = new JsonReader("./data/saveFile.json");
        init();
    }

    public FilmCollection getFilmCollection() {
        return filmCollection;
    }

    public CameraCollection getCameraCollection() {
        return cameraCollection;
    }

    public Film getActiveFilm() {
        return activeFilm;
    }

    public void setActiveFilm(Film activeFilm) {
        this.activeFilm = activeFilm;
    }

    // MODIFIES: this
    // EFFECTS: initializes collections
    private void init() {
        filmCollection = new FilmCollection();
        cameraCollection = new CameraCollection();
    }

    // EFFECTS: saves archive to file
    //          FileNotFoundException: thrown if the file cannot be found to save to.
    public void saveArchive() throws FileNotFoundException {
        jsonWriter.open();
        Archive archive = new Archive(cameraCollection, filmCollection);
        jsonWriter.write(archive);
        jsonWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: loads archive from file
    //          IOException: thrown if the file is unable to be read from
    public void loadArchive() throws IOException {
        Archive archive = jsonReader.read();
        cameraCollection = archive.cameraCollection;
        filmCollection = archive.filmCollection;
    }


}
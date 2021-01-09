package ui;

import model.FilmCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Represents the menu bar of the GUI, used to load and save the application state.
 *
 * app: represents the current state of the app, including all of the properties of the archive
 * gui: represents the current state of the GUI
 */
public class MenuBar extends JMenuBar implements ActionListener {

    private final AnalogArchiveApp app;
    private final AnalogArchiveGUI gui;

    // EFFECTS: constructs the menu bar, with menu items "load" and "save"
    public MenuBar(AnalogArchiveApp app, AnalogArchiveGUI gui) {

        this.app = app;
        this.gui = gui;

        JMenu file = new JMenu("File");

        JMenuItem load = new JMenuItem("Load from file");
        load.addActionListener(this);
        load.setActionCommand("load");
        file.add(load);

        JMenuItem save = new JMenuItem("Save to file");
        save.addActionListener(this);
        save.setActionCommand("save");
        file.add(save);
        add(file);
    }

    // EFFECTS: handles all of the action command of the menu bar
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            try {
                app.loadArchive();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(
                        null, "Save file could not be read from", "File Read Error", JOptionPane.ERROR_MESSAGE);
            }

            FilmCollection filmCollection = app.getFilmCollection();

            if (!filmCollection.getCollection().isEmpty()) {
                app.setActiveFilm(filmCollection.getRoll(0));
                gui.update(true);
            }

        } else if (e.getActionCommand().equals("save")) {
            try {
                app.saveArchive();
            } catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(
                        null, "Save file could not be located", "File Write Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

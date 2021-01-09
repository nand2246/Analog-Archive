package ui.panels;

import ui.AnalogArchiveApp;
import model.Film;
import ui.AnalogArchiveGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/*
 * Represents the film information panel that is displayed on the left side of the GUI
 *
 * app: represents the current state of the app, including all of the properties of the archive
 * gui: represents the current state of the GUI
 * textAreas: represents a Map of all the JTextAreas used to display properties of the current
 *            active film of the app
 */
public class FilmPanel extends JPanel implements ActionListener {

    private final AnalogArchiveApp app;
    private final AnalogArchiveGUI gui;
    private final HashMap<String, JTextArea> textAreas;

    // EFFECTS: constructs the panel with a GridLayout, and adds all required text to the panel
    public FilmPanel(AnalogArchiveApp app, AnalogArchiveGUI gui) {
        this.app = app;
        this.gui = gui;

        setLayout(new GridLayout(0, 1, 5, 0));
        setBorder(new MatteBorder(0, 0, 0, 1, new Color(100, 100, 100)));
        setPreferredSize(new Dimension(300, 600));

        textAreas = new HashMap<>();
        initializeTextAreas();

        JButton activeFilmButton = new JButton("Choose another film");
        activeFilmButton.addActionListener(this);
        activeFilmButton.setActionCommand("set active film");
        activeFilmButton.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(5, 5, 5, 5, new Color(60, 63, 65)),
                activeFilmButton.getBorder()));

        add(activeFilmButton, BorderLayout.SOUTH);
    }

    // EFFECTS: initializes all of the text areas used on the panel into the textAreas Map
    private void initializeTextAreas() {
        textAreas.put("name", new JTextArea("Film name: "));
        textAreas.put("iso", new JTextArea("Film iso: "));
        textAreas.put("type", new JTextArea("Film type: "));
        textAreas.put("camera", new JTextArea("Film camera: "));
        textAreas.put("brand", new JTextArea("Film brand: "));
        textAreas.put("expiry", new JTextArea("Film expiry: "));
        textAreas.put("developDate", new JTextArea("Film develop date: "));
        textAreas.put("developLocation", new JTextArea("Film develop location: "));
        textAreas.put("directory", new JTextArea("Film directory: "));

        formatTextAreas();

        add(textAreas.get("name"), BorderLayout.CENTER);
        add(textAreas.get("iso"), BorderLayout.CENTER);
        add(textAreas.get("type"), BorderLayout.CENTER);
        add(textAreas.get("camera"), BorderLayout.CENTER);
        add(textAreas.get("brand"), BorderLayout.CENTER);
        add(textAreas.get("expiry"), BorderLayout.CENTER);
        add(textAreas.get("developDate"), BorderLayout.CENTER);
        add(textAreas.get("developLocation"), BorderLayout.CENTER);
        add(textAreas.get("directory"), BorderLayout.CENTER);
    }

    // EFFECTS: formats all text areas on the panel with new borders, and word wrapping
    private void formatTextAreas() {
        textAreas.forEach((k, v) -> {
            v.setBorder(new EmptyBorder(0, 20, 0, 20));
            v.setWrapStyleWord(true);
            v.setLineWrap(true);
            v.setEditable(false);
        });
    }

    // EFFECTS: updates the panel with nw information when anything is changed
    public void update() {
        Film film = app.getActiveFilm();
        textAreas.get("name").setText("Film name: " + "\n" + film.getName());
        textAreas.get("iso").setText("Film iso: " + "\n" + film.getIso());
        textAreas.get("type").setText("Film type: " + "\n" + film.getType());
        textAreas.get("camera").setText("Film camera: " + "\n" + film.getCamera());
        textAreas.get("brand").setText("Film brand: " + "\n" + film.getBrand());
        textAreas.get("expiry").setText("Film expiry: " + "\n" + film.getExpiry());
        textAreas.get("developDate").setText("Film develop date: " + "\n" + film.getDevelopDate());
        textAreas.get("developLocation").setText("Film develop location: " + "\n" + film.getDevelopLocation());
        textAreas.get("directory").setText("Film directory: " + "\n" + film.getDirectory());
    }

    // EFFECTS: allows the user to set another active film from the current collection of films
    private void setActiveFilm() {
        Film[] films = new Film[app.getFilmCollection().getSize()];
        String[] filmStrings = new String[app.getFilmCollection().getSize()];

        int x = 0;
        for (Film f : app.getFilmCollection().getCollection()) {
            films[x] = f;
            filmStrings[x] = f.toString();
            x++;
        }

        JComboBox<String> filmBox = new JComboBox<>(filmStrings);

        JPanel panel = new JPanel();
        panel.add(filmBox);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Please select the desired film:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            app.setActiveFilm(films[filmBox.getSelectedIndex()]);
            gui.update(true);
        }

    }

    // EFFECTS: handles all ActionEvents for the FilmPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("set active film")) {
            setActiveFilm();
        }
    }
}

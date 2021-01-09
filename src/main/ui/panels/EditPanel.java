package ui.panels;

import com.toedter.calendar.JCalendar;
import ui.AnalogArchiveApp;
import model.Camera;
import model.Film;
import ui.AnalogArchiveGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Represents a JPanel that is displayed at the bottom of the GUI, and is used to
 * display buttons to the user that can be used to edit the current active film.
 *
 * app: represents the current state of the app, including all of the properties of the archive
 * gui: represents the current state of the GUI
 * buttons: a HashMap of JButtons, with String value keys that are used to identify each of
 *          the buttons in the map.
 * keys: represents the String keys used to identify the buttons in the HashMap, is used to
 *       keep the buttons in a specific order.
 */
public class EditPanel extends JPanel implements ActionListener {

    private final AnalogArchiveApp app;
    private final AnalogArchiveGUI gui;
    private final Map<String, JButton> buttons;
    private final ArrayList<String> keys;

    // EFFECTS: constructs the panel with a GridLayout, initializes app and gui,
    //          and adds buttons to the panel
    public EditPanel(AnalogArchiveApp app, AnalogArchiveGUI gui) {
        super(new GridLayout(1, 9, 10, 5));

        this.app = app;
        this.gui = gui;

        setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, new Color(100, 100, 100)),
                new EmptyBorder(5, 5, 5, 5)));


        keys = new ArrayList<>();
        initializeKeys();

        buttons = new HashMap<>();
        initializeButtons();
    }

    // EFFECTS: initializes the keys used to represent the JButtons in the "buttons" HashMap
    private void initializeKeys() {
        keys.add("name");
        keys.add("iso");
        keys.add("type");
        keys.add("camera");
        keys.add("brand");
        keys.add("expiry");
        keys.add("develop date");
        keys.add("develop location");
        keys.add("directory");
    }

    private void initializeButtons() {
        for (String s : keys) {
            buttons.put(s, new JButton("Edit film " + s));
            add(buttons.get(s));
            buttons.get(s).addActionListener(this);
            buttons.get(s).setActionCommand(s);
        }
    }

    // EFFECTS: used to receive a String input from the user
    private String getString(String command) {
        return JOptionPane.showInputDialog("Please enter the desired " + command + " of the film");
    }

    // EFFECTS: allows the user to select a camera from the list of cameras in the collection
    private Camera getCamera() {
        Camera[] cameras = new Camera[app.getCameraCollection().getSize()];
        String[] cameraStrings = new String[app.getCameraCollection().getSize()];
        int x = 0;
        for (Camera c : app.getCameraCollection().getCollection()) {
            cameras[x] = c;
            cameraStrings[x] = c.toString();
            x++;
        }
        JComboBox<String> cameraBox = new JComboBox<>(cameraStrings);
        JPanel panel = new JPanel();
        panel.add(cameraBox);
        JOptionPane.showConfirmDialog(null, panel, "Please select a camera:", JOptionPane.OK_CANCEL_OPTION);
        return cameras[cameraBox.getSelectedIndex()];
    }

    // EFFECTS: allows the user to select a date from a calendar
    private LocalDate getDate() {
        JPanel panel = new JPanel();
        JCalendar datePicker = new JCalendar();
        datePicker.setWeekOfYearVisible(false);
        datePicker.setDecorationBordersVisible(false);
        datePicker.setPreferredSize(new Dimension(300, 300));
        panel.add(datePicker);
        int result = JOptionPane.showConfirmDialog(null, panel, "Please enter the date", JOptionPane.DEFAULT_OPTION);
        return datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // EFFECTS: allows the user to select a directory from the files on the computer
    private String getDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showDialog(null, "Select Folder");
        return fileChooser.getSelectedFile().toString();
    }

    // EFFECTS: allows the user to enter the desired ISO for their film
    private int getIso() {
        JPanel panel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(400, 0, null, 1);
        JSpinner spinner = new JSpinner(model);
        panel.add(spinner);
        int result = JOptionPane.showConfirmDialog(null, panel, "Please enter the ISO", JOptionPane.DEFAULT_OPTION);
        return (int) spinner.getValue();
    }

    // EFFECTS: handles all ActionEvents for the EditPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Film f = app.getActiveFilm();
        doEditFilm(command, f, false);
    }

    // MODIFIES: this
    // EFFECTS: edits the film of the app depending on which property is given
    public void doEditFilm(String property, Film f, Boolean updateDifferentFilm) {
        if ("iso".equals(property)) {
            f.setIso(getIso());
        } else if ("type".equals(property)) {
            f.setType(getString(property));
        } else if ("brand".equals(property)) {
            f.setBrand(getString(property));
        } else if ("develop location".equals(property)) {
            f.setDevelopLocation(getString(property));
        } else if ("name".equals(property)) {
            f.setName(getString(property));
        } else if ("camera".equals(property)) {
            f.setCamera(getCamera());
        } else if ("expiry".equals(property)) {
            LocalDate date = getDate();
            f.setExpiry(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        } else if ("develop date".equals(property)) {
            LocalDate date = getDate();
            f.setDevelopDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        } else if ("directory".equals(property)) {
            f.setDirectory(getDirectory());
            updateDifferentFilm = true;
        }
        gui.update(updateDifferentFilm);
    }
}

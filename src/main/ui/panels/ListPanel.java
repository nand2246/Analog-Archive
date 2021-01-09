package ui.panels;

import com.toedter.calendar.JCalendar;
import model.*;
import ui.AnalogArchiveApp;
import ui.AnalogArchiveGUI;
import ui.tables.CameraTable;
import ui.tables.FilmTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

/*
 * Represents a JPanel that is displayed on the right side of the GUI, and is used to
 * display lists containing the film collection and the camera collection to the user,
 * along with buttons that can be used to add, remove of filter items from the given lists.
 *
 * app: represents the current state of the app, including all of the properties of the archive
 * gui: represents the current state of the GUI
 * filmTable: represents the table displaying all of the films in the collection
 * camerasTables: represents the table displaying all of the cameras in the collection
 * filmListScrollPane: is used to allow the user to scroll through the list of films displayed
 * cameraListScrollPane: is used to allow the user to scroll through the list of cameras displayed
 *
 */
public class ListPanel extends JPanel implements ActionListener {

    private final AnalogArchiveApp app;
    private final AnalogArchiveGUI gui;

    private FilmTable filmTable;
    private CameraTable cameraTable;

    private JScrollPane filmListScrollPane;
    private JScrollPane cameraListScrollPane;

    // EFFECTS: constructs the panel with a GridLayout, and add both tables to the panel
    public ListPanel(AnalogArchiveApp app, AnalogArchiveGUI gui) {
        super(new GridLayout(2, 1));

        this.app = app;
        this.gui = gui;

        setBorder(new MatteBorder(0, 1, 0, 0, new Color(100, 100, 100)));

        initFilmTable();

        initCameraTable();

        add(filmListScrollPane);
        add(cameraListScrollPane);
    }

    // EFFECTS: initializes the film table with required columns on the table
    private void initFilmTable() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("ISO");

        filmTable = new FilmTable(model);
        JPanel filmTablePanel = new JPanel(new BorderLayout());
        filmTablePanel.add(filmTable.getTableHeader(), BorderLayout.PAGE_START);
        filmTablePanel.add(filmTable);

        filmListScrollPane = new JScrollPane(filmTablePanel);
        filmListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        filmListScrollPane.setBorder(new MatteBorder(1, 0, 0, 0, new Color(100, 100, 100)));

        addButtons(filmTablePanel, "Film");
    }

    // EFFECTS: initializes the camera table with required columns on the table
    private void initCameraTable() {
        DefaultTableModel model;

        model = new DefaultTableModel();
        model.addColumn("Manufacturer");
        model.addColumn("Name");
        model.addColumn("Film Type");

        cameraTable = new CameraTable(model);
        JPanel cameraListPanel = new JPanel(new BorderLayout());

        cameraListPanel.add(cameraTable.getTableHeader(), BorderLayout.PAGE_START);
        cameraListPanel.add(cameraTable);

        cameraListScrollPane = new JScrollPane(cameraListPanel);
        cameraListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cameraListScrollPane.setBorder(new MatteBorder(1, 0, 0, 0, new Color(100, 100, 100)));

        addButtons(cameraListPanel, "Camera");
    }

    // EFFECTS: adds "add" "remove" and "filter" buttons to a panel
    private void addButtons(JPanel cameraListPanel, String s) {
        JPanel addRemoveButtons = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel filterButtonPanel = new JPanel(new GridLayout(1, 1, 10, 10));

        JButton filterButton = new JButton("Filter " + s + " Collection");
        filterButton.addActionListener(this);
        filterButton.setActionCommand("filter " + s.toLowerCase());
        filterButtonPanel.add(filterButton);

        JPanel cameraButtons = new JPanel(new GridLayout(2, 1));
        addRemoveButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
        filterButtonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton addCameraButton = new JButton("Add " + s);
        addCameraButton.addActionListener(this);
        addCameraButton.setActionCommand("add " + s.toLowerCase());

        JButton removeCameraButton = new JButton("Remove " + s);
        removeCameraButton.addActionListener(this);
        removeCameraButton.setActionCommand("remove " + s.toLowerCase());

        addRemoveButtons.add(addCameraButton);
        addRemoveButtons.add(removeCameraButton);

        cameraButtons.add(filterButtonPanel);
        cameraButtons.add(addRemoveButtons);

        cameraListPanel.add(cameraButtons, BorderLayout.SOUTH);
    }

    // EFFECTS: allows the user to add a film to the collection
    private void doAddFilm() {
        JTextField nameField = new JTextField(10);
        JSpinner isoSpinner = new JSpinner(new SpinnerNumberModel(400, 0, null, 1));
        JTextField typeField = new JTextField(10);
        JTextField brandField = new JTextField(10);

        Camera[] cameras = new Camera[app.getCameraCollection().getSize()];
        String[] cameraStrings = new String[app.getCameraCollection().getSize()];
        int x = 0;
        for (Camera c : app.getCameraCollection().getCollection()) {
            cameras[x] = c;
            cameraStrings[x] = c.toString();
            x++;
        }
        JComboBox<String> cameraBox = new JComboBox<>(cameraStrings);

        JLabel nameLabel = new JLabel("Film Name:");
        JLabel isoLabel = new JLabel("Film ISO:");
        JLabel typeLabel = new JLabel("Film Type:");
        JLabel brandLabel = new JLabel("Film Brand:");
        JLabel cameraLabel = new JLabel("Select Camera:");

        JPanel panel = initAddFilmPanel(
                nameField, isoSpinner, typeField, brandField, cameraBox,
                nameLabel, isoLabel, typeLabel, brandLabel, cameraLabel);

        addFilmDialog(nameField, isoSpinner, typeField, brandField, cameras, cameraBox, panel);
    }

    // EFFECTS: initializes the panel that allows the user to enter the details of a new film
    private JPanel initAddFilmPanel(JTextField nameField,
                                    JSpinner isoSpinner,
                                    JTextField typeField,
                                    JTextField brandField,
                                    JComboBox<String> cameraBox,
                                    JLabel nameLabel,
                                    JLabel isoLabel,
                                    JLabel typeLabel,
                                    JLabel brandLabel,
                                    JLabel cameraLabel) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 40, 10));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(isoLabel);
        panel.add(isoSpinner);
        panel.add(typeLabel);
        panel.add(typeField);
        panel.add(brandLabel);
        panel.add(brandField);
        panel.add(cameraLabel);
        panel.add(cameraBox);
        return panel;
    }

    // EFFECTS: creates a pop-up allowing the user to enter information about a new film
    private void addFilmDialog(JTextField nameField,
                               JSpinner isoSpinner,
                               JTextField typeField,
                               JTextField brandField,
                               Camera[] cameras,
                               JComboBox<String> cameraBox,
                               JPanel panel) {
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Please enter the details of the camera", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.CANCEL_OPTION) {
            if (nameField.getText().isEmpty()
                    || brandField.getText().isEmpty()
                    || typeField.getText().isEmpty()) {
                showEmptyFieldMessage();
            } else {
                String name = nameField.getText();
                int iso = (int) isoSpinner.getValue();
                String type = typeField.getText();
                Camera camera = cameras[cameraBox.getSelectedIndex()];
                String brand = brandField.getText();
                Film film = new Film(name, iso, type, camera, brand);
                app.getFilmCollection().addFilm(film);
                app.setActiveFilm(film);
                gui.update(true);
            }
        }
    }

    // EFFECTS: allows the user to remove a film from the collection
    private void doRemoveFilm() {
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
        JOptionPane.showConfirmDialog(null, panel, "Please select a film to remove", JOptionPane.OK_CANCEL_OPTION);
        app.getFilmCollection().removeFilm(films[filmBox.getSelectedIndex()]);
        if (!app.getFilmCollection().getCollection().isEmpty()) {
            app.setActiveFilm(app.getFilmCollection().getRoll(0));
        } else {
            app.setActiveFilm(null);
        }
        gui.update(true);
    }

    // EFFECTS: allows the user to add a camera to the collection
    private void doAddCamera() {
        JTextField nameField = new JTextField(10);
        JTextField manufacturerField = new JTextField(10);
        JTextField filmTypeField = new JTextField(10);
        JLabel nameLabel = new JLabel("Camera Name:");
        JLabel manufacturerLabel = new JLabel("Camera Manufacturer:");
        JLabel filmTypeLabel = new JLabel("Camera Film Type:");

        JPanel panel = initAddCameraPanel(
                nameField, manufacturerField, filmTypeField, nameLabel, manufacturerLabel, filmTypeLabel);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Please enter the details of the camera", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.CANCEL_OPTION) {
            if (nameField.getText().isEmpty()
                    || manufacturerField.getText().isEmpty()
                    || filmTypeField.getText().isEmpty()) {
                showEmptyFieldMessage();
            } else {
                String name = nameField.getText();
                String manufacturer = manufacturerField.getText();
                String filmType = filmTypeField.getText();
                app.getCameraCollection().addCamera(new Camera(name, filmType, manufacturer));
            }
        }
    }

    // EFFECTS: initializes the panel that allows the user to enter the details of a new camera
    private JPanel initAddCameraPanel(JTextField nameField,
                                      JTextField manufacturerField,
                                      JTextField filmTypeField,
                                      JLabel nameLabel,
                                      JLabel manufacturerLabel,
                                      JLabel filmTypeLabel) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 40, 10));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(manufacturerLabel);
        panel.add(manufacturerField);
        panel.add(filmTypeLabel);
        panel.add(filmTypeField);
        return panel;
    }

    // EFFECTS: allows the user to remove a camera from the collection
    private void doRemoveCamera() {
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
        JOptionPane.showConfirmDialog(null, panel, "Please select a camera to remove:", JOptionPane.OK_CANCEL_OPTION);
        app.getCameraCollection().removeCamera(cameras[cameraBox.getSelectedIndex()]);
        gui.update(false);
    }

    // EFFECTS: allows the user to select a parameter to filter the film collection by
    private void doSelectFilterFilmParameter() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 0, 10));
        JButton[] buttons = new JButton[7];
        buttons[0] = new JButton("Filter by Name");
        buttons[1] = new JButton("Filter by ISO");
        buttons[2] = new JButton("Filter by Type");
        buttons[3] = new JButton("Filter by Camera");
        buttons[4] = new JButton("Filter by Brand");
        buttons[5] = new JButton("Filter by Expiry");
        buttons[6] = new JButton("Filter by Develop Date");
        for (JButton b : buttons) {
            b.addActionListener(this);
            b.setActionCommand("filter film by " + b.getText().substring(10).toLowerCase());
            panel.add(b);
        }

        JOptionPane.showMessageDialog(null, panel, "Please choose an option", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: filters the film collection by the given parameter
    public void doFilterFilmCollection(String parameter) {
        ArrayList<Film> filtered = new ArrayList<>();

        if ("filter film by name".equals(parameter) || "filter film by type".equals(parameter)
                || "filter film by brand".equals(parameter)) {
            filtered = filterFilmByString(parameter, getStringToFilter());
        } else if ("filter film by iso".equals(parameter)) {
            filtered = app.getFilmCollection().filterByIso(getIntToFilter());
        } else if ("filter film by camera".equals(parameter)) {
            filtered = app.getFilmCollection().filterByCamera(getCameraToFilter());
        } else if ("filter film by expiry".equals(parameter)) {
            filtered = app.getFilmCollection().filterByExpiry(getDate());
        } else if ("filter film by develop date".equals(parameter)) {
            filtered = app.getFilmCollection().filterByDevelopDate(getDate());
        }
        if (!filtered.isEmpty()) {
            showFilteredFilmTable(filtered);
        }
    }

    // EFFECTS: allows the user to enter a string to filter the film collection by
    public ArrayList<Film> filterFilmByString(String command, String parameter) {
        ArrayList<Film> filtered;
        if ("filter by name".equals(command)) {
            filtered = app.getFilmCollection().filterByName(parameter);
        } else if ("filter by type".equals(command)) {
            filtered = app.getFilmCollection().filterByType(parameter);
        } else if ("filter by brand".equals(command)) {
            filtered = app.getFilmCollection().filterByBrand(parameter);
        } else {
            filtered = app.getFilmCollection().filterByDevelopLocation(parameter);
        }
        return filtered;
    }

    // EFFECTS: allows the user to enter a number to filter the film collection by
    public int getIntToFilter() {
        JPanel panel = new JPanel();
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(400, 0, null, 1));
        panel.add(spinner);
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Please enter the parameter to filter by", JOptionPane.DEFAULT_OPTION);

        return (int) spinner.getValue();
    }

    // EFFECTS: allows the user to select a camera to filter the film collection by
    public Camera getCameraToFilter() {
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
        JOptionPane.showConfirmDialog(
                null, panel, "Please select a camera to filter by:", JOptionPane.OK_CANCEL_OPTION);
        return cameras[cameraBox.getSelectedIndex()];
    }

    // EFFECTS: allows the user to select a date to filter the film collection by
    public LocalDate getDate() {
        JPanel panel = new JPanel();
        JCalendar datePicker = new JCalendar();
        datePicker.setWeekOfYearVisible(false);
        datePicker.setDecorationBordersVisible(false);
        datePicker.setPreferredSize(new Dimension(300, 300));
        panel.add(datePicker);
        int result = JOptionPane.showConfirmDialog(null, panel, "Please enter the date", JOptionPane.DEFAULT_OPTION);
        return datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // EFFECTS: displays the filtered film list to the user
    public void showFilteredFilmTable(ArrayList<Film> filtered) {

        String[][] data = new String[filtered.size()][3];
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("ISO");
        int x = 0;
        for (Film f : filtered) {
            data[x][0] = f.getName();
            data[x][1] = f.getBrand();
            data[x][2] = Integer.toString(f.getIso());
            model.insertRow(x, data[x]);
            x++;
        }

        JPanel panel = new JPanel();

        JTable filmTable = new JTable(model);
        JPanel filmListPanel = new JPanel(new BorderLayout());
        filmListPanel.add(filmTable.getTableHeader(), BorderLayout.PAGE_START);
        filmListPanel.add(filmTable);

        JScrollPane filmListScrollPane = new JScrollPane(filmListPanel);
        filmListScrollPane.setPreferredSize(new Dimension(500, 300));
        panel.add(filmListScrollPane);
        JOptionPane.showMessageDialog(null, panel, "Filtered List", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: allows the user to select a parameter to filter the camera collection by
    private void doSelectFilterCameraParameter() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
        JButton[] buttons = new JButton[3];
        buttons[0] = new JButton("Filter by Name");
        buttons[1] = new JButton("Filter by Film Type");
        buttons[2] = new JButton("Filter by Manufacturer");
        for (JButton b : buttons) {
            b.addActionListener(this);
            b.setActionCommand("filter camera by " + b.getText().substring(10).toLowerCase());
            panel.add(b);
        }

        JOptionPane.showMessageDialog(null, panel, "Please choose an option", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: filters the camera collection by the given parameter
    public void doFilterCameraCollection(String parameter) {
        ArrayList<Camera> filtered = new ArrayList<>();

        if ("filter camera by name".equals(parameter)) {
            filtered = app.getCameraCollection().filterByName(getStringToFilter());
        } else if ("filter camera by manufacturer".equals(parameter)) {
            filtered = app.getCameraCollection().filterByManufacturer(getStringToFilter());
        } else if ("filter camera by film type".equals(parameter)) {
            filtered = app.getCameraCollection().filterByFilmType(getStringToFilter());
        }
        if (!filtered.isEmpty()) {
            showFilteredCameraTable(filtered);
        }
    }

    // EFFECTS: allows the user to enter a string to filter either collection.
    public String getStringToFilter() throws NumberFormatException {
        JPanel panel = new JPanel();
        JTextField field = new JTextField();
        panel.add(field);
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Please enter the parameter to filter by", JOptionPane.DEFAULT_OPTION);

        return field.getText();
    }

    // EFFECTS: displays the filtered camera list to the user
    public void showFilteredCameraTable(ArrayList<Camera> filtered) {

        String[][] data = new String[filtered.size()][3];
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Manufacturer");
        model.addColumn("Name");
        model.addColumn("Film Type");
        int x = 0;
        for (Camera c : filtered) {
            data[x][0] = c.getManufacturer();
            data[x][1] = c.getName();
            data[x][2] = c.getFilmType();
            model.insertRow(x, data[x]);
            x++;
        }

        JPanel panel = new JPanel();

        JTable filmTable = new JTable(model);
        JPanel filmListPanel = new JPanel(new BorderLayout());
        filmListPanel.add(filmTable.getTableHeader(), BorderLayout.PAGE_START);
        filmListPanel.add(filmTable);

        JScrollPane filmListScrollPane = new JScrollPane(filmListPanel);
        filmListScrollPane.setPreferredSize(new Dimension(500, 300));
        panel.add(filmListScrollPane);
        JOptionPane.showMessageDialog(null, panel, "Filtered List", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: displays an error message if a field is left empty
    private void showEmptyFieldMessage() {
        JOptionPane.showMessageDialog(null,
                "Please do not leave any fields blank.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: returns array of film data from the collection
    private String[][] getFilmData() {
        String[][] data;

        try {
            FilmCollection fc = app.getFilmCollection();
            int size = fc.getSize();
            data = new String[size][3];

            int x = 0;
            for (Film f : fc.getCollection()) {
                data[x][0] = f.getName();
                data[x][1] = f.getBrand();
                data[x][2] = Integer.toString(f.getIso());
                x++;
            }
        } catch (NullPointerException e) {
            data = null;
        }
        return data;
    }

    // EFFECTS: returns array of camera data from the collection
    private String[][] getCameraData() {
        String[][] data;
        CameraCollection cc = app.getCameraCollection();
        int size = cc.getSize();
        data = new String[size][3];

        int x = 0;
        for (Camera c : cc.getCollection()) {
            data[x][0] = c.getManufacturer();
            data[x][1] = c.getName();
            data[x][2] = c.getFilmType();
            x++;
        }
        return data;
    }

    // EFFECTS: updates the panel with new information whenever a film or camera is added or removed.
    public void update() {

        String[][] filmData = getFilmData();
        DefaultTableModel model = (DefaultTableModel) filmTable.getModel();
        model.setRowCount(0);
        for (int x = 0; x < filmData.length; x++) {
            model.insertRow(x, filmData[x]);
        }

        String[][] cameraData = getCameraData();
        model = (DefaultTableModel) cameraTable.getModel();
        model.setRowCount(0);
        for (int x = 0; x < cameraData.length; x++) {
            model.insertRow(x, cameraData[x]);
        }
    }

    // EFFECTS: handles all action commands from the panel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("add film".equals(command)) {
            doAddFilm();
        } else if ("remove film".equals(command)) {
            doRemoveFilm();
        } else if ("add camera".equals(command)) {
            doAddCamera();
        } else if ("remove camera".equals(command)) {
            doRemoveCamera();
        } else if ("filter film".equals(command)) {
            doSelectFilterFilmParameter();
        } else if ("filter camera".equals(command)) {
            doSelectFilterCameraParameter();
        } else if (command.startsWith("film", 7)) {
            doFilterFilmCollection(command);
        } else if (command.startsWith("camera", 7)) {
            doFilterCameraCollection(command);
        }
    }
}

package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import ui.panels.EditPanel;
import ui.panels.FilmPanel;
import ui.panels.ListPanel;
import ui.panels.PhotoPanel;

import javax.swing.*;
import java.awt.*;

/*
 * Represents a graphical user interface that allows the user to interact with their Analog Archive Application
 *
 * WIDTH: represents the minimum width of the GUI
 * HEIGHT: represents the minimum height of the GUI
 * filmPanel: represents the panel of the GUI that displays film information
 * photoPanel: represents the panel of the GUI that displays the photos
 * listPanel: represents the panel of the GUI that displays lists of the film and camera collections
 */
public class AnalogArchiveGUI extends JFrame {

    private static final int WIDTH = 1591;
    private static final int HEIGHT = 695;

    private final FilmPanel filmPanel;
    private final PhotoPanel photoPanel;
    private final ListPanel listPanel;

    //constructs a GUI with menu bar, and  all required panels, along with dark mode styling
    public AnalogArchiveGUI() {
        super("Analog Archive");
        AnalogArchiveApp app = new AnalogArchiveApp();

        FlatDarkLaf.install();

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        MenuBar menuBar = new MenuBar(app, this);
        setJMenuBar(menuBar);

        filmPanel = new FilmPanel(app, this);
        add(filmPanel, BorderLayout.WEST);

        EditPanel editPanel = new EditPanel(app, this);
        add(editPanel, BorderLayout.PAGE_END);

        listPanel = new ListPanel(app, this);
        add(listPanel, BorderLayout.EAST);

        photoPanel = new PhotoPanel(app);
        add(photoPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // EFFECTS: updates all panels in the GUI
    public void update(Boolean updatePhotoPanel) {
        listPanel.update();
        filmPanel.update();
        if (updatePhotoPanel) {
            JDialog dialog = new JDialog((JFrame) null, "", true);
            dialog.setSize(new Dimension(300, 25));
            JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
            progressBar.setIndeterminate(true);
            dialog.add(progressBar);
            dialog.setLocationRelativeTo(null);
            dialog.setUndecorated(true);
            Thread t = new Thread(() -> dialog.setVisible(true));
            t.start();
            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                photoPanel.update();
                dialog.setVisible(false);
            });
            t2.start();
        }
    }
}

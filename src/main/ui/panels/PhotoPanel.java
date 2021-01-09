package ui.panels;

import ui.AnalogArchiveApp;
import net.coobird.thumbnailator.Thumbnailator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Represents a panel displayed in the middle of the GUI that displays photos from the current active film
 *
 * EXTENSIONS: represents an array of extensions accepted as images
 * IMAGE_FILTER: represents a filter that is used to filter all files in a given
 *               directory with files ending with anything in the EXTENSIONS array
 *  app: represents the current state of the app, including all of the properties of the archive
 *  imageList: represents a list of JLabels used to hold all images found in the directory
 *  dir: represents the File at given directory, usually a folder
 *  activeImage: represents the image that is currently displayed to the user
 *
 */
public class PhotoPanel extends JPanel implements ActionListener {

    static final String[] EXTENSIONS = new String[]{"gif", "png", "jpg", "jpeg"};
    static final FilenameFilter IMAGE_FILTER = (dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
    };
    private final AnalogArchiveApp app;
    ArrayList<JLabel> imageList;
    private File dir;
    private JLabel activeImage;

    // EFFECTS: constructs the panel with "next" and "previous" buttons
    public PhotoPanel(AnalogArchiveApp app) {
        this.app = app;

        setLayout(new BorderLayout());

        imageList = new ArrayList<>();

        try {
            dir = new File(app.getActiveFilm().getDirectory());
            loadImages();
        } catch (NullPointerException e) {
            //
        }
        activeImage = new JLabel();

        JButton nextButton = new JButton("next");
        nextButton.addActionListener(this);
        nextButton.setActionCommand("next");

        JButton previousButton = new JButton("previous");
        previousButton.addActionListener(this);
        previousButton.setActionCommand("previous");

        add(nextButton, BorderLayout.EAST);
        add(previousButton, BorderLayout.WEST);
        add(activeImage, BorderLayout.CENTER);
    }

    // EFFECTS: loads all images from the directory
    public void loadImages() {
        imageList.clear();
        if (dir.isDirectory()) {
            for (File f : Objects.requireNonNull(dir.listFiles(IMAGE_FILTER))) {
                try {
                    BufferedImage resized = Thumbnailator.createThumbnail(ImageIO.read(f), 800, 600);

                    ImageIcon ii = new ImageIcon(resized);
                    imageList.add(new JLabel(ii));
                } catch (final IOException e) {
                    //
                }
            }
            if (!imageList.isEmpty()) {
                activeImage = imageList.get(0);
            } else {
                activeImage = null;
            }
        }
    }

    // EFFECTS: updates the panel with images from new directory
    public void update() {
        if (activeImage != null) {
            remove(activeImage);
        }
        try {
            dir = new File(app.getActiveFilm().getDirectory());
            loadImages();
        } catch (NullPointerException e) {
            imageList = new ArrayList<>();
        }

        if (!imageList.isEmpty()) {
            activeImage = imageList.get(0);
            add(activeImage);
        } else {
            activeImage = null;
        }

        revalidate();
        repaint();
    }

    // EFFECTS: handles all action command for the panel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("next")) {
            remove(activeImage);
            int index = imageList.indexOf(activeImage);
            activeImage = imageList.get(index + 1);
            add(activeImage);
        } else if (command.equals("previous")) {
            remove(activeImage);
            int index = imageList.indexOf(activeImage);
            activeImage = imageList.get(index - 1);
            add(activeImage);
        }
        revalidate();
        repaint();
    }
}

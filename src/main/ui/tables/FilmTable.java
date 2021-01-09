package ui.tables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FilmTable extends JTable {

    public FilmTable(DefaultTableModel model) {
        super(model);

        setPreferredSize(new Dimension(300, 0));
        setLayout(new BorderLayout());
        setBackground(new Color(60, 63, 65));
        getTableHeader().setBackground(new Color(60, 63, 65));
        setBorder(new EmptyBorder(0, 0, 0, 0));

        getColumn("ISO").setPreferredWidth(1);
        getColumn("Name").setPreferredWidth(10);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}


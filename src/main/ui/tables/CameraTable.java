package ui.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CameraTable extends JTable {

    public CameraTable(DefaultTableModel model) {
        super(model);

        setPreferredSize(new Dimension(300, 0));
        setLayout(new BorderLayout());
        setBackground(new Color(60, 63, 65));
        getTableHeader().setBackground(new Color(60, 63, 65));
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

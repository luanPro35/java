package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public abstract class BasePanel extends JPanel {
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane scrollPane;
    protected JButton addButton;
    protected JButton refreshButton;

    public BasePanel(String[] columnNames) {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Thêm");
        refreshButton = new JButton("Làm Mới");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setAddActionListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setRefreshActionListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    protected abstract void onAddData();

    protected abstract void onRefreshData();
}

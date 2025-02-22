package org.example;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class BottomPanel extends JPanel {
    private DefaultTableModel selectedItemModel;

    public BottomPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(255, 182, 193));

        selectedItemModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng"}, 0);
        JTable selectedItemTable = new JTable(selectedItemModel);
        JScrollPane selectedItemScrollPane = new JScrollPane(selectedItemTable);
        selectedItemScrollPane.setPreferredSize(new Dimension(600, 100));

        add(selectedItemScrollPane);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        add(rightPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 182, 193));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel maKhachHangLabel = new JLabel("Mã Khách Hàng:");
        JTextField maKhachHangField = new JTextField(10);
        leftPanel.add(maKhachHangLabel);
        leftPanel.add(maKhachHangField);

        add(leftPanel);
    }
}

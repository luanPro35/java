package org.example;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        setLayout(new GridLayout(5, 1));
        setBackground(new Color(224, 255, 255));

        String[] buttonLabels = {"Thống kê", "Sản phẩm", "Hóa đơn", "Khách hàng", "Đánh Giá"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(173, 216, 230));
            add(button);
        }
    }
}

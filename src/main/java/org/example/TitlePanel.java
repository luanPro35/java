package org.example;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(135, 206, 250));

        JLabel titleLabel = new JLabel("Restaurant Manager");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 25, 112));
        add(titleLabel);
    }
}

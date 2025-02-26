package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Login extends JFrame {

    public Login() {
        setTitle("Đăng nhập vào phần mềm");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel chính chứa hai phần
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel bên trái (màu xanh lá)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(50, 150, 80));
        leftPanel.setPreferredSize(new Dimension(200, 300));
        leftPanel.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(Color.WHITE);
        JLabel iconLabel = new JLabel("\uD83D\uDC64"); // Icon người dùng (Unicode)

        leftPanel.add(iconLabel);
        leftPanel.add(loginLabel);

        // Panel bên phải (màu xanh đậm)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(20, 40, 60));
        rightPanel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(30, 30, 80, 25);

        JTextField userText = new JTextField();
        userText.setBounds(30, 55, 200, 30);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(30, 90, 80, 25);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(30, 115, 200, 30);
        JButton hienThiPass = new JButton("👁");
        hienThiPass.setBounds(370, 143, 20, 20);
        hienThiPass.addActionListener(new ActionListener() {
            boolean[] hienMatKhau = new boolean[]{false};
            @Override
            public void actionPerformed(ActionEvent e) {
                if(this.hienMatKhau[0]){
                    passText.setEchoChar('•');// Đặt ký tự che mật khẩu
                    this.hienMatKhau[0] = false;
                } else {
                    passText.setEchoChar((char) 0); // Hiển thị mật khẩu
                    this.hienMatKhau[0] = true;
                }
            }
        });
        this.add(hienThiPass);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(30, 160, 200, 35);
        loginButton.setBackground(new Color(50, 150, 80));
        loginButton.setForeground(Color.WHITE);

        JLabel forgotPassLabel = new JLabel("Quên mật khẩu ?");
        forgotPassLabel.setForeground(Color.LIGHT_GRAY);
        forgotPassLabel.setBounds(30, 200, 150, 25);

        // Thêm thành phần vào rightPanel
        rightPanel.add(userLabel);
        rightPanel.add(userText);
        rightPanel.add(passLabel);
        rightPanel.add(passText);
        rightPanel.add(loginButton);
        rightPanel.add(forgotPassLabel);

        // Thêm hai panel vào mainPanel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

}
public class LoginApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}


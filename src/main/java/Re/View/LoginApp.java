package Re.View;

import Re.Controller.MainScreen; // Thêm import đúng cho MainScreen
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class LoginApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(LoginApp::createAndShowLoginGUI);
    }

    private static void createAndShowLoginGUI() {
        JFrame loginFrame = new JFrame("Đăng nhập");
        loginFrame.setSize(400, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Tên người dùng:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Đăng Nhập");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFrame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        loginFrame.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {
                loginFrame.dispose(); // Đóng cửa sổ đăng nhập
                MainScreen.showMainScreen(); // Chuyển đến giao diện chính
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }
}
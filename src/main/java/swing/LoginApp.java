package swing;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginApp {

    public static void main(String[] args) {
        // Cài đặt FlatLaf để có giao diện hiện đại
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(LoginApp::createAndShowLoginScreen);
    }

    public static void createAndShowLoginScreen() {
        // Tạo khung chính
        JFrame loginFrame = new JFrame("Đăng Nhập");
        loginFrame.setSize(450, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // Tiêu đề hoặc logo
        JLabel titleLabel = new JLabel("WELCOME TO MY APP", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa form đăng nhập
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Các thành phần giao diện
        JLabel usernameLabel = new JLabel("Tên người dùng:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));

        JButton loginButton = new JButton("Đăng Nhập");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hiệu ứng hover cho nút
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 153, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
        });

        // Bố trí các thành phần trong panel form
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(loginButton, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Sự kiện nút đăng nhập
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(loginFrame, "Đăng nhập thành công!");
                loginFrame.setVisible(false);
                Re.showMainScreen(); // Gọi phương thức showMainScreen của lớp Re
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Tên người dùng hoặc mật khẩu không đúng!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm main panel vào frame
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }
}

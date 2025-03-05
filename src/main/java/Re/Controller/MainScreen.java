package Re.Controller;

import Re.View.RestaurantApp;
import Re.View.*;
import javax.swing.*;
import java.awt.*;

public class MainScreen {
    private static boolean isMainScreenVisible = true;
    private static JPanel mainContentPanel;
    private static RestaurantApp restaurantApp;

    public static void showMainScreen() {
        JFrame frame = new JFrame("Hệ Thống Quản Lý Nhà Hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Tiêu đề và biểu tượng
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(135, 206, 250));
        JLabel titleLabel = new JLabel("Restaurant Manager");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 25, 112));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Thanh bên
        JPanel sidebar = new JPanel(new GridLayout(6, 1)); // 6 hàng cho 6 nút
        sidebar.setBackground(new Color(224, 255, 255));
        String[] buttonLabels = {"Thống kê", "Sản phẩm", "Hóa đơn", "Khách hàng", "Đánh Giá", "Bàn ngồi"};
        JButton[] buttons = new JButton[6];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setBackground(new Color(173, 216, 230));
            if (i == 5) { // Chỉ áp dụng cho nút "Swap" (vị trí 5)
                buttons[i].setPreferredSize(new Dimension(80, 40)); // Kích thước nhỏ hơn (80x40)
                buttons[i].setMaximumSize(new Dimension(80, 40)); // Giới hạn kích thước tối đa
            } else {
                buttons[i].setPreferredSize(new Dimension(120, 60)); // Kích thước mặc định cho các nút khác
            }
            sidebar.add(buttons[i]);
        }
        frame.add(sidebar, BorderLayout.WEST);

        // Nội dung chính
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBackground(new Color(245, 245, 245));

        // Thêm các tab của MainScreen
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new StatisticsPanel(frame, contentPanel).getPanel(), "Thống Kê");
        contentPanel.add(new ProductPanel(frame, contentPanel).getPanel(), "Sản Phẩm");
        contentPanel.add(new InvoicePanel(frame, contentPanel).getPanel(), "Hóa Đơn");
        contentPanel.add(new CustomerPanel(frame, contentPanel).getPanel(), "Khách Hàng");
        contentPanel.add(new ReviewPanel(frame, contentPanel).getPanel(), "Đánh Giá");

        // Thêm panel của MainScreen vào mainContentPanel
        mainContentPanel.add(contentPanel, "MainScreen");

        // Khởi tạo RestaurantApp
        restaurantApp = new RestaurantApp();
        JPanel restaurantPanel = restaurantApp.getTablePanel(); // Giả định RestaurantApp có phương thức getTablePanel()
        mainContentPanel.add(restaurantPanel, "RestaurantApp");

        frame.add(mainContentPanel, BorderLayout.CENTER);

        // Sự kiện chuyển tab
        buttons[0].addActionListener(e -> {
            if (isMainScreenVisible) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "Thống Kê");
            }
        });

        buttons[1].addActionListener(e -> {
            if (isMainScreenVisible) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "Sản Phẩm");
            }
        });

        buttons[2].addActionListener(e -> {
            if (isMainScreenVisible) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "Hóa Đơn");
            }
        });

        buttons[3].addActionListener(e -> {
            if (isMainScreenVisible) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "Khách Hàng");
            }
        });

        buttons[4].addActionListener(e -> {
            if (isMainScreenVisible) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "Đánh Giá");
            }
        });

        // Sự kiện nút Swap
        buttons[5].addActionListener(e -> {
            CardLayout cl = (CardLayout) mainContentPanel.getLayout();
            if (isMainScreenVisible) {
                cl.show(mainContentPanel, "RestaurantApp");
                isMainScreenVisible = false;
            } else {
                cl.show(mainContentPanel, "MainScreen");
                isMainScreenVisible = true;
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
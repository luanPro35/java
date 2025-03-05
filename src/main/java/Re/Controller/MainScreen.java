package Re.Controller;

import Re.View.*;
import javax.swing.*;
import java.awt.*;

public class MainScreen {
    private static boolean isMainScreenVisible = true;
    private static JPanel mainContentPanel;
    private static RestaurantApp restaurantApp;
    private static JFrame frame;

    public static void showMainScreen() {
        frame = new JFrame("Restaurant Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700); // Tăng kích thước để phù hợp với nhiều tab
        frame.setLayout(new BorderLayout());

        // Tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(135, 206, 235)); // Màu xanh nhạt giống hình
        JLabel titleLabel = new JLabel("Hệ Thống Quản Lý Nhà Hàng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Thanh bên (Sidebar)
        JPanel sidebar = new JPanel(new GridLayout(8, 1)); // 8 hàng cho 8 tab
        sidebar.setBackground(new Color(135, 206, 235));
        sidebar.setPreferredSize(new Dimension(200, 0));
        String[] buttonLabels = {
                "Quản lý bán hàng", "Quản lý sản phẩm", "Lịch sử nhập sản phẩm",
                "Quản lý khách hàng", "Chương trình khuyến mãi & ưu đãi", "Thống kê",
                "Hóa đơn", "Bàn ngồi"
        };
        JButton[] buttons = new JButton[8];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(new Color(135, 206, 235));
            buttons[i].setBorderPainted(false);
            buttons[i].setHorizontalAlignment(SwingConstants.LEFT);
            buttons[i].setPreferredSize(new Dimension(180, 50)); // Kích thước đồng đều
            buttons[i].setOpaque(true);
            sidebar.add(buttons[i]);
        }
        frame.add(sidebar, BorderLayout.WEST);

        // Nội dung chính
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // Tạo các panel cho các tab
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new SalesPanel(frame, contentPanel).getPanel(), "Quản lý bán hàng");
        contentPanel.add(new ProductManagementPanel(frame, contentPanel).getPanel(), "Quản lý sản phẩm");
        contentPanel.add(new ImportHistoryPanel(frame, contentPanel).getPanel(), "Lịch sử nhập sản phẩm");
        contentPanel.add(new CustomerPanel(frame, contentPanel).getPanel(), "Quản lý khách hàng"); // Sử dụng CustomerPanel
        contentPanel.add(new PromotionPanel(frame, contentPanel).getPanel(), "Chương trình khuyến mãi & ưu đãi");
        contentPanel.add(new RevenueStatisticsPanel(frame, contentPanel).getPanel(), "Thống kê");
        contentPanel.add(new InvoicePanel(frame, contentPanel).getPanel(), "Hóa đơn");

        mainContentPanel.add(contentPanel, "MainScreen");

        // Khởi tạo RestaurantApp
        restaurantApp = new RestaurantApp();
        JPanel restaurantPanel = restaurantApp.getTablePanel();
        mainContentPanel.add(restaurantPanel, "Bàn ngồi");

        frame.add(mainContentPanel, BorderLayout.CENTER);

        // Thanh công cụ
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        toolBar.setBackground(new Color(200, 200, 200));
        JButton addInvoiceButton = new JButton("Thêm Hóa đơn");
        JButton refreshButton = new JButton("Làm mới");
        toolBar.add(addInvoiceButton);
        toolBar.add(refreshButton);
        frame.add(toolBar, BorderLayout.SOUTH);

        // Sự kiện chuyển tab
        for (int i = 0; i < 8; i++) {
            final int index = i;
            buttons[i].addActionListener(e -> {
                CardLayout cl = (CardLayout) mainContentPanel.getLayout();
                if (buttonLabels[index].equals("Bàn ngồi")) {
                    cl.show(mainContentPanel, "Bàn ngồi");
                    isMainScreenVisible = false;
                } else {
                    cl.show(mainContentPanel, "MainScreen");
                    CardLayout clContent = (CardLayout) contentPanel.getLayout();
                    clContent.show(contentPanel, buttonLabels[index]);
                    isMainScreenVisible = true;
                }
            });
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
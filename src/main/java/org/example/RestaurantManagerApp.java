package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RestaurantManagerApp {

    public static void main(String[] args) {
        // Tạo cửa sổ chính
        JFrame frame = new JFrame("Hệ Thống Quản Lý Nhà Hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Tiêu đề và biểu tượng
        frame.add(new TitlePanel(), BorderLayout.NORTH);

        // Thanh bên
        JPanel sidebar = new JPanel(new GridLayout(5, 1)); // 5 nút trong thanh bên
        sidebar.setBackground(new Color(224, 255, 255));
        String[] buttonLabels = {"Thống kê", "Sản phẩm", "Hóa đơn", "Khách hàng", "Đánh Giá"};
        JButton[] buttons = new JButton[5];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            buttons[i].setBackground(new Color(173, 216, 230));
            sidebar.add(buttons[i]);
        }
        frame.add(sidebar, BorderLayout.WEST);

        // Nội dung chính
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Tạo các panel cho các tab
        StatisticsPanel statisticsPanel = new StatisticsPanel();
        contentPanel.add(statisticsPanel, "Thống kê");

        ProductPanel productPanel = new ProductPanel(contentPanel);
        contentPanel.add(productPanel, "Sản Phẩm");

        InvoicePanel invoicePanel = new InvoicePanel(contentPanel);
        contentPanel.add(invoicePanel, "Hóa Đơn");

        CustomerPanel customerPanel = new CustomerPanel();
        contentPanel.add(customerPanel, "Khách hàng");

        ReviewPanel reviewPanel = new ReviewPanel() {
            @Override
            protected void onAddData() {
                // Thực hiện thêm dữ liệu khi cần thiết
            }

            @Override
            protected void onRefreshData() {
                // Thực hiện làm mới dữ liệu khi cần thiết
            }
        };
        contentPanel.add(reviewPanel, "Đánh Giá");

        // Xử lý chuyển đổi giữa các tab khi nhấn nút
        buttons[0].addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "Thống kê");  // Khớp tên panel
        });

        buttons[1].addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "Sản Phẩm");  // Khớp tên panel
        });

        buttons[2].addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "Hóa Đơn");  // Khớp tên panel
        });

        buttons[3].addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "Khách hàng");  // Khớp tên panel
        });

        buttons[4].addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "Đánh Giá");  // Khớp tên panel
        });

        // Hiển thị cửa sổ
        frame.setVisible(true);
    }
}

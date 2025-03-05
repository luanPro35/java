package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.KhachHang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CustomerPanel {
    private JPanel panel;
    private JFrame frame;

    public CustomerPanel(JFrame frame, JPanel contentPanel) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());

        // Tạo bảng khách hàng
        DefaultTableModel customerModel = new DefaultTableModel(new String[]{"Mã KH", "Tên KH", "Điện thoại"}, 0);
        JTable customerTable = new JTable(customerModel);
        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        panel.add(customerScrollPane, BorderLayout.CENTER);

        // Nút điều khiển
        JPanel customerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addCustomerButton = new JButton("Thêm Khách Hàng");
        JButton refreshCustomerButton = new JButton("Làm Mới");
        customerButtonPanel.add(addCustomerButton);
        customerButtonPanel.add(refreshCustomerButton);
        panel.add(customerButtonPanel, BorderLayout.SOUTH);

        // Sự kiện nút Thêm Khách Hàng
        addCustomerButton.addActionListener(e -> {
            try {
                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
                int maKhachHang = Integer.parseInt(maKhachHangStr);
                String tenKhachHang = JOptionPane.showInputDialog("Nhập tên khách hàng:");
                String dienThoai = JOptionPane.showInputDialog("Nhập điện thoại:");

                KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, dienThoai);

                try (Connection conn = DatabaseConnection.connect()) {
                    if (conn == null) return;
                    String query = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, khachHang.getMaKhachHang());
                        stmt.setString(2, khachHang.getTenKhachHang());
                        stmt.setString(3, khachHang.getSoDienThoai());
                        stmt.executeUpdate();
                    }
                    customerModel.addRow(new Object[]{maKhachHang, tenKhachHang, dienThoai});
                    JOptionPane.showMessageDialog(frame, "Thêm khách hàng thành công!");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm khách hàng!");
            }
        });

        // Sự kiện nút Làm Mới
        refreshCustomerButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "SELECT * FROM KhachHang";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {
                    customerModel.setRowCount(0);
                    while (rs.next()) {
                        customerModel.addRow(new Object[]{
                                rs.getInt("maKhachHang"),
                                rs.getString("tenKhachHang"),
                                rs.getString("dienThoai")
                        });
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.KhachHang;
import javax.swing.*;
import java.sql.*;

public class CustomerManagementPanel extends BasePanel {
    public CustomerManagementPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Mã khách hàng", "Tên khách hàng", "Số điện thoại"});
    }

    @Override
    protected void handleAdd() {
        try {
            String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
            int maKhachHang = Integer.parseInt(maKhachHangStr);
            String tenKhachHang = JOptionPane.showInputDialog("Nhập tên khách hàng:");
            String soDienThoai = JOptionPane.showInputDialog("Nhập số điện thoại:");

            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai);

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String query = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, khachHang.getMaKhachHang());
                    stmt.setString(2, khachHang.getTenKhachHang());
                    stmt.setString(3, khachHang.getSoDienThoai());
                    stmt.executeUpdate();
                }
                tableModel.addRow(new Object[]{maKhachHang, tenKhachHang, soDienThoai});
                JOptionPane.showMessageDialog(frame, "Thêm khách hàng thành công!");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT * FROM KhachHang";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("maKhachHang"),
                            rs.getString("tenKhachHang"),
                            rs.getString("soDienThoai")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }
}
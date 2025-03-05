package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.SanPham;
import javax.swing.*;
import java.sql.*;

public class ProductManagementPanel extends BasePanel {
    public ProductManagementPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Mã sản phẩm", "Ảnh sản phẩm", "Tên sản phẩm", "Giá bán", "Loại hàng", "Trạng thái"});
    }

    @Override
    protected void handleAdd() {
        try {
            String maSanPhamStr = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
            if (maSanPhamStr == null || maSanPhamStr.isEmpty()) return;
            int maSanPham = Integer.parseInt(maSanPhamStr);

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String checkQuery = "SELECT COUNT(*) FROM SanPham WHERE maSanPham = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setInt(1, maSanPham);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(frame, "Sản phẩm đã tồn tại!");
                        return;
                    }
                }
            }

            String tenSanPham = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
            String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá bán:");
            double giaSanPham = Double.parseDouble(giaSanPhamStr);
            String loaiHang = JOptionPane.showInputDialog("Nhập loại hàng:");
            String trangThai = JOptionPane.showInputDialog("Nhập trạng thái:");

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String query = "INSERT INTO SanPham (maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham) VALUES (?, ?, ?, 0, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, maSanPham);
                    stmt.setString(2, tenSanPham);
                    stmt.setDouble(3, giaSanPham);
                    stmt.setString(4, loaiHang);
                    stmt.executeUpdate();
                }
                tableModel.addRow(new Object[]{maSanPham, "", tenSanPham, giaSanPham, loaiHang, trangThai});
                JOptionPane.showMessageDialog(frame, "Thêm sản phẩm thành công!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT maSanPham, tenSanPham, giaSanPham, loaiSanPham FROM SanPham";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("maSanPham"),
                            "", // Placeholder cho ảnh
                            rs.getString("tenSanPham"),
                            rs.getDouble("giaSanPham"),
                            rs.getString("loaiSanPham"),
                            "Còn hàng" // Giả định trạng thái
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }
}
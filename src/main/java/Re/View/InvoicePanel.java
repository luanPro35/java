package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.HoaDon;
import javax.swing.*;
import java.sql.*;

public class InvoicePanel extends BasePanel {
    public InvoicePanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Mã Hóa Đơn", "Mã KH", "Ngày Lập", "Thành Tiền"});
    }

    @Override
    protected void handleAdd() {
        try {
            String maHoaDonStr = JOptionPane.showInputDialog("Nhập mã hóa đơn:");
            int maHoaDon = Integer.parseInt(maHoaDonStr);
            String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
            int maKhachHang = Integer.parseInt(maKhachHangStr);
            String maSanPham = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
            String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng:");
            int soLuong = Integer.parseInt(soLuongStr);
            String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá sản phẩm:");
            double giaSanPham = Double.parseDouble(giaSanPhamStr);
            double thanhTien = soLuong * giaSanPham;
            String ngayLap = JOptionPane.showInputDialog("Nhập ngày lập hóa đơn (YYYY-MM-DD):");

            HoaDon hoaDon = new HoaDon(maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap);

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String query = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, hoaDon.getMaHoaDon());
                    stmt.setInt(2, hoaDon.getMaKhachHang());
                    stmt.setString(3, hoaDon.getMaSanPham());
                    stmt.setInt(4, hoaDon.getSoLuong());
                    stmt.setDouble(5, hoaDon.getGiaSanPham());
                    stmt.setDouble(6, hoaDon.getThanhTien());
                    stmt.setString(7, hoaDon.getNgayLap());
                    stmt.executeUpdate();
                }
                tableModel.addRow(new Object[]{maHoaDon, maKhachHang, ngayLap, thanhTien});
                JOptionPane.showMessageDialog(frame, "Thêm hóa đơn thành công!");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT * FROM HoaDon";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("maHoaDon"),
                            rs.getInt("maKhachHang"),
                            rs.getString("ngayLap"),
                            rs.getDouble("thanhTien")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }
}
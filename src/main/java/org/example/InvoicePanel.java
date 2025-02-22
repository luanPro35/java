package org.example;

import javax.swing.*;
import java.sql.*;

public class InvoicePanel extends BasePanel {

    public InvoicePanel(JPanel contentPanel) {
        super(new String[]{"Mã Hóa Đơn", "Mã KH", "Ngày Lập", "Thành Tiền"});
    }

    @Override
    protected void onAddData() {
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
            String ngayLap = JOptionPane.showInputDialog("Nhập ngày lập hóa đơn:");

            try (Connection conn = connectToDatabase()) {
                if (conn == null) return;

                String query = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, maHoaDon);
                    stmt.setInt(2, maKhachHang);
                    stmt.setString(3, maSanPham);
                    stmt.setInt(4, soLuong);
                    stmt.setDouble(5, giaSanPham);
                    stmt.setDouble(6, thanhTien);
                    stmt.setString(7, ngayLap);
                    stmt.executeUpdate();
                }

                tableModel.addRow(new Object[]{maHoaDon, maKhachHang, ngayLap, thanhTien});
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hóa đơn!");
        }
    }

    @Override
    protected void onRefreshData() {
        try (Connection conn = connectToDatabase()) {
            if (conn == null) return;

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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu!");
        }
    }

    private Connection connectToDatabase() {
        return null;
    }
}

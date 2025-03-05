package org.example;

import Re.Model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DanhGiaManager {
    private Connection conn;

    public DanhGiaManager() {
        // Mở kết nối với cơ sở dữ liệu
        conn = databaseConnection.connect();
        if (conn != null) {
            // Thêm shutdown hook để tự động xóa danh sách đánh giá khi ứng dụng kết thúc
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                xoaDanhGia();
            }));
        }
    }

    // Phương thức xóa các đánh giá khi ứng dụng tắt
    private void xoaDanhGia() {
        try {
            if (conn != null) {
                String query = "DELETE FROM DanhGia";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.executeUpdate();
                    System.out.println("Các đánh giá đã được xóa.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm một đánh giá vào cơ sở dữ liệu
    public void themDanhGia(int id, String tenNguoiDanhGia, String noiDung, String ngayDanhGia) {
        try {
            String query = "INSERT INTO DanhGia (id, tenNguoiDanhGia, noiDung, ngayDanhGia) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.setString(2, tenNguoiDanhGia);
                stmt.setString(3, noiDung);
                stmt.setString(4, ngayDanhGia);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

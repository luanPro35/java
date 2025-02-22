package org.example;

import org.example.BasePanel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public abstract class ReviewPanel extends BasePanel {
    public ReviewPanel() {
        super(new String[]{"Tên Người Gửi", "Đánh Giá", "Ngày Đánh Giá"});

        // Action cho thêm đánh giá
        setAddActionListener(e -> {
            String tenNguoiGui = JOptionPane.showInputDialog("Nhập tên người gửi:");
            String danhGia = JOptionPane.showInputDialog("Nhập đánh giá:");

            try (Connection conn = databaseConnection.connect()) {
                String query = "INSERT INTO DanhGia (tenNguoiGui, danhGia) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, tenNguoiGui);
                    stmt.setString(2, danhGia);
                    stmt.executeUpdate();

                    getTableModel().addRow(new Object[] {
                            tenNguoiGui,
                            danhGia,
                            new java.util.Date()
                    });

                    JOptionPane.showMessageDialog(null, "Thêm đánh giá thành công!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm đánh giá!");
            }
        });

        // Action cho làm mới dữ liệu
        setRefreshActionListener(e -> {
            try (Connection conn = databaseConnection.connect()) {
                String query = "SELECT * FROM DanhGia";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    getTableModel().setRowCount(0); // Làm mới bảng
                    while (rs.next()) {
                        getTableModel().addRow(new Object[] {
                                rs.getString("tenNguoiGui"),
                                rs.getString("danhGia"),
                                rs.getString("ngayDang")
                        });
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi làm mới dữ liệu!");
            }
        });
    }
}

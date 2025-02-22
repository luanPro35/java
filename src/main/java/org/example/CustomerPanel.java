package org.example;

import org.example.BasePanel;
import org.example.databaseConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CustomerPanel extends BasePanel {
    public CustomerPanel() {
        // Sửa các cột bảng theo yêu cầu mới
        super(new String[]{"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại"});

        // Action cho thêm khách hàng
        setAddActionListener(e -> {
            String maKhachHang = JOptionPane.showInputDialog("Nhập mã khách hàng:");
            String tenKhachHang = JOptionPane.showInputDialog("Nhập tên khách hàng:");
            String soDienThoai = JOptionPane.showInputDialog("Nhập số điện thoại:");

            try (Connection conn = databaseConnection.connect()) {
                String query = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, Integer.parseInt(maKhachHang));
                    stmt.setString(2, tenKhachHang);
                    stmt.setString(3, soDienThoai);
                    stmt.executeUpdate();

                    // Thêm dòng mới vào bảng
                    getTableModel().addRow(new Object[]{
                            maKhachHang, tenKhachHang, soDienThoai
                    });

                    JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng!");
            }
        });

        // Action cho làm mới dữ liệu
        setRefreshActionListener(e -> {
            try (Connection conn = databaseConnection.connect()) {
                String query = "SELECT * FROM KhachHang";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    // Xóa tất cả dữ liệu trong bảng hiện tại
                    getTableModel().setRowCount(0);

                    // Thêm dữ liệu mới từ cơ sở dữ liệu vào bảng
                    while (rs.next()) {
                        getTableModel().addRow(new Object[]{
                                rs.getInt("maKhachHang"),
                                rs.getString("tenKhachHang"),
                                rs.getString("soDienThoai")
                        });
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi làm mới dữ liệu!");
            }
        });
    }

    @Override
    protected void onAddData() {
        // Không cần thay đổi gì ở đây nếu không sử dụng phương thức này
    }

    @Override
    protected void onRefreshData() {
        // Không cần thay đổi gì ở đây nếu không sử dụng phương thức này
    }
}

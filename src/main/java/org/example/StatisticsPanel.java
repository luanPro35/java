package org.example;

import org.example.BasePanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsPanel extends BasePanel {

    public StatisticsPanel() {
        super(new String[]{"Ngày", "Tổng tiền"});
    }

    @Override
    protected void onAddData() {
        String ngay = JOptionPane.showInputDialog("Nhập ngày (YYYY-MM-DD):");
        String tongTien = JOptionPane.showInputDialog("Nhập tổng tiền:");

        try (Connection conn = connectToDatabase()) {
            if (conn == null) return;

            String query = "INSERT INTO ThongKe(ngay, Tongtien) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, java.sql.Date.valueOf(ngay));
                stmt.setBigDecimal(2, new BigDecimal(tongTien));
                stmt.executeUpdate();

                tableModel.addRow(new Object[]{ngay, tongTien});
                JOptionPane.showMessageDialog(this, "Thêm thống kê thành công!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm thống kê!");
        }
    }

    private Connection connectToDatabase() {
        return null;
    }

    @Override
    protected void onRefreshData() {
        try (Connection conn = connectToDatabase()) {
            if (conn == null) return;

            String query = "SELECT ngay, Tongtien FROM ThongKe";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getDate("ngay").toString(),
                            rs.getBigDecimal("Tongtien").toString()
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu!");
        }
    }
}

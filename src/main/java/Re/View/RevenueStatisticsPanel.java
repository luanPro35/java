package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.ThongKe;
import javax.swing.*;
import java.sql.*;

public class RevenueStatisticsPanel extends BasePanel {
    public RevenueStatisticsPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Ngày", "Tổng tiền"});
    }

    @Override
    protected void handleAdd() {
        try {
            String ngay = JOptionPane.showInputDialog("Nhập ngày (YYYY-MM-DD):");
            String tongTien = JOptionPane.showInputDialog("Nhập tổng tiền:");

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "INSERT INTO ThongKe(ngay, Tongtien) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setDate(1, java.sql.Date.valueOf(ngay));
                    stmt.setDouble(2, Double.parseDouble(tongTien));
                    stmt.executeUpdate();
                }
                tableModel.addRow(new Object[]{ngay, tongTien});
                JOptionPane.showMessageDialog(frame, "Thêm thống kê thành công!");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm: " + ex.getMessage());
        }
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT ngay, Tongtien FROM ThongKe";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getDate("ngay").toString(),
                            rs.getDouble("Tongtien")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }
}
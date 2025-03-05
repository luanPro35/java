package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class StatisticsPanel {
    private JPanel panel;
    private JFrame frame;

    public StatisticsPanel(JFrame frame, JPanel contentPanel) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());

        // Tạo bảng thống kê
        DefaultTableModel statisticsModel = new DefaultTableModel(new String[]{"Ngày", "Tổng tiền"}, 0);
        JTable statisticsTable = new JTable(statisticsModel);
        JScrollPane statisticsScrollPane = new JScrollPane(statisticsTable);
        panel.add(statisticsScrollPane, BorderLayout.CENTER);

        // Nút điều khiển
        JPanel statisticsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addStatisticsButton = new JButton("Thêm Thống Kê");
        JButton refreshStatisticsButton = new JButton("Làm Mới");
        statisticsButtonPanel.add(addStatisticsButton);
        statisticsButtonPanel.add(refreshStatisticsButton);
        panel.add(statisticsButtonPanel, BorderLayout.SOUTH);

        // Sự kiện nút Thêm Thống Kê
        addStatisticsButton.addActionListener(e -> {
            String ngay = JOptionPane.showInputDialog("Nhập ngày (YYYY-MM-DD):");
            String tongTien = JOptionPane.showInputDialog("Nhập tổng tiền:");

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "INSERT INTO ThongKe(ngay, Tongtien) VALUES (?, ?);";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setDate(1, java.sql.Date.valueOf(ngay));
                    stmt.setBigDecimal(2, new BigDecimal(tongTien));
                    stmt.executeUpdate();
                    statisticsModel.addRow(new Object[]{ngay, tongTien});
                    JOptionPane.showMessageDialog(frame, "Thêm thống kê thành công!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm thống kê!");
            }
        });

        // Sự kiện nút Làm Mới
        refreshStatisticsButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "SELECT ngay, Tongtien FROM ThongKe";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {
                    statisticsModel.setRowCount(0);
                    while (rs.next()) {
                        statisticsModel.addRow(new Object[]{
                                rs.getDate("ngay").toString(),
                                rs.getBigDecimal("Tongtien").toString()
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
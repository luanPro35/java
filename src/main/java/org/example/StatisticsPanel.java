//package org.example;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.math.BigDecimal;
//import java.sql.*;
//
//public class StatisticsPanel {
//    private JPanel statisticsPanel;
//    private DefaultTableModel statisticsModel;
//    private JTable statisticsTable;
//    private JButton addStatisticsButton;
//    private JButton refreshStatisticsButton;
//    private JFrame frame;
//    private DatabaseConnection databaseConnection;
//
//    public StatisticsPanel(JFrame frame, DatabaseConnection databaseConnection) {
//        this.frame = frame;
//        this.databaseConnection = databaseConnection;
//        initializeUI();
//    }
//
//    private void initializeUI() {
//        statisticsPanel = new JPanel(new BorderLayout());
//        statisticsModel = new DefaultTableModel(new String[]{"Ngày", "Tổng tiền"}, 0);
//        statisticsTable = new JTable(statisticsModel);
//        JScrollPane statisticsScrollPane = new JScrollPane(statisticsTable);
//        statisticsPanel.add(statisticsScrollPane, BorderLayout.CENTER);
//
//        JPanel statisticsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        addStatisticsButton = new JButton("Thêm Thống Kê");
//        refreshStatisticsButton = new JButton("Làm Mới");
//        statisticsButtonPanel.add(addStatisticsButton);
//        statisticsButtonPanel.add(refreshStatisticsButton);
//        statisticsPanel.add(statisticsButtonPanel, BorderLayout.SOUTH);
//
//        addStatisticsButton.addActionListener(e -> addStatistics());
//        refreshStatisticsButton.addActionListener(e -> refreshStatistics());
//    }
//
//    private void addStatistics() {
//        String ngay = JOptionPane.showInputDialog("Nhập ngày (YYYY-MM-DD):");
//        String tongTien = JOptionPane.showInputDialog("Nhập tổng tiền:");
//
//        try (Connection conn = databaseConnection.connect()) {
//            if (conn == null) return;
//
//            String query = "INSERT INTO ThongKe(ngay, Tongtien) VALUES (?, ?);";
//            try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                stmt.setDate(1, Date.valueOf(ngay));
//                stmt.setBigDecimal(2, new BigDecimal(tongTien));
//                stmt.executeUpdate();
//                statisticsModel.addRow(new Object[]{ngay, tongTien});
//                JOptionPane.showMessageDialog(frame, "Thêm thống kê thành công!");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm thống kê!");
//        }
//    }
//
//    private void refreshStatistics() {
//        try (Connection conn = databaseConnection.connect()) {
//            if (conn == null) return;
//
//            System.out.println("Đang thực hiện truy vấn làm mới dữ liệu...");
//            String query = "SELECT ngay, Tongtien FROM ThongKe";
//            try (PreparedStatement stmt = conn.prepareStatement(query);
//                 ResultSet rs = stmt.executeQuery()) {
//                statisticsModel.setRowCount(0);
//                while (rs.next()) {
//                    statisticsModel.addRow(new Object[]{
//                            rs.getDate("ngay").toString(),
//                            rs.getBigDecimal("Tongtien").toString()
//                    });
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
//        }
//    }
//
//    public JPanel getStatisticsPanel() {
//        return statisticsPanel;
//    }
//}
//

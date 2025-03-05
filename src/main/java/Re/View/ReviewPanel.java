package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Date;

public class ReviewPanel {
    private JPanel panel;
    private JFrame frame;

    public ReviewPanel(JFrame frame, JPanel contentPanel) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());

        // Tạo bảng đánh giá
        DefaultTableModel danhGiaModel = new DefaultTableModel(new String[]{"Tên Người Gửi", "Đánh Giá", "Ngày Đánh Giá"}, 0);
        JTable danhGiaTable = new JTable(danhGiaModel);
        JScrollPane danhGiaScrollPane = new JScrollPane(danhGiaTable);
        panel.add(danhGiaScrollPane, BorderLayout.CENTER);

        // Nút điều khiển
        JPanel danhGiaButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addDanhGiaButton = new JButton("Thêm Đánh Giá");
        JButton refreshDanhGiaButton = new JButton("Làm Mới");
        danhGiaButtonPanel.add(addDanhGiaButton);
        danhGiaButtonPanel.add(refreshDanhGiaButton);
        panel.add(danhGiaButtonPanel, BorderLayout.SOUTH);

        // Sự kiện nút Thêm Đánh Giá
        addDanhGiaButton.addActionListener(e -> {
            try {
                String tenNguoiGui = JOptionPane.showInputDialog("Nhập tên người gửi:");
                String danhGia = JOptionPane.showInputDialog("Nhập đánh giá:");

                try (Connection conn = DatabaseConnection.connect()) {
                    if (conn == null) return;
                    String query = "INSERT INTO DanhGia (tenNguoiGui, danhGia) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, tenNguoiGui);
                        stmt.setString(2, danhGia);
                        stmt.executeUpdate();
                    }
                    danhGiaModel.addRow(new Object[]{tenNguoiGui, danhGia, new java.util.Date()});
                    JOptionPane.showMessageDialog(frame, "Thêm đánh giá thành công!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm đánh giá!");
            }
        });

        // Sự kiện nút Làm Mới
        refreshDanhGiaButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "SELECT * FROM DanhGia";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {
                    danhGiaModel.setRowCount(0);
                    while (rs.next()) {
                        danhGiaModel.addRow(new Object[]{
                                rs.getString("tenNguoiGui"),
                                rs.getString("danhGia"),
                                rs.getString("ngayDang")
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
package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import java.sql.*;

public class ImportHistoryPanel extends BasePanel {
    public ImportHistoryPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Lịch sử nhập sản phẩm"});
    }

    @Override
    protected void handleAdd() {
        JOptionPane.showMessageDialog(frame, "Chức năng Thêm cho Lịch sử nhập sản phẩm chưa được triển khai!");
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT * FROM SanPham"; // Giả định bảng chứa lịch sử nhập
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{"Nhập sản phẩm: " + rs.getString("tenSanPham")});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }
}
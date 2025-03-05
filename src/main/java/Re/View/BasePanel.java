package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public abstract class BasePanel {
    protected JPanel panel;
    protected JFrame frame;
    protected JPanel contentPanel;
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane scrollPane;
    protected JPanel buttonPanel;

    public BasePanel(JFrame frame, JPanel contentPanel, String[] columnNames) {
        this.frame = frame;
        this.contentPanel = contentPanel;
        this.panel = new JPanel(new BorderLayout());

        // Tạo bảng
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tạo panel nút
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm");
        JButton refreshButton = new JButton("Làm Mới");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện cơ bản
        addButton.addActionListener(e -> handleAdd());
        refreshButton.addActionListener(e -> handleRefresh());

        initializeData(); // Khởi tạo dữ liệu ban đầu
    }

    // Phương thức trừu tượng để xử lý thêm dữ liệu
    protected abstract void handleAdd();

    // Phương thức trừu tượng để làm mới dữ liệu
    protected abstract void handleRefresh();

    // Phương thức khởi tạo dữ liệu (có thể ghi đè)
    protected void initializeData() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                handleRefresh(); // Gọi làm mới để tải dữ liệu ban đầu
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
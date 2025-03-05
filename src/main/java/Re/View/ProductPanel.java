package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ProductPanel extends BasePanel {
    private JTextField maKhachHangField;
    private DefaultTableModel selectedItemModel;
    private JTable selectedItemTable;
    private JScrollPane selectedItemScrollPane;

    public ProductPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng", "Loại Sản Phẩm"});

        // Tùy chỉnh tên nút từ BasePanel
        buttonPanel.removeAll(); // Xóa nút mặc định
        JButton addProductButton = new JButton("Thêm Sản Phẩm");
        JButton refreshButton = new JButton("Làm Mới");
        JButton saveButton = new JButton("Lưu");
        buttonPanel.add(addProductButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(saveButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm panel nhập mã khách hàng
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(255, 182, 193));
        JLabel maKhachHangLabel = new JLabel("Mã Khách Hàng:");
        maKhachHangField = new JTextField(10);
        JLabel maKhachHangMessage = new JLabel("Mời quý khách nhập mã của mình!");
        inputPanel.add(maKhachHangLabel);
        inputPanel.add(maKhachHangField);
        inputPanel.add(maKhachHangMessage);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Thêm bảng sản phẩm đã chọn
        selectedItemModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng"}, 0);
        selectedItemTable = new JTable(selectedItemModel);
        selectedItemScrollPane = new JScrollPane(selectedItemTable);
        selectedItemScrollPane.setPreferredSize(new Dimension(frame.getWidth() - 50, 100)); // Điều chỉnh kích thước
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(selectedItemScrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.CENTER); // Thay thế scrollPane gốc

        // Thêm sự kiện click cho bảng sản phẩm
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int maSanPham = (int) tableModel.getValueAt(selectedRow, 0);
                    String tenSanPham = (String) tableModel.getValueAt(selectedRow, 1);
                    double giaSanPham = (double) tableModel.getValueAt(selectedRow, 2);
                    int soLuong = 1;

                    boolean isExist = false;
                    for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
                        if ((int) selectedItemModel.getValueAt(i, 0) == maSanPham) {
                            int currentQuantity = (int) selectedItemModel.getValueAt(i, 3);
                            selectedItemModel.setValueAt(currentQuantity + 1, i, 3);
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        selectedItemModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong});
                    }
                }
            }
        });

        // Sự kiện nút
        addProductButton.addActionListener(e -> handleAdd());
        refreshButton.addActionListener(e -> handleRefresh());
        saveButton.addActionListener(e -> handleSave());
    }

    @Override
    protected void handleAdd() {
        try {
            String maSanPhamStr = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
            if (maSanPhamStr == null || maSanPhamStr.isEmpty()) return;
            int maSanPham = Integer.parseInt(maSanPhamStr);

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String checkQuery = "SELECT COUNT(*) FROM SanPham WHERE maSanPham = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setInt(1, maSanPham);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(frame, "Sản phẩm đã tồn tại!");
                        return;
                    }
                }
            }

            String tenSanPham = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
            String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá:");
            double giaSanPham = Double.parseDouble(giaSanPhamStr);
            String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng:");
            int soLuong = Integer.parseInt(soLuongStr);
            String loaiSanPham = JOptionPane.showInputDialog("Nhập loại sản phẩm:");

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String query = "INSERT INTO SanPham (maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, maSanPham);
                    stmt.setString(2, tenSanPham);
                    stmt.setDouble(3, giaSanPham);
                    stmt.setInt(4, soLuong);
                    stmt.setString(5, loaiSanPham);
                    stmt.executeUpdate();
                }
                tableModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham});
                JOptionPane.showMessageDialog(frame, "Thêm sản phẩm thành công!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
        }
    }

    @Override
    protected void handleRefresh() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
            String query = "SELECT * FROM SanPham";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("maSanPham"),
                            rs.getString("tenSanPham"),
                            rs.getDouble("giaSanPham"),
                            rs.getInt("soLuong"),
                            rs.getString("loaiSanPham")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới: " + ex.getMessage());
        }
    }

    private void handleSave() {
        try {
            String maKhachHangStr = maKhachHangField.getText();
            if (maKhachHangStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập mã khách hàng!");
                return;
            }
            int maKhachHang = Integer.parseInt(maKhachHangStr);

            if (selectedItemModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "Giỏ hàng trống! Vui lòng chọn sản phẩm.");
                return;
            }

            double totalPrice = 0;
            StringBuilder productDetails = new StringBuilder();
            productDetails.append("Hóa đơn mua hàng:\n\n");
            productDetails.append("Mã khách hàng: ").append(maKhachHang).append("\n");
            productDetails.append("Danh sách sản phẩm:\n");

            for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
                String productName = (String) selectedItemModel.getValueAt(i, 1);
                double price = (double) selectedItemModel.getValueAt(i, 2);
                int quantity = (int) selectedItemModel.getValueAt(i, 3);
                totalPrice += price * quantity;
                productDetails.append("- ")
                        .append(productName)
                        .append(", Giá: ")
                        .append(price)
                        .append(", Số lượng: ")
                        .append(quantity)
                        .append(", Thành tiền: ")
                        .append(price * quantity)
                        .append("\n");
            }
            productDetails.append("\nTổng tiền: ").append(totalPrice).append(" VND");

            JOptionPane.showMessageDialog(frame, productDetails.toString());

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
                String insertHoaDonQuery = "INSERT INTO HoaDon (maKhachHang, thanhTien, ngayLap) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertHoaDonQuery)) {
                    stmt.setInt(1, maKhachHang);
                    stmt.setDouble(2, totalPrice);
                    stmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                    stmt.executeUpdate();
                }
                String insertThongKeQuery = "INSERT INTO ThongKe (ngay, Tongtien) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertThongKeQuery)) {
                    stmt.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                    stmt.setDouble(2, totalPrice);
                    stmt.executeUpdate();
                }
                selectedItemModel.setRowCount(0);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
            }

            Re.Controller.PdfGenerator.generateBill(frame, maKhachHang, totalPrice, selectedItemModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
        }
    }
}
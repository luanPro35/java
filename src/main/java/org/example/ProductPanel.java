package org.example;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ProductPanel extends JPanel {
    private DefaultTableModel productModel;
    private JTable productTable;
    private DefaultTableModel selectedItemModel;
    private JTextField maKhachHangField;

    public ProductPanel(JPanel contentPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Tạo bảng sản phẩm
        productModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng", "Loại Sản Phẩm"}, 0);
        productTable = new JTable(productModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 3;  // Chỉ cho phép chỉnh sửa cột số lượng
            }
        };
        productTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
        JScrollPane productScrollPane = new JScrollPane(productTable);

        // Panel dưới chứa thông tin bổ sung
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 182, 193));
        BoxLayout boxLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        bottomPanel.setLayout(boxLayout);

        // Bảng sản phẩm đã chọn
        selectedItemModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng"}, 0);
        JTable selectedItemTable = new JTable(selectedItemModel);
        JScrollPane selectedItemScrollPane = new JScrollPane(selectedItemTable);
        selectedItemScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 100));
        bottomPanel.add(selectedItemScrollPane);

        // Thêm sự kiện click cho bảng sản phẩm
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    int maSanPham = Integer.parseInt(productModel.getValueAt(selectedRow, 0).toString());
                    String tenSanPham = productModel.getValueAt(selectedRow, 1).toString();
                    double giaSanPham = Double.parseDouble(productModel.getValueAt(selectedRow, 2).toString());
                    int soLuong = 1;  // Mặc định chọn 1 món

                    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                    boolean isExist = false;
                    for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
                        if ((int) selectedItemModel.getValueAt(i, 0) == maSanPham) {
                            // Nếu sản phẩm đã có, cập nhật số lượng
                            int currentQuantity = (int) selectedItemModel.getValueAt(i, 3);
                            selectedItemModel.setValueAt(currentQuantity + 1, i, 3); // Tăng số lượng lên 1
                            isExist = true;
                            break;
                        }
                    }

                    if (!isExist) {
                        // Nếu sản phẩm chưa có trong giỏ, thêm mới vào giỏ hàng
                        selectedItemModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong});
                    }
                }
            }
        });

        // Thêm các nút chức năng
        JPanel productButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addProductButton = new JButton("Thêm Sản Phẩm");
        JButton refreshProductButton = new JButton("Làm Mới");
        JButton saveProductButton = new JButton("Lưu");
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(refreshProductButton);
        productButtonPanel.add(saveProductButton);

        // Panel dưới
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.LIGHT_GRAY);
        Dimension rightPanelSize = new Dimension((int)(frame.getWidth() * 0.09), bottomPanel.getHeight());
        rightPanel.setPreferredSize(rightPanelSize);
        Dimension leftPanelSize = new Dimension((int)(frame.getWidth() * 0.3), bottomPanel.getHeight());
        leftPanel.setPreferredSize(leftPanelSize);
        bottomPanel.add(rightPanel);
        bottomPanel.add(leftPanel);

        // Thiết lập chia đôi màn hình
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productScrollPane, bottomPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerLocation(300);

        add(splitPane, BorderLayout.CENTER);
        add(productButtonPanel, BorderLayout.SOUTH);

        // ActionListener cho các nút
        addProductButton.addActionListener(e -> addProduct());
        refreshProductButton.addActionListener(e -> refreshProducts());
        saveProductButton.addActionListener(e -> saveOrder(contentPanel));
    }

    public ProductPanel(JPanel contentPanel) {

    }

    // Thêm sản phẩm mới vào cơ sở dữ liệu
    private void addProduct() {
        try {
            String maSanPhamStr = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
            int maSanPham = Integer.parseInt(maSanPhamStr);

            // Kiểm tra sản phẩm đã tồn tại
            try (Connection conn = databaseConnection.connect()) {
                String checkQuery = "SELECT COUNT(*) FROM SanPham WHERE maSanPham = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setInt(1, maSanPham);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "Sản phẩm đã tồn tại.");
                        return;
                    }
                }
            }

            // Thêm sản phẩm mới vào cơ sở dữ liệu
            String tenSanPham = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
            double giaSanPham = Double.parseDouble(JOptionPane.showInputDialog("Nhập giá sản phẩm:"));
            int soLuong = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng sản phẩm:"));
            String loaiSanPham = JOptionPane.showInputDialog("Nhập loại sản phẩm:");
            int maKhachHang = Integer.parseInt(JOptionPane.showInputDialog("Nhập mã khách hàng:"));

            // Thêm vào cơ sở dữ liệu
            try (Connection conn = databaseConnection.connect()) {
                String query = "INSERT INTO SanPham (maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham, maKhachHang) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, maSanPham);
                    stmt.setString(2, tenSanPham);
                    stmt.setDouble(3, giaSanPham);
                    stmt.setInt(4, soLuong);
                    stmt.setString(5, loaiSanPham);
                    stmt.setInt(6, maKhachHang);
                    stmt.executeUpdate();
                }

                productModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham});
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được thêm!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }

    // Làm mới danh sách sản phẩm
    private void refreshProducts() {
        try (Connection conn = databaseConnection.connect()) {
            String query = "SELECT * FROM SanPham";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                productModel.setRowCount(0);
                while (rs.next()) {
                    productModel.addRow(new Object[]{
                            rs.getInt("maSanPham"),
                            rs.getString("tenSanPham"),
                            rs.getDouble("giaSanPham"),
                            rs.getInt("soLuong"),
                            rs.getString("loaiSanPham")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }

    // Lưu đơn hàng (có thể thêm logic lưu dữ liệu ở đây)
    private void saveOrder(JPanel contentPanel) {
        // Thêm mã lưu đơn hàng tại đây (nếu cần thiết)
    }
}

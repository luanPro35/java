package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ProductPanel {
    private JPanel panel;
    private JFrame frame;
    private DefaultTableModel productModel;
    private DefaultTableModel selectedItemModel;
    private JTextField maKhachHangField;

    public ProductPanel(JFrame frame, JPanel contentPanel) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());

        // Tạo bảng sản phẩm
        productModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng", "Loại Sản Phẩm"}, 0);
        JTable productTable = new JTable(productModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
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

        // Sự kiện click cho bảng sản phẩm
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    int maSanPham = Integer.parseInt(productModel.getValueAt(selectedRow, 0).toString());
                    String tenSanPham = productModel.getValueAt(selectedRow, 1).toString();
                    double giaSanPham = Double.parseDouble(productModel.getValueAt(selectedRow, 2).toString());
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

        // Panel trái (mã khách hàng)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 182, 193));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel maKhachHangLabel = new JLabel("Mã Khách Hàng:");
        maKhachHangField = new JTextField(10);
        JLabel maKhachHangMessage = new JLabel("Mời quý khách nhập mã của mình!");
        leftPanel.add(maKhachHangLabel);
        leftPanel.add(maKhachHangField);
        leftPanel.add(maKhachHangMessage);

        // Panel phải (chọn món ăn)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        Dimension rightPanelSize = new Dimension((int) (frame.getWidth() * 0.7), bottomPanel.getHeight());
        rightPanel.setPreferredSize(rightPanelSize);
        Dimension leftPanelSize = new Dimension((int) (frame.getWidth() * 0.3), bottomPanel.getHeight());
        leftPanel.setPreferredSize(leftPanelSize);

        bottomPanel.add(rightPanel);
        bottomPanel.add(leftPanel);

        // Thiết lập split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productScrollPane, bottomPanel);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerLocation(300);

        // Nút điều khiển
        JPanel productButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addProductButton = new JButton("Thêm Sản Phẩm");
        JButton refreshProductButton = new JButton("Làm Mới");
        JButton saveProductButton = new JButton("Lưu");
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(refreshProductButton);
        productButtonPanel.add(saveProductButton);

        JPanel productSubPanel = new JPanel(new BorderLayout());
        productSubPanel.add(splitPane, BorderLayout.CENTER);
        productSubPanel.add(productButtonPanel, BorderLayout.SOUTH);

        panel.add(productSubPanel, BorderLayout.CENTER);

        // Sự kiện nút Thêm Sản Phẩm
        addProductButton.addActionListener(e -> {
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
                            JOptionPane.showMessageDialog(frame, "Sản phẩm đã tồn tại trong cơ sở dữ liệu.");
                            return;
                        }
                    }
                }

                String tenSanPham = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
                if (tenSanPham == null || tenSanPham.isEmpty()) return;
                String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá sản phẩm:");
                if (giaSanPhamStr == null || giaSanPhamStr.isEmpty()) return;
                double giaSanPham = Double.parseDouble(giaSanPhamStr);
                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng sản phẩm:");
                if (soLuongStr == null || soLuongStr.isEmpty()) return;
                int soLuong = Integer.parseInt(soLuongStr);
                String loaiSanPham = JOptionPane.showInputDialog("Nhập loại sản phẩm:");
                if (loaiSanPham == null || loaiSanPham.isEmpty()) return;
                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
                if (maKhachHangStr == null || maKhachHangStr.isEmpty()) return;
                int maKhachHang = Integer.parseInt(maKhachHangStr);

                try (Connection conn = DatabaseConnection.connect()) {
                    if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
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
                    JOptionPane.showMessageDialog(frame, "Thêm sản phẩm thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
            }
        });

        // Sự kiện nút Làm Mới
        refreshProductButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
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
                JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
            }
        });

        // Sự kiện nút Lưu
        saveProductButton.addActionListener(e -> {
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
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
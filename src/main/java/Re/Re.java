//package swing;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.property.TextAlignment;
//import swing.Model.HoaDon;
//import swing.Model.KhachHang;
//import swing.Model.databaseConnection;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import java.math.BigDecimal;
//import java.sql.*;
//
//
//public class Re {
//
//    public static void showMainScreen(){
//        // Tạo cửa sổ chính
//        JFrame frame = new JFrame("Hệ Thống Quản Lý Nhà Hàng");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Cấu hình đóng cửa sổ
//        frame.setSize(1000, 600);// Kích thước cửa sổ
//        frame.setLayout(new BorderLayout());// Thiết lập Layout
//
//        // Tiêu đề và biểu tượng
//        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        titlePanel.setBackground(new Color(135, 206, 250));// Màu nền cho tiêu đề
//        JLabel titleLabel = new JLabel("Restaurant Manager"); // Tạo nhãn tiêu đề
//        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));// Cài đặt phông chữ cho tiêu đề
//        titleLabel.setForeground(new Color(25, 25, 112)); // Màu chữ tiêu đề
//        titlePanel.add(titleLabel); // Thêm nhãn vào panel
//        frame.add(titlePanel, BorderLayout.NORTH);// Thêm panel tiêu đề vào khu vực phía Bắc của cửa sổ
//
//        // Thanh bên
//        JPanel sidebar = new JPanel(new GridLayout(5, 1));
//        sidebar.setBackground(new Color(224, 255, 255)); // Màu nền của thanh bên
//        String[] buttonLabels = {"Thống kê", "Sản phẩm", "Hóa đơn", "Khách hàng", "Đánh Giá"};
//        JButton[] buttons = new JButton[5]; // Mảng chứa các nút
//        for (int i = 0; i < buttons.length; i++) {
//            buttons[i] = new JButton(buttonLabels[i]);
//            buttons[i].setFocusPainted(false);
//            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
//            buttons[i].setBackground(new Color(173, 216, 230));
//            sidebar.add(buttons[i]);// Thêm nút vào thanh bên
//        }
//        frame.add(sidebar, BorderLayout.WEST); // Thêm thanh bên vào khu vực phía Tây của cửa sổ
//
//
//        // Nội dung chính
//        JPanel contentPanel = new JPanel(new CardLayout());
//        contentPanel.setBackground(new Color(245, 245, 245));
//        frame.add(contentPanel, BorderLayout.CENTER);
//
//        // Tạo bảng sản phẩm
//        DefaultTableModel productModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng", "Loại Sản Phẩm"}, 0);
//        JTable productTable = new JTable(productModel) {// Tạo bảng với dữ liệu mặc định
//            public boolean isCellEditable(int row, int column) {// Cấu hình bảng để cột số lượng có thể chỉnh sửa
//                return column == 3;  // Chỉ cho phép chỉnh sửa cột số lượng
//            }
//        };
//        productTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));// Chỉnh sửa cột số lượng bằng JTextField
//        JScrollPane productScrollPane = new JScrollPane(productTable);// Thêm bảng vào JScrollPane để tạo cuộn cho bảng
//
//        // Panel dưới chứa thông tin bổ sung
//        JPanel bottomPanel = new JPanel();
//        bottomPanel.setBackground(new Color(255, 182, 193));
//        BoxLayout boxLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
//        bottomPanel.setLayout(boxLayout);
//
//        // Bảng sản phẩm đã chọn
//        DefaultTableModel selectedItemModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng"}, 0);
//        JTable selectedItemTable = new JTable(selectedItemModel);
//        JScrollPane selectedItemScrollPane = new JScrollPane(selectedItemTable);
//        selectedItemScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 100));
//        bottomPanel.add(selectedItemScrollPane);
//
//        // Thêm sự kiện click cho bảng sản phẩm
//        productTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int selectedRow = productTable.getSelectedRow();
//                if (selectedRow != -1) {
//                    int maSanPham = Integer.parseInt(productModel.getValueAt(selectedRow, 0).toString());
//                    String tenSanPham = productModel.getValueAt(selectedRow, 1).toString();
//                    double giaSanPham = Double.parseDouble(productModel.getValueAt(selectedRow, 2).toString());
//                    int soLuong = 1;  // Mặc định chọn 1 món
//
//                    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
//                    boolean isExist = false;
//                    for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
//                        if ((int) selectedItemModel.getValueAt(i, 0) == maSanPham) {
//                            // Nếu sản phẩm đã có, cập nhật số lượng
//                            int currentQuantity = (int) selectedItemModel.getValueAt(i, 3);
//                            selectedItemModel.setValueAt(currentQuantity + 1, i, 3); // Tăng số lượng lên 1
//                            isExist = true;
//                            break;
//                        }
//                    }
//
//                    if (!isExist) {
//                        // Nếu sản phẩm chưa có trong giỏ, thêm mới vào giỏ hàng
//                        selectedItemModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong});
//                    }
//                }
//            }
//        });
//
//        // Tạo panel trái (mã khách hàng)
//        JPanel leftPanel = new JPanel();
//        leftPanel.setBackground(new Color(255, 182, 193));  // Màu hồng
//        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//        JLabel maKhachHangLabel = new JLabel("Mã Khách Hàng:");
//        JTextField maKhachHangField = new JTextField(10);
//        JLabel maKhachHangMessage = new JLabel("Mời quý khách nhập mã của mình!");
//        leftPanel.add(maKhachHangLabel);
//        leftPanel.add(maKhachHangField);
//        leftPanel.add(maKhachHangMessage);
//
//        // Tạo panel phải (chọn món ăn)
//        JPanel rightPanel = new JPanel();
//        rightPanel.setBackground(Color.WHITE);  // Màu trắng
//
//        // Chỉnh kích thước cho panel trái và phải
//        Dimension rightPanelSize = new Dimension((int)(frame.getWidth() * 0.009), bottomPanel.getHeight());  // 70% cho phần sản phẩm đã chọn
//        rightPanel.setPreferredSize(rightPanelSize);
//        Dimension leftPanelSize = new Dimension((int)(frame.getWidth() * 0.3), bottomPanel.getHeight());  // 30% cho phần nhập mã
//        leftPanel.setPreferredSize(leftPanelSize);
//
//        // Thêm panel vào dưới panel
//        bottomPanel.add(rightPanel);
//        bottomPanel.add(leftPanel);
//
//        // Thiết lập chia đôi màn hình (split pane)
//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productScrollPane, bottomPanel);
//        splitPane.setResizeWeight(0.7);  // Thay đổi tỷ lệ chia: 70% cho phần sản phẩm đã chọn
//        splitPane.setDividerLocation(300);
//
//        // Thêm các nút và bảng điều khiển
//        JPanel productButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addProductButton = new JButton("Thêm Sản Phẩm");
//        JButton refreshProductButton = new JButton("Làm Mới");
//        JButton saveProductButton = new JButton("Lưu");
//        productButtonPanel.add(addProductButton);
//        productButtonPanel.add(refreshProductButton);
//        productButtonPanel.add(saveProductButton);
//
//        JPanel productPanel = new JPanel(new BorderLayout());
//        productPanel.add(splitPane, BorderLayout.CENTER);
//        productPanel.add(productButtonPanel, BorderLayout.SOUTH);
//
//        contentPanel.add(productPanel, "Sản Phẩm");
//
//        // Sự kiện thêm sản phẩm
//        addProductButton.addActionListener(e -> {
//            try {
//                String maSanPhamStr = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
//                if (maSanPhamStr == null || maSanPhamStr.isEmpty()) return;
//                int maSanPham = Integer.parseInt(maSanPhamStr);
//
//                // Kiểm tra sản phẩm đã tồn tại
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
//                    //PreparedStatement dùng để thực thi những câu lệnh sql
//                    String checkQuery = "SELECT COUNT(*) FROM SanPham WHERE maSanPham = ?";
//                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
//                        checkStmt.setInt(1, maSanPham);
//                        ResultSet rs = checkStmt.executeQuery();
//                        rs.next();
//                        if (rs.getInt(1) > 0) {
//                            JOptionPane.showMessageDialog(frame, "Sản phẩm đã tồn tại trong cơ sở dữ liệu.");
//                            return;
//                        }
//                    }
//                }
//
//                // Thêm sản phẩm mới
//                String tenSanPham = JOptionPane.showInputDialog("Nhập tên sản phẩm:");
//                if (tenSanPham == null || tenSanPham.isEmpty()) return;
//
//                String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá sản phẩm:");
//                if (giaSanPhamStr == null || giaSanPhamStr.isEmpty()) return;
//                double giaSanPham = Double.parseDouble(giaSanPhamStr);
//
//                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng sản phẩm:");
//                if (soLuongStr == null || soLuongStr.isEmpty()) return;
//                int soLuong = Integer.parseInt(soLuongStr);
//
//                String loaiSanPham = JOptionPane.showInputDialog("Nhập loại sản phẩm:");
//                if (loaiSanPham == null || loaiSanPham.isEmpty()) return;
//
//                // Yêu cầu người dùng nhập maKhachHang
//                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
//                if (maKhachHangStr == null || maKhachHangStr.isEmpty()) return;
//                int maKhachHang = Integer.parseInt(maKhachHangStr);
//
//                // Thêm vào cơ sở dữ liệu
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
//
//                    String query = "INSERT INTO SanPham (maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham, maKhachHang) VALUES (?, ?, ?, ?, ?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                        // Thêm maKhachHang vào câu lệnh INSERT
//                        stmt.setInt(1, maSanPham);
//                        stmt.setString(2, tenSanPham);
//                        stmt.setDouble(3, giaSanPham);
//                        stmt.setInt(4, soLuong);
//                        stmt.setString(5, loaiSanPham);
//                        stmt.setInt(6, maKhachHang);
//                        stmt.executeUpdate();
//                    }
//
//                    productModel.addRow(new Object[]{maSanPham, tenSanPham, giaSanPham, soLuong, loaiSanPham});
//                    JOptionPane.showMessageDialog(frame, "Thêm sản phẩm thành công!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
//            }
//        });
//
//        // Sự kiện nút Làm Mới
//        refreshProductButton.addActionListener(e -> {
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
//
//                String query = "SELECT * FROM SanPham";
//                try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                    ResultSet rs = stmt.executeQuery();
//                    productModel.setRowCount(0);
//                    while (rs.next()) {
//                        productModel.addRow(new Object[]{
//                                rs.getInt("maSanPham"),
//                                rs.getString("tenSanPham"),
//                                rs.getDouble("giaSanPham"),
//                                rs.getInt("soLuong"),
//                                rs.getString("loaiSanPham")
//                        });
//                    }
//                }
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
//            }
//        });
//
//        saveProductButton.addActionListener(e -> {
//            try {
//                // Lấy mã khách hàng từ trường nhập liệu
//                String maKhachHangStr = maKhachHangField.getText();
//                if (maKhachHangStr.isEmpty()) {
//                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập mã khách hàng!");
//                    return;
//                }
//                int maKhachHang = Integer.parseInt(maKhachHangStr);
//
//                // Kiểm tra giỏ hàng không rỗng
//                if (selectedItemModel.getRowCount() == 0) {
//                    JOptionPane.showMessageDialog(frame, "Giỏ hàng trống! Vui lòng chọn sản phẩm.");
//                    return;
//                }
//
//                // Tính tổng tiền của giỏ hàng
//                double totalPrice = 0;
//                StringBuilder productDetails = new StringBuilder();
//                productDetails.append("Hóa đơn mua hàng:\n\n");
//                productDetails.append("Mã khách hàng: ").append(maKhachHang).append("\n");
//                productDetails.append("Danh sách sản phẩm:\n");
//
//                for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
//                    String productName = (String) selectedItemModel.getValueAt(i, 1);
//                    double price = (double) selectedItemModel.getValueAt(i, 2);
//                    int quantity = (int) selectedItemModel.getValueAt(i, 3);
//                    totalPrice += price * quantity;
//
//                    productDetails.append("- ")
//                            .append(productName)
//                            .append(", Giá: ")
//                            .append(price)
//                            .append(", Số lượng: ")
//                            .append(quantity)
//                            .append(", Thành tiền: ")
//                            .append(price * quantity)
//                            .append("\n");
//                }
//                productDetails.append("\nTổng tiền: ").append(totalPrice).append(" VND");
//
//                // Hiển thị thông báo tổng tiền
//                JOptionPane.showMessageDialog(frame, productDetails.toString());
//
//                // Lưu thông tin vào cơ sở dữ liệu
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
//
//                    // Lưu hóa đơn vào bảng HoaDon
//                    String insertHoaDonQuery = "INSERT INTO HoaDon (maKhachHang, thanhTien, ngayLap) VALUES (?, ?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(insertHoaDonQuery)) {
//                        stmt.setInt(1, maKhachHang);
//                        stmt.setDouble(2, totalPrice);
//                        stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//                        stmt.executeUpdate();
//                    }
//
//                    // Lưu vào bảng ThongKe
//                    String insertThongKeQuery = "INSERT INTO ThongKe (ngay, Tongtien) VALUES (?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(insertThongKeQuery)) {
//                        stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//                        stmt.setDouble(2, totalPrice);
//                        stmt.executeUpdate();
//                    }
//
//                    // Xóa giỏ hàng sau khi thanh toán thành công
//                    selectedItemModel.setRowCount(0);
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(frame, "Lỗi khi lưu hóa đơn: " + ex.getMessage());
//                }
//
//                // Tạo hóa đơn PDF
//                String billFileName = "C:\\ItextExample\\Orders for customers.pdf";
//                try {
//                    // Kiểm tra và tạo thư mục nếu cần
//                    File file = new File("C:\\ItextExample");
//                    if (!file.exists()) {
//                        boolean dirsCreated = file.mkdirs();
//                        if (!dirsCreated) {
//                            JOptionPane.showMessageDialog(frame, "Không thể tạo thư mục: C:\\ItextExample");
//                            return; // Dừng lại nếu không tạo được thư mục
//                        }
//                    }
//
//                    // Kiểm tra xem có thể tạo tệp không
//                    File billFile = new File(billFileName);
//                    if (billFile.exists() && !billFile.canWrite()) {
//                        JOptionPane.showMessageDialog(frame, "Không thể ghi vào tệp: " + billFileName);
//                        return;
//                    }
//
//                    // Tạo PDF và viết dữ liệu
//                    try (PdfWriter writer = new PdfWriter(billFileName);
//                         PdfDocument pdfDoc = new PdfDocument(writer)) {
//
//                        pdfDoc.addNewPage();
//                        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);
//
//                        // Thông tin cửa hàng
//                        document.add(new com.itextpdf.layout.element.Paragraph("Nhà Hàng Luxury").setBold().setFontSize(18));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Địa chỉ: 248 Lê Trọng Tấn, khối Trảng Sỏi").setFontSize(12));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Số điện thoại: 0123456789").setFontSize(12));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Email: contact@abcstore.com").setFontSize(12));
//                        document.add(new com.itextpdf.layout.element.Paragraph("\n"));
//
//                        // Tiêu đề hóa đơn
//                        document.add(new com.itextpdf.layout.element.Paragraph("HÓA ĐƠN MUA HÀNG")
//                                .setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Mã khách hàng: " + maKhachHang).setFontSize(14));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Ngày lập: " + new Timestamp(System.currentTimeMillis()).toString()).setFontSize(12));
//                        document.add(new com.itextpdf.layout.element.Paragraph("\n"));
//
//                        // Tổng tiền
//                        document.add(new com.itextpdf.layout.element.Paragraph("Tổng tiền: " + totalPrice + " VND").setBold().setFontSize(14));
//
//                        // Cảm ơn và thông tin liên hệ
//                        document.add(new com.itextpdf.layout.element.Paragraph("\n\nCảm ơn quý khách đã mua sắm tại cửa hàng chúng tôi!")
//                                .setTextAlignment(TextAlignment.CENTER).setFontSize(14));
//                        document.add(new com.itextpdf.layout.element.Paragraph("Hãy liên hệ với chúng tôi qua số điện thoại hoặc email nếu có thắc mắc.")
//                                .setTextAlignment(TextAlignment.CENTER).setFontSize(12));
//
//                        // Đóng tài liệu
//                        document.close();
//                    }
//                } catch (Exception pdfEx) {
//                    pdfEx.printStackTrace();
//                    JOptionPane.showMessageDialog(frame, "Lỗi khi tạo PDF: " + pdfEx.getMessage());
//                }
//
//                JOptionPane.showMessageDialog(frame, "Hóa đơn đã được lưu tại: " + billFileName);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(frame, "Lỗi: " + ex.getMessage());
//            }
//
//
//        });
//
//// Hiển thị tab Sản Phẩm khi nhấn nút
//        buttons[1].addActionListener(e -> {
//            CardLayout cl = (CardLayout) contentPanel.getLayout();
//            cl.show(contentPanel, "Sản Phẩm");
//            });
//        // Panel Thống Kê
//        JPanel statisticsPanel = new JPanel(new BorderLayout());
//        DefaultTableModel statisticsModel = new DefaultTableModel(new String[]{"Ngày", "Tổng tiền"}, 0);
//        JTable statisticsTable = new JTable(statisticsModel);
//        JScrollPane statisticsScrollPane = new JScrollPane(statisticsTable);
//        statisticsPanel.add(statisticsScrollPane, BorderLayout.CENTER);
//
//        JPanel statisticsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addStatisticsButton = new JButton("Thêm Thống Kê");
//        JButton refreshStatisticsButton = new JButton("Làm Mới");
//        statisticsButtonPanel.add(addStatisticsButton);
//        statisticsButtonPanel.add(refreshStatisticsButton);
//        statisticsPanel.add(statisticsButtonPanel, BorderLayout.SOUTH);
//
//        addStatisticsButton.addActionListener(e -> {
//            String ngay = JOptionPane.showInputDialog("Nhập ngày (YYYY-MM-DD):");
//            String tongTien = JOptionPane.showInputDialog("Nhập tổng tiền:");
//
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) return;
//
//                String query = "INSERT INTO ThongKe(ngay, Tongtien) VALUES (?, ?);";
//                try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                    stmt.setDate(1, Date.valueOf(ngay));
//                    stmt.setBigDecimal(2, new BigDecimal(tongTien));
//                    stmt.executeUpdate();
//                    statisticsModel.addRow(new Object[]{ngay, tongTien});
//                    JOptionPane.showMessageDialog(frame, "Thêm thống kê thành công!");
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm thống kê!");
//            }
//        });
//
//        refreshStatisticsButton.addActionListener(e -> {
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) return;
//
//                System.out.println("Đang thực hiện truy vấn làm mới dữ liệu...");
//                String query = "SELECT ngay, Tongtien FROM ThongKe";
//                try (PreparedStatement stmt = conn.prepareStatement(query);
//                     ResultSet rs = stmt.executeQuery()) {
//                    statisticsModel.setRowCount(0);
//                    while (rs.next()) {
//                        statisticsModel.addRow(new Object[]{
//                                rs.getDate("ngay").toString(),
//                                rs.getBigDecimal("Tongtien").toString()
//                        });
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
//            }
//        });
//        buttons[0].addActionListener(e -> {
//            CardLayout cl = (CardLayout) contentPanel.getLayout();
//            cl.show(contentPanel, "Thống Kê");
//        });
//
//        // Panel Hóa Đơn
//        JPanel invoicePanel = new JPanel(new BorderLayout());
//        DefaultTableModel invoiceModel = new DefaultTableModel(new String[]{"Mã Hóa Đơn", "Mã KH", "Ngày Lập", "Thành Tiền"}, 0);
//        JTable invoiceTable = new JTable(invoiceModel);
//        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);
//        invoicePanel.add(invoiceScrollPane, BorderLayout.CENTER);
//
//// Button thêm hóa đơn
//        JPanel invoiceButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addInvoiceButton = new JButton("Thêm Hóa Đơn");
//        JButton refreshInvoiceButton = new JButton("Làm Mới");
//        invoiceButtonPanel.add(addInvoiceButton);
//        invoiceButtonPanel.add(refreshInvoiceButton);
//        invoicePanel.add(invoiceButtonPanel, BorderLayout.SOUTH);
//
//        addInvoiceButton.addActionListener(e -> {
//            try {
//                String maHoaDonStr = JOptionPane.showInputDialog("Nhập mã hóa đơn:");
//                int maHoaDon = Integer.parseInt(maHoaDonStr);
//
//                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
//                int maKhachHang = Integer.parseInt(maKhachHangStr);
//
//                String maSanPham = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
//                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng:");
//                int soLuong = Integer.parseInt(soLuongStr);
//
//                String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá sản phẩm:");
//                double giaSanPham = Double.parseDouble(giaSanPhamStr);
//
//                // Tính thành tiền
//                double thanhTien = soLuong * giaSanPham;
//
//                // Ngày lập hóa đơn
//                String ngayLap = JOptionPane.showInputDialog("Nhập ngày lập hóa đơn:");
//
//                // Tạo đối tượng HoaDon
//                HoaDon hoaDon = new HoaDon(maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap);
//
//                // Thêm vào cơ sở dữ liệu
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) return;
//
//                    String query = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap) VALUES (?, ?, ?, ?, ?, ?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                        stmt.setInt(1, hoaDon.getMaHoaDon());
//                        stmt.setInt(2, hoaDon.getMaKhachHang());
//                        stmt.setString(3, hoaDon.getMaSanPham());
//                        stmt.setInt(4, hoaDon.getSoLuong());
//                        stmt.setDouble(5, hoaDon.getGiaSanPham());
//                        stmt.setDouble(6, hoaDon.getThanhTien());
//                        stmt.setString(7, hoaDon.getNgayLap());
//                        stmt.executeUpdate();
//                    }
//
//                    // Cập nhật bảng
//                    invoiceModel.addRow(new Object[]{
//                            hoaDon.getMaHoaDon(),
//                            hoaDon.getMaKhachHang(),
//                            hoaDon.getNgayLap(),
//                            hoaDon.getThanhTien()
//                    });
//
//                    JOptionPane.showMessageDialog(frame, "Thêm hóa đơn thành công!");
//                }
//            } catch (SQLException | NumberFormatException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm hóa đơn!");
//            }
//        });
//
//        refreshInvoiceButton.addActionListener(e -> {
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) return;
//
//                String query = "SELECT * FROM HoaDon";
//                try (PreparedStatement stmt = conn.prepareStatement(query);
//                     ResultSet rs = stmt.executeQuery()) {
//                    invoiceModel.setRowCount(0);
//                    while (rs.next()) {
//                        invoiceModel.addRow(new Object[]{
//                                rs.getInt("maHoaDon"),
//                                rs.getInt("maKhachHang"),
//                                rs.getString("ngayLap"),
//                                rs.getDouble("thanhTien")
//                        });
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
//            }
//        });
//        buttons[2].addActionListener(e -> {
//            CardLayout cl = (CardLayout) contentPanel.getLayout();
//            cl.show(contentPanel, "Hóa Đơn");
//        });
//
//        JPanel customerPanel = new JPanel(new BorderLayout());
//        DefaultTableModel customerModel = new DefaultTableModel(new String[]{"Mã KH", "Tên KH", "Điện thoại"}, 0);
//        JTable customerTable = new JTable(customerModel);
//        JScrollPane customerScrollPane = new JScrollPane(customerTable);
//        customerPanel.add(customerScrollPane, BorderLayout.CENTER);
//
//        JPanel customerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addCustomerButton = new JButton("Thêm Khách Hàng");
//        JButton refreshCustomerButton = new JButton("Làm Mới");
//        customerButtonPanel.add(addCustomerButton);
//        customerButtonPanel.add(refreshCustomerButton);
//        customerPanel.add(customerButtonPanel, BorderLayout.SOUTH);
//
//        addCustomerButton.addActionListener(e -> {
//            try {
//                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
//                int maKhachHang = Integer.parseInt(maKhachHangStr);
//                String tenKhachHang = JOptionPane.showInputDialog("Nhập tên khách hàng:");
//                String dienThoai = JOptionPane.showInputDialog("Nhập điện thoại:");
//
//                KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, dienThoai);
//
//                // Thêm vào cơ sở dữ liệu
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) return;
//
//                    String query = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai) VALUES (?, ?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                        stmt.setInt(1, khachHang.getMaKhachHang());
//                        stmt.setString(2, khachHang.getTenKhachHang());
//                        stmt.setString(3, khachHang.getSoDienThoai());
//                        stmt.executeUpdate();
//                    }
//
//                    // Cập nhật bảng
//                    customerModel.addRow(new Object[] {
//                            khachHang.getMaKhachHang(),
//                            khachHang.getTenKhachHang(),
//                            khachHang.getSoDienThoai()
//                    });
//
//                    JOptionPane.showMessageDialog(frame, "Thêm khách hàng thành công!");
//                }
//            } catch (SQLException | NumberFormatException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm khách hàng!");
//            }
//        });
//
//        refreshCustomerButton.addActionListener(e -> {
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) return;
//
//                String query = "SELECT * FROM KhachHang";
//                try (PreparedStatement stmt = conn.prepareStatement(query);
//                     ResultSet rs = stmt.executeQuery()) {
//                    customerModel.setRowCount(0);
//                    while (rs.next()) {
//                        customerModel.addRow(new Object[] {
//                                rs.getInt("maKhachHang"),
//                                rs.getString("tenKhachHang"),
//                                rs.getString("dienThoai")
//                        });
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
//            }
//        });
//
//        buttons[3].addActionListener(e -> {
//            CardLayout cl = (CardLayout) contentPanel.getLayout();
//            cl.show(contentPanel, "Khách Hàng");
//        });
//        // Panel Đánh Giá
//        JPanel danhGiaPanel = new JPanel(new BorderLayout());
//        DefaultTableModel danhGiaModel = new DefaultTableModel(new String[]{"Tên Người Gửi", "Đánh Giá", "Ngày Đánh Giá"}, 0);
//        JTable danhGiaTable = new JTable(danhGiaModel);
//        JScrollPane danhGiaScrollPane = new JScrollPane(danhGiaTable);
//        danhGiaPanel.add(danhGiaScrollPane, BorderLayout.CENTER);
//
//        JPanel danhGiaButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton addDanhGiaButton = new JButton("Thêm Đánh Giá");
//        JButton refreshDanhGiaButton = new JButton("Làm Mới");
//        danhGiaButtonPanel.add(addDanhGiaButton);
//        danhGiaButtonPanel.add(refreshDanhGiaButton);
//        danhGiaPanel.add(danhGiaButtonPanel, BorderLayout.SOUTH);
//
//        addDanhGiaButton.addActionListener(e -> {
//            try {
//                String tenNguoiGui = JOptionPane.showInputDialog("Nhập tên người gửi:");
//                String danhGia = JOptionPane.showInputDialog("Nhập đánh giá:");
//
//                // Thêm vào cơ sở dữ liệu
//                try (Connection conn = databaseConnection.connect()) {
//                    if (conn == null) return;
//
//                    String query = "INSERT INTO DanhGia (tenNguoiGui, danhGia) VALUES (?, ?)";
//                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
//                        stmt.setString(1, tenNguoiGui);
//                        stmt.setString(2, danhGia);
//                        stmt.executeUpdate();
//                    }
//
//                    // Cập nhật bảng
//                    danhGiaModel.addRow(new Object[] {
//                            tenNguoiGui,
//                            danhGia,
//                            new java.util.Date()
//                    });
//
//                    JOptionPane.showMessageDialog(frame, "Thêm đánh giá thành công!");
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm đánh giá!");
//            }
//        });
//
//        refreshDanhGiaButton.addActionListener(e -> {
//            try (Connection conn = databaseConnection.connect()) {
//                if (conn == null) return;
//
//                String query = "SELECT * FROM DanhGia";
//                try (PreparedStatement stmt = conn.prepareStatement(query);
//                     ResultSet rs = stmt.executeQuery()) {
//                    danhGiaModel.setRowCount(0); // Làm mới bảng
//                    while (rs.next()) {
//                        danhGiaModel.addRow(new Object[] {
//                                rs.getString("tenNguoiGui"),
//                                rs.getString("danhGia"),
//                                rs.getString("ngayDang")
//                        });
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(frame, "Lỗi khi làm mới dữ liệu!");
//            }
//        });
//
//// Chuyển sang tab Đánh Giá
//        buttons[4].addActionListener(e -> {
//            CardLayout cl = (CardLayout) contentPanel.getLayout();
//            cl.show(contentPanel, "Đánh Giá");
//        });
//
//
//        // Thêm tất cả các Panel vào contentPanel
//        contentPanel.add(statisticsPanel, "Thống Kê");
//        contentPanel.add(invoicePanel, "Hóa Đơn");
//        contentPanel.add(productPanel, "Sản Phẩm");
//        contentPanel.add(customerPanel, "Khách Hàng");
//        contentPanel.add(danhGiaPanel, "Đánh Giá");
//
//        // Hiển thị giao diện
//        frame.setVisible(true);
//    }
//}

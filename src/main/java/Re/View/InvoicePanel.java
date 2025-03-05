package Re.View;

import Re.Model.DatabaseConnection;
import Re.Model.HoaDon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InvoicePanel {
    private JPanel panel;
    private JFrame frame;

    public InvoicePanel(JFrame frame, JPanel contentPanel) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());

        // Tạo bảng hóa đơn
        DefaultTableModel invoiceModel = new DefaultTableModel(new String[]{"Mã Hóa Đơn", "Mã KH", "Ngày Lập", "Thành Tiền"}, 0);
        JTable invoiceTable = new JTable(invoiceModel);
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);
        panel.add(invoiceScrollPane, BorderLayout.CENTER);

        // Nút điều khiển
        JPanel invoiceButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addInvoiceButton = new JButton("Thêm Hóa Đơn");
        JButton refreshInvoiceButton = new JButton("Làm Mới");
        invoiceButtonPanel.add(addInvoiceButton);
        invoiceButtonPanel.add(refreshInvoiceButton);
        panel.add(invoiceButtonPanel, BorderLayout.SOUTH);

        // Sự kiện nút Thêm Hóa Đơn
        addInvoiceButton.addActionListener(e -> {
            try {
                String maHoaDonStr = JOptionPane.showInputDialog("Nhập mã hóa đơn:");
                int maHoaDon = Integer.parseInt(maHoaDonStr);
                String maKhachHangStr = JOptionPane.showInputDialog("Nhập mã khách hàng:");
                int maKhachHang = Integer.parseInt(maKhachHangStr);
                String maSanPham = JOptionPane.showInputDialog("Nhập mã sản phẩm:");
                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng:");
                int soLuong = Integer.parseInt(soLuongStr);
                String giaSanPhamStr = JOptionPane.showInputDialog("Nhập giá sản phẩm:");
                double giaSanPham = Double.parseDouble(giaSanPhamStr);
                double thanhTien = soLuong * giaSanPham;
                String ngayLap = JOptionPane.showInputDialog("Nhập ngày lập hóa đơn (YYYY-MM-DD):");

                HoaDon hoaDon = new HoaDon(maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap);

                try (Connection conn = DatabaseConnection.connect()) {
                    if (conn == null) return;
                    String query = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maSanPham, soLuong, giaSanPham, thanhTien, ngayLap) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, hoaDon.getMaHoaDon());
                        stmt.setInt(2, hoaDon.getMaKhachHang());
                        stmt.setString(3, hoaDon.getMaSanPham());
                        stmt.setInt(4, hoaDon.getSoLuong());
                        stmt.setDouble(5, hoaDon.getGiaSanPham());
                        stmt.setDouble(6, hoaDon.getThanhTien());
                        stmt.setString(7, hoaDon.getNgayLap());
                        stmt.executeUpdate();
                    }
                    invoiceModel.addRow(new Object[]{maHoaDon, maKhachHang, ngayLap, thanhTien});
                    JOptionPane.showMessageDialog(frame, "Thêm hóa đơn thành công!");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi thêm hóa đơn!");
            }
        });

        // Sự kiện nút Làm Mới
        refreshInvoiceButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.connect()) {
                if (conn == null) return;
                String query = "SELECT * FROM HoaDon";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {
                    invoiceModel.setRowCount(0);
                    while (rs.next()) {
                        invoiceModel.addRow(new Object[]{
                                rs.getInt("maHoaDon"),
                                rs.getInt("maKhachHang"),
                                rs.getString("ngayLap"),
                                rs.getDouble("thanhTien")
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
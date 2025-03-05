package Re.View;

import Re.Model.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SalesPanel extends BasePanel {
    private JTextField maKhachHangField;

    public SalesPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng tiền", "Mã khách hàng", "Tên khách hàng"});

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
    }

    @Override
    protected void handleAdd() {
        JOptionPane.showMessageDialog(frame, "Chức năng Thêm cho Quản lý bán hàng chưa được triển khai!");
    }

    @Override
    protected void handleRefresh() {
        // Logic làm mới (chưa có bảng dữ liệu cụ thể, cần định nghĩa thêm)
        JOptionPane.showMessageDialog(frame, "Làm mới Quản lý bán hàng!");
    }
}
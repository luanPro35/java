package Re.View;

import javax.swing.*;

public class PromotionPanel extends BasePanel {
    public PromotionPanel(JFrame frame, JPanel contentPanel) {
        super(frame, contentPanel, new String[]{"Tạo mã giảm giá", "Quản lý chương trình giảm giá"});
    }

    @Override
    protected void handleAdd() {
        JOptionPane.showMessageDialog(frame, "Chức năng Thêm cho Khuyến mãi chưa được triển khai!");
    }

    @Override
    protected void handleRefresh() {
        JOptionPane.showMessageDialog(frame, "Làm mới Khuyến mãi!");
    }
}
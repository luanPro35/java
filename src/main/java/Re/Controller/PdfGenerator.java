package Re.Controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.Timestamp;

public class PdfGenerator {
    public static void generateBill(JFrame frame, int maKhachHang, double totalPrice, DefaultTableModel selectedItemModel) {
        String billFileName = "C:\\ItextExample\\Orders for customers.pdf";
        try {
            File file = new File("C:\\ItextExample");
            if (!file.exists()) {
                boolean dirsCreated = file.mkdirs();
                if (!dirsCreated) {
                    JOptionPane.showMessageDialog(frame, "Không thể tạo thư mục: C:\\ItextExample");
                    return;
                }
            }

            File billFile = new File(billFileName);
            if (billFile.exists() && !billFile.canWrite()) {
                JOptionPane.showMessageDialog(frame, "Không thể ghi vào tệp: " + billFileName);
                return;
            }

            try (PdfWriter writer = new PdfWriter(billFileName);
                 PdfDocument pdfDoc = new PdfDocument(writer)) {

                pdfDoc.addNewPage();
                Document document = new Document(pdfDoc);

                document.add(new Paragraph("Nhà Hàng Luxury").setBold().setFontSize(18));
                document.add(new Paragraph("Địa chỉ: 248 Lê Trọng Tấn, khối Trảng Sỏi").setFontSize(12));
                document.add(new Paragraph("Số điện thoại: 0123456789").setFontSize(12));
                document.add(new Paragraph("Email: contact@abcstore.com").setFontSize(12));
                document.add(new Paragraph("\n"));

                document.add(new Paragraph("HÓA ĐƠN MUA HÀNG")
                        .setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("Mã khách hàng: " + maKhachHang).setFontSize(14));
                document.add(new Paragraph("Ngày lập: " + new Timestamp(System.currentTimeMillis()).toString()).setFontSize(12));
                document.add(new Paragraph("\n"));

                StringBuilder productDetails = new StringBuilder();
                for (int i = 0; i < selectedItemModel.getRowCount(); i++) {
                    String productName = (String) selectedItemModel.getValueAt(i, 1);
                    double price = (double) selectedItemModel.getValueAt(i, 2);
                    int quantity = (int) selectedItemModel.getValueAt(i, 3);
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
                document.add(new Paragraph(productDetails.toString()).setFontSize(12));

                document.add(new Paragraph("Tổng tiền: " + totalPrice + " VND").setBold().setFontSize(14));

                document.add(new Paragraph("\n\nCảm ơn quý khách đã mua sắm tại cửa hàng chúng tôi!")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(14));
                document.add(new Paragraph("Hãy liên hệ với chúng tôi qua số điện thoại hoặc email nếu có thắc mắc.")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(12));

                document.close();
            }
            JOptionPane.showMessageDialog(frame, "Hóa đơn đã được lưu tại: " + billFileName);
        } catch (Exception pdfEx) {
            pdfEx.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi tạo PDF: " + pdfEx.getMessage());
        }
    }
}
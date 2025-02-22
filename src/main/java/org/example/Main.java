package org.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;

public class Main {
    public static void main(String[] args) {
        try {
            // Tạo file PDF
            PdfWriter writer = new PdfWriter("invoice.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Tiêu đề hóa đơn
            document.add(new Paragraph("Invoice")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(20));

            // Thông tin người bán và người mua
            document.add(new Paragraph("\nSeller: ABC Company")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Buyer: John Doe")
                    .setTextAlignment(TextAlignment.LEFT));

            // Dòng kẻ phân cách
            document.add(new Paragraph("\n-----------------------------------"));

            // Tạo bảng cho danh sách sản phẩm
            float[] columnWidths = {200F, 100F, 100F, 100F};
            Table table = new Table(columnWidths);

            // Tiêu đề các cột
            table.addCell(new Cell().add(new Paragraph("Product")));
            table.addCell(new Cell().add(new Paragraph("Quantity")));
            table.addCell(new Cell().add(new Paragraph("Unit Price")));
            table.addCell(new Cell().add(new Paragraph("Total Price")));

            // Dữ liệu sản phẩm
            table.addCell(new Cell().add(new Paragraph("Product A")));
            table.addCell(new Cell().add(new Paragraph("2")));
            table.addCell(new Cell().add(new Paragraph("$50")));
            table.addCell(new Cell().add(new Paragraph("$100")));

            table.addCell(new Cell().add(new Paragraph("Product B")));
            table.addCell(new Cell().add(new Paragraph("3")));
            table.addCell(new Cell().add(new Paragraph("$30")));
            table.addCell(new Cell().add(new Paragraph("$90")));

            // Thêm bảng vào tài liệu
            document.add(table);

            // Dòng kẻ phân cách
            document.add(new Paragraph("\n-----------------------------------"));

            // Tổng tiền
            document.add(new Paragraph("\nTotal: $190")
                    .setTextAlignment(TextAlignment.RIGHT));

            // Đóng tài liệu
            document.close();

            System.out.println("Invoice created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

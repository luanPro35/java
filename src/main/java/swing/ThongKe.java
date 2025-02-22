package swing;

public class ThongKe {
    private String ngay;      // Ngày cần thống kê
    private double tongTien;  // Tổng tiền trong ngày

    // Constructor
    public ThongKe(String ngay, double tongTien) {
        this.ngay = ngay;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    // Hiển thị thông tin thống kê
    @Override
    public String toString() {
        return "ThongKe{" +
                "ngay='" + ngay + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}

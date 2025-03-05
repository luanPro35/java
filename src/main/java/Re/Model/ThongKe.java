package Re.Model;

public class ThongKe {
    private String ngay;
    private double tongTien;

    public ThongKe(String ngay, double tongTien) {
        this.ngay = ngay;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    @Override
    public String toString() {
        return "ThongKe{" +
                "ngay='" + ngay + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}
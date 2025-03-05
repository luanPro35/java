package Re.Model;

public class HoaDon {
    private int maHoaDon;
    private int maKhachHang;
    private String maSanPham;
    private int soLuong;
    private double giaSanPham;
    private double thanhTien;
    private String ngayLap;

    public HoaDon(int maHoaDon, int maKhachHang, String maSanPham, int soLuong, double giaSanPham, double thanhTien, String ngayLap) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaSanPham = giaSanPham;
        this.thanhTien = thanhTien;
        this.ngayLap = ngayLap;
    }

    // Getters
    public int getMaHoaDon() { return maHoaDon; }
    public int getMaKhachHang() { return maKhachHang; }
    public String getMaSanPham() { return maSanPham; }
    public int getSoLuong() { return soLuong; }
    public double getGiaSanPham() { return giaSanPham; }
    public double getThanhTien() { return thanhTien; }
    public String getNgayLap() { return ngayLap; }
}
package swing;

public class HoaDon {
    private int maHoaDon;
    private int maKhachHang;
    private String tenKhachHang;
    private int maNhanVien;
    private String tenSanPham;
    private int soLuong;
    private double giaSanPham;
    private double thanhTien;
    private String ngayLap;
    private String maSanPham;

    // Constructor
    public HoaDon(int maHoaDon, int maKhachHang, String tenKhachHang, int maNhanVien,
                  double giaSanPham, double thanhTien, String ngayLap) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.maNhanVien = maNhanVien;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaSanPham = giaSanPham;
        this.thanhTien = soLuong * giaSanPham;
        this.ngayLap = ngayLap;
    }

    // Getters và Setters
    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        this.thanhTien = soLuong * this.giaSanPham;
    }

    public double getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(double giaSanPham) {
        this.giaSanPham = giaSanPham;
        this.thanhTien = this.soLuong * giaSanPham;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
}

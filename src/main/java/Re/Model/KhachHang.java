package Re.Model;

public class KhachHang {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
    }

    // Getters
    public int getMaKhachHang() { return maKhachHang; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getSoDienThoai() { return soDienThoai; }
}
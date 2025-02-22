package swing;

public class DanhGia {
    private int id;
    private String tenNguoiGui;
    private String danhGia;
    private String ngayDang;

    public DanhGia(int id, String tenNguoiGui, String danhGia, String ngayDang) {
        this.id = id;
        this.tenNguoiGui = tenNguoiGui;
        this.danhGia = danhGia;
        this.ngayDang = ngayDang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    @Override
    public String toString() {
        return "DanhGia{" +
                "id=" + id +
                ", tenNguoiGui='" + tenNguoiGui + '\'' +
                ", danhGia='" + danhGia + '\'' +
                ", ngayDang='" + ngayDang + '\'' +
                '}';
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author Duy Toan
 */
public class SanPham {

    String MaSanPham;
    String TenSanPham;
    int GiaSanPham;
    String MaNhaCungCap;
    String Hinh;

    public SanPham(String MaSanPham, String TenSanPham, int GiaSanPham, String MaNhaCungCap, String Hinh) {
        this.MaSanPham = MaSanPham;
        this.TenSanPham = TenSanPham;
        this.GiaSanPham = GiaSanPham;
        this.MaNhaCungCap = MaNhaCungCap;
        this.Hinh = Hinh;
    }

    public SanPham() {
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public int getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(int GiaSanPham) {
        this.GiaSanPham = GiaSanPham;
    }

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String MaNhaCungCap) {
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    

}

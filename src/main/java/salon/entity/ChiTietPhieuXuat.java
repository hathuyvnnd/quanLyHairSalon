/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author hminh
 */
public class ChiTietPhieuXuat {

    int id, MaPhieuXuat;
    String MaSanPham;
    int SoLuongXuat;

    public ChiTietPhieuXuat(int MaPhieuXuat, String MaSanPham, int SoLuongXuat) {
        this.MaPhieuXuat = MaPhieuXuat;
        this.MaSanPham = MaSanPham;
        this.SoLuongXuat = SoLuongXuat;
    }

    public ChiTietPhieuXuat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaPhieuXuat() {
        return MaPhieuXuat;
    }

    public void setMaPhieuXuat(int MaPhieuXuat) {
        this.MaPhieuXuat = MaPhieuXuat;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getSoLuongXuat() {
        return SoLuongXuat;
    }

    public void setSoLuongXuat(int SoLuongXuat) {
        this.SoLuongXuat = SoLuongXuat;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author hminh
 */
public class ChiTietPhieuNhap {
    
    int id, MaPhieuNhap;
    String MaSanPham;
    int SoLuongnhap;

    public ChiTietPhieuNhap(int MaPhieuNhap, String MaSanPham, int SoLuongnhap) {
        this.MaPhieuNhap = MaPhieuNhap;
        this.MaSanPham = MaSanPham;
        this.SoLuongnhap = SoLuongnhap;
    }

    public ChiTietPhieuNhap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(int MaPhieuNhap) {
        this.MaPhieuNhap = MaPhieuNhap;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getSoLuongnhap() {
        return SoLuongnhap;
    }

    public void setSoLuongnhap(int SoLuongnhap) {
        this.SoLuongnhap = SoLuongnhap;
    }
    
    
}

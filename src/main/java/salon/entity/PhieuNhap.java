/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

import java.util.Date;

/**
 *
 * @author hminh
 */
public class PhieuNhap {
    
    int MaPhieuNhap;
    Date NgayNhap;
    String MaNCC, MaCH;

    public PhieuNhap(int MaPhieuNhap, Date NgayNhap, String MaNCC, String MaCH) {
        this.MaPhieuNhap = MaPhieuNhap;
        this.NgayNhap = NgayNhap;
        this.MaNCC = MaNCC;
        this.MaCH = MaCH;
    }

    public PhieuNhap() {
    }

    public int getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(int MaPhieuNhap) {
        this.MaPhieuNhap = MaPhieuNhap;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getMaCH() {
        return MaCH;
    }

    public void setMaCH(String MaCH) {
        this.MaCH = MaCH;
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

import java.util.Date;

/**
 *
 * @author Duy Toan
 */
public class Phieuchi {

    String MaPhieuChi;
    String MaCuaHang;
    String MaChiPhi;
    Date Ngay;
    int ThanhTien;

    public String getMaPhieuChi() {
        return MaPhieuChi;
    }

    public void setMaPhieuChi(String MaPhieuChi) {
        this.MaPhieuChi = MaPhieuChi;
    }

    public String getMaCuaHang() {
        return MaCuaHang;
    }

    public void setMaCuaHang(String MaCuaHang) {
        this.MaCuaHang = MaCuaHang;
    }

    public String getMaChiPhi() {
        return MaChiPhi;
    }

    public void setMaChiPhi(String MaChiPhi) {
        this.MaChiPhi = MaChiPhi;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

}

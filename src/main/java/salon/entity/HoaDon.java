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
public class HoaDon {

    int MaHoaDon;
    String sdtKH;
    Date Ngay;
    String MaNhanVien;
    String MaUuDai;
    String HinhThucThanhToan;

    public HoaDon(int MaHoaDon, String sdtKH, Date Ngay, String MaNhanVien, String MaUuDai, String HinhThucThanhToan) {
        this.MaHoaDon = MaHoaDon;
        this.sdtKH = sdtKH;
        this.Ngay = Ngay;
        this.MaNhanVien = MaNhanVien;
        this.MaUuDai = MaUuDai;
        this.HinhThucThanhToan = HinhThucThanhToan;
    }

    public HoaDon(String sdtKH, Date Ngay, String MaNhanVien, String MaUuDai, String HinhThucThanhToan) {
        this.sdtKH = sdtKH;
        this.Ngay = Ngay;
        this.MaNhanVien = MaNhanVien;
        this.MaUuDai = MaUuDai;
        this.HinhThucThanhToan = HinhThucThanhToan;
    }
    
    

    public HoaDon() {
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getMaUuDai() {
        return MaUuDai;
    }

    public void setMaUuDai(String MaUuDai) {
        this.MaUuDai = MaUuDai;
    }

    public String getHinhThucThanhToan() {
        return HinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String HinhThucThanhToan) {
        this.HinhThucThanhToan = HinhThucThanhToan;
    }

    

}

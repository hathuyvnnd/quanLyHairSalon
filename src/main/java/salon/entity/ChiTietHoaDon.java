/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author hminh
 */
public class ChiTietHoaDon {

    int id, MaHoaDon;
    String MaDichVu;
    int SoLuong;

    public ChiTietHoaDon(int id, int MaHoaDon, String MaDichVu, int SoLuong) {
        this.id = id;
        this.MaHoaDon = MaHoaDon;
        this.MaDichVu = MaDichVu;
        this.SoLuong = SoLuong;
    }

    public ChiTietHoaDon(String MaDichVu, int SoLuong) {
        this.MaDichVu = MaDichVu;
        this.SoLuong = SoLuong;
    }

    public ChiTietHoaDon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaDichVu() {
        return MaDichVu;
    }

    public void setMaDichVu(String MaDichVu) {
        this.MaDichVu = MaDichVu;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

}

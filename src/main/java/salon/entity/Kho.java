/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author hminh
 */
public class Kho {
    
    int id;
    String MaCuaHang, MaSanPham;
    int SoLuong;

    public Kho(String MaCuaHang, String MaSanPham, int SoLuong) {
        this.MaCuaHang = MaCuaHang;
        this.MaSanPham = MaSanPham;
        this.SoLuong = SoLuong;
    }

    public Kho() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaCuaHang() {
        return MaCuaHang;
    }

    public void setMaCuaHang(String MaCuaHang) {
        this.MaCuaHang = MaCuaHang;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }
    
    
    
}

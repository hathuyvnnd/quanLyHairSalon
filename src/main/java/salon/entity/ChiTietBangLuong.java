/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author mrphu
 */
public class ChiTietBangLuong {
    int thang, nam;
    String maNhanVien;
    int ngayCong;

    public ChiTietBangLuong(int thang, int nam, String maNhanVien, int ngayCong) {
        this.thang = thang;
        this.nam = nam;
        this.maNhanVien = maNhanVien;
        this.ngayCong = ngayCong;
    }

    public ChiTietBangLuong() {
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getNgayCong() {
        return ngayCong;
    }

    public void setNgayCong(int ngayCong) {
        this.ngayCong = ngayCong;
    }
    
    
}

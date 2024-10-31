/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

import java.util.Date;

/**
 *
 * @author mrphu
 */
public class LichHen {

    int maLichHen;
    String sodienthoaiKH;
    Date ngayHen;
    String gioHen, maNhanVien, ghiChu;
    Boolean trangThai;

    public LichHen(int maLichHen, String sodienthoaiKH, Date ngayHen, String gioHen, String maNhanVien, String ghiChu, Boolean trangThai ) {
        this.maLichHen = maLichHen;
        this.sodienthoaiKH = sodienthoaiKH;
        this.ngayHen = ngayHen;
        this.gioHen = gioHen;
        this.maNhanVien = maNhanVien;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public LichHen() {
    }

    public int getMaLichHen() {
        return maLichHen;
    }

    public void setMaLichHen(int maLichHen) {
        this.maLichHen = maLichHen;
    }

    public String getSodienthoaiKH() {
        return sodienthoaiKH;
    }

    public void setSodienthoaiKH(String sodienthoaiKH) {
        this.sodienthoaiKH = sodienthoaiKH;
    }

    public Date getNgayHen() {
        return ngayHen;
    }

    public void setNgayHen(Date ngayHen) {
        this.ngayHen = ngayHen;
    }

    public String getGioHen() {
        return gioHen;
    }

    public void setGioHen(String gioHen) {
        this.gioHen = gioHen;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}

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
public class UuDai {

    String MaUuDai;
    String TenUuDai;
    Date NgayBatDau;
    Date NgayKetThuc;
    String NoiDung;
    float GiamGia;

    public UuDai(String MaUuDai, String TenUuDai, Date NgayBatDau, Date NgayKetThuc, String NoiDung, float GiamGia) {
        this.MaUuDai = MaUuDai;
        this.TenUuDai = TenUuDai;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
        this.NoiDung = NoiDung;
        this.GiamGia = GiamGia;
    }

    public UuDai() {
    }

    public String getMaUuDai() {
        return MaUuDai;
    }

    public void setMaUuDai(String MaUuDai) {
        this.MaUuDai = MaUuDai;
    }

    public String getTenUuDai() {
        return TenUuDai;
    }

    public void setTenUuDai(String TenUuDai) {
        this.TenUuDai = TenUuDai;
    }

    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    public float getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(float GiamGia) {
        this.GiamGia = GiamGia;
    }

    
}

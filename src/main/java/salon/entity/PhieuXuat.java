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
public class PhieuXuat {

    int MaPhieuXuat;
    Date NgayXuat;
    String MaCH;

    public PhieuXuat(int MaPhieuXuat, Date NgayXuat, String MaCH) {
        this.MaPhieuXuat = MaPhieuXuat;
        this.NgayXuat = NgayXuat;
        this.MaCH = MaCH;
    }

    public PhieuXuat() {
    }

    public int getMaPhieuXuat() {
        return MaPhieuXuat;
    }

    public void setMaPhieuXuat(int MaPhieuXuat) {
        this.MaPhieuXuat = MaPhieuXuat;
    }

    public Date getNgayXuat() {
        return NgayXuat;
    }

    public void setNgayXuat(Date NgayXuat) {
        this.NgayXuat = NgayXuat;
    }

    public String getMaCH() {
        return MaCH;
    }

    public void setMaCH(String MaCH) {
        this.MaCH = MaCH;
    }

}

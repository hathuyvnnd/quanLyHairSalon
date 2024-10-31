/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author mrphu
 */
public class CuaHang {

    String maCuaHang, tenCuaHang, diaChi, soDienThoaiCH;

    public CuaHang() {
    }

    public CuaHang(String maCuaHang, String tenCuaHang, String diaChi, String soDienThoaiCH) {
        this.maCuaHang = maCuaHang;
        this.tenCuaHang = tenCuaHang;
        this.diaChi = diaChi;
        this.soDienThoaiCH = soDienThoaiCH;
    }

    public String getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(String maCuaHang) {
        this.maCuaHang = maCuaHang;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoaiCH() {
        return soDienThoaiCH;
    }

    public void setSoDienThoaiCH(String soDienThoaiCH) {
        this.soDienThoaiCH = soDienThoaiCH;
    }

    

    @Override
    public String toString() {
        return this.maCuaHang;
    }
}

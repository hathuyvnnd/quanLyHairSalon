/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author Duy Toan
 */
public class KhachHang {

    String HoTen,SoDienThoai;

    public KhachHang(String HoTen, String SoDienThoai) {
        this.HoTen = HoTen;
        this.SoDienThoai = SoDienThoai;
    }

    public KhachHang() {
    }
    
    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.entity;

/**
 *
 * @author Duy Toan
 */
public class ChiPhi {

    String MaChiPhi;
    String TenChiPhi;
    String MoTa;

    public ChiPhi(String MaChiPhi, String TenChiPhi, String MoTa) {
        this.MaChiPhi = MaChiPhi;
        this.TenChiPhi = TenChiPhi;
        this.MoTa = MoTa;
    }

    public ChiPhi() {
    }

    public String getMaChiPhi() {
        return MaChiPhi;
    }

    public void setMaChiPhi(String MaChiPhi) {
        this.MaChiPhi = MaChiPhi;
    }

    public String getTenChiPhi() {
        return TenChiPhi;
    }

    public void setTenChiPhi(String TenChiPhi) {
        this.TenChiPhi = TenChiPhi;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

}

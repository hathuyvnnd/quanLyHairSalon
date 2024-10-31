/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.ui;

import java.util.List;
import salon.Dao.CuaHangDao;
import salon.Dao.NhanVienDao;
import salon.entity.CuaHang;
import salon.entity.NhanVien;

/**
 *
 * @author mrphu
 */

public class test {
    public static void main(String[] args) {
//        NhanVienDao nvDao = new NhanVienDao();
//        List<NhanVien> list = nvDao.selectAll();
//        System.out.println(list);
CuaHangDao chDao = new CuaHangDao();
List<CuaHang> list = chDao.selectAll();
for(CuaHang ch : list){
    Object[] a ={ch.getMaCuaHang().toString(),ch.getTenCuaHang(),ch.getDiaChi(),ch.getSoDienThoaiCH()};
    System.out.println(a);
}
        }
    }


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.Kho;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class KhoDao extends SalonDao<Kho, Integer> {

    String insert_sql = "insert into kho (macuahang, masanpham, soluong) values (?,?,?)";
    String update_sql = "update kho set soluong=soluong+? where masanpham = ? and macuahang =?";
    String delete_sql = "delete from kho where macuahang=? and masanpham=?";
    String select_all_sql = "select * from kho ";
    String selectbyid_sql = "select * from  kho where id=? ";
    String selectbymch_sql = "select * from  kho where macuahang=?";

    @Override
    public void insert(Kho entity) {
        JdbcHelp.update(insert_sql, entity.getMaCuaHang(), entity.getMaSanPham(), entity.getSoLuong());
    }

    @Override
    public void update(Kho entity) {
        JdbcHelp.update(update_sql, entity.getSoLuong(), entity.getMaSanPham(), entity.getMaCuaHang());
    }

    public void delete(Kho entity) {
        JdbcHelp.update(delete_sql, entity.getMaCuaHang(), entity.getMaSanPham());
    }

    @Override
    public List<Kho> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public Kho selectById(Integer id) {
        List<Kho> list = selectBySql(selectbyid_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<Kho> selectByMaCuaHang(String mach){
        return this.selectBySql(selectbymch_sql,mach);
    }

    @Override
    public List<Kho> selectBySql(String sql, Object... args) {
        List<Kho> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                Kho kho = new Kho();
                kho.setId(rs.getInt("id"));
                kho.setMaCuaHang(rs.getString("macuahang"));
                kho.setMaSanPham(rs.getString("masanpham"));
                kho.setSoLuong(rs.getInt("soluong"));
                list.add(kho);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

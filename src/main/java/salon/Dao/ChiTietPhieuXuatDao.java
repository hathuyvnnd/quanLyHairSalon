/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import salon.entity.ChiTietPhieuNhap;
import salon.entity.ChiTietPhieuXuat;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class ChiTietPhieuXuatDao extends SalonDao<ChiTietPhieuXuat, Integer> {

    String insert_sql = "insert into chitietphieuxuat (maphieuxuat, masanpham, soluongxuat) values (?,?,?)";
    String delete_sql = "delete from chitietphieuxuat where id=?";
    String deleteBy_maphieuxuat_sql = "delete from chitietphieuxuat where maphieuxuat=?";
    String select_all_sql = "select * from chitietphieuxuat ";
    String selectby_maphieuxuat_sql = "select * from chitietphieuxuat where maphieuxuat=? ";
    String selectby_id_sql = "select * from chitietphieuxuat where id=? ";

    @Override
    public void insert(ChiTietPhieuXuat entity) {
        JdbcHelp.update(insert_sql, entity.getMaPhieuXuat(), entity.getMaSanPham(), entity.getSoLuongXuat());
    }

    @Override
    public void update(ChiTietPhieuXuat entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(delete_sql, id);
    }

    public void deleteBympx(Integer id) {
        JdbcHelp.update(deleteBy_maphieuxuat_sql, id);
    }

    @Override
    public List<ChiTietPhieuXuat> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    public List<ChiTietPhieuXuat> selectByMaphieuxuat(Integer maphieuxuat) {
        return this.selectBySql(selectby_maphieuxuat_sql, maphieuxuat);
    }

    @Override
    public ChiTietPhieuXuat selectById(Integer id) {
    	List<ChiTietPhieuXuat> result = selectBySql(selectby_id_sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ChiTietPhieuXuat> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuXuat> list = new ArrayList<ChiTietPhieuXuat>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuXuat entity = new ChiTietPhieuXuat();
                entity.setId(rs.getInt("id"));
                entity.setMaPhieuXuat(rs.getInt("maphieuxuat"));
                entity.setMaSanPham(rs.getString("masanpham"));
                entity.setSoLuongXuat(rs.getInt("soluongxuat"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

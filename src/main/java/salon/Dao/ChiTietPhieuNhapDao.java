/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import salon.entity.ChiPhi;
import salon.entity.ChiTietPhieuNhap;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class ChiTietPhieuNhapDao extends SalonDao<ChiTietPhieuNhap, Integer> {

    String insert_sql = "insert into chitietphieunhap (maphieunhap, masanpham, soluongnhap) values (?,?,?)";
    String delete_sql = "delete from chitietphieunhap where id=?";
    String deleteBy_maphieunhap_sql = "delete from chitietphieunhap where maphieunhap=?";
    String select_all_sql = "select * from chitietphieunhap";
    String selectby_maphieunhap_sql = "select * from chitietphieunhap where maphieunhap=? ";
    String selectby_id_sql = "select * from chitietphieunhap where id=? ";

    @Override
    public void insert(ChiTietPhieuNhap entity) {
        JdbcHelp.update(insert_sql, entity.getMaPhieuNhap(), entity.getMaSanPham(), entity.getSoLuongnhap());
    }

    @Override
    public void update(ChiTietPhieuNhap entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(delete_sql, id);
    }

    public void deleteBympn(Integer id) {
        JdbcHelp.update(deleteBy_maphieunhap_sql, id);
    }

    @Override
    public List<ChiTietPhieuNhap> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    public List<ChiTietPhieuNhap> selectByMaphieunhap(Integer maphieunhap) {
        return this.selectBySql(selectby_maphieunhap_sql, maphieunhap);
    }

    @Override
    public ChiTietPhieuNhap selectById(Integer id) {
    	List<ChiTietPhieuNhap> result = selectBySql(selectby_id_sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ChiTietPhieuNhap> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuNhap> list = new ArrayList<ChiTietPhieuNhap>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuNhap entity = new ChiTietPhieuNhap();
                entity.setId(rs.getInt("id"));
                entity.setMaPhieuNhap(rs.getInt("maphieunhap"));
                entity.setMaSanPham(rs.getString("masanpham"));
                entity.setSoLuongnhap(rs.getInt("soluongnhap"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

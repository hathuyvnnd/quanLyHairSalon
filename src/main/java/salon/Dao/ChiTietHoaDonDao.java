/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import salon.entity.ChiPhi;
import salon.entity.ChiTietHoaDon;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class ChiTietHoaDonDao extends SalonDao<ChiTietHoaDon, Integer>{

    String insert_sql = "insert into chitiethoadon (mahoadon, madichvu, soluong) values (?,?,?)";
    String delete_sql = "delete from chitiethoadon where mahoadon=?";
    String update_sql = "update chitiethoadon set madichvu=?, soluong=? where id=?";
    String select_all_sql = "select * from chitiethoadon";
    String select_byId = "select * from chitiethoadon where id = ?";
    String selectby_mahoadon_sql = "select * from chitiethoadon where mahoadon=? ";
    
    @Override
    public void insert(ChiTietHoaDon entity) {
        JdbcHelp.update(insert_sql, entity.getMaHoaDon(), entity.getMaDichVu(), entity.getSoLuong());
    }

    @Override
    public void update(ChiTietHoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
    	JdbcHelp.update(delete_sql,id);
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
    	return this.selectBySql(select_all_sql);
    }

    @Override
    public ChiTietHoaDon selectById(Integer id) {
    	List<ChiTietHoaDon> result = selectBySql(select_byId, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ChiTietHoaDon> selectBySql(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<ChiTietHoaDon>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                ChiTietHoaDon entity = new ChiTietHoaDon();
                entity.setId(rs.getInt("id"));
                entity.setMaHoaDon(rs.getInt("mahoadon"));
                entity.setMaDichVu(rs.getString("madichvu"));
                entity.setSoLuong(rs.getInt("soluong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}

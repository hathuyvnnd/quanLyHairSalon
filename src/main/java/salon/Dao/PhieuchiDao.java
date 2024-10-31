/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.Phieuchi;
import salon.entity.ChiPhi;
import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class PhieuchiDao extends SalonDao<Phieuchi, Object> {

    String insert_sql = "insert into phieuchi (maphieuchi,macuahang,machiphi,ngay,thanhtien) values(?,?,?,?,?)";
    String select_all_sql = "select * from phieuchi ";

    @Override
    public void insert(Phieuchi entity) {
        JdbcHelp.update(insert_sql, entity.getMaPhieuChi(), entity.getMaCuaHang(), entity.getMaChiPhi(), entity.getNgay(), entity.getThanhTien());

    }

    @Override
    public void update(Phieuchi entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Phieuchi> selectAll() {
        return this.selectBySql(select_all_sql);

    }

    @Override
    public Phieuchi selectById(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Phieuchi> selectBySql(String sql, Object... args) {
        List<Phieuchi> list = new ArrayList<Phieuchi>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                Phieuchi entity = new Phieuchi();
                entity.setMaPhieuChi(rs.getString("maphieuchi"));
                entity.setMaCuaHang(rs.getString("macuahang"));
                entity.setMaChiPhi(rs.getString("machiphi"));
                entity.setNgay(rs.getDate("ngay"));
                entity.setThanhTien(rs.getInt("thanhtien"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    public List<Phieuchi> selectbycuahang(String mach) {
        String sql = "select * from cuahang where mach=?";
        return this.selectBySql(sql, mach);
    }

}

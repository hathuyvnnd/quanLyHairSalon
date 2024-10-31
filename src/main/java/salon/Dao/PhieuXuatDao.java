/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.PhieuXuat;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class PhieuXuatDao extends SalonDao<PhieuXuat, Integer> {

    String insert_sql = "insert into phieuxuathang (ngayxuat,macuahang) values (?,?)";
    String update_sql = "update phieuxuathang set ngaynxuat =?,macuahang=? where maphieuxuat = ? ";
    String delete_sql = "delete from phieuxuathang where maphieuxuat = ?";
    String select_all_sql = "select * from phieuxuathang ";
    String selectbyid_sql = "select * from phieuxuathang where maphieuxuat = ?";

    @Override
    public void insert(PhieuXuat entity) {
        try {
            JdbcHelp.update(insert_sql, entity.getNgayXuat(), entity.getMaCH());
        } catch (Exception e) {
        }
    }

    @Override
    public void update(PhieuXuat entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(delete_sql, id);
    }

    @Override
    public List<PhieuXuat> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public PhieuXuat selectById(Integer id) {
        List<PhieuXuat> list = selectBySql(selectbyid_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuXuat> selectBySql(String sql, Object... args) {
        List<PhieuXuat> list = new ArrayList<PhieuXuat>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                PhieuXuat entity = new PhieuXuat();
                entity.setMaPhieuXuat(rs.getInt("maphieuxuat"));
                entity.setNgayXuat(rs.getDate("ngayxuat"));
                entity.setMaCH(rs.getString("macuahang"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

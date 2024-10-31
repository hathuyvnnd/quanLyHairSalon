/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.PhieuNhap;
import salon.tienich.JdbcHelp;

/**
 *
 * @author hminh
 */
public class PhieuNhapDao extends SalonDao<PhieuNhap, Integer> {

    String insert_sql = "insert into phieunhaphang (ngaynhap,manhacungcap,macuahang) values (?,?,?)";
    String update_sql = "update phieunhaphang set ngaynhap =?,manhacungcap=?,macuahang=? where maphieunhap = ? ";
    String delete_sql = "delete from phieunhaphang where maphieunhap = ?";
    String select_all_sql = "select * from phieunhaphang ";
    String selectbyid_sql = "select * from phieunhaphang where maphieunhap = ?";

    @Override
    public void insert(PhieuNhap entity) {
        try {
            JdbcHelp.update(insert_sql, entity.getNgayNhap(), entity.getMaNCC(), entity.getMaCH());
        } catch (Exception e) {
        }
    }

    @Override
    public void update(PhieuNhap entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(delete_sql, id);
    }

    @Override
    public List<PhieuNhap> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public PhieuNhap selectById(Integer id) {
        List<PhieuNhap> list = selectBySql(selectbyid_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuNhap> selectBySql(String sql, Object... args) {
        List<PhieuNhap> list = new ArrayList<PhieuNhap>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                PhieuNhap entity = new PhieuNhap();
                entity.setMaPhieuNhap(rs.getInt("maphieunhap"));
                entity.setNgayNhap(rs.getDate("ngaynhap"));
                entity.setMaNCC(rs.getString("manhacungcap"));
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

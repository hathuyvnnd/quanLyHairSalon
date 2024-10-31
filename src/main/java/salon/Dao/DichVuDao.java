/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.DichVu;

import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class DichVuDao extends SalonDao<DichVu, String> {

    String insert_sql = "insert into dichvu (madichvu,tendichvu,giadichvu,mota,trangthai) values(?,?,?,?,?)";
    String update_sql = "update dichvu set tendichvu =?,giadichvu=? , mota=?,trangthai=? where madichvu = ? ";
    String delete_sql = "delete from loaichiphi where machiphi=?";
    String select_all_sql = "select * from DICHVU ";
    String selectbyid_sql = "select * from  dichvu where madichvu=? ";

    @Override
    public void insert(DichVu entity) {
        JdbcHelp.update(insert_sql, entity.getMaDichVu(), entity.getTenDichVu(), entity.getGiaDichVu(), entity.getMoTa(), entity.isTrangThai());
    }

    @Override
    public void update(DichVu entity) {
        JdbcHelp.update(update_sql, entity.getTenDichVu(), entity.getGiaDichVu(), entity.getMoTa(), entity.isTrangThai(), entity.getMaDichVu());
    }

    @Override
    public List<DichVu> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public List<DichVu> selectBySql(String sql, Object... args) {
        List<DichVu> list = new ArrayList<DichVu>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                DichVu entity = new DichVu();
                entity.setMaDichVu(rs.getString("madichvu"));
                entity.setTenDichVu(rs.getString("tendichvu"));
                entity.setGiaDichVu(rs.getInt("giadichvu"));
                entity.setMoTa(rs.getString("mota"));
                entity.setTrangThai(rs.getBoolean("trangthai"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public List<DichVu> selectbykeyword(String madichvu) {
        String sql = "select * from  dichvu where madichvu like ? or tendichvu like ?";
        return this.selectBySql(sql, "%" + madichvu + "%", "%" + madichvu + "%");
    }

    public int getGia(String madichvu) {
        DichVu dv = this.selectById(madichvu);
        return dv.getGiaDichVu();
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DichVu selectById(String id) {
        List<DichVu> list = selectBySql(selectbyid_sql,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}

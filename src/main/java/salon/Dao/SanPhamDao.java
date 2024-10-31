/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.SanPham;
import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class SanPhamDao extends SalonDao<SanPham, String> {

    String insert_sql = "insert into sanpham (masanpham,tensanpham,giasanpham,manhacungcap,hinh) values(?,?,?,?,?)";
    String update_sql = "update sanpham set tensanpham =?,giasanpham=?,manhacungcap=?, hinh=?  where masanpham = ? ";
    String delete_sql = "delete from sanpham where masanpham=?";
    String select_all_sql = "select * from SANPHAM ";
    String selectbyid_sql = "select * from  sanpham where masanpham=? ";
    String selectbyncc_sql = "select * from  sanpham where manhacungcap=?";

    @Override
    public void insert(SanPham entity) {
        JdbcHelp.update(insert_sql, entity.getMaSanPham(), entity.getTenSanPham(), entity.getGiaSanPham(), entity.getMaNhaCungCap(), entity.getHinh());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelp.update(update_sql, entity.getTenSanPham(), entity.getGiaSanPham(), entity.getMaNhaCungCap(), entity.getHinh(),entity.getMaSanPham());

    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySql(selectbyid_sql, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<SanPham>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSanPham(rs.getString("masanpham"));
                entity.setTenSanPham(rs.getString("tensanpham"));
                entity.setGiaSanPham(rs.getInt("giasanpham"));
                entity.setMaNhaCungCap(rs.getString("manhacungcap"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public List<SanPham> selectbykeyword(String keyword) {
        String sql = "select * from  sanpham where tensanpham like ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    public List<SanPham> selecbyNhaCungCap(String mancc){
        return this.selectBySql(selectbyncc_sql, mancc);
    }
    
}

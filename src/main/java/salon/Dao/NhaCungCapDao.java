/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.NhaCungCap;
import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class NhaCungCapDao extends SalonDao<NhaCungCap, Object> {

    String insert_sql = "insert into nhacungcap (manhacungcap,tennhacungcap,sodienthoai,diachi,trangthai) values (?,?,?,?,?)";
    String update_sql = "update nhacungcap set tennhacungcap =?,sodienthoai=?,diachi=?,trangthai=? where manhacungcap = ? ";
    String delete_sql = "delete from nhacungcap where manhacungcap = ?";
    String select_all_sql = "select * from nhacungcap ";
    String selectbyid_sql = "select * from nhacungcap where manhacungcap = ?";

    @Override
    public void insert(NhaCungCap entity) {
        try {
            JdbcHelp.update(insert_sql, entity.getMaNhaCungCap(), entity.getTenNhaCungCap(), entity.getSoDienThoai(), entity.getDiaChi(), entity.isTrangThai() );
        } catch (Exception e) {
        }
    }

    @Override
    public void update(NhaCungCap entity) {
        JdbcHelp.update(update_sql, entity.getTenNhaCungCap(),  entity.getSoDienThoai(), entity.getDiaChi(), entity.isTrangThai(), entity.getMaNhaCungCap());
    }

    @Override
    public void delete(Object id) {
        JdbcHelp.update(delete_sql, id);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return this.selectBySql(select_all_sql);

    }

    @Override
    public NhaCungCap selectById(Object id) {
        List<NhaCungCap> list = selectBySql(selectbyid_sql,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    } 

    @Override
    public List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<NhaCungCap>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNhaCungCap(rs.getString("manhacungcap"));
                entity.setTenNhaCungCap(rs.getString("tennhacungcap"));
                entity.setSoDienThoai(rs.getString("sodienthoai"));
                entity.setDiaChi(rs.getString("diachi"));
                entity.setTrangThai(rs.getBoolean("trangthai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

}

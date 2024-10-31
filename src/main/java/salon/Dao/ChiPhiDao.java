/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.ChiPhi;
import salon.entity.KhachHang;
import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class ChiPhiDao extends SalonDao<ChiPhi, Object> {

    String insert_sql = "insert into loaichiphi (machiphi,tenchiphi,mota) values(?,?,?)";
    String update_sql = "update loaichiphi set tenchiphi =?,mota=?  where machiphi = ? ";
    String delete_sql = "delete from loaichiphi where machiphi=?";
    String select_all_sql = "select * from LOAICHIPHI ";
    String selectbyid_sql = "select * from  LOAICHIPHI where machiphi=? ";

    @Override
    public void insert(ChiPhi entity) {
        JdbcHelp.update(insert_sql, entity.getMaChiPhi(), entity.getTenChiPhi(), entity.getMoTa());

    }

    @Override
    public void update(ChiPhi entity) {
        JdbcHelp.update(update_sql, entity.getTenChiPhi(), entity.getMoTa(), entity.getMaChiPhi());

    }

    @Override
    public void delete(Object id) {
    	JdbcHelp.update(delete_sql, id);
    }

    @Override
    public List<ChiPhi> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public ChiPhi selectById(Object id) {
    	List<ChiPhi> result = selectBySql(selectbyid_sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ChiPhi> selectBySql(String sql, Object... args) {
        List<ChiPhi> list = new ArrayList<ChiPhi>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                ChiPhi entity = new ChiPhi();
                entity.setMaChiPhi(rs.getString("machiphi"));
                entity.setTenChiPhi(rs.getString("tenchiphi"));
                entity.setMoTa(rs.getString("mota"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

}

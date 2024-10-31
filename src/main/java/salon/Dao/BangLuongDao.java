/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.BangLuong;
import salon.tienich.JdbcHelp;

/**
 *
 * @author mrphu
 */
public class BangLuongDao extends SalonDao<BangLuong, Integer> {
    final String SELECT_ALL = "Select * from BANGLUONG";
    final String SELECT_INSERT ="Insert into BANGLUONG(Thang,Nam) values (?,?)";
    final String SELECT_BY_ID = "Select * from BANGLUONG where Thang =? and Nam =?";

    @Override
    public void insert(BangLuong entity) {
        JdbcHelp.update(SELECT_INSERT, entity.getThang(),entity.getNam());
    }

    @Override
    public void update(BangLuong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BangLuong> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BangLuong selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BangLuong> selectBySql(String sql, Object... args) {
        List<BangLuong> list = new ArrayList<>();
        try{
            ResultSet rs = JdbcHelp.query(sql, args);
            while(rs.next()){
                BangLuong bl = new BangLuong();
                bl.setThang(rs.getInt("Thang"));
                bl.setThang(rs.getInt("Nam"));
                list.add(bl);
            }
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public BangLuong selectByThangNam(int thang, int nam){
        List<BangLuong> list = selectBySql(SELECT_BY_ID,  thang, nam);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
   
    
}
